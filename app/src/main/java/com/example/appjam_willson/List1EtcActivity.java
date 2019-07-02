package com.example.appjam_willson;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class List1EtcActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton list1_etc_radiobtn;
    Context context;

    Button list1_etc_nextbtn;
    TextView etc_custom_text;
    EditText etc_custom_edit_text;
    LinearLayout etc_usercustom_layout;

    ImageButton list1_etc_backbtn;
    ImageButton list1_etc_cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1_etc);

        context = this;

        list1_etc_cancelbtn = (ImageButton) findViewById(R.id.toolbar_list_btn_cancel);
        list1_etc_cancelbtn.setOnClickListener(new list1_etc_cancelbtn_listener());

        list1_etc_backbtn = (ImageButton) findViewById(R.id.toolbar_list_btn_backbtn);
        list1_etc_backbtn.setOnClickListener(new list1_etc_backbtn_listener());

        list1_etc_radiobtn = (RadioButton) findViewById(R.id.list1_etc_btn_visual);
        list1_etc_radiobtn.setOnClickListener(new list1_etc_radiobtn_listener());

        list1_etc_nextbtn = (Button) findViewById(R.id.list1_etc_btn_next);
        list1_etc_nextbtn.setOnClickListener(this);

        etc_custom_text = (TextView)findViewById(R.id.list1_etc_btn_usercustom);
        etc_custom_text.setOnClickListener(new etc_custom_btn_listener());

        etc_custom_edit_text = (EditText)findViewById(R.id.list1_etc_usercustom_edittext);
        etc_custom_edit_text.setOnClickListener(new etc_custom_edit_Clicklistener());
        etc_custom_edit_text.setOnKeyListener(new etc_custom_edit_listener());

        etc_usercustom_layout = (LinearLayout)findViewById(R.id.list1_etc_btn_usercustom_layout);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {






    }

    class list1_etc_cancelbtn_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            ListPopupActivity customDialog = new ListPopupActivity(List1EtcActivity.this);
            customDialog.callFunction();
        }
    }

    class list1_etc_backbtn_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    class list1_etc_radiobtn_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            list1_etc_radiobtn.setChecked(true);
            list1_etc_nextbtn.setEnabled(true);
            hidekeyboard(etc_custom_edit_text);
            etc_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
            int backcolor = getResources().getColor(R.color.lightPurple);
            etc_custom_edit_text.setTextColor(backcolor);
            String title;
            title = etc_custom_edit_text.getText().toString();
            if(title.getBytes().length <= 0) {
                etc_custom_text.setVisibility(View.VISIBLE);
                etc_custom_edit_text.setVisibility(View.INVISIBLE);
            }
        }
    }

    class etc_custom_btn_listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String title;
            title = etc_custom_edit_text.getText().toString();
            if(title.getBytes().length <= 0) {
                list1_etc_nextbtn.setEnabled(false);
                etc_custom_text.setVisibility(View.VISIBLE);
                etc_custom_edit_text.setVisibility(View.INVISIBLE);
            }
            list1_etc_radiobtn.setChecked(false);
            etc_custom_text.setVisibility(View.INVISIBLE);
            etc_custom_edit_text.setVisibility(View.VISIBLE);
            int backcolor = getResources().getColor(R.color.white);
            etc_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selected);
            etc_custom_edit_text.setTextColor(backcolor);
        }
    }

    class etc_custom_edit_Clicklistener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String title;
            title = etc_custom_edit_text.getText().toString();
            if(title.getBytes().length <= 0) {
                list1_etc_nextbtn.setEnabled(false);
                etc_custom_text.setVisibility(View.VISIBLE);
                etc_custom_edit_text.setVisibility(View.INVISIBLE);
            }
            else{
                list1_etc_nextbtn.setEnabled(true);
            }
            list1_etc_radiobtn.setChecked(false);
            etc_custom_text.setVisibility(View.INVISIBLE);
            etc_custom_edit_text.setVisibility(View.VISIBLE);
            int backcolor = getResources().getColor(R.color.white);
            etc_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selected);
            etc_custom_edit_text.setTextColor(backcolor);
        }
    }

    class etc_custom_edit_listener implements View.OnKeyListener {

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {

            switch (i) {
                case KeyEvent.KEYCODE_ENTER :
                    int backcolor = getResources().getColor(R.color.white);
                    etc_custom_edit_text.setTextColor(backcolor);
                    hidekeyboard(etc_custom_edit_text);
            }
            return false;
        }
    }

    private void hidekeyboard(EditText edit) {
        InputMethodManager input = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }








}