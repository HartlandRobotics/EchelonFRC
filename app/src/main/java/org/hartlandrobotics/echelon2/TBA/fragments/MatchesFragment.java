package org.hartlandrobotics.echelon2.TBA.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.TBAActivity;
import org.hartlandrobotics.echelon2.TBA.models.SyncMatch;
import org.hartlandrobotics.echelon2.database.entities.Match;
import org.hartlandrobotics.echelon2.database.repositories.MatchRepo;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchesFragment extends Fragment {
    private static final String TAG = "MatchesFragment";
    private Button matchFetchButton;
    private RecyclerView matchRecycler;
    private MatchListAdapter matchListAdapter;
    //private TextView errorTextDisplay;

    public MatchesFragment(){
        // required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.fragment_matches, container, false);

        matchFetchButton = fragmentView.findViewById(R.id.matchPullButton);
        //errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);
        //errorTextDisplay.setVisibility(View.GONE);

        setupCurrentMatches();

        setupPullMatches();

        return fragmentView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        matchListAdapter = new MatchListAdapter(getActivity());

        matchRecycler = view.findViewById(R.id.match_recycler);
        matchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        matchRecycler.setAdapter(matchListAdapter);
        matchRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }

    public void setupCurrentMatches(){
        Context appContext = getActivity().getApplicationContext();
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        String eventKey = status.getEventKey();
        MatchRepo matchRepo = new MatchRepo(MatchesFragment.this.getActivity().getApplication());
        matchRepo.getMatchesByEvent(eventKey).observe(getViewLifecycleOwner(), events -> {
            matchListAdapter.setMatches(events.matches);
        });

    }

    public  void setupPullMatches(){
        matchFetchButton.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Context appContext = getActivity().getApplicationContext();
                BlueAllianceStatus status = new BlueAllianceStatus(appContext);
                String eventKey = status.getEventKey();

                Call<List<SyncMatch>> newCall = newApi.getMatchesByEvent(eventKey);
                newCall.enqueue(new Callback<List<SyncMatch>>(){
                    @Override
                    public void onResponse(Call<List<SyncMatch>> call, Response<List<SyncMatch>> response){
                        try{
                            if(!response.isSuccessful()){
                                Log.e(TAG, "Couldn't pull matches" );
                            }else{
                                MatchRepo matchRepo = new MatchRepo(MatchesFragment.this.getActivity().getApplication());
                                List<SyncMatch> syncMatches = response.body();
                                List<Match> matches = syncMatches.stream()
                                        .map(match -> match.toMatch())
                                        .collect(Collectors.toList());

                                matchRepo.upsert(matches);

                                matchListAdapter.setMatches(matches);

                                Log.e(TAG, "Got matches " + syncMatches.size());

                            }
                        }catch(Exception e){
                            Log.e(TAG, "Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncMatch>> call, Throwable t){
                        Log.e(TAG, "Couldn't pull matches");
                    }
                });
            }catch(Exception e){
                Log.e(TAG, "Error second catch " + e.getMessage());
            }
        });
    }

    private void showError(String errorMessage){

    }

    public class MatchViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView matchNumber;
        private MaterialTextView matchKey;
        private MaterialTextView red1;
        private MaterialTextView red2;
        private MaterialTextView red3;
        private MaterialTextView blue1;
        private MaterialTextView blue2;
        private MaterialTextView blue3;

        private MatchListViewModel matchViewModel;

        MatchViewHolder(View itemView){
            super(itemView);

            matchNumber = itemView.findViewById(R.id.match_number);
            red1 = itemView.findViewById(R.id.red1);
            red1 = itemView.findViewById(R.id.red2);
            red1 = itemView.findViewById(R.id.red3);
            red1 = itemView.findViewById(R.id.blue1);
            red1 = itemView.findViewById(R.id.blue2);
            red1 = itemView.findViewById(R.id.blue3);


        }

        public void setMatch(MatchListViewModel matchViewModel){
            this.matchViewModel = matchViewModel;

            matchNumber.setText(matchViewModel.getMatchNumber());
            red1.setText(matchViewModel.getRed1());
            red2.setText(matchViewModel.getRed2());
            red3.setText(matchViewModel.getRed3());
            blue1.setText(matchViewModel.getBlue1());
            blue2.setText(matchViewModel.getBlue2());
            blue3.setText(matchViewModel.getBlue3());


        }

        public void setDisplayText(String displayText){
            matchNumber.setText(displayText);
        }


    }

    public class MatchListAdapter extends RecyclerView.Adapter<MatchViewHolder>{
        private final LayoutInflater inflater;
        private List<MatchListViewModel> matchViewModels;

        MatchListAdapter(Context context){
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_match, parent, false);
            return new MatchViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
            if(matchViewModels != null){
                holder.setMatch(matchViewModels.get(position));
            }else{
                holder.setDisplayText("No Match Data Yet...");
            }
        }

        void setMatches(List<Match> matches){
            Context appContext = getActivity().getApplicationContext();
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            String currentMatchKey = status.getMatchKey();

            matchViewModels = new ArrayList<>();
            for(Match match : matches){
                MatchListViewModel viewModel = new MatchListViewModel(match);
                if(match.getMatchKey().equals(currentMatchKey)){
                    viewModel.setIsSelected(true);
                }
                matchViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentMatch(MatchListViewModel currentViewModel){
            TBAActivity tbaActivity = (TBAActivity)getActivity();
            tbaActivity.setMatchKey(currentViewModel.getMatchKey());

            for(MatchListViewModel viewModel : matchViewModels){
                viewModel.setIsSelected(currentViewModel.getMatchKey().equals(viewModel.getMatchKey()));

            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return matchViewModels == null ? 0 : matchViewModels.size();
        }
    }
}
