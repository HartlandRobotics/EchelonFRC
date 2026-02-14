package org.hartlandrobotics.echelonFRC.blueAlliance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelonFRC.EchelonActivity;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

public class ScoreActivity extends EchelonActivity {
    BlueAllianceStatus tbaStatus;

    TabLayout scoreTabLayout;
    ViewPager2 scoreViewPager;
    ScorePagerAdapter scorePagerAdapter;

    public static void launch(Context context){
        Intent intent = new Intent(context, ScoreActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        setupToolbar("Accuracy Config");

        tbaStatus = new BlueAllianceStatus(getApplicationContext());

        scoreViewPager = findViewById(R.id.scoreViewPager);
        scoreTabLayout = findViewById(R.id.scoreTabLayout);

        scorePagerAdapter = new ScorePagerAdapter(getSupportFragmentManager(), getLifecycle());
        scoreViewPager.setAdapter(scorePagerAdapter);

        new TabLayoutMediator(scoreTabLayout, scoreViewPager,
                (tab, position) -> tab.setText(scorePagerAdapter.getTabTitle(position))).attach();


    }
}
