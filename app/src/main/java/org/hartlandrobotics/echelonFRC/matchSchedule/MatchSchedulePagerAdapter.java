package org.hartlandrobotics.echelonFRC.matchSchedule;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchSchedulePagerAdapter extends FragmentStateAdapter {

    private static final int QUALIFICATIONS = 0;
    private static final int PLAYOFFS = 1;

    private MatchScheduleQualsFragment qualFragment;
    private MatchScheduleQualsFragment playoffsFragment;

    public MatchSchedulePagerAdapter(
        @NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        //this.data = data;
    }

    public void setData() {
        //this.data = data;
        //if( data==null ) return;

        //if( qualFragment != null ){
        //qualFragment.setData(data);
        //}
        //if( playoffsFragment != null ){
        //}
    }

    public static final String TAG = "MatchSchedulePagerAdapter";
    private Map<Integer , String> titleByPosition = new HashMap<>();
    public String getTabTitle(int position){
        if( titleByPosition.isEmpty() ){
            titleByPosition.put(0, "Qualifications");
            titleByPosition.put(1, "Playoffs");
        }

        return titleByPosition.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case QUALIFICATIONS:
                Log.i(TAG, "creating new Qualifications Fragment");
                qualFragment = new MatchScheduleQualsFragment();
                return qualFragment;
            case PLAYOFFS:
                Log.i(TAG, "creating new Playoffs Fragment");
                playoffsFragment = new MatchScheduleQualsFragment();
                return playoffsFragment;
            default:
                throw new IllegalArgumentException("invalid tab selection for match schedule activity");

        }
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public  int getItemCount(){return 2;}
}

