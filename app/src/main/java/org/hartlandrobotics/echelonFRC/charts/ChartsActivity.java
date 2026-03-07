package org.hartlandrobotics.echelonFRC.charts;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelonFRC.EchelonActivity;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.TeamRepo;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChartsActivity extends EchelonActivity {

    TabLayout tabLayout;
    ViewPager2 chartViewPager;
    ChartPagerAdapter chartPagerAdapter;

    public static void launch(Context context){
        Intent intent = new Intent(context, ChartsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        setupToolbar("Charts");

        setupTabLayout();
    }

    public void setupTabLayout(){
        tabLayout = findViewById(R.id.tabLayout);
        chartViewPager = findViewById(R.id.viewPager);
        chartPagerAdapter = new ChartPagerAdapter(getSupportFragmentManager(), getLifecycle());
        chartViewPager.setAdapter(chartPagerAdapter);

        new TabLayoutMediator(tabLayout, chartViewPager, (tab, position) -> tab.setText(chartPagerAdapter.getTabTitle(position))).attach();
    }
}