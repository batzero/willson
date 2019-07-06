package com.example.appjam_willson.MainActivities;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appjam_willson.R;
import com.example.appjam_willson.model.WillsonModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainFragment3 extends Fragment {

    private RecyclerView fragment3_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<WillsonModel> willsonModels;
    private Fragment3Adapter fragment3Adapter;
    private String myUid;
    private String roomKey;

    public MainFragment3() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment3, null);

        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    WillsonModel users = item.getValue(WillsonModel.class);
                    String uidGet =users.getUid();
                    if (uidGet.equals(myUid)) {
                        roomKey = users.getRoomKey();
                        findChatRooms(roomKey);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fragment3_recyclerView = view.findViewById(R.id.fragment3_recyclerview);
        fragment3_recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        fragment3_recyclerView.setLayoutManager(layoutManager);
        willsonModels = new ArrayList<>();
        fragment3Adapter = new Fragment3Adapter(willsonModels, getActivity());
        fragment3_recyclerView.setAdapter(fragment3Adapter);

        return view;
    }

    //Database에서 본인(질문자 역할)이 속해있는 채팅방을 불러옴
    public void findChatRooms(String setRoomKey) {
        FirebaseDatabase.getInstance().getReference().child("chatRooms").child(setRoomKey).child("willsonUser").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() == null) {
                        Log.d("데이터가 없어요", "데이터가ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ");
                    }
                    WillsonModel users = dataSnapshot.getValue(WillsonModel.class);
                    Log.d("데이터 찾았다ㅏㅏㅏㅏㅏㅏ", users.getUid());
                    willsonModels.add(users);
                    fragment3Adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}