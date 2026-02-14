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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncOpr;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettingsViewModel;
import org.hartlandrobotics.echelonFRC.database.entities.Opr;
import org.hartlandrobotics.echelonFRC.database.repositories.OprRepo;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OprFragment extends Fragment {
    private static String TAG = "OprFragment";

    private Button oprFetchButton;

    private RecyclerView oprRecycler;

    private OprListAdapter oprListAdapter;

    public OprFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_opr, container, false);

        oprFetchButton = fragmentView.findViewById(R.id.oprPullButton);

        setupCurrentOpr();
        setupPullOpr();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oprListAdapter = new OprListAdapter(getActivity());

        oprRecycler = view.findViewById(R.id.opr_recycler);
        oprRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        oprRecycler.setAdapter(oprListAdapter);
        oprRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }

    public void setupCurrentOpr(){
        Context appContext = getActivity().getApplicationContext();
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        String currentEventKey = status.getEventKey();
        OprRepo oprRepo = new OprRepo(OprFragment.this.getActivity().getApplication());
        oprRepo.getOprs().observe(getViewLifecycleOwner(), oprs -> {
            oprListAdapter.setOprs(oprs);
        });
    }
    public void setupPullOpr() {
        oprFetchButton.setOnClickListener((view) -> {
            Context appContext = this.getActivity().getApplicationContext();
            AdminSettingsViewModel vm = AdminSettingsProvider.getAdminSettings(appContext);
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);

            String userAgentPrefix = "Echelon/1.0 ";
            String userAgent = userAgentPrefix + " " + vm.getTeamNumber();
            String tbaApiKey = vm.getBlueAllianceApiKey();

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            OkHttpClient httpClient = httpClientBuilder.build();
            Request request = new Request.Builder()
                    .header("Accept", "application/json")
                    .header("User-Agent", userAgent)
                    .header("X-TBA-Auth-Key", tbaApiKey)
                    .url("https://thebluealliance.com/api/v3/event/" + status.getEventKey() + "/coprs")
                    .build();

            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e(TAG, "Fail to call ok http");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.i(TAG, "Called ok http");
                    String str = response.body().string();
                    ObjectMapper om = new ObjectMapper();
                    SyncOpr syncOpr = om.readValue(str, SyncOpr.class);
                    OprRepo dao = new OprRepo(OprFragment.this.getActivity().getApplication());
                    dao.upsert(syncOpr.toOprs().stream().sorted(Comparator.comparingDouble(Opr::getOpr).reversed()).collect(Collectors.toList()));
                }
            });
        });
    }

    public class OprViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView teamKeyText;
        private MaterialTextView oprText;
        private Opr opr;

        OprViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            teamKeyText = itemView.findViewById(R.id.team_key);
            oprText = itemView.findViewById(R.id.opr);
        }

        public void setOpr(Opr opr) {
            this.opr = opr;
            teamKeyText.setText(this.opr.getTeamKey().substring(3));
            oprText.setText(String.format("%.3f", this.opr.getOpr()));
        }

        public void setTeamKeyText(String displayText) {
            teamKeyText.setText(displayText);
        }

        @Override
        public void onClick(View view){}
    }

    public class OprListAdapter extends RecyclerView.Adapter<OprViewHolder> {
        private final LayoutInflater inflater;
        private List<Opr> oprs;

        OprListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public OprViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_opr, parent, false);
            return new OprViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull OprViewHolder holder, int position) {
            if (oprs != null) {
                holder.setOpr(oprs.get(position));
            } else {
                holder.setTeamKeyText("No Team Data Yet...");
            }
        }

        void setOprs(List<Opr> oprsPara) {
            oprs = new ArrayList<>();
            oprs.addAll(oprsPara);

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if( oprs != null ) return oprs.size();
            return 0;
        }
    }
}