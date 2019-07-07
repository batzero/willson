package com.example.appjam_willson.HelperSignUpActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appjam_willson.HelperProfileEdit.HelperProfileEditActivityC1;
import com.example.appjam_willson.HelperProfileEdit.HelperProfileEditActivityC2;
import com.example.appjam_willson.R;
// public class HelperSignUpActivity1 extends AppCompatActivity implements View.OnClickListener {


public class HelperSignUpActivity1 extends AppCompatActivity  {
//다음버튼에 액티비티 2 와 연결 해야함

    View view;
    ImageView btn;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_sign_up1);
        Button button1 = (Button) findViewById(R.id.helperSU_btn_love1) ;
        Button button2 = (Button) findViewById(R.id.helperSU_btn_love2) ;
        Button button3 = (Button) findViewById(R.id.helperSU_btn_love3) ;
        Button button4 = (Button) findViewById(R.id.helperSU_btn_love4) ;
        Button nextbtn = (Button)findViewById(R.id.HelperSU_btn_next);

        view = (View)findViewById(R.id.activity_list1_daily_toolbar);
        btn =(ImageView)findViewById(R.id.cancel_btn);
        btn.setVisibility(View.INVISIBLE);

        ImageView btn_back;


        btn_back = (ImageView) findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new HelperSignUpActivity1.list1_love_backbtn_listener());




        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextbtn.setEnabled(true);
                button1.setBackgroundResource(R.drawable.helpersignupbackground);
                button1.setTextColor(getColor(R.color.white));
                button2.setTextColor(getColor(R.color.lightPurple));
                button3.setTextColor(getColor(R.color.lightPurple));
                button4.setTextColor(getColor(R.color.lightPurple));
                button2.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button3.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button4.setBackgroundResource(R.drawable.helpersignup_nonchecked);


            }



        });
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextbtn.setEnabled(true);
                button2.setBackgroundResource(R.drawable.helpersignupbackground);
                button2.setTextColor(getColor(R.color.white));
                button1.setTextColor(getColor(R.color.lightPurple));
                button3.setTextColor(getColor(R.color.lightPurple));
                button4.setTextColor(getColor(R.color.lightPurple));
                button1.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button3.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button4.setBackgroundResource(R.drawable.helpersignup_nonchecked);






            }
        });

        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextbtn.setEnabled(true);
                button3.setBackgroundResource(R.drawable.helpersignupbackground);
                button3.setTextColor(getColor(R.color.white));
                button2.setTextColor(getColor(R.color.lightPurple));
                button1.setTextColor(getColor(R.color.lightPurple));
                button4.setTextColor(getColor(R.color.lightPurple));
                button1.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button2.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button4.setBackgroundResource(R.drawable.helpersignup_nonchecked);




            }
        });

        button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextbtn.setEnabled(true);
                button4.setBackgroundResource(R.drawable.helpersignupbackground);
                button4.setTextColor(getColor(R.color.white));
                button2.setTextColor(getColor(R.color.lightPurple));
                button3.setTextColor(getColor(R.color.lightPurple));
                button1.setTextColor(getColor(R.color.lightPurple));
                button1.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button2.setBackgroundResource(R.drawable.helpersignup_nonchecked);
                button3.setBackgroundResource(R.drawable.helpersignup_nonchecked);


            }
        });

        nextbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProfileEdit = new Intent(HelperSignUpActivity1.this, HelperSignUpActivity2.class);
                startActivity(intentProfileEdit);


            }


        });












    }

    class list1_love_backbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }








    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            //이곳에 버튼 클릭시 일어날 일을 적습니다.
        }
    };
};






