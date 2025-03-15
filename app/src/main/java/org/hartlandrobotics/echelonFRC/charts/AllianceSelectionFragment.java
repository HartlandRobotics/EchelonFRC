package org.hartlandrobotics.echelonFRC.charts;

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

import org.hartlandrobotics.echelonFRC.R;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AllianceSelectionFragment extends Fragment {
    private AutoCompleteTextView teamNumberAutoComplete;
    private List<String> sortedTeamNumbers;
    ChartsActivity.TeamDataViewModel teamData;

    public AllianceSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_alliance_selection, container, false);
        teamNumberAutoComplete = view.findViewById(R.id.team1SelectionAutoComplete);
        teamNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("AllianceSelectionFragment", "item clicked");
                String teamNumber = sortedTeamNumbers.get(position);
                teamData = ((ChartsActivity) getActivity()).getTeamData(teamNumber);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        setData(((ChartsActivity)getActivity()).getAllTeamNumbers());

        setupDropDown();
    }

    public void setData(List<TeamListViewModel> allTeamNumbers){
        sortedTeamNumbers = allTeamNumbers.stream()
                .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());
    }

    public void setupDropDown(){
        if(sortedTeamNumbers == null) {
            Log.i("AllianceSelectionFragment", "No teams");
            return;
        }
        Log.i("AllianceSelectionFragment", "setupDropDown with" + sortedTeamNumbers.size() + "teams");
        ArrayAdapter teamNumberAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_item, sortedTeamNumbers);
        teamNumberAutoComplete.setAdapter(teamNumberAdapter);
    }
}