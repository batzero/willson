package com.example.appjam_willson.HelperSignUpActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appjam_willson.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelperSignUpActivity2 extends AppCompatActivity {

    int REQUEST_CODE;

    EditText helper_experience;
    TextView textViewCount;

    LinearLayout background;

    LinearLayout linear1;
    LinearLayout linear2;
    LinearLayout linear3;

    EditText edit1;
    EditText edit2;
    EditText edit3;

    Button signup_nextbtn;
    ImageView signup_backbtn;

    Context context;

    String resName;
    String packName;
    int resid;

    Typeface typebold;
    Typeface typereg;

    View view;
    ImageView btn;
    String[] tags = new String[3];
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_sign_up2);

        view = findViewById(R.id.toolbar);
        btn = findViewById(R.id.cancel_btn);
        btn.setVisibility(View.INVISIBLE);

        context = this;

        REQUEST_CODE = ((HelperSignUpActivity2) context).getTaskId();

        typebold = getResources().getFont(R.font.nanum_square_b);
        typereg = getResources().getFont(R.font.nanum_square_r);

        resName = "@drawable/request_couldnt_find";
        packName = this.getPackageName();
        resid = getResources().getIdentifier(resName, "drawable", packName);

        signup_backbtn = view.findViewById(R.id.back_btn);
        signup_backbtn.setOnClickListener(new signup_backbtn_listener());

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);

        edit1 = findViewById(R.id.editText1);
        edit2 = findViewById(R.id.editText2);
        edit3 = findViewById(R.id.editText3);

        edit1.setTypeface(typereg);
        edit2.setTypeface(typereg);
        edit3.setTypeface(typereg);

        edit1.setOnFocusChangeListener(new edit1_focus());
        edit2.setOnFocusChangeListener(new edit2_focus());
        edit3.setOnFocusChangeListener(new edit3_focus());

        edit3.setOnKeyListener(new edit_listener());

        background = findViewById(R.id.signup_background);
        background.setOnClickListener(new signup_background_listener());

        signup_nextbtn = findViewById(R.id.next_btn);
        signup_nextbtn.setOnClickListener(new signup_nextbtn_listener());

        textViewCount = findViewById(R.id.textViewCount);

        helper_experience = findViewById(R.id.helper_signup_edittext);
        helper_experience.setOnFocusChangeListener(new edit_exper());

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 ) {
                    edit1.setTypeface(typereg);
                }
                else edit1.setTypeface(typebold);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 ) {
                    edit2.setTypeface(typereg);
                }
                else edit2.setTypeface(typebold);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 ) {
                    edit3.setTypeface(typereg);
                }
                else edit3.setTypeface(typebold);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        helper_experience.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textViewCount.setText(Integer.toString(s.toString().length()));
                if (s.length() == 0 ) {
                    signup_nextbtn.setEnabled(false);
                }
                else signup_nextbtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:
                    bundle = data.getExtras();
                    bundle.putString("content",helper_experience.getText().toString());
                    bundle.putStringArray("experience_name",tags);
                    data.putExtras(bundle);
                    setResult(RESULT_OK,data);
                    finish();

                case RESULT_CANCELED:
                    finish();
            }
        }
    }

    class edit_exper implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b) {
                helper_experience.setCursorVisible(true);
            }
        }
    }

    class edit1_focus implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(hasFocus){
                linear1.setBackgroundResource(R.drawable.rounded_corner_thick);
                edit1.setCursorVisible(true);
                showkeyboard(edit1);
                if(edit1.length() > 0) {
                    edit1.setTypeface(typebold);
                }
                else {
                    edit1.setTypeface(typereg);
                }
                edit2.setTypeface(typereg);
                edit3.setTypeface(typereg);
                linear2.setBackgroundResource(R.drawable.rounded_corner_radius5);
                linear3.setBackgroundResource(R.drawable.rounded_corner_radius5);

            }
        }
    }

    class edit2_focus implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(hasFocus){
                linear1.setBackgroundResource(R.drawable.rounded_corner_radius5);
                linear2.setBackgroundResource(R.drawable.rounded_corner_thick);
                edit2.setCursorVisible(true);
                showkeyboard(edit2);
                if(edit2.length() > 0) {
                    edit2.setTypeface(typebold);
                }
                else {
                    edit2.setTypeface(typereg);
                }
                edit1.setTypeface(typereg);
                edit3.setTypeface(typereg);
                linear3.setBackgroundResource(R.drawable.rounded_corner_radius5);
            }
        }
    }

    class edit3_focus implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(hasFocus){
                linear1.setBackgroundResource(R.drawable.rounded_corner_radius5);
                linear2.setBackgroundResource(R.drawable.rounded_corner_radius5);
                linear3.setBackgroundResource(R.drawable.rounded_corner_thick);
                edit3.setCursorVisible(true);
                showkeyboard(edit3);
                if(edit3.length() > 0) {
                    edit3.setTypeface(typebold);
                }
                else {
                    edit3.setTypeface(typereg);
                }
                edit1.setTypeface(typereg);
                edit2.setTypeface(typereg);
            }
        }
    }

    class signup_nextbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            tags[0] = edit1.getText().toString();
            tags[1] = edit2.getText().toString();
            tags[2] = edit3.getText().toString();

            Intent intent = new Intent(context, HelperSignupActivity3.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    class signup_background_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            hidekeyboard(edit1);
            hidekeyboard(edit2);
            hidekeyboard(edit3);
            edit1.setTypeface(typereg);
            edit2.setTypeface(typereg);
            edit3.setTypeface(typereg);
            linear1.setBackgroundResource(R.drawable.rounded_corner_radius5);
            linear2.setBackgroundResource(R.drawable.rounded_corner_radius5);
            linear3.setBackgroundResource(R.drawable.rounded_corner_radius5);
            edit1.setCursorVisible(false);
            edit2.setCursorVisible(false);
            edit3.setCursorVisible(false);
            edit1.clearFocus();
            edit2.clearFocus();
            edit3.clearFocus();
            helper_experience.clearFocus();
            helper_experience.setCursorVisible(false);
            hidekeyboard(helper_experience);
        }
    }

    class edit_listener implements View.OnKeyListener {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {

            switch (i) {
                case KeyEvent.KEYCODE_ENTER :
                    hidekeyboard(edit3);
                    edit3.setCursorVisible(false);
                    edit3.setTypeface(typereg);
                    linear3.setBackgroundResource(R.drawable.rounded_corner_radius5);
                    edit3.clearFocus();
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

    class signup_backbtn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("result", "BACK");
            setResult(REQUEST_CODE, intent);
            finish();        }
    }
}