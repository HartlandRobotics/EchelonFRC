package org.hartlandrobotics.echelonFRC.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.database.dao.MatchResultDao;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResultWithTeamMatch;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchResultRepo;

import java.util.List;

public class MatchResultViewModel extends AndroidViewModel {
    private MatchResultRepo matchResultRepo;

    public MatchResultViewModel(Application app) {
        super(app);

        matchResultRepo = new MatchResultRepo(app);
    }

    public MatchResult getDefault(String eventKey, String matchKey, String teamKey
    ) {

        MatchResult matchResult = new MatchResult(
                StringUtils.EMPTY,
                eventKey,
                matchKey,
                teamKey,
                false,
                false,
                0,
                0,
                null,
                null,
                0,
                0,
                0,
                null,
                null,
                false,
                false,
                0,
                false,
                false,
                StringUtils.EMPTY,
                0
        );

        return matchResult;
    }

    public LiveData<MatchResult> getMatchResultByMatchTeam(String matchKey, String teamKey) {
        return matchResultRepo.getMatchResultByMatchTeam(matchKey, teamKey);
    }

    public LiveData<List<MatchResult>> getMatchResultsByEvent(String eventKey) {
        return matchResultRepo.getMatchResultsByEvent(eventKey);
    }

    public LiveData<List<MatchResultWithTeamMatch>> getMatchResultsWithTeamMatchByEvent(String eventKey) {
        return matchResultRepo.getMatchResultsWithTeamMatchByEvent(eventKey);
    }



    public void upsert(MatchResult matchResult) {
        matchResultRepo.upsert(matchResult);
    }
}
