package org.hartlandrobotics.echelonFRC.blueAlliance.fragments;

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
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncTeam;
import org.hartlandrobotics.echelonFRC.database.entities.EvtTeamCrossRef;
import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFRC.database.entities.Team;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchScoreRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.TeamRepo;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScoreFragment extends Fragment {
    private static String TAG = "ScoreFragment";

    private Button scoreFetchButton;

    private RecyclerView scoreRecycler;

    private MatchScoreListAdapter matchScoreListAdapter;


    public ScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_score, container, false);

        scoreFetchButton = fragmentView.findViewById(R.id.scorePullButton);

        setupCurrentScore();
        setupPullScore();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        matchScoreListAdapter = new MatchScoreListAdapter(getActivity());

        scoreRecycler = view.findViewById(R.id.score_recycler);
        scoreRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreRecycler.setAdapter(matchScoreListAdapter);
        scoreRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }
    public void setupCurrentScore(){
        Context appContext = getActivity().getApplicationContext();
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        String eventKey = status.getEventKey();

        MatchScoreRepo matchScoreRepo = new MatchScoreRepo(ScoreFragment.this.getActivity().getApplication());
        matchScoreRepo.getMatchScores().observe(getViewLifecycleOwner(), scores -> {
            matchScoreListAdapter.setScores(scores);
        });
    }

    public void setupPullScore(){
        scoreFetchButton.setOnClickListener((view -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Context context = getActivity().getApplication();
                BlueAllianceStatus status = new BlueAllianceStatus(context);
                String eventKey = status.getEventKey();

                Call<List<SyncMatchScore>> newCall = newApi.getMatchScoresByEvent(eventKey);
                newCall.enqueue(new Callback<List<SyncMatchScore>>() {
                    @Override
                    public void onResponse(Call<List<SyncMatchScore>> call, Response<List<SyncMatchScore>> response) {
                        Log.i(TAG, "Successfully got response for scores");

                        try{
                            if(!response.isSuccessful()){
                                Log.i(TAG,"Couldn't pull Scores");
                            }
                            else{
                                MatchScoreRepo matchScoreRepo = new MatchScoreRepo(ScoreFragment.this.getActivity().getApplication());
                                List<SyncMatchScore> syncMatchScores = response.body();
                                List<MatchScore> scores = syncMatchScores.stream()
                                        .filter(sms -> sms.getMatchKey().contains("_qm"))
                                        .map(score -> score.toMatchScore())
                                        .sorted(Comparator.comparingInt(MatchScore::getMatchNumber))
                                        .collect(Collectors.toList());

                                matchScoreRepo.upsert(scores);

                                matchScoreListAdapter.setScores(scores);

                                int i;
                                i = 10;

                            }
                        }
                        catch(Exception e){
                            Log.e(TAG,"Error " + e.getMessage());
                        }
                    }


                    @Override
                    public void onFailure(Call<List<SyncMatchScore>> call, Throwable t) {
                        Log.i(TAG, "Failed to get response for scores");

                    }
                });
            }
            catch(Exception e){
                Log.i(TAG, "Error second catch " + e.getMessage());
            }
        }));
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView matchNumberText;
        private final TextView matchRedTotalText;
        private final TextView matchRedFoulText;
        private final TextView matchBlueTotalText;
        private final TextView matchBlueFoulText;

        private MatchScore matchScore;

        private ScoreViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener( this );

            matchNumberText = itemView.findViewById( R.id.match_number );
            matchRedTotalText = itemView.findViewById( R.id.red_total );
            matchRedFoulText = itemView.findViewById( R.id.red_foul );
            matchBlueTotalText = itemView.findViewById( R.id.blue_total );
            matchBlueFoulText = itemView.findViewById( R.id.blue_foul );
        }

        public void setMatchScore(MatchScore matchScore ){
            this.matchScore = matchScore;

            matchNumberText.setText(String.valueOf(matchScore.getMatchNumber()));
            matchRedTotalText.setText(String.valueOf(matchScore.getRedTotal()));
            matchRedFoulText.setText(String.valueOf(matchScore.getRedFoul()));
            matchBlueTotalText.setText(String.valueOf(matchScore.getBlueTotal()));
            matchBlueFoulText.setText(String.valueOf(matchScore.getBlueFoul()));
        }


        @Override
        public void onClick(View view) {

        }

    }

    public class MatchScoreListAdapter extends RecyclerView.Adapter<ScoreViewHolder> {


        private final LayoutInflater inflater;
        private List<MatchScore> scores;

        MatchScoreListAdapter(Context context ){ inflater = LayoutInflater.from(context); }

        @NonNull
        @Override
        public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate( R.layout.list_item_score, parent, false );
            return new ScoreViewHolder( itemView );
        }

        @Override
        public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
            if( scores != null ){
                holder.setMatchScore( scores.get(position) );
            } else {
                //holder.setDisplayText("No Team Data Yet...");
            }
        }

        void setScores(List<MatchScore> matchScoresPara){
            scores= new ArrayList<>();
            scores.addAll(matchScoresPara);

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if( scores != null ) return scores.size();
            return 0;
        }
    }



}