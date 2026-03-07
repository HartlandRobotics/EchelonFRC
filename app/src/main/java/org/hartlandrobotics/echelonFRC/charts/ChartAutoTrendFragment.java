package org.hartlandrobotics.echelonFRC.charts;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.dao.MatchResultDao;
import org.hartlandrobotics.echelonFRC.database.entities.Team;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.TeamRepo;
import org.hartlandrobotics.echelonFRC.pitScouting.PitScoutActivity;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;
import org.hartlandrobotics.echelonFRC.utilities.MatchUtilities;
import org.hartlandrobotics.echelonFRC.utilities.TeamUtilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ChartAutoTrendFragment extends Fragment {

    private LineChart autoTrendChart;
    private AutoCompleteTextView teamNumberAutoComplete;

    private TeamRepo teamRepo;
    private List<Team> allTeams;
    private List<TeamListViewModel> allTeamViewModels;

    private MatchResultRepo matchResultRepo;
    private List<TeamDataViewModel2> allTeamData = new ArrayList<TeamDataViewModel2>();


    public ChartAutoTrendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_auto_trend, container, false);

        autoTrendChart = view.findViewById(R.id.auto_line_chart);
        setupChart();

        teamNumberAutoComplete = view.findViewById(R.id.teamSelectionAutoComplete);
        teamNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ChartAutoTrendFragment", "item clicked");

                int teamNumber = allTeamViewModels.get(position).getTeamInteger();

                allTeams.stream()
                        .filter(Team::isSelected)
                        .forEach(team -> {
                            team.setSelected(false);
                            //teamRepo.upsert(team);
                        });

                allTeams.stream()
                        .filter(team -> team.getTeamNumber() == teamNumber)
                        .forEach(team -> {
                            team.setSelected(true);
                            //TeamRepo repo = new TeamRepo(getActivity().getApplication());
                            //repo.upsert(team);
                        });

                TeamDataViewModel2 teamData = MatchUtilities.teamData(teamNumber, allTeamData);
                setupChartData(teamData);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        Application app = this.getActivity().getApplication();
        BlueAllianceStatus status = new BlueAllianceStatus(app);
        String currentEvent = status.getEventKey();

        teamRepo = new TeamRepo(app);
        teamRepo.getEventsWithTeams(currentEvent).observe(getViewLifecycleOwner(), eventWithTeams -> {
            allTeams = eventWithTeams.teams;
            allTeamViewModels = TeamUtilities.toListViewModels(allTeams);
            setupDropDown();

            Optional<TeamListViewModel> optSelectedListItem = allTeamViewModels.stream().filter(TeamListViewModel::isSelected).findFirst();

            matchResultRepo = new MatchResultRepo(app);
            matchResultRepo.getMatchResultsByEvent(currentEvent).observe(getViewLifecycleOwner(), matchResults -> {
                allTeamData = MatchUtilities.toViewModels(matchResults);

                String teamNumber = teamNumberAutoComplete.getText().toString();
                Optional<TeamDataViewModel2> optTeamData =  allTeamData.stream()
                        .filter(td -> String.valueOf(td.getTeamNumber()).equals( teamNumber))
                        .findFirst();
                optTeamData.ifPresent(this::setupChartData);
            });
        });


    }

    public void setupDropDown(){
        if( allTeamViewModels == null ){
            Log.i("ChartAutoTrendFragment", "No teams.");
            return;
        }

        Log.i("ChartAutoTrendFragment", "setupDropDown with " + allTeamViewModels.size() + " teams");

        List<String> teamNumbers = allTeamViewModels.stream()
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());

        Optional<TeamListViewModel> selectedTeam = allTeamViewModels.stream()
                .filter(TeamListViewModel::isSelected)
                .findFirst();

        ArrayAdapter<String> teamNumberAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, teamNumbers);
        teamNumberAutoComplete.setAdapter(teamNumberAdapter);

        if( selectedTeam.isPresent() ) {
            TeamListViewModel teamList = selectedTeam.get();
            teamNumberAutoComplete.setText(teamList.getTeamNumber(),false);
            Optional<TeamDataViewModel2> teamData = allTeamData
                    .stream()
                    .filter( t -> t.getTeamNumber() == teamList.getTeamInteger())
                    .findFirst();
            teamData.ifPresent(this::setupChartData);
        }
    }

    public void setupChart(){
        autoTrendChart.setTouchEnabled(false);
        autoTrendChart.setPinchZoom(false);
        autoTrendChart.getDescription().setEnabled(false);

        XAxis xAxis = autoTrendChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);

        YAxis yAxis = autoTrendChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        autoTrendChart.getAxisRight().setDrawAxisLine(false);
    }

    public void setupChartData(TeamDataViewModel2 teamData){
        if( teamData == null ) return;

        ArrayList<Entry> entries = new ArrayList<>();

        List<Integer> matchNumbers = teamData.getAutoScores()
                .keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList());

        for( Integer matchNumber : matchNumbers ){
            entries.add( new Entry(matchNumber, teamData.getAutoScores().get(matchNumber)));
        }

        LineDataSet set1;
        if (autoTrendChart.getData() != null &&
                autoTrendChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) autoTrendChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            autoTrendChart.getData().notifyDataChanged();
            autoTrendChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(entries, "Auto Scores");
            set1.setDrawCircles(true);
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 0f, 0f);
            set1.setColor(Color.parseColor("#444444") );
            set1.setCircleColor(Color.parseColor("#000000"));
            set1.setLineWidth(2f);//line size
            set1.setCircleRadius(5f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(10f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(5f);
            //set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(5.f);

            set1.setFillColor(Color.WHITE);

            set1.setDrawValues(true);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            autoTrendChart.setData(data);
        }

        autoTrendChart.invalidate();
    }
}