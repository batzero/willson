package com.example.appjam_willson.MainActivities;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appjam_willson.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout button1=findViewById(R.id.layout_home);
        LinearLayout button2=findViewById(R.id.layout_request);
        LinearLayout button3=findViewById(R.id.layout_chat);
        LinearLayout button4=findViewById(R.id.layout_mypage);

        final ImageView image_home=findViewById(R.id.Image_home);
        final ImageView image_request=findViewById(R.id.Image_request);
        final ImageView image_chat=findViewById(R.id.Image_chat);
        final ImageView image_mypage=findViewById(R.id.Image_mypage);

        final TextView text_home=findViewById(R.id.text_home);
        final TextView text_request=findViewById(R.id.text_request);
        final TextView text_chat=findViewById(R.id.text_chat);
        final TextView text_mypage=findViewById(R.id.text_mypage);

        //MainFragment1을 자동으로 띄워줌
        startMainView();
        changeImage(image_home,image_chat,image_mypage,image_request);
        changeTextColor(text_home,text_request,text_chat,text_mypage);

        // passPushTokenToServer();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(image_home,image_chat,image_mypage,image_request);
                changeTextColor(text_home,text_request,text_chat,text_mypage);
                MainFragment fragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(image_request,image_home,image_mypage,image_chat);
                changeTextColor(text_request,text_home,text_chat,text_mypage);
                checkMatch();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(image_chat,image_home,image_mypage,image_request);
                changeTextColor(text_chat,text_home,text_request,text_mypage);

                MainFragment3 fragment = new MainFragment3();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(image_mypage,image_home,image_chat,image_request);
                changeTextColor(text_mypage,text_home,text_chat,text_request);
                MainFragment4 fragment = new MainFragment4();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();

            }
        });
    }
    void passPushTokenToServer(){
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w( "getInstanceId failed", task.getException());
                    return;
                }
                String token = task.getResult().getToken();
                Map<String,Object> map = new HashMap<>();
                map.put("pushToken",token);
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);
            }
        });
    }

    private void changeImage(ImageView first,ImageView second,ImageView third,ImageView fourth) {
        first.setSelected(true);
        second.setSelected(false);
        third.setSelected(false);
        fourth.setSelected(false);
    }

    private void changeTextColor(TextView first,TextView second, TextView third, TextView fourth) {
        first.setTextColor(Color.parseColor("#2f2f2f"));
        second.setTextColor(Color.parseColor("#9e9e9e"));
        third.setTextColor(Color.parseColor("#9e9e9e"));
        fourth.setTextColor(Color.parseColor("#9e9e9e"));
    }



    private void startMainView(){

        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
    }
    public void checkMatch(){
        FirebaseDatabase.getInstance().getReference().child("testUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                        if(dataSnapshot.getValue() != null){
                            MainFragment2 fragment = new MainFragment2();
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
                        }
                        else{
                            MainFragment2_null fragment = new MainFragment2_null();
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
                        }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
