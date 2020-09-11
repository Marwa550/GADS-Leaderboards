package com.example.gadsleaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.model.HoursTopLearner;
import com.example.gadsleaderboard.model.SkillsTopLearner;
import com.example.gadsleaderboard.model.TopLearner;

import java.util.List;

public class LeaderDataAdapter extends RecyclerView.Adapter<LeaderDataAdapter.LeaderDataViewHolder>  {

    List<TopLearner> topLearnerList;

    public LeaderDataAdapter(List<TopLearner> topLearnerList) {
        this.topLearnerList = topLearnerList;
    }

    @NonNull
    @Override
    public LeaderDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_learner_row, parent, false);

        return new LeaderDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderDataViewHolder leaderDataViewHolder, int position) {
        TopLearner topLearner = topLearnerList.get(position);
        leaderDataViewHolder.nameTV.setText(topLearner.getName());
        if(topLearner instanceof SkillsTopLearner) {
            leaderDataViewHolder.badgeIV.setImageResource(R.drawable.skill_iq_badge);
            leaderDataViewHolder.dataTv.setText(((SkillsTopLearner) topLearner).getScore() +
                    " skill IQ Score, " + topLearner.getCountry());
        } else if(topLearner instanceof HoursTopLearner) {
            leaderDataViewHolder.badgeIV.setImageResource(R.drawable.learner_badge);
            leaderDataViewHolder.dataTv.setText(((HoursTopLearner) topLearner).getHours() +
                    " learning hours, " + topLearner.getCountry());
        }
    }

    @Override
    public int getItemCount() {
        return topLearnerList.size();
    }

    public class LeaderDataViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView badgeIV;
        AppCompatTextView nameTV;
        AppCompatTextView dataTv;

        public LeaderDataViewHolder(@NonNull View itemView) {
            super(itemView);

            badgeIV = itemView.findViewById(R.id.badge_iv);
            nameTV = itemView.findViewById(R.id.leader_name_tv);
            dataTv = itemView.findViewById(R.id.leader_data_tv);
        }
    }

    public void setData(List<TopLearner> topLearnerList) {
        this.topLearnerList = topLearnerList;
        notifyDataSetChanged();
    }
}
