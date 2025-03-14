package org.hartlandrobotics.echelonFRC.models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import org.hartlandrobotics.echelonFRC.database.entities.Season;
import org.hartlandrobotics.echelonFRC.database.repositories.SeasonRepo;
import java.util.List;

public class SeasonViewModel extends AndroidViewModel {
    private SeasonRepo seasonRepo;
    private LiveData<List<Season>> seasons;

    public SeasonViewModel(Application application){
        super(application);
        seasonRepo = new SeasonRepo(application);
    }

    public LiveData<List<Season>> getSeasons(){
        seasons = seasonRepo.getSeasons();
        return seasons;
    }
}
