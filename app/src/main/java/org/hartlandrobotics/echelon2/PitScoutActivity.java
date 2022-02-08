package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.models.TeamViewModel;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutingPagerAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class PitScoutActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    PitScoutingPagerAdapter viewPagerAdapter;
    AutoCompleteTextView teamNumberAutoComplete;

    TeamViewModel teamViewModel;
    List<Team> teams;
    List<String> teamNames;
    Team currentTeam;

    public static void launch(Context context){
        Intent intent = new Intent( context, PitScoutActivity.class );
        context.startActivity(intent);
    }

    private PitScout data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scout);

        teamNumberAutoComplete = findViewById(R.id.teamSelectionAutoComplete);

        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        teamViewModel.getAllTeams().observe( this, ts -> {

            teams = ts;

            teamNames = teams.stream()
                    .map( t -> t.getTeamNumber() + " - " + t.getNickname())
                    .sorted()
                    .collect(Collectors.toList());

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, teamNames);
            teamNumberAutoComplete.setAdapter(adapter);
            teamNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentTeam = teams.get(position);
                }
            });
        });

        data = new PitScout();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);



        viewPagerAdapter = new PitScoutingPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), data);
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getTabTitle(position))
        ).attach();
    }
    public boolean hasSelectedTeam(){
        return currentTeam != null;
    }
    private ViewGroup getTabViewGroup(TabLayout tabLayout){
        ViewGroup viewGroup = null;

        if(tabLayout != null && tabLayout.getChildCount() > 0){
            View view = tabLayout.getChildAt(0);
            if (view != null && view instanceof ViewGroup)
                viewGroup = (ViewGroup) view;

        }
        return viewGroup;
    }
}