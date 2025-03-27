package org.hartlandrobotics.echelonFRC.charts;

import static android.graphics.Color.GRAY;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.MatchScheduleActivity;
import org.hartlandrobotics.echelonFRC.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



public class AllianceSelectionFragment extends Fragment {
    private AutoCompleteTextView teamNumber1AutoComplete;
    private AutoCompleteTextView teamNumber2AutoComplete;
    private AutoCompleteTextView teamNumber3AutoComplete;
    private List<String> sortedTeamNumbers;
    ChartsActivity.TeamDataViewModel teamData1;
    ChartsActivity.TeamDataViewModel teamData2;
    ChartsActivity.TeamDataViewModel teamData3;


    private ListView teamNumberListView;
    private ListViewItemCheckboxBaseAdapter teamListAdapter;

    private ListView teamDataListView;
    private ListDataViewItemBaseAdapter teamDataListAdapter;


    private List<TeamListViewModel> allTeamNumbers;
    private List<ChartsActivity.TeamDataViewModel> allTeamData;
    private List<ChartsActivity.TeamDataViewModel> visibleTeamData;




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
                if(position != 0){
                String teamNumber = sortedTeamNumbers.get(position);
                teamData1 = ((ChartsActivity) getActivity()).getTeamData(teamNumber);
                teamDataListAdapter.notifyDataSetChanged();
                }
            }
        });

        teamNumber2AutoComplete = view.findViewById(R.id.team2SelectionAutoComplete);
        teamNumber2AutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("AllianceSelectionFragment", "item clicked");
                String teamNumber = sortedTeamNumbers.get(position);
                teamData2 = ((ChartsActivity) getActivity()).getTeamData(teamNumber);
                teamDataListAdapter.notifyDataSetChanged();
            }
        });

        teamNumber3AutoComplete = view.findViewById(R.id.team3SelectionAutoComplete);
        teamNumber3AutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("AllianceSelectionFragment", "item clicked");
                String teamNumber = sortedTeamNumbers.get(position);
                teamData3 = ((ChartsActivity) getActivity()).getTeamData(teamNumber);
                teamDataListAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        teamListAdapter = new ListViewItemCheckboxBaseAdapter(getContext());
        teamNumberListView = view.findViewById(R.id.team_list);
        teamNumberListView.setAdapter(teamListAdapter);

        teamDataListAdapter = new ListDataViewItemBaseAdapter(getContext());
        teamDataListView = view.findViewById(R.id.team_data_listview);
        teamDataListView.setAdapter(teamDataListAdapter);

        // use visible all

        setData(((ChartsActivity)getActivity()).getAllTeamNumbers(), ((ChartsActivity)getActivity()).allTeamsData);


        setupDropDown();
    }

    public void setData(List<TeamListViewModel> allTeamNumbers, List<ChartsActivity.TeamDataViewModel> allTeamData){
        this.allTeamNumbers = new ArrayList<TeamListViewModel>( allTeamNumbers );

        sortedTeamNumbers = this.allTeamNumbers.stream()
                .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());
        sortedTeamNumbers.add(0,StringUtils.EMPTY);

        teamListAdapter.setTeams(this.allTeamNumbers);
        teamListAdapter.notifyDataSetChanged();


                //new MatchScheduleActivity.MatchListAdapter(this);

        this.allTeamData = new ArrayList<ChartsActivity.TeamDataViewModel>( allTeamData );
        setVisibleTeams();

        if( visibleTeamData != null ) {
            //teamDataListAdapter.setTeamsData(this.allTeamData);
            teamDataListAdapter.setTeamsData(this.visibleTeamData);

            teamDataListAdapter.notifyDataSetChanged();
        }





    }
    public void setVisibleTeams(){
        List<String> visibleTeamNumbers = allTeamNumbers.stream()
                .filter(TeamListViewModel::getIsSelected)
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());

        visibleTeamData = allTeamData.stream()
                .filter( teamData -> {
                    return visibleTeamNumbers.contains( String.valueOf(teamData.getTeamNumber()) );
                })
                .sorted(Comparator.comparingDouble(ChartsActivity.TeamDataViewModel::getTotalAverage).reversed())
                .limit(35)
                .collect(Collectors.toList());
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
        public final String TAG = "ListViewItemCheckboxBaseAdapter";

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

                    setVisibleTeams();

                    //notifyDataSetChanged();
                    teamDataListAdapter.notifyDataSetChanged();

                    Log.e(TAG, "onCheckedChanged: "+ isChecked );
                    }
            });
            return rowView;
        };
    }
    public class ListDataViewItemBaseAdapter extends BaseAdapter {

        public final String TAG = "ListDataViewItemBaseAdapter";
        Context context;
        List<ChartsActivity.TeamDataViewModel> teamDataViewModels;

        public ListDataViewItemBaseAdapter(Context context) {
            this.context = context;
        }

        public void setTeamsData(List<ChartsActivity.TeamDataViewModel> teamDataViewModels) {
            this.teamDataViewModels = teamDataViewModels;
        }

        @Override
        public int getCount() {
            if( this.teamDataViewModels == null ) return 0;
            return this.teamDataViewModels.size();
        }

        @Override
        public ChartsActivity.TeamDataViewModel getItem(int index) {
            return this.teamDataViewModels.get(index);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_item_alliance_selection_team_data, null,true);

            if(position % 2 == 0) {
                rowView.setBackgroundColor(GRAY);
            }

            MaterialTextView teamNumberText = rowView.findViewById(R.id.team_number);
            MaterialTextView autoNumberText = rowView.findViewById(R.id.autoNumber);
            MaterialTextView teleOpNumberText = rowView.findViewById(R.id.teleOpNumber);
            MaterialTextView endGameNumberText = rowView.findViewById(R.id.endGameNumber);
            MaterialTextView totalNumberText = rowView.findViewById(R.id.totalNumber);

            //MaterialCheckBox teamSelectedCheckBox = rowView.findViewById(R.id.team_visible);

            ChartsActivity.TeamDataViewModel teamListDataViewModel = teamDataViewModels.get(position);
            int theTeamNumber = teamListDataViewModel.getTeamNumber();
            teamNumberText.setText( String.valueOf(theTeamNumber) );

            Log.e(TAG, "theTeamNumber: " + theTeamNumber );
            boolean vis = visibleTeamData.stream().filter( vtd -> vtd.getTeamNumber() == theTeamNumber).collect(Collectors.toList()).isEmpty();
            Log.e(TAG, "if visable: " + vis );
            if( vis ) {
                if(view != null) {
                    view.setVisibility(View.GONE);
                    rowView.setVisibility(View.GONE);
                }
            }

            float theAutoNumber = teamListDataViewModel.getAutoAverage();
            autoNumberText.setText(String.format("%.2s", theAutoNumber));

            float theTeleOpNumber = teamListDataViewModel.getTeleOpAverage();
            teleOpNumberText.setText(String.format("%.2s", theTeleOpNumber));

            float theEndGameNumber = teamListDataViewModel.getEndGameAverage();
            endGameNumberText.setText(String.format("%.2s", theEndGameNumber));

            float theTotalNumber = teamListDataViewModel.getTotalAverage();
            totalNumberText.setText(String.format("%.2s", theTotalNumber));

            if(!StringUtils.isBlank(teamNumber1AutoComplete.getText())){
                int teamNum = Integer.parseInt(teamNumber1AutoComplete.getText().toString());
                ChartsActivity.TeamDataViewModel vm = allTeamData.stream().filter(ad -> ad.getTeamNumber() == teamNum).findFirst().get();
                theTotalNumber += vm.getTotalAverage();
                totalNumberText.setText(String.format("%.2s", theTotalNumber));
            }

            if(!StringUtils.isBlank(teamNumber2AutoComplete.getText())){
                int teamNum = Integer.parseInt(teamNumber2AutoComplete.getText().toString());
                ChartsActivity.TeamDataViewModel vm = allTeamData.stream().filter(ad -> ad.getTeamNumber() == teamNum).findFirst().get();
                theTotalNumber += vm.getTotalAverage();
                totalNumberText.setText(String.format("%.2s", theTotalNumber));
            }

            if(!StringUtils.isBlank(teamNumber3AutoComplete.getText())){
                int teamNum = Integer.parseInt(teamNumber3AutoComplete.getText().toString());
                ChartsActivity.TeamDataViewModel vm = allTeamData.stream().filter(ad -> ad.getTeamNumber() == teamNum).findFirst().get();
                theTotalNumber += vm.getTotalAverage();
                totalNumberText.setText(String.format("%.2s", theTotalNumber));
            }

            return rowView;
        };


    }

}