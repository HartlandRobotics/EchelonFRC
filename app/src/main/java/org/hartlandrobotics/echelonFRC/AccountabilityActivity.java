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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    EventRepo eventRepo;
    MatchRepo matchRepo;
    MatchResultRepo matchResultRepo;
    MatchScoreRepo matchScoreRepo;
    OprRepo oprRepo;

    List<AccountabilityViewModel> viewModels = new ArrayList<>();

    RecyclerView accuracyRecycler;
    AccuracyListAdapter accuracyListAdapter;
    TextInputLayout calculationType;
    AutoCompleteTextView calculationTypeAutoComplete;
    String defaultCalculationTypes;

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
        calculationTypeAutoComplete.setText(defaultCalculationTypes,false);

        String[] allianceColors = {"red", "blue"};
        if( !viewModels.isEmpty() ) viewModels.clear();

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
        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResults -> {
            for(Match match : matches) {
                String currentMatchKey = match.getMatchKey();
                int currentMatchNumber = match.getMatchNumber();
                MatchScore matchScore = matchScores.stream().filter(ms -> ms.getMatchKey().equals(currentMatchKey)).collect(Collectors.toList()).get(0);

                for (String currentAllianceColor : allianceColors){
                    AccountabilityViewModel vm = new AccountabilityViewModel();
                    int studentSum = 0;
                    vm.setMatchNumber(currentMatchNumber);
                    if( currentAllianceColor.equals("red") ){
                        {
                            String currentTeamKey = match.getRed1TeamKey();
                            Optional<MatchResult> team1 = matchResults.stream()
                                    .filter(mr -> mr.getMatchKey().equals(currentMatchKey) )
                                    .filter(mr -> mr.getTeamKey().equals(currentTeamKey) )
                                    .filter(mr -> mr.getAlliance().equals("red"))
                                    .findFirst();
                            if(team1.isPresent()){
                                CurrentGamePoints cgp = new CurrentGamePoints(team1.get());
                                studentSum += cgp.getTotalPoints();
                            }


                            vm.setTablet1Name(currentTeamKey.substring(3));
                        }
                        {
                            String currentTeamKey = match.getRed2TeamKey();
                            Optional<MatchResult> team2 = matchResults.stream()
                                    .filter(mr -> mr.getMatchKey().equals(currentMatchKey) )
                                    .filter(mr -> mr.getTeamKey().equals(currentTeamKey) )
                                    .filter(mr -> mr.getAlliance().equals("red"))
                                    .findFirst();
                            if(team2.isPresent()){
                                CurrentGamePoints cgp = new CurrentGamePoints(team2.get());
                                studentSum += cgp.getTotalPoints();
                            }

                            vm.setTablet2Name(currentTeamKey.substring(3));
                        }
                        {
                            String currentTeamKey = match.getRed3TeamKey();
                            Optional<MatchResult> team3 = matchResults.stream()
                                    .filter(mr -> mr.getMatchKey().equals(currentMatchKey) )
                                    .filter(mr -> mr.getTeamKey().equals(currentTeamKey) )
                                    .filter(mr -> mr.getAlliance().equals("red"))
                                    .findFirst();
                            if(team3.isPresent()){
                                CurrentGamePoints cgp = new CurrentGamePoints(team3.get());
                                studentSum += cgp.getTotalPoints();
                            }

                            vm.setTablet3Name(currentTeamKey.substring(3));
                        }
                        vm.setBlueAlliancePoints(matchScore.getRedTotal() - matchScore.getRedFoul());
                        vm.setStudentPoints(studentSum);
                        vm.setPercentInaccuracy(Math.abs(vm.getBlueAlliancePoints() - vm.getStudentPoints()));
                    } else if (currentAllianceColor.equals("blue")){
                        {
                            String currentTeamKey = match.getBlue1TeamKey();
                            Optional<MatchResult> team1 = matchResults.stream()
                                    .filter(mr -> mr.getMatchKey().equals(currentMatchKey) )
                                    .filter(mr -> mr.getTeamKey().equals(currentTeamKey) )
                                    .filter(mr -> mr.getAlliance().equals("blue"))
                                    .findFirst();
                            if(team1.isPresent()){
                                CurrentGamePoints cgp = new CurrentGamePoints(team1.get());
                                studentSum += cgp.getTotalPoints();
                            }

                            vm.setTablet1Name(currentTeamKey.substring(3));
                        }
                        {
                            String currentTeamKey = match.getBlue2TeamKey();
                            Optional<MatchResult> team2 = matchResults.stream()
                                    .filter(mr -> mr.getMatchKey().equals(currentMatchKey) )
                                    .filter(mr -> mr.getTeamKey().equals(currentTeamKey) )
                                    .filter(mr -> mr.getAlliance().equals("blue"))
                                    .findFirst();
                            if(team2.isPresent()){
                                CurrentGamePoints cgp = new CurrentGamePoints(team2.get());
                                studentSum += cgp.getTotalPoints();
                            }

                            vm.setTablet2Name(currentTeamKey.substring(3));
                        }
                        {
                            String currentTeamKey = match.getBlue3TeamKey();
                            Optional<MatchResult> team3 = matchResults.stream()
                                    .filter(mr -> mr.getMatchKey().equals(currentMatchKey) )
                                    .filter(mr -> mr.getTeamKey().equals(currentTeamKey) )
                                    .filter(mr -> mr.getAlliance().equals("blue"))
                                    .findFirst();
                            if(team3.isPresent()){
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

//        matchScoreRepo.getMatchScores().observe(this, matchScores -> {
//
//            matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResults -> {
//                        for(MatchScore matchScore : matchScores) {
//                            for (String allianceColor : allianceColors) {
//                                AccountabilityViewModel vm = new AccountabilityViewModel();
//                                vm.setMatchNumber(matchScore.getMatchNumber());
//
//                                if (allianceColor.equals("red")) {
//                                    vm.setAllianceColor(allianceColor);
//                                    vm.setBlueAlliancePoints(matchScore.getRedTotal() - matchScore.getRedFoul());
//                                    List<MatchResult> currentMR = matchResults.stream()
//                                            .filter(matchResult -> matchResult.getMatchKey().equals(currentEvent))
//                                            .filter(matchResult -> matchResult.getAlliance().equals(allianceColor))
//                                            .collect(Collectors.toList());
//
//
//                                    viewModels.add(vm);
//                                }
//                                if (allianceColor.equals("blue")) {
//                                    vm.setAllianceColor(allianceColor);
//                                    vm.setBlueAlliancePoints(matchScore.getBlueTotal() - matchScore.getBlueFoul());
//                                    viewModels.add(vm);
//
//                                }
//                            }
//                        }
//            });
//
//
//
//
//
//        });
//            accuracyListAdapter.setAccuracies(viewModels);
//

    }






    public class AccuracyViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView matchNumberText;
        private MaterialTextView blueAllianceScoreText;
        private MaterialTextView studentScoreText;
        private MaterialTextView inaccuracyPercentText;
        private MaterialTextView team1Text;
        private MaterialTextView team2Text;
        private MaterialTextView team3Text;

        private AccountabilityViewModel accountabilityViewModel;

        AccuracyViewHolder(View itemView){
            super(itemView);

            matchNumberText = itemView.findViewById(R.id.match_number);
            blueAllianceScoreText = itemView.findViewById(R.id.blue_alliance_score);
            studentScoreText = itemView.findViewById(R.id.student_score);
            inaccuracyPercentText = itemView.findViewById(R.id.match_percent_differance);
            team1Text = itemView.findViewById(R.id.team1);
            team2Text = itemView.findViewById(R.id.team2);
            team3Text = itemView.findViewById(R.id.team3);
        }

        public void setMatch(AccountabilityViewModel vm){
            this.accountabilityViewModel = vm;

            matchNumberText.setText( String.valueOf( vm.getMatchNumber() ) );

            int color = Color.GRAY;
            if( vm.getAllianceColor().equals("red") ){
                color = getColor(R.color.redAlliance);
            }
            if( vm.getAllianceColor().equals("blue") ){
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

        public void setDisplayText(String displayText){
            matchNumberText.setText(displayText);
        }
    }

    public class AccuracyListAdapter extends RecyclerView.Adapter<AccuracyViewHolder>{
        private final LayoutInflater inflater;
        private List<AccountabilityViewModel> allHolderViewModels;
        private List<AccountabilityViewModel> holderViewModels;
        private String teamFilter = StringUtils.EMPTY;

        AccuracyListAdapter(Context context){
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
            if(holderViewModels != null){
                holder.setMatch(holderViewModels.get(position));
            }else{
                holder.setDisplayText("No Match Data Yet...");
            }
        }

        void setAccuracies(List<AccountabilityViewModel> vms){
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