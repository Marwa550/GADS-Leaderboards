package com.example.gadsleaderboard.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.adapter.LeaderDataAdapter;
import com.example.gadsleaderboard.model.HoursTopLearner;
import com.example.gadsleaderboard.model.SkillsTopLearner;
import com.example.gadsleaderboard.model.TopLearner;
import com.example.gadsleaderboard.network.GADSApi;
import com.example.gadsleaderboard.network.NetworkConfig;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopLearnerFragment extends Fragment {

    private List<TopLearner> topLearnerList = new ArrayList<>();
    private RecyclerView topLearnerRecyclerView;
    private LeaderDataAdapter leaderDataAdapter;
    Call<List<SkillsTopLearner>> callGetSkillsTopLearner;
    Call<List<HoursTopLearner>> callGetHoursTopLearner;
    boolean isSkillsListSelected;
    ProgressBar topLearnerProgressBar;

    public TopLearnerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isSkillsListSelected = getArguments().getBoolean("second_tab");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_learner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(NetworkConfig.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        GADSApi client =  retrofit.create(GADSApi.class);
        callGetSkillsTopLearner = client.getSkillsTopLearner();
        callGetHoursTopLearner = client.getHoursTopLearner();

        topLearnerProgressBar = view.findViewById(R.id.top_learner_progress_bar);
        topLearnerRecyclerView = view.findViewById(R.id.top_learner_rv);
        leaderDataAdapter = new LeaderDataAdapter(topLearnerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        topLearnerRecyclerView.setLayoutManager(mLayoutManager);
        topLearnerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topLearnerRecyclerView.setAdapter(leaderDataAdapter);
        topLearnerRecyclerView.setVisibility(View.INVISIBLE);
        topLearnerProgressBar.setVisibility(View.VISIBLE);

        if(isSkillsListSelected) {
            getSkillsTopLearner();
        } else {
            getHoursTopLearner();
        }
    }

    void getSkillsTopLearner() {
        callGetSkillsTopLearner.enqueue(new Callback<List<SkillsTopLearner>>() {
            @Override
            public void onResponse(Call<List<SkillsTopLearner>> call, Response<List<SkillsTopLearner>> response) {
                if (response.body() != null) {
                    List<TopLearner> topLearnerList = new ArrayList<>(response.body());
                    leaderDataAdapter.setData(topLearnerList);
                    setRecyclerViewVisible();
                }
            }

            @Override
            public void onFailure(Call<List<SkillsTopLearner>> call, Throwable t) {
            }
        });
    }

    void getHoursTopLearner() {
        callGetHoursTopLearner.enqueue(new Callback<List<HoursTopLearner>>() {
            @Override
            public void onResponse(Call<List<HoursTopLearner>> call, Response<List<HoursTopLearner>> response) {
                if (response.body() != null) {
                    List<TopLearner> topLearnerList = new ArrayList<>(response.body());
                    leaderDataAdapter.setData(topLearnerList);
                    setRecyclerViewVisible();
                }
            }

            @Override
            public void onFailure(Call<List<HoursTopLearner>> call, Throwable t) {
            }
        });
    }

    void setRecyclerViewVisible() {
        topLearnerRecyclerView.setVisibility(View.VISIBLE);
        topLearnerProgressBar.setVisibility(View.INVISIBLE);
    }
}