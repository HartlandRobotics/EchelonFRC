//package org.hartlandrobotics.echelonFRC.database.dao;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//import androidx.room.Transaction;
//import androidx.room.Update;
//
//import org.hartlandrobotics.echelonFRC.blueAlliance.ScoreActivity;
//import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
//import org.hartlandrobotics.echelonFRC.database.entities.Team;
//
//import java.util.List;
//
//@Dao
//public abstract class ScoreDao {
//
//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    public abstract long insert(MatchScore matchScore);
//
//    @Transaction
//    @Update(onConflict = OnConflictStrategy.IGNORE)
//    public abstract void update(MatchScore matchScore);
//
//    @Transaction
//    @Query("SELECT * FROM match_score")
//    public abstract LiveData<List<MatchScore>> getMatchScore();
//
//    @Transaction
//    @Query("SELECT * FROM team WHERE team_key = :teamKey")
//    public abstract LiveData<Team> getTeams(String teamKey);
//
//    @Transaction
//    public void upsert(Team team){
//        long id = insert(team);
//        if(id == -1){
//            update(team);
//        }
//    }
//}
