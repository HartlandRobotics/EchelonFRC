package org.hartlandrobotics.echelon2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.repositories.PitScoutRepo;

public class PitScoutViewModel extends AndroidViewModel {
    private final PitScoutRepo pitScoutRepo;

    public PitScoutViewModel( Application app ){
        super(app);

        pitScoutRepo = new PitScoutRepo(app);
    }

    public LiveData<PitScout> getPitScout(String eventKey, String teamKey) {
        return pitScoutRepo.getPitScout(eventKey, teamKey);

    }
    public void upsert( PitScout pitScout ){
        pitScoutRepo.upsert( pitScout );
    }
}
