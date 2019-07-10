package com.example.appjam_willson.MainActivities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appjam_willson.FillinListActivity.List1CourseStartActivity;
import com.example.appjam_willson.FillinListActivity.List1DailyStartActivity;
import com.example.appjam_willson.FillinListActivity.List1EtcStartActivity;
import com.example.appjam_willson.FillinListActivity.List1LoveStartActivity;
import com.example.appjam_willson.FillinListActivity.List1MentalityStartActivity;
import com.example.appjam_willson.FillinListActivity.List1RelationshipsStartActivity;
import com.example.appjam_willson.HelperSignUpActivity.HelperSignUpStartActivity;
import com.example.appjam_willson.NetworkService.RetrofitAPI;
import com.example.appjam_willson.NetworkService.RetrofitService;
import com.example.appjam_willson.R;
import com.example.appjam_willson.model.HelperStoryModel;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {

    int REQUEST_CODE_LOVE = 1;
    int REQUEST_CODE_COURSE = 2;
    int REQUEST_CODE_MENTAL = 3;
    int REQUEST_CODE_RELATION= 4;
    int REQUEST_CODE_DAILY = 5;
    int REQUEST_CODE_ETC = 6;

    Context context;

    private StoryAdapter storyAdapter;
    private RecyclerView storyRecyclerView;
    private LinearLayoutManager storyLayoutManager;
    private List<HelperStoryModel.story> storyAdapterModels;

    private ReviewAdapter reviewAdapter;
    private RecyclerView reviewRecyclerView;
    private LinearLayoutManager reviewLayoutManager;
    private List<HelperStoryModel.story> reviewAdapterModels;

    public MainFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment1,null);
        LinearLayout firstContent = view.findViewById(R.id.fragment1_firstContent);
        LinearLayout secondContent = view.findViewById(R.id.fragment1_secondContent);
        LinearLayout thirdContent = view.findViewById(R.id.fragment1_thirdContent);
        LinearLayout fourthContent = view.findViewById(R.id.fragment1_fourthContent);
        LinearLayout fifthContent = view.findViewById(R.id.fragment1_fifthContent);
        LinearLayout sixthContent = view.findViewById(R.id.fragment1_sixthContent);
        TextView main_fragment1_text = view.findViewById(R.id.main_fragment1_text);
        TextView main_fragment1_textSecond = view.findViewById(R.id.main_fragment1_textSecond);
        TextView main_fragment1_textThird = view.findViewById(R.id.main_fragment1_textThird);
        storyRecyclerView = view.findViewById(R.id.fragment1_rv);
        reviewRecyclerView = view.findViewById(R.id.fragment1_rv_second);


        LinearLayout changeMode = view.findViewById(R.id.helper_fragment1_change);

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6Nywibmlja25hbWUiOiJhIiwiZ2VuZGVyIjoi7JesIiwiYWdlIjozNSwidXNlcl9sZXZlbCI6MCwiaWF0IjoxNTYyNTkxNDE4LCJleHAiOjE1NzEyMzE0MTgsImlzcyI6IndpbGxzb24ifQ.8ZxnOA11-BUSyHqKj5piY1VMFxkua8Cy3BcZ5hCyBME";

        Call<HelperStoryModel> call_helper = RetrofitService.getInstance().getService().helper_story_get(token);
        call_helper.enqueue(retrofitCallback);


        firstContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), List1LoveStartActivity.class);
                startActivityForResult(intent, REQUEST_CODE_LOVE);
            }
        });

        secondContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), List1CourseStartActivity.class);
                startActivityForResult(intent, REQUEST_CODE_COURSE);
            }
        });

        thirdContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), List1MentalityStartActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MENTAL);
            }
        });

        fourthContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), List1RelationshipsStartActivity.class);
                startActivityForResult(intent, REQUEST_CODE_RELATION);
            }
        });

        fifthContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), List1DailyStartActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DAILY);
            }
        });

        sixthContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), List1EtcStartActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ETC);
            }
        });

        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //헬퍼 가입했는지 아닌지 판단해서
                //                Intent intent = new Intent(getActivity() , HelperActivity.class);
                Intent intent = new Intent(getActivity() , HelperSignUpStartActivity.class);
                startActivity(intent);
            }
        });

        storyAdapterModels = new ArrayList<>();
        storyRecyclerView.setHasFixedSize(true);
        storyLayoutManager = new LinearLayoutManager(getActivity());
        storyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        storyRecyclerView.setLayoutManager(storyLayoutManager);
        storyAdapter = new StoryAdapter(storyAdapterModels,getActivity());
        storyRecyclerView.setAdapter(storyAdapter);

        // 스토리   리사이클러뷰의 아이템 번호를 읽은 뒤, 고정 시켜주는 기능
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(storyRecyclerView);

        // 스토리   밑에 동그라미 표시를 해주는 클래스
        CircleIndicator2 indicator = view.findViewById(R.id.indicator);
        indicator.attachToRecyclerView(storyRecyclerView, pagerSnapHelper);
        storyAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

//        reviewAdapterModels = new ArrayList<>();
//        reviewRecyclerView.setHasFixedSize(true);
//        reviewLayoutManager = new LinearLayoutManager(getActivity());
//        reviewLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
//        reviewAdapter = new ReviewAdapter(reviewAdapterModels,getActivity());
//        reviewRecyclerView.setAdapter(reviewAdapter);

        //  리뷰   리사이클러뷰의 아이템 번호를 읽은 뒤, 고정 시켜주는 기능
//        PagerSnapHelper pagerSnapHelper_second = new PagerSnapHelper();
//        pagerSnapHelper_second.attachToRecyclerView(reviewRecyclerView);

        //  리뷰    밑에 동그라미 표시를 해주는 클래스
//        CircleIndicator2 indicator_second = view.findViewById(R.id.indicator_second);
//        indicator_second.attachToRecyclerView(reviewRecyclerView, pagerSnapHelper_second);
//        reviewAdapter.registerAdapterDataObserver(indicator_second.getAdapterDataObserver());

        //Text들의 특정 위치 색, 타입을 바꿔주는 메소드
        changeText(main_fragment1_text,7,9,"#5252a1");
        changeText(main_fragment1_textSecond,0,2,"#ffc326");
        changeText(main_fragment1_textThird,6,8,"#5252a1");

        return view;
    }

    //text뷰의 start포인트부터 end포인트까지 색을 바꿔줌 color값으로

    void changeText(TextView text,int start,int end,String color){
        SpannableStringBuilder spannableStringBuilder_second = new SpannableStringBuilder();
        String mainText_second = text.getText().toString();
        spannableStringBuilder_second.append(mainText_second);
        spannableStringBuilder_second.setSpan(new StyleSpan(Typeface.BOLD),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder_second.setSpan(new ForegroundColorSpan(Color.parseColor(color)),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannableStringBuilder_second);
    }

    private Callback<HelperStoryModel> retrofitCallback = new Callback<HelperStoryModel>() {

        @Override
        public void onResponse(Call<HelperStoryModel> call, Response<HelperStoryModel> response) {
            HelperStoryModel result = response.body();

            for (int i = 0; i < result.getData().size(); i++) {
                storyAdapterModels.add(result.getData().get(i));
//            }
                storyAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onFailure(Call<HelperStoryModel> call, Throwable t) {
            t.printStackTrace();
            Log.d("실ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ패", ">>>>>>>>>>>");
        }
    };
}
