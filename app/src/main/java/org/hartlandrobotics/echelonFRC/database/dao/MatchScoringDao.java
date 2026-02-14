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
//
//import org.hartlandrobotics.echelonFRC.database.entities.MatchScoring;
//import org.hartlandrobotics.echelonFRC.models.MatchScoringSummaryModel;
//
//import java.util.List;
//
//@Dao
//public abstract class MatchScoringDao {
//    @Insert( onConflict = OnConflictStrategy.IGNORE )
//    public abstract long insert( MatchScoring matchScoring );
//
//    @Update( onConflict = OnConflictStrategy.IGNORE )
//    public abstract void update( MatchScoring matchScoring );
//
//    public void upsert( MatchScoring matchScoring ){
//        long id = insert( matchScoring );
//        if( id == -1 ){
//            update( matchScoring );
//        }
//    }
//
//    @Transaction
//    @Query("SELECT * FROM match_scoring WHERE team_key = :teamKey order by match_scoring_key")
//    public abstract LiveData<MatchScoring> getScoringDetailByTeam(String teamKey);
//
//    @Transaction
//    @Query("SELECT s.* from match_scoring s WHERE s.event_key = :eventKey")
//    public abstract LiveData<List<MatchScoring>> getScoringDetailByEvent(String eventKey);
//    @Transaction
//    @Query("SELECT * FROM match_scoring WHERE event_key = :eventKey and match_key = :matchKey order by match_scoring_key")
//    public abstract LiveData<MatchScoring> getScoringDetailByEventMatch(String eventKey,String matchKey);
//
//    @Transaction
//    @Query("SELECT * FROM match_scoring WHERE event_key = :eventKey and team_key = :teamKey order by match_scoring_key")
//    public abstract LiveData<MatchScoring> getScoringDetailByEventTeam(String eventKey,String teamKey);
//
//    @Transaction
//    @Query("SELECT * FROM match_scoring WHERE event_key = :eventKey and team_key = :teamKey and match_key = :matchKey order by match_scoring_key")
//    public abstract LiveData<MatchScoring> getScoringDetailByEventTeamMatch(String eventKey, String teamKey, String matchKey);
//
//    // 02-26-24 jaw -code analysis is having issues with table aliases so commented out for now
//   /* @Transaction
//    @Query(value="with scoringSummary as (\n" +
//            "select t1.*, t2.scoring_time, \n" +
//            "ifnull(Cast ((JulianDay(t1.scoring_time) - JulianDay(t2.scoring_time)) * 24 * 60 * 60 As Integer),0) as seconds\n" +
//            "from matchscoring as t1 left outer join matchscoring as t2 on t1.match_scoring_key - 1 = t2.match_scoring_key\n" +
//            "and t1.team_key = t2.team_key and t1.event_key = t2.event_key and t1.match_key = t2.match_key\n" +
//            "where t1.team_key = :teamKey \n" +
//            "and t1.event_key =  :eventKey \n" +
//            "and t1.match_key = :matchKey \n" +
//            ") \n" +
//            "select x.event_key as eventKey, x.team_key as teamKey, x.match_key as matchKey, s.scoring_type as scoringType, count(*) as totalCt, sum(s.scoring_value) as totalScore, round(cast(sum(x.seconds) as float)/cast ((count(*)-1) as float),2) as AvgCycleTime \n" +
//            " from scoringSummary as x inner join seasonscoring s on x.season_scoring_key = s.season_scoring_key\n" +
//            "group by x.event_key, x.team_key, x.match_key,s.scoring_type")
//    public abstract LiveData<List<MatchScoringSummaryModel>> getScoringSummaryByEventTeamMatch(String eventKey, String teamKey, String matchKey);
//
//    @Transaction
//    @Query(value="with scoringSummary as (\n" +
//            "select t1.*, t2.scoring_time, \n" +
//            "ifnull(Cast ((JulianDay(t1.scoring_time) - JulianDay(t2.scoring_time)) * 24 * 60 * 60 As Integer),0) as seconds\n" +
//            "from matchscoring as t1 left outer join matchscoring as t2 on t1.match_scoring_key - 1 = t2.match_scoring_key\n" +
//            "and t1.team_key = t2.team_key and t1.event_key = t2.event_key and t1.match_key = t2.match_key\n" +
//            "where t1.team_key = :teamKey \n" +
//            "and t1.event_key =  :eventKey \n" +
//            ") \n" +
//            "select x.event_key as eventKey, x.team_key as teamKey, x.match_key as matchKey, s.scoring_type as scoringType, count(*) as totalCt, sum(s.scoring_value) as totalScore, round(cast(sum(x.seconds) as float)/cast ((count(*)-1) as float),2) as AvgCycleTime \n" +
//            " from scoringSummary as x inner join seasonscoring as s on x.season_scoring_key = s.season_scoring_key\n" +
//            "group by x.event_key, x.team_key, x.match_key,s.scoring_type")
//    public abstract LiveData<List<MatchScoringSummaryModel>> getScoringSummaryByEventTeam(String eventKey, String teamKey);
//
//    @Transaction
//    @Query("with scoringSummary as (\n" +
//            "select t1.*, t2.scoring_time, \n" +
//            "ifnull(Cast ((JulianDay(t1.scoring_time) - JulianDay(t2.scoring_time)) * 24 * 60 * 60 As Integer),0) as seconds\n" +
//            "from matchscoring as t1 left outer join matchscoring as t2 on t1.match_scoring_key - 1 = t2.match_scoring_key\n" +
//            "and t1.team_key = t2.team_key and t1.event_key = t2.event_key and t1.match_key = t2.match_key\n" +
//            "where t1.team_key = :teamKey \n" +
//            ") \n" +
//            "select x.event_key as eventKey, x.team_key as teamKey, x.match_key as matchKey, s.scoring_type as scoringType, count(*) as totalCt, sum(s.scoring_value) as totalScore, round(cast(sum(x.seconds) as float)/cast ((count(*)-1) as float),2) as AvgCycleTime \n" +
//            " from scoringSummary as x inner join seasonscoring as s on x.season_scoring_key = s.season_scoring_key\n" +
//            "group by x.event_key, x.team_key, x.match_key,s.scoring_type")
//    public abstract LiveData<List<MatchScoringSummaryModel>> getScoringSummaryByTeam(String teamKey);
//
//    @Transaction
//    @Query("with scoringSummary as (\n" +
//            "select t1.*, t2.scoring_time, \n" +
//            "ifnull(Cast ((JulianDay(t1.scoring_time) - JulianDay(t2.scoring_time)) * 24 * 60 * 60 As Integer),0) as seconds\n" +
//            "from matchscoring as t1 left outer join matchscoring as t2 on t1.match_scoring_key - 1 = t2.match_scoring_key\n" +
//            "and t1.team_key = t2.team_key and t1.event_key = t2.event_key and t1.match_key = t2.match_key\n" +
//            "where t1.event_key =  :eventKey \n" +
//            ") \n" +
//            "select x.event_key as eventKey, x.team_key as teamKey, x.match_key as matchKey, s.scoring_type as scoringType, count(*) as totalCt, sum(s.scoring_value) as totalScore, round(cast(sum(x.seconds) as float)/cast ((count(*)-1) as float),2) as AvgCycleTime \n" +
//            " from scoringSummary as x inner join seasonscoring as s on x.season_scoring_key = s.season_scoring_key\n" +
//            "group by x.event_key, x.team_key, x.match_key,s.scoring_type")
//    public abstract LiveData<List<MatchScoringSummaryModel>> getScoringSummaryByEvent(String eventKey);
//*/
//
//}
