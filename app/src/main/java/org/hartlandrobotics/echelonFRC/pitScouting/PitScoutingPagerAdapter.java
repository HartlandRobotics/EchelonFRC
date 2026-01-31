package org.hartlandrobotics.echelonFRC.pitScouting;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import org.hartlandrobotics.echelonFRC.database.entities.PitScout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitScoutingPagerAdapter extends FragmentStateAdapter {

    private PitScout data;

    public static final  int ROBOT1_POSITION = 0;
    public static final  int ROBOT2_POSITION = 1;
    private static final int AUTO_POSITION = 2;
    private static final int TELEOP_POSITION = 3;
    private static final int ENDGAME_POSITION = 4;
    private static final int PHOTOS_POSITION = 5;

    private PitScoutRobot1Fragment pitScoutRobot1Fragment;
    private PitScoutRobot2Fragment pitScoutRobot2Fragment;
    private PitScoutAutoFragment autoFragment;
    private PitScoutTeleOpFragment teleOpFragment;
    private PitScoutEndGameFragment endGameFragment;
    private PitScoutPhotosFragment photosFragment;

    public PitScoutingPagerAdapter(
            @NonNull FragmentManager fragmentManager, Lifecycle lifecycle, PitScout data)
    {
        super(fragmentManager, lifecycle);
        this.data = data;
    }

    public void setData(PitScout data){
        this.data = data;
        if( data == null ) return;

        if (pitScoutRobot1Fragment != null){
            pitScoutRobot1Fragment.setData(data);
        }
        if (pitScoutRobot2Fragment != null){
            pitScoutRobot2Fragment.setData(data);
        }
        if( autoFragment != null ){
            autoFragment.setData(data);
        }
        if( teleOpFragment != null ){
            teleOpFragment.setData(data);
        }
        if( endGameFragment != null ){
            endGameFragment.setData(data);
        }
        if( photosFragment != null ){
            photosFragment.setData(data);
            // may not need to set data for this one
        }
    }

    public static final String TAG = "PitScoutPagerAdapter";
    private Map<Integer, String> titleByPosition = new HashMap<>();
    public String getTabTitle(int position){
        if( titleByPosition.isEmpty() ){
            titleByPosition.put(0,"Robot");
            titleByPosition.put(1,"Robot Cont.");
            titleByPosition.put(2,"Auto");
            titleByPosition.put(3, "TeleOp");
            titleByPosition.put(4, "Endgame");
            titleByPosition.put(5, "Photos");
        }
        return titleByPosition.get(position);
    }

    public void updatePitScoutData(){
        if (pitScoutRobot1Fragment != null){
            Log.i(TAG, "populating robot 1 fragment data");
            pitScoutRobot1Fragment.populateDataFromControls();
        }
        if (pitScoutRobot2Fragment != null){
            Log.i(TAG, "populating robot 2 fragment data");
           pitScoutRobot2Fragment.populateDataFromControls();
        }
        if( autoFragment != null ){
            Log.i(TAG, "populating auto fragment data");
            autoFragment.populateDataFromControls();
        }
        if( teleOpFragment != null ){
            Log.i(TAG, "populating telop fragment data");
            teleOpFragment.populateDataFromControls();
        }
        if( endGameFragment != null ){
            Log.i(TAG, "populate end game fragment data");
            endGameFragment.populateDataFromControls();
        }
        if( photosFragment != null ){
            Log.i(TAG, "populate photos fragment");
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case ROBOT1_POSITION:
                Log.i(TAG, "creating new Robot 1 Fragment");
                pitScoutRobot1Fragment = new PitScoutRobot1Fragment();
                pitScoutRobot1Fragment.setData(data);
                return pitScoutRobot1Fragment;
            case ROBOT2_POSITION:
                Log.i(TAG, "creating new Robot 2 Fragment");
                pitScoutRobot2Fragment = new PitScoutRobot2Fragment();
                pitScoutRobot2Fragment.setData(data);
                return pitScoutRobot2Fragment;
            case AUTO_POSITION:
                Log.i(TAG,"creating new Auto Fragment");
                autoFragment = new PitScoutAutoFragment();
                autoFragment.setData(data);
                return autoFragment;
            case TELEOP_POSITION:
                Log.i(TAG,"creating new TeleOp Fragment");
                teleOpFragment = new PitScoutTeleOpFragment();
                teleOpFragment.setData(data);
                return teleOpFragment;
            case ENDGAME_POSITION:
                Log.i(TAG,"creating new EndGame Fragment");
                endGameFragment = new PitScoutEndGameFragment();
                endGameFragment.setData(data);
                return endGameFragment;
            case PHOTOS_POSITION:
                Log.i(TAG, "creating new Photos Fragment");
                photosFragment = new PitScoutPhotosFragment();
                photosFragment.setData(data);
                return photosFragment;
            default:
                throw new IllegalArgumentException("invalid tab selection for pitscouting");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
