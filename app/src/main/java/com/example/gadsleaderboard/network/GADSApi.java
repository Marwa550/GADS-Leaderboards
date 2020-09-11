package com.example.gadsleaderboard.network;

import com.example.gadsleaderboard.model.HoursTopLearner;
import com.example.gadsleaderboard.model.SkillsTopLearner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GADSApi {
    @GET(NetworkConfig.skillIQLeadersURL)
    Call<List<SkillsTopLearner>> getSkillsTopLearner();

    @GET(NetworkConfig.learningLeadersURL)
    Call<List<HoursTopLearner>> getHoursTopLearner();
}
