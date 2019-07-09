package com.example.appjam_willson.ChatActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appjam_willson.R;
import com.example.appjam_willson.model.ChatModel;
import com.example.appjam_willson.model.WillsonModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private String destinationUid;
    private String RoomKey;

    private TimerTask timerTask;
    private Timestamp newTime;
    private Timer timer = new Timer();
    private String restTime;

    private String uid;
    private EditText editText;
    private Button btnSent;
    private long totalTime = 3600000;

    private long startTime;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");
    private SimpleDateFormat timerFormat = new SimpleDateFormat("mm:ss");

    private LinearLayout linearLayout_startMsg ;
    private RelativeLayout chat_startMsg_time;

    private TextView chat_timer;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    private WillsonModel destinationUserModel;
    private WillsonModel askerUserModel;

    int peopleCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

//        chat_timer = findViewById(R.id.chat_timer);
//
//
//        TimerTask timerTask = new TimerTask() {
//            // 1시간
//            @Override
//            public void run() {
//                ChatActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        long passTime;
//                        newTime = new Timestamp(System.currentTimeMillis());
//                        long passedTime = newTime.getTime()-startTime;H
//                        passTime = totalTime - passedTime;
//
//                        Date date = new Date(passTime);
//                        restTime = timerFormat.format(date);
//                        chat_timer.setText(restTime);
//                        Log.d("쓰레드 돌아가나 확인","쓰레드ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
//                    }
//                });
//            }
//        };

        linearLayout_startMsg = findViewById(R.id.chat_startMsg);

        destinationUid = getIntent().getStringExtra("destinationUid");

        //채팅방에 참여한 유저들의 uid들을 먼저 가져옴.
        getUserData();

        RoomKey = (destinationUid+uid);

        //roomKey를 유저들에게 보내줌
        sendRoomkey( destinationUid , uid , RoomKey);

        Button finishButton = findViewById(R.id.chat_btn_back);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText = findViewById(R.id.chat_editText);
        btnSent = findViewById(R.id.chat_sendButton);

        mRecyclerView = findViewById(R.id.chat_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if(text.equals("")){
                    Toast.makeText(ChatActivity.this, "내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid = uid;
                    comment.message = text;
                    comment.timeStamp = ServerValue.TIMESTAMP;

                    FirebaseDatabase.getInstance().getReference().child("chatRooms").child(RoomKey).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //            sendFcm(); Fcm 보내는 부분
                            editText.setText("");
                        }
                    });
                }
            }
        });
        checkChatRoom();
//        timer.schedule(timerTask,0,300);
    }

    void checkChatRoom() {
        FirebaseDatabase.getInstance().getReference().child("chatRooms").orderByKey().equalTo(RoomKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    ChatModel newRoom = new ChatModel();
                    newRoom.users.put(uid, true);
                    newRoom.users.put(destinationUid, true);
                    newRoom.chatStart.put("timeStamp",ServerValue.TIMESTAMP);

                    FirebaseDatabase.getInstance().getReference().child("chatRooms").child(RoomKey).setValue(newRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });
                    return;
                }

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    ChatModel chatModel = item.getValue(ChatModel.class);
                    if (chatModel.users.containsKey(destinationUid)) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                        mRecyclerView.setAdapter(new ChatAdapter());
                    }
                }

                //Timer를 위해 처음 대화를 시작한 값을 가져옴
                getChatStart();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<ChatModel.Comment> comments;

        public ChatAdapter() {
            comments = new ArrayList<>();

            //UserData를 먼저 생성해준다.
            //destinationUid를 참고하여 users 목록에서 data를 받은 후에 destinationUserModel에 데이터를 넣어준다.
            makeUserData();
            FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        destinationUserModel= dataSnapshot.getValue(WillsonModel.class);
                        getMessageList();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        void getMessageList(){

            databaseReference = FirebaseDatabase.getInstance().getReference().child("chatRooms").child(RoomKey).child("comments");
            valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    comments.clear();
                    Map<String, Object> readUsersMap = new HashMap<>();
                    for(DataSnapshot item : dataSnapshot.getChildren()){
                        String key = item.getKey();
                        ChatModel.Comment comment_origin = item.getValue(ChatModel.Comment.class);
                        ChatModel.Comment comment_modify = item.getValue(ChatModel.Comment.class);
                        comment_modify.readUser.put(uid,true);

                        readUsersMap.put(key,comment_modify);
                        comments.add(comment_origin);
                    }

                    if(comments.size() == 0){
                        return ;
                    }

                    //누군가가 대화를 시작하면  사라지게 만듦
                    if(linearLayout_startMsg.getVisibility() != View.GONE && comments.size() !=0){
                        linearLayout_startMsg.setVisibility(View.GONE);
//                        chat_startMsg_time.setVisibility(View.GONE);
                    }

                    if(!comments.get(comments.size()-1).readUser.containsKey(uid)){
                        FirebaseDatabase.getInstance().getReference().child("chatRooms").child(RoomKey).child("comments")
                                .updateChildren(readUsersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                notifyDataSetChanged();
                                mRecyclerView.scrollToPosition(comments.size()-1);
                            }
                        });
                    }
                    else{
                        notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(comments.size()-1);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recyclerview,parent,false);

            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);

            long unixTime = (long) comments.get(position).timeStamp;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);

            //내가 보낸거
            if(comments.get(position).uid.equals(uid)) {

                messageViewHolder.textView_another_msg.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_my_msg.setText(comments.get(position).message);
                messageViewHolder.textView_my_msg.setVisibility(View.VISIBLE);

                messageViewHolder.imageView_profile.setVisibility(View.INVISIBLE);

                messageViewHolder.textView_readCounter_right.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_readCounter_left.setVisibility(View.VISIBLE);

                messageViewHolder.textView_timeStamp_left.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_timeStamp_right.setVisibility(View.VISIBLE);

                setReadCounter(position,messageViewHolder.textView_readCounter_left);
                messageViewHolder.textView_timeStamp_right.setText(time);
            }

            //상대방이 보낸거
            else{

                messageViewHolder.textView_another_msg.setVisibility(View.VISIBLE);
                messageViewHolder.textView_another_msg.setText(comments.get(position).message);
                messageViewHolder.textView_my_msg.setVisibility(View.INVISIBLE);

                messageViewHolder.imageView_profile.setVisibility(View.VISIBLE);

                messageViewHolder.textView_readCounter_right.setVisibility(View.VISIBLE);
                messageViewHolder.textView_readCounter_left.setVisibility(View.INVISIBLE);

                messageViewHolder.textView_timeStamp_left.setVisibility(View.VISIBLE);
                messageViewHolder.textView_timeStamp_right.setVisibility(View.INVISIBLE);

                setReadCounter(position,messageViewHolder.textView_readCounter_right);
                messageViewHolder.textView_timeStamp_left.setText(time);

            }
        }

        void setReadCounter(final int position, final TextView textView) {
       if (peopleCount == 0) {
                FirebaseDatabase.getInstance().getReference().child("chatRooms").child(RoomKey).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Boolean> users = (Map<String, Boolean>) dataSnapshot.getValue();
                        peopleCount = users.size();
                        int count = peopleCount - comments.get(position).readUser.size();
                        if (count > 0) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(String.valueOf(count));
                        }else {
                            textView.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }else {
                int count = peopleCount - comments.get(position).readUser.size();
                if (count > 0) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(String.valueOf(count));
                } else {
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        private class MessageViewHolder extends RecyclerView.ViewHolder {

            public TextView textView_another_msg;
            public TextView textView_my_msg;

            public ImageView imageView_profile;

            public LinearLayout linearLayout_main;

            public LinearLayout chat_finish_layout;

            public TextView textView_timeStamp_left;
            public TextView textView_timeStamp_right;

            public TextView textView_readCounter_left;
            public TextView textView_readCounter_right;

            public MessageViewHolder(View view) {
                super(view);
                textView_another_msg =  view.findViewById(R.id.chat_another_msg);
                textView_my_msg = view.findViewById(R.id.chat_myMsg);

                imageView_profile = view.findViewById(R.id.chat_Image);

                linearLayout_main = view.findViewById(R.id.chat_layoutMain);

                chat_finish_layout = view.findViewById(R.id.chat_finish_layout);

                textView_timeStamp_right = view.findViewById(R.id.chat_timeStamp_right);
                textView_timeStamp_left = view.findViewById(R.id.chat_timeStamp_left);

                textView_readCounter_right = view.findViewById(R.id.chat_chatCount_right);
                textView_readCounter_left = view.findViewById(R.id.chat_chatCount_left);
            }
        }
    }
    void getUserData(){
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                askerUserModel= dataSnapshot.getValue(WillsonModel.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("willsonUsers").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                destinationUserModel= dataSnapshot.getValue(WillsonModel.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    void makeUserData(){
        Map<String, String> willsonProfile = new HashMap<>();
        willsonProfile.put("photo", "");
        willsonProfile.put("uid", destinationUid);
        willsonProfile.put("nickName",destinationUserModel.getNickName());

        Map<String, String> askerProfile = new HashMap<>();
        askerProfile.put("photo", "");
        askerProfile.put("uid", uid);
        askerProfile.put("nickName",askerUserModel.getNickName() );

        FirebaseDatabase.getInstance().getReference().child("chatRooms").child(RoomKey).child("willsonUser").setValue(willsonProfile);
        FirebaseDatabase.getInstance().getReference().child("chatRooms").child(RoomKey).child("askerUser").setValue(askerProfile);

    }

    void sendRoomkey(String willsonUser,String askerUser, String roomKey){
        Map<String, Object> setRoomKey = new HashMap<>();
        setRoomKey.put("roomKey", roomKey);
        FirebaseDatabase.getInstance().getReference("willsonUsers").child(willsonUser).updateChildren(setRoomKey);
        FirebaseDatabase.getInstance().getReference("users").child(askerUser).updateChildren(setRoomKey);
    }

    void getChatStart(){
        FirebaseDatabase.getInstance().getReference("chatRooms").child(RoomKey).child("chatStart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> getTimeStamp = (Map <String,Object>)dataSnapshot.getValue();
                startTime = (long) getTimeStamp.get("timeStamp");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        timerTask.cancel();
        databaseReference.removeEventListener(valueEventListener);
        finish();
    }


//    void sendFcm() {
//        Gson gson = new Gson();
//
//        String userName = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        NotificationModel notificationModel = new NotificationModel();
//        notificationModel.to = destinationUserModel.pushToken;
//        notificationModel.notification.title = userName;
//        notificationModel.notification.text = editText.getText().toString();
//        notificationModel.data.title = userName;
//        notificationModel.data.text = editText.getText().toString();
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; chareset=utf8"), gson.toJson(notificationModel));
//
//        Request request = new Request.Builder()
//                .header("Content-Type","application/json")
//                .addHeader("Authorization","key=AIzaSyB8Ofuj0Q1_7A6iJjH8m7AsUdFLXEB9454")
//                .url("https://gcm-http.googleapis.com/gcm/send")
//                .post(requestBody)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
//    }
}