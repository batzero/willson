package com.example.appjam_willson;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class List1RelationshipsActivity extends AppCompatActivity implements OnClickListener {

    RadioGroup list1_relationships_radioGroup1;
    RadioGroup list1_relationships_radioGroup2;

    Button list1_relationships_nextbtn;
    TextView relationships_custom_text;
    EditText relationships_custom_edit_text;
    LinearLayout relationships_usercustom_layout;

    LinearLayout list1_relationships_backbtn;
    LinearLayout list1_relationships_cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1_relationships);


        list1_relationships_cancelbtn = (LinearLayout) findViewById(R.id.toolbar_list_btn_cancel);
        list1_relationships_cancelbtn.setOnClickListener(new list1_relationships_cancelbtn_listener());

        list1_relationships_backbtn = (LinearLayout) findViewById(R.id.toolbar_list_btn_backbtn);
        list1_relationships_backbtn.setOnClickListener(new list1_relationships_backbtn_listener());


        list1_relationships_radioGroup1 = (RadioGroup) findViewById(R.id.list1_relationships_radioGroup1);
        list1_relationships_radioGroup1.clearCheck();
        list1_relationships_radioGroup1.setOnCheckedChangeListener(radioGroup_relationships_listener1);
        list1_relationships_radioGroup2 = (RadioGroup) findViewById(R.id.list1_relationships_radioGroup2);
        list1_relationships_radioGroup2.clearCheck();
        list1_relationships_radioGroup2.setOnCheckedChangeListener(radioGroup_relationships_listener2);

        list1_relationships_nextbtn = (Button) findViewById(R.id.list1_relationships_btn_next);
        list1_relationships_nextbtn.setOnClickListener(this);

        relationships_custom_text = (TextView)findViewById(R.id.list1_relationships_btn_usercustom);
        relationships_custom_text.setOnClickListener(new relationships_custom_btn_listener());

        relationships_custom_edit_text = (EditText)findViewById(R.id.list1_relationships_usercustom_edittext);
        relationships_custom_edit_text.setOnClickListener(new relationships_custom_edit_Clicklistener());
        relationships_custom_edit_text.setOnKeyListener(new relationships_custom_edit_listener());

        relationships_usercustom_layout = (LinearLayout)findViewById(R.id.list1_relationships_btn_usercustom_layout);
    }

    private OnCheckedChangeListener radioGroup_relationships_listener1 = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                list1_relationships_nextbtn.setEnabled(true);
                hidekeyboard(relationships_custom_edit_text);
                list1_relationships_radioGroup2.setOnCheckedChangeListener(null);
                list1_relationships_radioGroup2.clearCheck();
                list1_relationships_radioGroup2.setOnCheckedChangeListener(radioGroup_relationships_listener2);
                relationships_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
                int backcolor = getResources().getColor(R.color.lightPurple);
                relationships_custom_edit_text.setTextColor(backcolor);
                String title;
                title = relationships_custom_edit_text.getText().toString();
                if(title.getBytes().length <= 0) {
                    relationships_custom_text.setVisibility(View.VISIBLE);
                    relationships_custom_edit_text.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    private OnCheckedChangeListener radioGroup_relationships_listener2 = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId != -1) {
                list1_relationships_nextbtn.setEnabled(true);
                hidekeyboard(relationships_custom_edit_text);
                list1_relationships_radioGroup1.setOnCheckedChangeListener(null);
                list1_relationships_radioGroup1.clearCheck();
                list1_relationships_radioGroup1.setOnCheckedChangeListener(radioGroup_relationships_listener1);
                relationships_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selector);
                int backcolor = getResources().getColor(R.color.lightPurple);
                relationships_custom_edit_text.setTextColor(backcolor);
                String title;
                title = relationships_custom_edit_text.getText().toString();
                if(title.getBytes().length <= 0) {
                    relationships_custom_text.setVisibility(View.VISIBLE);
                    relationships_custom_edit_text.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {





    }

    class list1_relationships_cancelbtn_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            ListPopupActivity customDialog = new ListPopupActivity(List1RelationshipsActivity.this);
            customDialog.callFunction();
        }
    }

    class list1_relationships_backbtn_listener implements OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    class relationships_custom_btn_listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String title;
            title = relationships_custom_edit_text.getText().toString();
            if(title.getBytes().length <= 0) {
                list1_relationships_nextbtn.setEnabled(false);
                relationships_custom_text.setVisibility(View.VISIBLE);
                relationships_custom_edit_text.setVisibility(View.INVISIBLE);
            }
            list1_relationships_radioGroup1.setOnCheckedChangeListener(null);
            list1_relationships_radioGroup1.clearCheck();
            list1_relationships_radioGroup1.setOnCheckedChangeListener(radioGroup_relationships_listener1);
            list1_relationships_radioGroup2.setOnCheckedChangeListener(null);
            list1_relationships_radioGroup2.clearCheck();
            list1_relationships_radioGroup2.setOnCheckedChangeListener(radioGroup_relationships_listener2);
            relationships_custom_text.setVisibility(View.INVISIBLE);
            relationships_custom_edit_text.setVisibility(View.VISIBLE);
            int backcolor = getResources().getColor(R.color.white);
            relationships_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selected);
            relationships_custom_edit_text.setTextColor(backcolor);
        }
    }

    class relationships_custom_edit_Clicklistener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String title;
            title = relationships_custom_edit_text.getText().toString();
            if(title.getBytes().length <= 0) {
                list1_relationships_nextbtn.setEnabled(false);
                relationships_custom_text.setVisibility(View.VISIBLE);
                relationships_custom_edit_text.setVisibility(View.INVISIBLE);
            }
            else{
                list1_relationships_nextbtn.setEnabled(true);
            }
            list1_relationships_radioGroup1.setOnCheckedChangeListener(null);
            list1_relationships_radioGroup1.clearCheck();
            list1_relationships_radioGroup1.setOnCheckedChangeListener(radioGroup_relationships_listener1);
            list1_relationships_radioGroup2.setOnCheckedChangeListener(null);
            list1_relationships_radioGroup2.clearCheck();
            list1_relationships_radioGroup2.setOnCheckedChangeListener(radioGroup_relationships_listener2);
            relationships_custom_text.setVisibility(View.INVISIBLE);
            relationships_custom_edit_text.setVisibility(View.VISIBLE);
            int backcolor = getResources().getColor(R.color.white);
            relationships_usercustom_layout.setBackgroundResource(R.drawable.list_btns_selected);
            relationships_custom_edit_text.setTextColor(backcolor);
        }
    }

    class relationships_custom_edit_listener implements View.OnKeyListener {

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {

            switch (i) {
                case KeyEvent.KEYCODE_ENTER :
                    int backcolor = getResources().getColor(R.color.white);
                    relationships_custom_edit_text.setTextColor(backcolor);
                    hidekeyboard(relationships_custom_edit_text);
            }
            return false;
        }
    }

    private void hidekeyboard(EditText edit) {
        InputMethodManager input = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }




}
