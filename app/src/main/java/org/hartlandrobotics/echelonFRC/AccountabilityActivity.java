package org.hartlandrobotics.echelonFRC;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;

import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.Match;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFRC.database.entities.Opr;
import org.hartlandrobotics.echelonFRC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchScoreRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.OprRepo;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountabilityActivity extends EchelonActivity {

    String TAG = "AccountabilityActivity";
    MatchRepo matchRepo;
    MatchResultRepo matchResultRepo;
    MatchScoreRepo matchScoreRepo;
    OprRepo oprRepo;

    List<AccountabilityViewModel> viewModels = new ArrayList<>();
    List<MatchResult> allMatchResults;
    List<MatchScore> allMatchScores;
    List<Opr> allOpr;

    RecyclerView accuracyRecycler;
    AccuracyListAdapter accuracyListAdapter;
    TextInputLayout calculationType;
    AutoCompleteTextView calculationTypeAutoComplete;
    String defaultCalculationTypes;
    TextInputLayout inaccuracyThreshold;

    MaterialButton calculate;

    public static void launch(Context context) {
        Intent intent = new Intent(context, AccountabilityActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuracy);

        setupToolbar("Accuracy");

        BlueAllianceStatus status = new BlueAllianceStatus(getApplicationContext());
        String currentEvent = status.getEventKey();

        calculationType = findViewById(R.id.calculationType);
        calculationTypeAutoComplete = findViewById(R.id.calculationTypeAutoComplete);
        String[] calculationTypes = getResources().getStringArray(R.array.calculation_type);
        defaultCalculationTypes = calculationTypes[0];
        ArrayAdapter<String> adapterCalculationType = new ArrayAdapter<String>(this, R.layout.dropdown_item, calculationTypes);
        calculationTypeAutoComplete.setAdapter(adapterCalculationType);
        calculationTypeAutoComplete.setText(defaultCalculationTypes, false);
        inaccuracyThreshold = findViewById(R.id.calculationThreshold);
        calculate = findViewById(R.id.calculateButton);
        calculate.setOnClickListener(this::CalculateOnClick);

        String[] allianceColors = {"red", "blue"};
        if (!viewModels.isEmpty()) viewModels.clear();

        accuracyListAdapter = new AccuracyListAdapter(this);
        accuracyRecycler = findViewById(R.id.accuracy_recycler);
        accuracyRecycler.setLayoutManager(new LinearLayoutManager(this));
        accuracyRecycler.setAdapter(accuracyListAdapter);
        accuracyRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        matchRepo = new MatchRepo(getApplication());
        matchScoreRepo = new MatchScoreRepo(getApplication());
        oprRepo = new OprRepo(getApplication());
        matchResultRepo = new MatchResultRepo(getApplication());

        matchRepo.getMatches().observe(this, matches -> {
            matchScoreRepo.getMatchScores().observe(this, matchScores -> {
                allMatchScores = matchScores;
                matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResults -> {
                    allMatchResults = matchResults;
                    oprRepo.getOprs().observe(this, oprs -> {
                        allOpr = oprs;
                                for (Match match : matches) {
                                    String currentMatchKey = match.getMatchKey();
                                    int currentMatchNumber = match.getMatchNumber();
                                    MatchScore matchScore = matchScores.stream().filter(ms -> ms.getMatchKey().equals(currentMatchKey)).collect(Collectors.toList()).get(0);

                                    for (String currentAllianceColor : allianceColors) {
                                        AccountabilityViewModel vm = new AccountabilityViewModel();
                                        int studentSum = 0;
                                        vm.setMatchNumber(currentMatchNumber);
                                        if (currentAllianceColor.equals("red")) {
                                            {
                                                String currentTeamKey = match.getRed1TeamKey();
                                                Optional<MatchResult> team1 = matchResults.stream()
                                                        .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                        .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                        .filter(mr -> mr.getAlliance().equals("red"))
                                                        .findFirst();
                                                if (team1.isPresent()) {
                                                    CurrentGamePoints cgp = new CurrentGamePoints(team1.get());
                                                    studentSum += cgp.getTotalPoints();
                                                }


                                                vm.setTablet1Name(currentTeamKey.substring(3));
                                            }
                                            {
                                                String currentTeamKey = match.getRed2TeamKey();
                                                Optional<MatchResult> team2 = matchResults.stream()
                                                        .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                        .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                        .filter(mr -> mr.getAlliance().equals("red"))
                                                        .findFirst();
                                                if (team2.isPresent()) {
                                                    CurrentGamePoints cgp = new CurrentGamePoints(team2.get());
                                                    studentSum += cgp.getTotalPoints();
                                                }

                                                vm.setTablet2Name(currentTeamKey.substring(3));
                                            }
                                            {
                                                String currentTeamKey = match.getRed3TeamKey();
                                                Optional<MatchResult> team3 = matchResults.stream()
                                                        .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                        .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                        .filter(mr -> mr.getAlliance().equals("red"))
                                                        .findFirst();
                                                if (team3.isPresent()) {
                                                    CurrentGamePoints cgp = new CurrentGamePoints(team3.get());
                                                    studentSum += cgp.getTotalPoints();
                                                }

                                                vm.setTablet3Name(currentTeamKey.substring(3));
                                            }
                                            vm.setBlueAlliancePoints(matchScore.getRedTotal() - matchScore.getRedFoul());
                                            vm.setStudentPoints(studentSum);
                                            vm.setPercentInaccuracy(Math.abs(vm.getBlueAlliancePoints() - vm.getStudentPoints()));
                                        } else if (currentAllianceColor.equals("blue")) {
                                            {
                                                String currentTeamKey = match.getBlue1TeamKey();
                                                Optional<MatchResult> team1 = matchResults.stream()
                                                        .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                        .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                        .filter(mr -> mr.getAlliance().equals("blue"))
                                                        .findFirst();
                                                if (team1.isPresent()) {
                                                    CurrentGamePoints cgp = new CurrentGamePoints(team1.get());
                                                    studentSum += cgp.getTotalPoints();
                                                }

                                                vm.setTablet1Name(currentTeamKey.substring(3));
                                            }
                                            {
                                                String currentTeamKey = match.getBlue2TeamKey();
                                                Optional<MatchResult> team2 = matchResults.stream()
                                                        .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                        .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                        .filter(mr -> mr.getAlliance().equals("blue"))
                                                        .findFirst();
                                                if (team2.isPresent()) {
                                                    CurrentGamePoints cgp = new CurrentGamePoints(team2.get());
                                                    studentSum += cgp.getTotalPoints();
                                                }

                                                vm.setTablet2Name(currentTeamKey.substring(3));
                                            }
                                            {
                                                String currentTeamKey = match.getBlue3TeamKey();
                                                Optional<MatchResult> team3 = matchResults.stream()
                                                        .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                        .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                        .filter(mr -> mr.getAlliance().equals("blue"))
                                                        .findFirst();
                                                if (team3.isPresent()) {
                                                    CurrentGamePoints cgp = new CurrentGamePoints(team3.get());
                                                    studentSum += cgp.getTotalPoints();
                                                }

                                                vm.setTablet3Name(currentTeamKey.substring(3));
                                            }
                                            vm.setBlueAlliancePoints(matchScore.getBlueTotal() - matchScore.getBlueFoul());
                                            vm.setStudentPoints(studentSum);
                                            vm.setPercentInaccuracy(Math.abs(vm.getBlueAlliancePoints() - vm.getStudentPoints()));
                                        }

                                        vm.setAllianceColor(currentAllianceColor);
                                        viewModels.add(vm);
                                    }
                                }
                        accuracyListAdapter.setAccuracies(viewModels);

                    });

                });
            });
        });
    }

    public void CalculateOnClick(View view) {
        BlueAllianceStatus status = new BlueAllianceStatus(getApplicationContext());
        String eventKey = status.getEventKey();
        boolean studentScore = calculationTypeAutoComplete.getText().toString().equals("StudentScore");
        double threshold = Double.parseDouble(inaccuracyThreshold.getEditText().getText().toString());

        for (MatchResult mr : allMatchResults) {
            if (studentScore) {
                mr.setContribution(0);
                matchResultRepo.upsert(mr);
            } else {

                AccountabilityViewModel vm = viewModels.stream()
                        .filter(viewModel -> viewModel.getMatchNumber() == MatchKeyToNumber(mr.getMatchKey()))
                        .filter(viewModel -> viewModel.getAllianceColor().equals(mr.getAlliance()))
                        .findFirst().get();

                if( vm.getPercentInaccuracy() > threshold ){
                    Log.i(TAG, "> threshold");
                    MatchScore currentMatchScore = allMatchScores.stream().filter(ms ->{
                        return ms.getMatchNumber() == vm.getMatchNumber();
                    })
                            .findFirst().get()
                    ;
                    int team1Number = Integer.parseInt(vm.getTablet1Name());
                    Opr team1Opr = allOpr.stream().filter( opr -> TeamKeyToNumber(opr.getTeamKey()) == team1Number).findFirst().get();
                    double team1Points = team1Opr.getOpr() - team1Opr.getFoul();

                    int team2Number =  Integer.parseInt(vm.getTablet2Name());
                    Opr team2Opr = allOpr.stream().filter( opr -> TeamKeyToNumber(opr.getTeamKey()) == team2Number).findFirst().get();
                    double team2Points = team1Opr.getOpr() - team2Opr.getFoul();

                    int team3Number = Integer.parseInt(vm.getTablet3Name());
                    Opr team3Opr = allOpr.stream().filter( opr -> TeamKeyToNumber(opr.getTeamKey()) == team3Number).findFirst().get();
                    double team3Points = team1Opr.getOpr() - team3Opr.getFoul();

                    double totalPoints = team1Points + team2Points + team3Points;

                    double team1Percentage = team1Points/totalPoints;
                    double team2Percentage = team2Points/totalPoints;
                    double team3Percentage = team3Points/totalPoints;

                    if(TeamKeyToNumber(mr.getTeamKey()) == team1Number){
                        mr.setContribution((int)(team1Points * team1Percentage));
                    }
                    else if(TeamKeyToNumber(mr.getTeamKey()) == team2Number){
                        mr.setContribution((int)(team2Points * team2Percentage));
                    }
                    else if(TeamKeyToNumber(mr.getTeamKey()) == team3Number) {
                        mr.setContribution((int) (team3Points * team3Percentage));
                    }

                    int currentScore = 0;
                    if( vm.getAllianceColor().equals("red")){
                        currentScore = currentMatchScore.getRedTotal() - currentMatchScore.getRedFoul();
                    } else if (vm.getAllianceColor().equals("blue")){
                        currentScore = currentMatchScore.getBlueTotal() - currentMatchScore.getBlueFoul();
                    }

                    matchResultRepo.upsert(mr);

                } else {

                    Log.i(TAG, "< threshold ");

                }



                int i;
                i = 10;


            }

            //for (AccountabilityViewModel vm : viewModels) {

            //}
        }
    }

    public int MatchKeyToNumber(String matchKey) {
        if(matchKey == null) return 0;
        //Log.i(TAG,matchKey);
        if(matchKey.length() <= 1) return 0;

        //String matchValueStr = StringUtils.defaultIfBlank(matchKey, "2025_qm0" );
        //Log.i(TAG, String.valueOf(matchKey.length()));
        String matchNumberStr = matchKey.split("_")[1].substring(2);
        return Integer.parseInt(matchNumberStr);
    }

    private int TeamKeyToNumber(String teamKey){
        return Integer.parseInt(teamKey.substring(3));
    }

    public class AccuracyViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView matchNumberText;
        private MaterialTextView blueAllianceScoreText;
        private MaterialTextView studentScoreText;
        private MaterialTextView inaccuracyPercentText;
        private MaterialTextView team1Text;
        private MaterialTextView team2Text;
        private MaterialTextView team3Text;

        private AccountabilityViewModel accountabilityViewModel;

        AccuracyViewHolder(View itemView) {
            super(itemView);

            matchNumberText = itemView.findViewById(R.id.match_number);
            blueAllianceScoreText = itemView.findViewById(R.id.blue_alliance_score);
            studentScoreText = itemView.findViewById(R.id.student_score);
            inaccuracyPercentText = itemView.findViewById(R.id.match_percent_differance);
            team1Text = itemView.findViewById(R.id.team1);
            team2Text = itemView.findViewById(R.id.team2);
            team3Text = itemView.findViewById(R.id.team3);
        }

        public void setMatch(AccountabilityViewModel vm) {
            this.accountabilityViewModel = vm;

            matchNumberText.setText(String.valueOf(vm.getMatchNumber()));

            int color = Color.GRAY;
            if (vm.getAllianceColor().equals("red")) {
                color = getColor(R.color.redAlliance);

            }
            if (vm.getAllianceColor().equals("blue")) {
                color = getColor(R.color.blueAlliance);
            }
            blueAllianceScoreText.setTextColor(color);
            blueAllianceScoreText.setText(String.valueOf(vm.getBlueAlliancePoints()));

            studentScoreText.setTextColor(color);
            studentScoreText.setText(String.valueOf(vm.getStudentPoints()));

            inaccuracyPercentText.setText(String.valueOf(vm.getPercentInaccuracy()));

            team1Text.setTextColor(color);
            team1Text.setText(vm.getTablet1Name());

            team2Text.setTextColor(color);
            team2Text.setText(vm.getTablet2Name());

            team3Text.setTextColor(color);
            team3Text.setText(vm.getTablet3Name());


        }

        public void setDisplayText(String displayText) {
            matchNumberText.setText(displayText);
        }
    }

    public class AccuracyListAdapter extends RecyclerView.Adapter<AccuracyViewHolder> {
        private final LayoutInflater inflater;
        private List<AccountabilityViewModel> allHolderViewModels;
        private List<AccountabilityViewModel> holderViewModels;
        private String teamFilter = StringUtils.EMPTY;

        AccuracyListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public AccuracyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_accuracy, parent, false);
            return new AccuracyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AccuracyViewHolder holder, int position) {
            if (holderViewModels != null) {
                holder.setMatch(holderViewModels.get(position));
            } else {
                holder.setDisplayText("No Match Data Yet...");
            }
        }

        void setAccuracies(List<AccountabilityViewModel> vms) {
            allHolderViewModels = vms;

            holderViewModels = vms.stream()
                    .sorted(Comparator.comparingDouble(AccountabilityViewModel::getPercentInaccuracy).reversed())
                    .collect(Collectors.toList());


            //            viewModels.stream().sorted(Comparator.comparingDouble(AccountabilityViewModel::getPercentInaccuracy).reversed())
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return holderViewModels == null ? 0 : holderViewModels.size();
        }
    }


}


/*
eventRepo = new EventRepo(getApplication());
        matchRepo = new MatchRepo(getApplication());
        matchResultRepo = new MatchResultRepo(getApplication());

        eventRepo.getMatchesForEvent(currentEvent).observe(this, e -> {
            matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResultList -> {
                for (Match m : e.matches) {
                    String currentMatchKey = m.getMatchKey();


                    int BlueScore = m.getBlueScore();
                    int RedScore = m.getRedScore();

                    int studentRedScore = 0;
                    int studentBlueScore = 0;
                    List<MatchResult> matchResultByMatch = matchResultList.stream()
                            .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                            .collect(Collectors.toList());

                    for (MatchResult mr : matchResultByMatch) {
                        String alliance = mr.getAlliance();
                        CurrentGamePoints cg = new CurrentGamePoints(mr);
                        if (alliance.equals("red")) {
                            studentRedScore += cg.getAutoPoints() + cg.getTeleOpPoints() + cg.getEndPoints();
                        }
                        if (alliance.equals("blue")) {
                            studentBlueScore += cg.getAutoPoints() + cg.getTeleOpPoints() + cg.getEndPoints();
                        }
                    }



                }
            });

        });

 */