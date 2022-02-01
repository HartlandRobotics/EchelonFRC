package org.hartlandrobotics.echelon2.TBA.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.TBAActivity;
import org.hartlandrobotics.echelon2.TBA.models.SyncDistrict;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictsFragment extends Fragment {
    public DistrictsFragment()
    {
        //required empty constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView errorTextDisplay;
    Button districtPull;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_districts, container, false);
        districtPull = fragmentView.findViewById(R.id.districtPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);

        setupPullDistricts();

        return fragmentView;
    }

    public void setupPullDistricts(){
        districtPull.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Call<List<SyncDistrict>> newCall = newApi.getDistrictsByYear(2022);
                newCall.enqueue(new Callback<List<SyncDistrict>>() {
                    @Override
                    public void onResponse(Call<List<SyncDistrict>> call, Response<List<SyncDistrict>> response) {
                        try{
                            if(!response.isSuccessful()){
                                errorTextDisplay.setText("Couldn't pull districts");
                            }
                            else{
                                List<SyncDistrict> syncDistricts = response.body();
                                List<District> districts = syncDistricts.stream()
                                        .map(district -> district.toDistrict())
                                        .collect(Collectors.toList());
                                ((TBAActivity)getActivity()).updateDistricts(districts);
                                errorTextDisplay.setText("Got districts " + syncDistricts.size());
                            }
                        }
                        catch(Exception e){
                            errorTextDisplay.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncDistrict>> call, Throwable t) {
                        errorTextDisplay.setText("Couldn't pull districts");
                    }
                });
            }
            catch(Exception e){
                errorTextDisplay.setText("Error second catch " + e.getMessage());
            }
        });
    }
}
