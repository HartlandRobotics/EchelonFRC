package org.hartlandrobotics.echelonFRC.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.entities.EvtWithTeams;
import org.hartlandrobotics.echelonFRC.database.entities.Team;
import org.hartlandrobotics.echelonFRC.database.repositories.TeamRepo;

import java.util.List;

public class TeamViewModel extends AndroidViewModel {
    private TeamRepo teamRepo;

    public TeamViewModel( Application app ){
        super(app);

        teamRepo = new TeamRepo(app);
    }

    public LiveData<List<Team>> getAllTeams(){
        return teamRepo.getAllTeams();
    }

    public LiveData<EvtWithTeams> getTeamsForEvent(String eventKey){
        return teamRepo.getEventsWithTeams(eventKey);
    }
}
