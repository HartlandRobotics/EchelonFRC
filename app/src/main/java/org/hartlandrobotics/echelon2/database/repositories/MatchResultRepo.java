package org.hartlandrobotics.echelonFRC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.MatchResultDao;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResultWithTeamMatch;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;

import java.util.List;

public class MatchResultRepo {
    private MatchResultDao matchResultDao;

    public MatchResultRepo( Application application ){
        EchelonDatabase db = EchelonDatabase.getDatabase( application);
        matchResultDao = db.matchResultDao();
    }

    public LiveData<MatchResult> getMatchResultByMatchTeam(String matchKey, String teamKey ){
        return matchResultDao.getMatchResultByMatchTeam( matchKey, teamKey );
    }


    public void upsert(MatchResult matchResult){
        EchelonDatabase.databaseWriteExecutor.execute(() -> matchResultDao.upsert(matchResult));
    }

    public LiveData<List<MatchResult>> getMatchResultsByEvent(String eventKey) {
        return matchResultDao.getMatchResultsByEvent(eventKey);
    }

    public LiveData<List<MatchResultWithTeamMatch>> getMatchResultsWithTeamMatchByEvent(String eventKey) {
        return matchResultDao.getMatchResultsWithTeamMatchByEvent(eventKey);
    }
}
