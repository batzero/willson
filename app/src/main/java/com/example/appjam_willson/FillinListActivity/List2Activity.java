package com.example.appjam_willson.FillinListActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appjam_willson.PopUp.OneTextTwoButton_CustomDialog;
import com.example.appjam_willson.R;

public class List2Activity extends AppCompatActivity {

    int REQUEST_CODE;

    public int check_num = 0;
    LinearLayout list2_cancelbtn;
    LinearLayout list2_backbtn;
    Button list2_nextbtn;
    Context context;

    private OneTextTwoButton_CustomDialog dialog;

    String resName;
    String packName;
    int resid;

    Bundle bundle = new Bundle();

    int[] strings = new int[3];

    Typeface typebold;
    Typeface typereg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        context = this;

        REQUEST_CODE = ((List2Activity) context).getTaskId();

        typebold = getResources().getFont(R.font.nanum_square_b);
        typereg = getResources().getFont(R.font.nanum_square_r);

        resName = "@drawable/request_couldnt_find";
        packName = this.getPackageName();
        resid = getResources().getIdentifier(resName, "drawable", packName);

        list2_cancelbtn = findViewById(R.id.toolbar_list_btn_cancel);
        list2_cancelbtn.setOnClickListener(new list2_cancelbtn_listener());

        list2_backbtn = findViewById(R.id.toolbar_list_btn_backbtn);
        list2_backbtn.setOnClickListener(new list2_backbtn_listener());

        list2_nextbtn = findViewById(R.id.submit);
        list2_nextbtn.setOnClickListener(new list2_nextbtn_listener());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:

                    bundle = data.getExtras();
                    bundle.putIntArray("feeling",strings);
                    data.putExtras(bundle);
                    setResult(RESULT_OK,data);
                    finish();

                case RESULT_CANCELED:
                    finish();
            }
        }
    }


    public void char_check(View view){
        Button nextbtn = findViewById(R.id.submit);
        CheckBox checkBox = (CheckBox)view;

        if (check_num < 3) {
            if (!checkBox.isChecked()) {
                checkBox.setChecked(false);
                checkBox.setTypeface(typereg);
                check_num -= 1;
                if(check_num<=0) check_num=0;

                for(int i = 0 ; i<3 ; i++) {
                    if (Integer.parseInt(checkBox.getTag().toString())== strings[i]) {
                        strings[i] = 0;
                    }
                }

            } else {
                checkBox.setChecked(true);
                checkBox.setTypeface(typebold);
                check_num += 1;
                if(check_num>3) check_num =3;

                for(int i = 0; i<3; i++){
                    if(strings[i]== 0) {
                        strings[i] = Integer.parseInt(checkBox.getTag().toString());
                        break;
                    }
                }

            }
        } else {
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                Toast.makeText(getApplicationContext(), "느낀 감정은 최대 세 개까지 고를 수 있습니다.", Toast.LENGTH_SHORT).show();

            }
            else{
                checkBox.setChecked(false);
                checkBox.setTypeface(typereg);
                check_num -= 1;
                for(int i = 0 ; i<3 ; i++) {
                    if (Integer.parseInt(checkBox.getTag().toString())== strings[i]) {
                        strings[i] = 0;
                    }
                }

            }
        }

        if(check_num == 3) nextbtn.setEnabled(true);
        else nextbtn.setEnabled(false);
    }

    class list2_cancelbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Dialog();
        }
    }

    class list2_backbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("result", "BACK");
            setResult(REQUEST_CODE, intent);
            finish();
        }
    }

    class list2_nextbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {


            Intent intent = new Intent(context, List3Activity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public void Dialog() {
        dialog = new OneTextTwoButton_CustomDialog(List2Activity.this, resid,
                "벌써 20%나 진행했어요!\n그래도 그만 작성하시겠어요?", "계속 작성하기", "그만하기", keepListener, exitListener);

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
