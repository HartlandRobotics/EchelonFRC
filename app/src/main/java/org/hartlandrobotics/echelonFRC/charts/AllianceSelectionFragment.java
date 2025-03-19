package org.hartlandrobotics.echelonFRC.charts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFRC.MatchScheduleActivity;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AllianceSelectionFragment extends Fragment {
    private AutoCompleteTextView teamNumber1AutoComplete;
    private AutoCompleteTextView teamNumber2AutoComplete;
    private AutoCompleteTextView teamNumber3AutoComplete;
    private List<String> sortedTeamNumbers;
    ChartsActivity.TeamDataViewModel teamData;

    private ListView teamNumberListView;
    private ListViewItemCheckboxBaseAdapter teamListAdapter;

    private List<TeamListViewModel> allTeamNumbers;
    private List<ChartsActivity.TeamDataViewModel> allTeamData;

    RecyclerView teamRecycler;


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

        teamNumber1AutoComplete = view.findViewById(R.id.team1SelectionAutoComplete);
        teamNumber1AutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("AllianceSelectionFragment", "item clicked");
                String teamNumber = sortedTeamNumbers.get(position);
                teamData = ((ChartsActivity) getActivity()).getTeamData(teamNumber);
            }
        });

        teamNumber2AutoComplete = view.findViewById(R.id.team2SelectionAutoComplete);
        teamNumber2AutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("AllianceSelectionFragment", "item clicked");
                String teamNumber = sortedTeamNumbers.get(position);
                teamData = ((ChartsActivity) getActivity()).getTeamData(teamNumber);
            }
        });

        teamNumber3AutoComplete = view.findViewById(R.id.team3SelectionAutoComplete);
        teamNumber3AutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        super.onViewCreated(view, savedInstanceState);

        teamListAdapter = new ListViewItemCheckboxBaseAdapter(getContext());
        teamNumberListView = view.findViewById(R.id.team_list);
        //teamNumberRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        teamNumberListView.setAdapter(teamListAdapter);

        teamRecycler = view.findViewById(R.id.team_recycler);



        setData(((ChartsActivity)getActivity()).getAllTeamNumbers(), ((ChartsActivity)getActivity()).allTeamsData);


        setupDropDown();
    }

    public void setData(List<TeamListViewModel> allTeamNumbers, List<ChartsActivity.TeamDataViewModel> allTeamData){
        sortedTeamNumbers = allTeamNumbers.stream()
                .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());

        this.allTeamNumbers = allTeamNumbers;
        teamListAdapter.setTeams(allTeamNumbers);
        teamListAdapter.notifyDataSetChanged();

        matchListAdapter = new MatchScheduleActivity.MatchListAdapter(this);

        this.allTeamData = allTeamData;
        teamRecycler.setLayoutManager(new LinearLayoutManager(this));
        teamRecycler.setAdapter(matchListAdapter);
        teamRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



    }

    public void setupDropDown(){
        if(sortedTeamNumbers == null) {
            Log.i("AllianceSelectionFragment", "No teams");
            return;
        }

        Log.i("AllianceSelectionFragment", "setupDropDown with" + sortedTeamNumbers.size() + "teams");
        ArrayAdapter teamNumber1Adapter = new ArrayAdapter(getContext(), R.layout.dropdown_item, sortedTeamNumbers);
        teamNumber1AutoComplete.setAdapter(teamNumber1Adapter);
        ArrayAdapter teamNumber2Adapter = new ArrayAdapter(getContext(), R.layout.dropdown_item, sortedTeamNumbers);
        teamNumber2AutoComplete.setAdapter(teamNumber2Adapter);
        ArrayAdapter teamNumber3Adapter = new ArrayAdapter(getContext(), R.layout.dropdown_item, sortedTeamNumbers);
        teamNumber3AutoComplete.setAdapter(teamNumber3Adapter);

    }

    public class ListViewItemCheckboxBaseAdapter extends BaseAdapter {
        Context context;
        List<TeamListViewModel> teamViewModels;

        public ListViewItemCheckboxBaseAdapter(Context context) {
            this.context = context;
        }

        public void setTeams(List<TeamListViewModel> teamViewModels){
            this.teamViewModels = teamViewModels;
        }

        @Override
        public int getCount() {
            if( this.teamViewModels == null ) return 0;
            return this.teamViewModels.size();
        }

        @Override
        public TeamListViewModel getItem(int index) {
            return this.teamViewModels.get(index);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_item_alliance_selection, null,true);

            MaterialTextView teamNumber = rowView.findViewById(R.id.team_number);
            MaterialCheckBox teamSelectedCheckBox = rowView.findViewById(R.id.team_visible);

            TeamListViewModel teamListViewModel = teamViewModels.get(position);
            teamNumber.setText(teamListViewModel.getTeamNumber());
            teamSelectedCheckBox.setChecked(teamListViewModel.getIsSelected());
            teamSelectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ViewParent layoutViewParent = buttonView.getParent();
                    ListView listView =  (ListView) layoutViewParent.getParent();
                    int position = listView.getPositionForView(buttonView);
                    teamViewModels.get(position).setIsSelected(isChecked);

                    //setVisibleTeams();
                    }
            });
            return rowView;
        };
    }
}