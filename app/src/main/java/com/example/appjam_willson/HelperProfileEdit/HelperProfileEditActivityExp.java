package com.example.appjam_willson.HelperProfileEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appjam_willson.FillinListActivity.List4Activity;
import com.example.appjam_willson.FillinListActivity.List5Activity;
import com.example.appjam_willson.PopUp.OneTextTwoButton_CustomDialog;
import com.example.appjam_willson.R;



////다음버튼 위로 올라오는 문제




public class HelperProfileEditActivityExp extends AppCompatActivity {
    int REQUEST_CODE;

    EditText editTextSMS;
    TextView textViewCount;

    TextView text;
    ImageView btn;

    LinearLayout cancelbtn;

    private OneTextTwoButton_CustomDialog dialog;
    LinearLayout list4_cancelbtn;
    LinearLayout list4_backbtn;
    Button list4_nextbtn;

    Context context;


    String resName;
    String packName;
    int resid;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_profile_edit_exp);

        context = this;
        btn = (ImageView)findViewById(R.id.back_btn);
        btn.setVisibility(View.INVISIBLE);
        text = (TextView)findViewById(R.id.toolbar_text);
        text.setText("프로필 수정");

       // REQUEST_CODE = ((com.example.appjam_willson.FillinListActivity.List4Activity) context).getTaskId();

        resName = "@drawable/request_couldnt_find";
        packName = this.getPackageName();
        resid = getResources().getIdentifier(resName, "drawable", packName);

        ImageView btn_back;
        btn_back = (ImageView) findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new HelperProfileEditActivityExp.list1_love_backbtn_listener());

        cancelbtn = findViewById(R.id.toolbar_list_btn_cancel);
        cancelbtn.setOnClickListener(new HelperProfileEditActivityExp.cancelbtn_listener());

        list4_nextbtn = (Button) findViewById(R.id.helper_edit_exp_nextbtn);
        list4_nextbtn.setOnClickListener(new helper_edit_exp_nextbtn_listener());


        textViewCount = (TextView) findViewById(R.id.textViewCount);
        editTextSMS = (EditText) findViewById(R.id.list4_edittext);

        editTextSMS.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textViewCount.setText(Integer.toString(s.toString().length()));

                if (s.length() == 0) {
                    list4_nextbtn.setEnabled(false);
                } else list4_nextbtn.setEnabled(true);

            }


            @Override

            public void afterTextChanged(Editable s) {

            }

        });

    }



    class helper_edit_exp_nextbtn_listener  implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentProfileEdit = new Intent(HelperProfileEditActivityExp.this, HelperProfileEditActivityStart.class);
            startActivity(intentProfileEdit);

        }
    }

    class list1_love_backbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    class cancelbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }


}




