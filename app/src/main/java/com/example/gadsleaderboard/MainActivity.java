package com.example.gadsleaderboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gadsleaderboard.fragment.TopLearnerFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPager2 topLearnerVP;
    TopLearnerPagerAdapter topLearnerPagerAdapter;
    TabLayout topLearnerTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar topLearnerToolbar = findViewById(R.id.top_learner_toolbar);
        setSupportActionBar(topLearnerToolbar);

        Button submitBtn = findViewById(R.id.top_learner_submit_btn);
        submitBtn.setOnClickListener(
            (view) -> {
                Intent submitIntent = new Intent(MainActivity.this, SubmitionActivity.class);
                startActivity(submitIntent);
        });

        topLearnerPagerAdapter = new TopLearnerPagerAdapter(getSupportFragmentManager(), this.getLifecycle());
        topLearnerVP = findViewById(R.id.top_learner_vp);
        topLearnerVP.setAdapter(topLearnerPagerAdapter);
        topLearnerTabLayout = findViewById(R.id.top_learner_tab_layout);
        topLearnerTabLayout.setTabTextColors(getResources().getColor(android.R.color.darker_gray),
                getResources().getColor(android.R.color.white));
        topLearnerTabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
        new TabLayoutMediator(topLearnerTabLayout, topLearnerVP,
            (tab, position) -> {
                switch(position) {
                    case 0:
                        tab.setText("Learning Leaders");
                        break;
                    case 1:
                        tab.setText("Skill IQ Leaders");
                        break;
                }
            }
        ).attach();
    }

    class TopLearnerPagerAdapter extends FragmentStateAdapter {
        public TopLearnerPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle bundle;
            switch (position) {
                case 0:
                    TopLearnerFragment hoursTopLearnerFragment = new TopLearnerFragment();
                    bundle = new Bundle();
                    bundle.putBoolean("second_tab", false);
                    hoursTopLearnerFragment.setArguments(bundle);
                    return hoursTopLearnerFragment;
                case 1:
                    TopLearnerFragment skillsTopLearnerFragment = new TopLearnerFragment();
                    bundle = new Bundle();
                    bundle.putBoolean("second_tab", true);
                    skillsTopLearnerFragment.setArguments(bundle);
                    return skillsTopLearnerFragment;
            }
            return new TopLearnerFragment();
        }

        @Override
        public int getItemCount() { return 2; }
    }
}
