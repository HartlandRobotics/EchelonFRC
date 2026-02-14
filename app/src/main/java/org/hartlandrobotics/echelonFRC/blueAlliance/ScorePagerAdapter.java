package org.hartlandrobotics.echelonFRC.blueAlliance;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.hartlandrobotics.echelonFRC.blueAlliance.fragments.DistrictsFragment;
import org.hartlandrobotics.echelonFRC.blueAlliance.fragments.OprFragment;
import org.hartlandrobotics.echelonFRC.blueAlliance.fragments.ScoreFragment;

import java.util.HashMap;
import java.util.Map;

public class ScorePagerAdapter extends FragmentStateAdapter {
    public ScorePagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public static final String TAG = "ScorePagerAdapter";

    private Map<Integer, String> titleByPosition = new HashMap<>();
    public String getTabTitle(int position){
        if( titleByPosition.size() == 0 ){
            titleByPosition.put(0,"OPR");
            titleByPosition.put(1,"Score");
        }
        return titleByPosition.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            Log.e(TAG, "creating new OPR Fragment");
            OprFragment oprFragment = new OprFragment();
            fragment = oprFragment;
        }
        if (position == 1) {
            Log.e(TAG, "creating new Score Fragment");
            ScoreFragment scoreFragment = new ScoreFragment();
            fragment = scoreFragment;
        }
        return fragment;
    }

    @Override
    public int getItemCount(){ return 2; }
}
