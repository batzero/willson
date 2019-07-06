package com.example.appjam_willson;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment2_null extends Fragment {

    public MainFragment2_null(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.main_fragment2_null,null);
            LinearLayout linearLayout = view.findViewById(R.id.fragment2_null_goHome);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainFragment fragment = new MainFragment();
                    getFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
                    getActivity().finish();
                }
            });
            return view;
    }

}