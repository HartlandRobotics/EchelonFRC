package org.hartlandrobotics.echelonFRC.matchSchedule;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.EchelonActivity;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.Match;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchScheduleActivity extends EchelonActivity {
    private static final String TAG = "MatchScheduleActivity";
    TabLayout tabLayout;
    ViewPager2 matchScheduleViewPager;
    MatchSchedulePagerAdapter matchSchedulePagerAdapter;


    EventRepo eventRepo;
    MatchResultRepo matchResultRepo;
    Map<String, List<MatchResult>> matchResultsByTeam = new HashMap<>();
    List<MatchScheduleViewModel> viewModels = new ArrayList<>();
    //TextInputLayout teamSearchLayout;
    //RecyclerView matchRecycler;
    //MatchListAdapter matchListAdapter;
    String deviceName;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MatchScheduleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_schedule);

        deviceName = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

        setupToolbar("Match Schedule");

        setupTabLayout();


//       teamSearchLayout = findViewById(R.id.team_search);
//        teamSearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //matchListAdapter.setTeamFilter(StringUtils.defaultIfBlank(s.toString(), StringUtils.EMPTY));
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        //matchListAdapter = new MatchListAdapter(this);

        //matchRecycler = findViewById(R.id.match_recycler);
        //matchRecycler.setLayoutManager(new LinearLayoutManager(this));
        //matchRecycler.setAdapter(matchListAdapter);
        //matchRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        BlueAllianceStatus status = new BlueAllianceStatus(getApplicationContext());
        String currentEvent = status.getEventKey();

        eventRepo = new EventRepo(getApplication());
        matchResultRepo = new MatchResultRepo(getApplication());

        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResults -> {
            Log.i(TAG, "getMatchResultsByEvent: " + matchResults.size());
            for (MatchResult matchResult : matchResults) {
                String teamKey = matchResult.getTeamKey();
                matchResultsByTeam.computeIfAbsent(teamKey, key -> new ArrayList<>());
                matchResultsByTeam.get(teamKey).add(matchResult);
            }

            eventRepo.getMatchesForEvent(currentEvent).observe(this, event -> {
                List<Match> matches = event.matches;

                Log.i(TAG, "getMatchesForEvent: " + matches.size());

                for (Match match : matches) {
                    MatchScheduleViewModel matchScheduleViewModel = new MatchScheduleViewModel();
                    matchScheduleViewModel.setMatchNumber(match.getMatchNumber());

                    int red1Average = getAverageByTeam(match.getRed1TeamKey());
                    matchScheduleViewModel.setRed1(match.getRed1TeamKey());
                    matchScheduleViewModel.setRed1Average(red1Average);
                    matchScheduleViewModel.setRed1Auto( getAverageAutoPointsByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1TeleOp( getAverageTeleOpPointsByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1End( getAverageEndPointsByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1StdDeviation(getStdDeviationByTeam(match.getRed1TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed1TeamKey())));

                    int red2Average = getAverageByTeam(match.getRed2TeamKey());
                    matchScheduleViewModel.setRed2(match.getRed2TeamKey());
                    matchScheduleViewModel.setRed2Average(red2Average);
                    matchScheduleViewModel.setRed2Auto( getAverageAutoPointsByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2TeleOp( getAverageTeleOpPointsByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2End( getAverageEndPointsByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2StdDeviation(getStdDeviationByTeam(match.getRed2TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed2TeamKey())));

                    int red3Average = getAverageByTeam(match.getRed3TeamKey());
                    matchScheduleViewModel.setRed3(match.getRed3TeamKey());
                    matchScheduleViewModel.setRed3Average(red3Average);
                    matchScheduleViewModel.setRed3Auto( getAverageAutoPointsByTeam(match.getRed3TeamKey()) );
                    matchScheduleViewModel.setRed3TeleOp( getAverageTeleOpPointsByTeam(match.getRed3TeamKey()) );
                    matchScheduleViewModel.setRed3End( getAverageEndPointsByTeam(match.getRed3TeamKey()) );
                    matchScheduleViewModel.setRed3StdDeviation(getStdDeviationByTeam(match.getRed3TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed3TeamKey())));

                    int blue1Average = getAverageByTeam(match.getBlue1TeamKey());
                    matchScheduleViewModel.setBlue1(match.getBlue1TeamKey());
                    matchScheduleViewModel.setBlue1Average(blue1Average);
                    matchScheduleViewModel.setBlue1Auto( getAverageAutoPointsByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1TeleOp( getAverageTeleOpPointsByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1End( getAverageEndPointsByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1StdDeviation(getStdDeviationByTeam(match.getBlue1TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue1TeamKey())));

                    int blue2Average = getAverageByTeam(match.getBlue2TeamKey());
                    matchScheduleViewModel.setBlue2(match.getBlue2TeamKey());
                    matchScheduleViewModel.setBlue2Average(blue2Average);
                    matchScheduleViewModel.setBlue2Auto( getAverageAutoPointsByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2TeleOp( getAverageTeleOpPointsByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2End( getAverageEndPointsByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2StdDeviation(getStdDeviationByTeam(match.getBlue2TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue2TeamKey())));

                    int blue3Average = getAverageByTeam(match.getBlue3TeamKey());
                    matchScheduleViewModel.setBlue3(match.getBlue3TeamKey());
                    matchScheduleViewModel.setBlue3Average(blue3Average);
                    matchScheduleViewModel.setBlue3Auto( getAverageAutoPointsByTeam(match.getBlue3TeamKey()) );
                    matchScheduleViewModel.setBlue3TeleOp( getAverageTeleOpPointsByTeam(match.getBlue3TeamKey()) );
                    matchScheduleViewModel.setBlue3End( getAverageEndPointsByTeam(match.getBlue3TeamKey()) );
                    matchScheduleViewModel.setBlue3StdDeviation(getStdDeviationByTeam(match.getBlue3TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue3TeamKey())));

                    viewModels.add(matchScheduleViewModel);
                }

                //matchListAdapter.setMatches(viewModels);
            });

        });
    }

    public void setupTabLayout() {
        tabLayout = findViewById(R.id.matchScheduleLayout);
        Log.i(TAG, "setupTabLayout: " + tabLayout);

        matchScheduleViewPager = findViewById(R.id.schedulePager);
        matchSchedulePagerAdapter = new MatchSchedulePagerAdapter(getSupportFragmentManager(), getLifecycle());
        matchScheduleViewPager.setAdapter(matchSchedulePagerAdapter);

        new TabLayoutMediator(tabLayout, matchScheduleViewPager, (tab, position) -> tab.setText(matchSchedulePagerAdapter.getTabTitle(position))).attach();
    }

    private int getSize(String teamKey) {
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if (teamMatchResults == null) return 0;
        return teamMatchResults.size();
    }

    private int getAverageAutoPointsByTeam(String teamKey) {
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if (teamMatchResults == null || teamMatchResults.size() == 0) return 0;

        int totalPoints = 0;
        for (MatchResult matchResult : teamMatchResults) {
            CurrentGamePoints currentGamePoints = new CurrentGamePoints(matchResult);
            totalPoints += currentGamePoints.getAutoPoints();
        }
        int averagePoints = totalPoints / teamMatchResults.size();
        return averagePoints;
    }

    private int getAverageTeleOpPointsByTeam(String teamKey) {
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if (teamMatchResults == null || teamMatchResults.isEmpty()) return 0;

        int totalPoints = 0;
        for (MatchResult matchResult : teamMatchResults) {
            CurrentGamePoints currentGamePoints = new CurrentGamePoints(matchResult);
            totalPoints += currentGamePoints.getTeleOpPoints();
        }
        int averagePoints = totalPoints / teamMatchResults.size();
        return averagePoints;
    }

    private int getAverageEndPointsByTeam(String teamKey) {
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if (teamMatchResults == null || teamMatchResults.size() == 0) return 0;

        int totalPoints = 0;
        for (MatchResult matchResult : teamMatchResults) {
            CurrentGamePoints currentGamePoints = new CurrentGamePoints(matchResult);
            totalPoints += currentGamePoints.getEndPoints();
        }
        int averagePoints = totalPoints / teamMatchResults.size();
        return averagePoints;
    }

    private int getAverageByTeam(String teamKey) {
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if (teamMatchResults == null || teamMatchResults.size() == 0) return 0;

        int totalScore = 0;
        for (MatchResult matchResult : teamMatchResults) {
            CurrentGamePoints points = new CurrentGamePoints(matchResult);

            int matchScore = 0;
            matchScore += points.getAutoPoints();
            matchScore += points.getTeleOpPoints();
            matchScore += points.getEndPoints();

            totalScore += matchScore;
        }

        int averageScore = totalScore / teamMatchResults.size();

        return averageScore;
    }

    private double getStdDeviationByTeam(String teamKey) {
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if (teamMatchResults == null || teamMatchResults.isEmpty()) return 0;

        int totalScore = 0;
        for (MatchResult matchResult : teamMatchResults) {
            CurrentGamePoints currentGamePoints = new CurrentGamePoints(matchResult);

            int matchScore = 0;
            matchScore += currentGamePoints.getAutoPoints();
            matchScore += currentGamePoints.getTeleOpPoints();
            matchScore += currentGamePoints.getEndPoints();

            totalScore += matchScore;
        }

        double averageScore = (double) totalScore / teamMatchResults.size();

        int totalDeviation = 0;
        for (MatchResult matchResult : teamMatchResults) {
            CurrentGamePoints currentGamePoints = new CurrentGamePoints(matchResult);
            int matchScore = 0;
            matchScore += currentGamePoints.getAutoPoints();
            matchScore += currentGamePoints.getTeleOpPoints();
            matchScore += currentGamePoints.getEndPoints();


            totalDeviation += (matchScore - averageScore) * (matchScore - averageScore);
        }

        double stdDeviation = Math.sqrt((float) totalDeviation / teamMatchResults.size());

        return stdDeviation;
    }

}