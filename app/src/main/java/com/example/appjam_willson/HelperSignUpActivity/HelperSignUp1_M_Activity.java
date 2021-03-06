package com.example.appjam_willson.HelperSignUpActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appjam_willson.ApplicationField.ApplicationFields;
import com.example.appjam_willson.NetworkService.RetrofitService;
import com.example.appjam_willson.PopUp.OneTextTwoButton_CustomDialog;
import com.example.appjam_willson.R;
import com.example.appjam_willson.model.WorryCategoryListAddModel;
import com.example.appjam_willson.model.WorryCategoryListAddResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperSignUp1_M_Activity extends AppCompatActivity implements OnClickListener{

    int REQUEST_CODE;

    RadioGroup list1_mentality_radioGroup1;
    RadioGroup list1_mentality_radioGroup2;
    RadioGroup list1_mentality_radioGroup3;

    Button list1_mentality_nextbtn;
    LinearLayout mentality_custom_text;
    EditText mentality_custom_edit_text;
    LinearLayout mentality_usercustom_layout;

    LinearLayout list1_mentality_backbtn;
    LinearLayout list1_mentality_cancelbtn;

    LinearLayout background;

    private OneTextTwoButton_CustomDialog dialog;

    Context context;

    String resName;
    String packName;
    int resid;

    RadioButton selfesteem;
    RadioButton unrest;
    RadioButton depressed;
    RadioButton torpor;
    RadioButton ego;
    RadioButton exist;

    Typeface typebold;
    Typeface typereg;

    int category_listId;

    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_mentality);

        context = this;

        REQUEST_CODE = ((HelperSignUp1_M_Activity) context).getTaskId();

        typebold = getResources().getFont(R.font.nanum_square_b);
        typereg = getResources().getFont(R.font.nanum_square_r);

        selfesteem = findViewById(R.id.list1_mentality_btn_hurt);
        unrest = findViewById(R.id.list1_mentality_btn_unrest);
        depressed = findViewById(R.id.list1_mentality_btn_depressed);
        torpor = findViewById(R.id.list1_mentality_btn_torpor);
        ego = findViewById(R.id.list1_mentality_btn_ego);
        exist = findViewById(R.id.list1_mentality_btn_exist);

        selfesteem.setTypeface(typereg);
        unrest.setTypeface(typereg);
        depressed.setTypeface(typereg);
        torpor.setTypeface(typereg);
        ego.setTypeface(typereg);
        exist.setTypeface(typereg);

        resName = "@drawable/request_couldnt_find";
        packName = this.getPackageName();
        resid = getResources().getIdentifier(resName, "drawable", packName);

        list1_mentality_cancelbtn = findViewById(R.id.toolbar_list_btn_cancel);
        list1_mentality_cancelbtn.setOnClickListener(new list1_mentality_cancelbtn_listener());

        list1_mentality_backbtn = findViewById(R.id.back_btn_layout);
        list1_mentality_backbtn.setOnClickListener(new list1_mentality_backbtn_listener());

        list1_mentality_radioGroup1 = findViewById(R.id.list1_mentality_radioGroup1);
        list1_mentality_radioGroup1.clearCheck();
        list1_mentality_radioGroup1.setOnCheckedChangeListener(radioGroup_mentality_listener1);
        list1_mentality_radioGroup2 = findViewById(R.id.list1_mentality_radioGroup2);
        list1_mentality_radioGroup2.clearCheck();
        list1_mentality_radioGroup2.setOnCheckedChangeListener(radioGroup_mentality_listener2);
        list1_mentality_radioGroup3 = findViewById(R.id.list1_mentality_radioGroup3);
        list1_mentality_radioGroup3.clearCheck();
        list1_mentality_radioGroup3.setOnCheckedChangeListener(radioGroup_mentality_listener3);

        list1_mentality_nextbtn = findViewById(R.id.list1_mentality_btn_next);
        list1_mentality_nextbtn.setOnClickListener(this);

        mentality_custom_text = findViewById(R.id.list1_mentality_btn_usercustom);
        mentality_custom_text.setOnClickListener(new mentality_custom_btn_listener());

        mentality_custom_edit_text = findViewById(R.id.list1_mentality_usercustom_edittext);
        mentality_custom_edit_text.setOnClickListener(new mentality_custom_edit_Clicklistener());
        mentality_custom_edit_text.setOnKeyListener(new mentality_custom_edit_listener());
        mentality_custom_edit_text.setTypeface(typebold);

        mentality_usercustom_layout = findViewById(R.id.list1_mentality_btn_usercustom_layout);

        background = findViewById(R.id.list_background);
        background.setOnClickListener(new list_background_listener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:
                    bundle = data.getExtras();
                    bundle.putInt("categoryList_idx",category_listId);
                    data.putExtras(bundle);
                    setResult(RESULT_OK,data);
                    finish();

                case RESULT_CANCELED:
                    finish();
            }
        }
    }

    private OnCheckedChangeListener radioGroup_mentality_listener1 = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {

                if(checkedId == R.id.list1_mentality_btn_hurt){
                    selfesteem.setTypeface(typebold);
                    unrest.setTypeface(typereg);
                }
                else if(checkedId == R.id.list1_mentality_btn_unrest){
                    unrest.setTypeface(typebold);
                    selfesteem.setTypeface(typereg);
                }

                depressed.setTypeface(typereg);
                torpor.setTypeface(typereg);
                ego.setTypeface(typereg);
                exist.setTypeface(typereg);
                mentality_custom_edit_text.setTypeface(typereg);
                mentality_custom_edit_text.setCursorVisible(false);
                list1_mentality_nextbtn.setEnabled(true);
                hidekeyboard(mentality_custom_edit_text);
                list1_mentality_radioGroup2.setOnCheckedChangeListener(null);
                list1_mentality_radioGroup2.clearCheck();
                list1_mentality_radioGroup2.setOnCheckedChangeListener(radioGroup_mentality_listener2);
                list1_mentality_radioGroup3.setOnCheckedChangeListener(null);
                list1_mentality_radioGroup3.clearCheck();
                list1_mentality_radioGroup3.setOnCheckedChangeListener(radioGroup_mentality_listener3);
                mentality_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
                int backcolor = getResources().getColor(R.color.lightBlue);
                mentality_custom_edit_text.setTextColor(backcolor);
                String title;
                title = mentality_custom_edit_text.getText().toString();
                if(title.getBytes().length <= 0) {
                    mentality_custom_text.setVisibility(View.VISIBLE);
                    mentality_custom_edit_text.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    private OnCheckedChangeListener radioGroup_mentality_listener2 = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId != -1) {

                if(checkedId == R.id.list1_mentality_btn_depressed){
                    depressed.setTypeface(typebold);
                    torpor.setTypeface(typereg);
                }
                else if(checkedId == R.id.list1_mentality_btn_torpor){
                    torpor.setTypeface(typebold);
                    depressed.setTypeface(typereg);
                }
                selfesteem.setTypeface(typereg);
                unrest.setTypeface(typereg);
                ego.setTypeface(typereg);
                exist.setTypeface(typereg);
                mentality_custom_edit_text.setTypeface(typereg);
                mentality_custom_edit_text.setCursorVisible(false);
                list1_mentality_nextbtn.setEnabled(true);
                hidekeyboard(mentality_custom_edit_text);
                list1_mentality_radioGroup1.setOnCheckedChangeListener(null);
                list1_mentality_radioGroup1.clearCheck();
                list1_mentality_radioGroup1.setOnCheckedChangeListener(radioGroup_mentality_listener1);
                list1_mentality_radioGroup3.setOnCheckedChangeListener(null);
                list1_mentality_radioGroup3.clearCheck();
                list1_mentality_radioGroup3.setOnCheckedChangeListener(radioGroup_mentality_listener3);
                mentality_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
                int backcolor = getResources().getColor(R.color.lightBlue);
                mentality_custom_edit_text.setTextColor(backcolor);
                String title;
                title = mentality_custom_edit_text.getText().toString();
                if(title.getBytes().length <= 0) {
                    mentality_custom_text.setVisibility(View.VISIBLE);
                    mentality_custom_edit_text.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    private OnCheckedChangeListener radioGroup_mentality_listener3 = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId != -1) {

                if(checkedId == R.id.list1_mentality_btn_ego){
                    ego.setTypeface(typebold);
                    exist.setTypeface(typereg);
                }
                else if(checkedId == R.id.list1_mentality_btn_exist){
                    exist.setTypeface(typebold);
                    ego.setTypeface(typereg);
                }
                selfesteem.setTypeface(typereg);
                unrest.setTypeface(typereg);
                depressed.setTypeface(typereg);
                torpor.setTypeface(typereg);
                mentality_custom_edit_text.setTypeface(typereg);
                mentality_custom_edit_text.setCursorVisible(false);
                list1_mentality_nextbtn.setEnabled(true);
                hidekeyboard(mentality_custom_edit_text);
                list1_mentality_radioGroup1.setOnCheckedChangeListener(null);
                list1_mentality_radioGroup1.clearCheck();
                list1_mentality_radioGroup1.setOnCheckedChangeListener(radioGroup_mentality_listener1);
                list1_mentality_radioGroup2.setOnCheckedChangeListener(null);
                list1_mentality_radioGroup2.clearCheck();
                list1_mentality_radioGroup2.setOnCheckedChangeListener(radioGroup_mentality_listener2);
                mentality_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
                int backcolor = getResources().getColor(R.color.lightBlue);
                mentality_custom_edit_text.setTextColor(backcolor);
                String title;
                title = mentality_custom_edit_text.getText().toString();
                if(title.getBytes().length <= 0) {
                    mentality_custom_text.setVisibility(View.VISIBLE);
                    mentality_custom_edit_text.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {

        if(selfesteem.isChecked()){
            category_listId = 8;
            Intent intent = new Intent(context,HelperSignUpActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else if (unrest.isChecked()){
            category_listId = 9;
            Intent intent = new Intent(context,HelperSignUpActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else if (depressed.isChecked()){
            category_listId = 10;
            Intent intent = new Intent(context,HelperSignUpActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else if (torpor.isChecked()){
            category_listId = 11;
            Intent intent = new Intent(context,HelperSignUpActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else if (ego.isChecked()){
            category_listId = 12;
            Intent intent = new Intent(context,HelperSignUpActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else if (exist.isChecked()){
            category_listId = 13;
            Intent intent = new Intent(context,HelperSignUpActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else if (mentality_custom_edit_text.isFocused()){
            WorryCategoryListAddModel worryCategoryListAddModel = new WorryCategoryListAddModel();
            worryCategoryListAddModel.category_idx = 3;
            worryCategoryListAddModel.categoryList_name = mentality_custom_edit_text.getText().toString();

            String token = ApplicationFields.userToken;
            Call<WorryCategoryListAddResponseModel> call_helper = RetrofitService.getInstance().getService().concern_category_list_post(token,worryCategoryListAddModel);
            call_helper.enqueue(new Callback<WorryCategoryListAddResponseModel>() {
                @Override
                public void onResponse(Call<WorryCategoryListAddResponseModel> call, Response<WorryCategoryListAddResponseModel> response) {
                    WorryCategoryListAddResponseModel result = response.body();
                    if(response.isSuccessful()) {
                        category_listId = result.data.categoryList_idx;
                        Intent intent = new Intent(context, HelperSignUpActivity2.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    else{
                        return ;
                    }
                }
                @Override
                public void onFailure(Call<WorryCategoryListAddResponseModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    class list1_mentality_cancelbtn_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            Dialog();
        }
    }

    class list1_mentality_backbtn_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("result", "BACK");
            setResult(REQUEST_CODE, intent);
            finish();
        }
    }

    class list_background_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            if(mentality_custom_edit_text.isFocused()){
                String title;
                title = mentality_custom_edit_text.getText().toString();
                if(title.getBytes().length <= 0) {
                    list1_mentality_nextbtn.setEnabled(false);
                    mentality_custom_text.setVisibility(View.VISIBLE);
                    mentality_custom_edit_text.setVisibility(View.INVISIBLE);
                    mentality_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
                }
                else{
                    list1_mentality_nextbtn.setEnabled(true);
                }
                hidekeyboard(mentality_custom_edit_text);
                mentality_custom_edit_text.setCursorVisible(false);
            }
        }
    }

    class mentality_custom_btn_listener implements OnClickListener {
        @Override
        public void onClick(View v) {
            String title;
            title = mentality_custom_edit_text.getText().toString();
            if(title.getBytes().length <= 0) {
                list1_mentality_nextbtn.setEnabled(false);
                mentality_custom_text.setVisibility(View.VISIBLE);
                mentality_custom_edit_text.setVisibility(View.INVISIBLE);
            }
            selfesteem.setTypeface(typereg);
            unrest.setTypeface(typereg);
            depressed.setTypeface(typereg);
            torpor.setTypeface(typereg);
            ego.setTypeface(typereg);
            exist.setTypeface(typereg);
            mentality_custom_edit_text.setTypeface(typebold);
            list1_mentality_radioGroup1.setOnCheckedChangeListener(null);
            list1_mentality_radioGroup1.clearCheck();
            list1_mentality_radioGroup1.setOnCheckedChangeListener(radioGroup_mentality_listener1);
            list1_mentality_radioGroup2.setOnCheckedChangeListener(null);
            list1_mentality_radioGroup2.clearCheck();
            list1_mentality_radioGroup2.setOnCheckedChangeListener(radioGroup_mentality_listener2);
            list1_mentality_radioGroup3.setOnCheckedChangeListener(null);
            list1_mentality_radioGroup3.clearCheck();
            list1_mentality_radioGroup3.setOnCheckedChangeListener(radioGroup_mentality_listener3);
            mentality_custom_text.setVisibility(View.INVISIBLE);
            mentality_custom_edit_text.setVisibility(View.VISIBLE);
            mentality_custom_edit_text.setCursorVisible(true);
            mentality_custom_edit_text.requestFocus();
            showkeyboard(mentality_custom_edit_text);
            int backcolor = getResources().getColor(R.color.white);
            mentality_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selected);
            mentality_custom_edit_text.setTextColor(backcolor);
        }
    }
    class mentality_custom_edit_Clicklistener implements OnClickListener {
        @Override
        public void onClick(View view) {
            String title;
            title = mentality_custom_edit_text.getText().toString();
            if(title.getBytes().length <= 0) {
                list1_mentality_nextbtn.setEnabled(false);
                mentality_custom_text.setVisibility(View.VISIBLE);
                mentality_custom_edit_text.setVisibility(View.INVISIBLE);
            }
            else{
                list1_mentality_nextbtn.setEnabled(true);
            }
            selfesteem.setTypeface(typereg);
            unrest.setTypeface(typereg);
            depressed.setTypeface(typereg);
            torpor.setTypeface(typereg);
            ego.setTypeface(typereg);
            exist.setTypeface(typereg);
            mentality_custom_edit_text.setTypeface(typebold);
            list1_mentality_radioGroup1.setOnCheckedChangeListener(null);
            list1_mentality_radioGroup1.clearCheck();
            list1_mentality_radioGroup1.setOnCheckedChangeListener(radioGroup_mentality_listener1);
            list1_mentality_radioGroup2.setOnCheckedChangeListener(null);
            list1_mentality_radioGroup2.clearCheck();
            list1_mentality_radioGroup2.setOnCheckedChangeListener(radioGroup_mentality_listener2);
            list1_mentality_radioGroup3.setOnCheckedChangeListener(null);
            list1_mentality_radioGroup3.clearCheck();
            list1_mentality_radioGroup3.setOnCheckedChangeListener(radioGroup_mentality_listener3);
            mentality_custom_text.setVisibility(View.INVISIBLE);
            mentality_custom_edit_text.setVisibility(View.VISIBLE);
            mentality_custom_edit_text.setCursorVisible(true);
            int backcolor = getResources().getColor(R.color.white);
            mentality_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selected);
            mentality_custom_edit_text.setTextColor(backcolor);
        }
    }

    class mentality_custom_edit_listener implements View.OnKeyListener {

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {

            switch (i) {
                case KeyEvent.KEYCODE_ENTER :
                    hidekeyboard(mentality_custom_edit_text);
                    String title;
                    title = mentality_custom_edit_text.getText().toString();
                    if(title.getBytes().length <= 0) {
                        list1_mentality_nextbtn.setEnabled(false);
                        mentality_custom_text.setVisibility(View.VISIBLE);
                        mentality_custom_edit_text.setVisibility(View.INVISIBLE);
                        mentality_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
                    }
                    mentality_custom_edit_text.setCursorVisible(false);
            }
            return false;
        }
    }

    private void hidekeyboard(EditText edit) {
        InputMethodManager input = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    private void showkeyboard(EditText edit){
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        input.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void Dialog() {
        dialog = new OneTextTwoButton_CustomDialog(HelperSignUp1_M_Activity.this, resid,
                "정말 그만두시겠어요?\n아직 하나도 작성하시지 않으셨어요!", "계속 작성하기", "그만하기", keepListener, exitListener);

        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    private OnClickListener keepListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    };

    private OnClickListener exitListener = new OnClickListener() {
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
