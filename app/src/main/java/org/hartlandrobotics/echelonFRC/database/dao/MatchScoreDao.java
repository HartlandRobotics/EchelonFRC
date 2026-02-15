package org.hartlandrobotics.echelonFRC.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
import java.util.List;

@Dao
public abstract class MatchScoreDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(MatchScore matchScore);

    @Transaction
    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(MatchScore matchScore);

    @Transaction
    @Query("SELECT * FROM match_score")
    public abstract LiveData<List<MatchScore>> getMatchScore();

    @Transaction
    @Query("SELECT * FROM match_score WHERE match_key = :matchKey")
    public abstract LiveData<MatchScore> getMatchScoreByKey(String matchKey);

    @Transaction
    public void upsert(MatchScore matchScore){
        long id = insert(matchScore);
        if(id == -1){
            update(matchScore);
        }
    }

}
