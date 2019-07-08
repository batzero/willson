package com.example.appjam_willson.HelperSignUpActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appjam_willson.R;

public class HelperSignUpActivityStart extends AppCompatActivity {


    int REQUEST_CODE;

    Context context;
    View view;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_sign_up_start);

        context = this;
        REQUEST_CODE = ((HelperSignUpActivityStart) context).getTaskId();


        view = (View)findViewById(R.id.signupstart);
        back = (ImageView)findViewById(R.id.back_btn);
        back.setVisibility(View.INVISIBLE);


        Button nextbtn = (Button)findViewById(R.id.h_su_start_btn); //버튼이름 변경

        nextbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelperSignUpActivityStart.this, HelperSignUpActivity0.class);
                startActivityForResult(intent,REQUEST_CODE);


            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:
                    setResult(RESULT_OK,data);
                    finish();

                case RESULT_CANCELED:
                    finish();
            }
        }
    }
}

