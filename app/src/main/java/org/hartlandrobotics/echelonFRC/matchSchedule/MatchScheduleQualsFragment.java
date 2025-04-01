package org.hartlandrobotics.echelonFRC.matchSchedule;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class MatchScheduleQualsFragment extends Fragment {

    private static final String TAG = "MatchScheduleQualsFragment";


    public MatchScheduleQualsFragment() {
        // Required empty public constructor
    }

    public static MatchScheduleQualsFragment newInstance(String param1, String param2) {
        MatchScheduleQualsFragment fragment = new MatchScheduleQualsFragment();
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

    private void setupControls(View view) {
        //fill later
    }

    public void populateDataFromControls() {
        //fill later
    }

    public  void populateControlFromData() {
        //fill later
    }

    public void setVisibility(){
        //fill later
    }
    public class MatchScheduleViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView matchNumber;
        //private MaterialTextView matchKey;
        private MaterialTextView red1;
        private MaterialTextView red2;
        private MaterialTextView red3;
        private MaterialTextView blue1;
        private MaterialTextView blue2;
        private MaterialTextView blue3;
        private MaterialTextView redPrediction;
        private MaterialTextView bluePrediction;
        private MaterialTextView redAutoPrediction;
        private MaterialTextView blueAutoPrediction;
        private MaterialTextView redTeleOpPrediction;
        private MaterialTextView blueTeleOpPrediction;
        private MaterialTextView redEndPrediction;
        private MaterialTextView blueEndPrediction;

        private MaterialTextView redConfidenceIntervalMin;
        private MaterialTextView blueConfidenceIntervalMin;
        private MaterialTextView redConfidenceIntervalMax;
        private MaterialTextView blueConfidenceIntervalMax;
        private MaterialTextView redPercentage;
        private MaterialTextView bluePercentage;
        private LinearLayout predictionLayout;

        private MatchScheduleViewModel matchScheduleViewModel;

        MatchScheduleViewHolder(View itemView){
            super(itemView);


            matchNumber = itemView.findViewById(R.id.match_number);
            red1 = itemView.findViewById(R.id.red1);
            red2 = itemView.findViewById(R.id.red2);
            red3 = itemView.findViewById(R.id.red3);
            blue1 = itemView.findViewById(R.id.blue1);
            blue2 = itemView.findViewById(R.id.blue2);
            blue3 = itemView.findViewById(R.id.blue3);
            redPrediction = itemView.findViewById(R.id.red_prediction);
            bluePrediction = itemView.findViewById(R.id.blue_prediction);
            redAutoPrediction = itemView.findViewById(R.id.red_auto_points);
            blueAutoPrediction = itemView.findViewById(R.id.blue_auto_points);
            redTeleOpPrediction = itemView.findViewById(R.id.red_teleOp_points);
            blueTeleOpPrediction = itemView.findViewById(R.id.blue_teleOp_points);
            redEndPrediction = itemView.findViewById(R.id.red_end_points);
            blueEndPrediction = itemView.findViewById(R.id.blue_end_points);
            redConfidenceIntervalMin = itemView.findViewById(R.id.red_confidence_interval_min);
            blueConfidenceIntervalMin = itemView.findViewById(R.id.blue_confidence_interval_min);
            redConfidenceIntervalMax = itemView.findViewById(R.id.red_confidence_interval_max);
            blueConfidenceIntervalMax = itemView.findViewById(R.id.blue_confidence_interval_max);
            redPercentage = itemView.findViewById(R.id.red_percentage_prediction);
            bluePercentage = itemView.findViewById(R.id.blue_percentage_prediction);

            predictionLayout = itemView.findViewById(R.id.prediction_layout);
            String deviceName = "dennis";

            if( !( deviceName.contains("aptain" ) || deviceName.contains("oach"))){
                predictionLayout.setVisibility(View.INVISIBLE);
            }

        }

        public void setMatch(MatchScheduleViewModel matchScheduleViewModel){
            this.matchScheduleViewModel = matchScheduleViewModel;

            matchNumber.setText(String.valueOf(matchScheduleViewModel.getMatchNumber()));
            red1.setText("1: " + matchScheduleViewModel.getRed1());
            red2.setText("2: " + matchScheduleViewModel.getRed2());
            red3.setText("3: " + matchScheduleViewModel.getRed3());
            blue1.setText("1: " + matchScheduleViewModel.getBlue1());
            blue2.setText("2: " + matchScheduleViewModel.getBlue2());
            blue3.setText("3: " + matchScheduleViewModel.getBlue3());
            redPrediction.setText( String.valueOf( matchScheduleViewModel.getRedTotal() ) );
            bluePrediction.setText( String.valueOf( matchScheduleViewModel.getBlueTotal() ) );

            redConfidenceIntervalMin.setText(String.valueOf(matchScheduleViewModel.getRedConfidenceIntervalMin()));
            blueConfidenceIntervalMin.setText(String.valueOf(matchScheduleViewModel.getBlueConfidenceIntervalMin()));
            redConfidenceIntervalMax.setText(String.valueOf(matchScheduleViewModel.getRedConfidenceIntervalMax()));
            blueConfidenceIntervalMax.setText(String.valueOf(matchScheduleViewModel.getBlueConfidenceIntervalMax()));
            redPercentage.setText(matchScheduleViewModel.getRedPercentage() + "%");
            bluePercentage.setText(matchScheduleViewModel.getBluePercentage() + "%");

            redAutoPrediction.setText( String.valueOf(matchScheduleViewModel.getRedAutoTotal()));
            blueAutoPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueAutoTotal()));
            redTeleOpPrediction.setText( String.valueOf(matchScheduleViewModel.getRedTeleOpTotal()));
            blueTeleOpPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueTeleOpTotal()));
            redEndPrediction.setText( String.valueOf(matchScheduleViewModel.getRedEndTotal()));
            blueEndPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueEndTotal()));
        }

        public void setDisplayText(String displayText){
            matchNumber.setText(displayText);
        }
    }

    public class MatchListAdapter extends RecyclerView.Adapter<MatchScheduleViewHolder>{
        private final LayoutInflater inflater;
        private List<MatchScheduleViewModel> allHolderViewModels;
        private List<MatchScheduleViewModel> holderViewModels;
        private String teamFilter = StringUtils.EMPTY;

        MatchListAdapter(Context context){
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MatchScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_match_schedule, parent, false);
            return new MatchScheduleViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MatchScheduleViewHolder holder, int position) {
            if(holderViewModels != null){
                holder.setMatch(holderViewModels.get(position));
            }else{
                holder.setDisplayText("No Match Data Yet...");
            }
        }

        void setTeamFilter(String filter){
            teamFilter = StringUtils.defaultIfBlank(filter, StringUtils.EMPTY);
            setMatches(allHolderViewModels);
        }

        void setMatches(List<MatchScheduleViewModel> vms){
            allHolderViewModels = vms;
            String filter = StringUtils.defaultIfBlank(teamFilter, StringUtils.EMPTY );
            String teamKeyFilter = "frc" + filter;
            holderViewModels = vms.stream()
                    .sorted(Comparator.comparingInt(m -> Integer.valueOf( m.getMatchNumber())))
                    .filter( m -> StringUtils.isBlank(filter)
                            || m.getRed1().equals(teamKeyFilter)
                            || m.getRed2().equals(teamKeyFilter)
                            || m.getRed3().equals(teamKeyFilter)
                            || m.getBlue1().equals(teamKeyFilter)
                            || m.getBlue2().equals(teamKeyFilter)
                            || m.getBlue3().equals(teamKeyFilter)
                    )
                    .collect(Collectors.toList());

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return holderViewModels == null ? 0 : holderViewModels.size();
        }
    }
}