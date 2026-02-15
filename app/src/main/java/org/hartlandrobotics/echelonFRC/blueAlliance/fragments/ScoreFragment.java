package org.hartlandrobotics.echelonFRC.blueAlliance.fragments;

import android.app.Application;
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
import android.widget.Button;
import android.widget.TextView;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.blueAlliance.Api;
import org.hartlandrobotics.echelonFRC.blueAlliance.ApiInterface;
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncMatchScore;
import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchScoreRepo;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScoreFragment extends Fragment {
    private static final String TAG = "ScoreFragment";
    private MatchScoreListAdapter matchScoreListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_score, container, false);

        Button scoreFetchButton = fragmentView.findViewById(R.id.scorePullButton);

        setupCurrentScore();
        setupPullScore(scoreFetchButton);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        matchScoreListAdapter = new MatchScoreListAdapter(getActivity());

        RecyclerView scoreRecycler = view.findViewById(R.id.score_recycler);
        scoreRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreRecycler.setAdapter(matchScoreListAdapter);
        scoreRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }
    public void setupCurrentScore(){
        Context appContext = requireActivity().getApplicationContext();
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        String eventKey = status.getEventKey();

        MatchScoreRepo matchScoreRepo = new MatchScoreRepo(ScoreFragment.this.requireActivity().getApplication());
        matchScoreRepo.getMatchScores().observe(getViewLifecycleOwner(), scores -> {
            matchScoreListAdapter.setScores(scores);
        });
    }

    public void setupPullScore(Button scoreFetchButton){
        scoreFetchButton.setOnClickListener((view -> {
            Application app = requireActivity().getApplication();
            ApiInterface newApi = Api.getApiClient(app);

            try{
                BlueAllianceStatus status = new BlueAllianceStatus(app.getApplicationContext());
                String eventKey = status.getEventKey();

                Call<List<SyncMatchScore>> newCall = newApi.getMatchScoresByEvent(eventKey);
                newCall.enqueue(new Callback<List<SyncMatchScore>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<SyncMatchScore>> call, @NonNull Response<List<SyncMatchScore>> response) {
                        Log.i(TAG, "Successfully got response for scores");

                        try{
                            if(!response.isSuccessful()){
                                Log.i(TAG,"Couldn't pull Scores");
                            }
                            else{
                                MatchScoreRepo matchScoreRepo = new MatchScoreRepo(app);
                                List<SyncMatchScore> syncMatchScores = response.body();
                                if( syncMatchScores == null ) return;
                                List<MatchScore> scores = syncMatchScores.stream()
                                        .filter(sms -> sms.getMatchKey().contains("_qm"))
                                        .map(SyncMatchScore::toMatchScore)
                                        .sorted(Comparator.comparingInt(MatchScore::getMatchNumber))
                                        .collect(Collectors.toList());

                                matchScoreRepo.upsert(scores);
                                matchScoreListAdapter.setScores(scores);
                            }
                        }
                        catch(Exception e){
                            Log.e(TAG,"Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<SyncMatchScore>> call, @NonNull Throwable t) {
                        Log.i(TAG, "Failed to get response for scores");
                    }
                });
            }
            catch(Exception e){
                Log.i(TAG, "Error second catch " + e.getMessage());
            }
        }));
    }

    public static class MatchScoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView matchNumberText;
        private final TextView matchRedTotalText;
        private final TextView matchRedFoulText;
        private final TextView matchBlueTotalText;
        private final TextView matchBlueFoulText;

        private MatchScoreViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener( this );

            matchNumberText = itemView.findViewById( R.id.match_number );
            matchRedTotalText = itemView.findViewById( R.id.red_total );
            matchRedFoulText = itemView.findViewById( R.id.red_foul );
            matchBlueTotalText = itemView.findViewById( R.id.blue_total );
            matchBlueFoulText = itemView.findViewById( R.id.blue_foul );
        }

        public void setMatchScore(MatchScore matchScore ){
            matchNumberText.setText(String.valueOf(matchScore.getMatchNumber()));
            matchRedTotalText.setText(String.valueOf(matchScore.getRedTotal()));
            matchRedFoulText.setText(String.valueOf(matchScore.getRedFoul()));
            matchBlueTotalText.setText(String.valueOf(matchScore.getBlueTotal()));
            matchBlueFoulText.setText(String.valueOf(matchScore.getBlueFoul()));
        }

        @Override
        public void onClick(View view) {}
    }

    public static class MatchScoreListAdapter extends RecyclerView.Adapter<MatchScoreViewHolder> {
        private final LayoutInflater inflater;
        private List<MatchScore> scores;

        MatchScoreListAdapter(Context context ){ inflater = LayoutInflater.from(context); }

        @NonNull
        @Override
        public MatchScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate( R.layout.list_item_score, parent, false );
            return new MatchScoreViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MatchScoreViewHolder holder, int position) {
            if( scores != null ){
                holder.setMatchScore( scores.get(position) );
            }
        }

        void setScores(List<MatchScore> matchScoresPara){
            scores = new ArrayList<>();
            scores.addAll(matchScoresPara);

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return ( scores == null ) ? 0 : scores.size();
        }
    }
}