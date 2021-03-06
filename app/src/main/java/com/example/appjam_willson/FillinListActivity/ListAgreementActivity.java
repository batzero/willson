package com.example.appjam_willson.FillinListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appjam_willson.ApplicationField.ApplicationFields;
import com.example.appjam_willson.PopUp.OneTextTwoButton_CustomDialog;
import com.example.appjam_willson.R;

import java.sql.Timestamp;

public class ListAgreementActivity extends AppCompatActivity {

    int REQUEST_CODE;

    private OneTextTwoButton_CustomDialog dialog;
    LinearLayout agree_cancelbtn;
    LinearLayout agree_backbtn;
    Button submit_btn;
    Context context;

    String resName;
    String packName;
    int resid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

        context = this;

        REQUEST_CODE = ((ListAgreementActivity) context).getTaskId();

        resName = "@drawable/request_couldnt_find";
        packName = this.getPackageName();
        resid = getResources().getIdentifier(resName, "drawable", packName);


        agree_cancelbtn = findViewById(R.id.toolbar_list_btn_cancel);
        agree_cancelbtn.setOnClickListener(new agree_cancelbtn_listener());

        agree_backbtn = findViewById(R.id.toolbar_list_btn_backbtn);
        agree_backbtn.setOnClickListener(new agree_backbtn_listener());

        submit_btn = findViewById(R.id.submit);
        submit_btn.setOnClickListener((new submitbtn_listener()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            if(requestCode == REQUEST_CODE)
                this.finish();
        }
    }

    public void btn_check(View view) {
        CheckBox checkBox1 = findViewById(R.id.check1);
        CheckBox checkBox2 = findViewById(R.id.check2);
        Button submit = findViewById(R.id.submit);

        if(checkBox1.isChecked() && checkBox2.isChecked()){
            submit.setEnabled(true);
        } else{
            submit.setEnabled(false);
        }
    }

    class agree_cancelbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Dialog();
        }
    }

    class agree_backbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("result", "BACK");
            setResult(REQUEST_CODE, intent);
            finish();
        }
    }

    class submitbtn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            ApplicationFields.timerStart = timestamp.getTime();
            ApplicationFields.matchingStack = true;
            ApplicationFields.timerSwitch = true;

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void Dialog() {
        dialog = new OneTextTwoButton_CustomDialog(ListAgreementActivity.this, resid,
                "이제 거의 다왔어요!\n그래도 그만 작성하시겠어요?", "계속 작성하기", "그만하기", keepListener, exitListener);

        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    private View.OnClickListener keepListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    };

    private View.OnClickListener exitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    };

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("result", "BACK");
        setResult(REQUEST_CODE, intent);
        finish();
    }

}
