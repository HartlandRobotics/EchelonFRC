package org.hartlandrobotics.echelonFRC.matchSchedule;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.charts.TeamListViewModel;
import org.hartlandrobotics.echelonFRC.database.entities.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class MatchSchedulePlayoffsFragment extends Fragment {

    private List<String> sortedTeamNumbers;
    private List<TeamListViewModel> allTeamNumbers;

    TextInputLayout team1Layout;
    TextInputLayout team2Layout;
    TextInputLayout team3Layout;
    TextInputLayout team4Layout;
    TextInputLayout team5Layout;
    TextInputLayout team6Layout;

    AutoCompleteTextView team1AutoComplete;
    AutoCompleteTextView team2AutoComplete;
    AutoCompleteTextView team3AutoComplete;
    AutoCompleteTextView team4AutoComplete;
    AutoCompleteTextView team5AutoComplete;
    AutoCompleteTextView team6AutoComplete;

    public MatchSchedulePlayoffsFragment() {
        // Required empty public constructor
    }

    public static MatchSchedulePlayoffsFragment newInstance(String param1, String param2) {
        MatchSchedulePlayoffsFragment fragment = new MatchSchedulePlayoffsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_schedule_quals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        //setData(((MatchScheduleActivity)getActivity()).getAllTeamNumbers());
    }

    private void setupControls(View view) {
        team1Layout = view.findViewById(R.id.team1Selection);
        team2Layout = view.findViewById(R.id.team2Selection);
        team3Layout = view.findViewById(R.id.team3Selection);
        team4Layout = view.findViewById(R.id.team4Selection);
        team5Layout = view.findViewById(R.id.team5Selection);
        team6Layout = view.findViewById(R.id.team6Selection);

        team1AutoComplete = view.findViewById(R.id.team1SelectionAutoComplete);
        team2AutoComplete = view.findViewById(R.id.team2SelectionAutoComplete);
        team3AutoComplete = view.findViewById(R.id.team3SelectionAutoComplete);
        team4AutoComplete = view.findViewById(R.id.team4SelectionAutoComplete);
        team5AutoComplete = view.findViewById(R.id.team5SelectionAutoComplete);
        team6AutoComplete = view.findViewById(R.id.team6SelectionAutoComplete);
    }

    public void populateDataFromControls() {
        //fill later
    }

    public void populateControlFromData() {
        //fill later
    }

    public void setData(List<TeamListViewModel> allTeamNumbers) {
        this.allTeamNumbers = new ArrayList<TeamListViewModel>(allTeamNumbers);

        sortedTeamNumbers = this.allTeamNumbers.stream()
                .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());
        sortedTeamNumbers.add(0, StringUtils.EMPTY);
    }

    public void setVisibility() {
        //fill later
    }
}