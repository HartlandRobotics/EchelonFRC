package org.hartlandrobotics.echelonFRC.charts;

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

import org.hartlandrobotics.echelonFRC.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ChartEndGameTrendFragment extends Fragment {

    private LineChart endGameTrendChart;
    private AutoCompleteTextView teamNumberAutoComplete;
    private String teamNumber;

    private List<String> sortedTeamNumbers;
    ChartsActivity.TeamDataViewModel teamData;

    public ChartEndGameTrendFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_end_game_trend, container, false);

        endGameTrendChart = view.findViewById(R.id.endgame_line_chart);
        setupChart();

        teamNumberAutoComplete = view.findViewById(R.id.teamSelectionAutoComplete);
        teamNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ChartEndGameTrendFragment", "item clicked");
                String teamNumber = sortedTeamNumbers.get(position);
                teamData = ((ChartsActivity) getActivity()).getTeamData(teamNumber);

                setupChartData();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        setData(((ChartsActivity)getActivity()).getAllTeamNumbers());

        setupDropDown();
    }

    public void setData(List<TeamListViewModel> allTeamNumbers) {
        sortedTeamNumbers = allTeamNumbers.stream()
                .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());
    }

    public void setupDropDown(){
        if( sortedTeamNumbers == null ){
            Log.i("ChartEndGameTrendFragment", "No teams.");
            return;
        }
        Log.i("ChartEndGameTrendFragment", "setupDropDown with " + sortedTeamNumbers.size() + " teams");
        ArrayAdapter teamNumberAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_item, sortedTeamNumbers);
        teamNumberAutoComplete.setAdapter(teamNumberAdapter);
    }

    public void setupChart(){
        endGameTrendChart.setTouchEnabled(false);
        endGameTrendChart.setPinchZoom(false);
        endGameTrendChart.getDescription().setEnabled(false);

        XAxis xAxis = endGameTrendChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);



        YAxis yAxis = endGameTrendChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        endGameTrendChart.getAxisRight().setDrawAxisLine(false);


    }

    public void setupChartData(){
        if( teamData == null ) return;

        ArrayList<Entry> entries = new ArrayList<>();

        List<Integer> matchNumbers = teamData.getEndGameScores().keySet().stream().sorted().collect(Collectors.toList());

        for( Integer matchNumber : matchNumbers ){
            entries.add( new Entry(matchNumber, teamData.getEndGameScores().get(matchNumber)));
        }

        LineDataSet set1;
        if (endGameTrendChart.getData() != null &&
                endGameTrendChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) endGameTrendChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            endGameTrendChart.getData().notifyDataChanged();
            endGameTrendChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(entries, "End Game Scores");
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

            endGameTrendChart.setData(data);
        }

        endGameTrendChart.invalidate();
    }
}