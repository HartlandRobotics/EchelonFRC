package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
//import org.hartlandrobotics.echelon2.database.currentGame.CurrentGameCounts;
import org.hartlandrobotics.echelon2.database.currentGame.CurrentGamePoints;

import java.util.UUID;

@Entity(tableName="match_result")
public class MatchResult {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "match_result_key")
    private String matchResultKey;

    @NonNull
    @ColumnInfo(name = "event_key")
    private String eventKey;

    @NonNull
    @ColumnInfo(name = "match_key")
    private String matchKey;

    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "has_been_synced")
    private boolean hasBeenSynced;

    @ColumnInfo(name="auto_flag_1")
    private boolean autoFlag1;

    @ColumnInfo(name = "auto_int_2")
    private int autoInt2;

    @ColumnInfo( name = "auto_int_3")
    private int autoInt3;

    @ColumnInfo( name = "auto_4")
    private String auto4;

    @ColumnInfo( name = "auto_5")
    private String auto5;

    @ColumnInfo( name = "teleop_int_1")
    private int teleOpInt1;

    @ColumnInfo( name = "teleop_int_2")
    private int teleOpInt2;

    @ColumnInfo( name = "teleop_int_3")
    private int teleOpInt3;

    @ColumnInfo( name = "teleop_4")
    private String teleOp4;

    @ColumnInfo( name = "teleop_5")
    private String teleOp5;

    @ColumnInfo( name = "end_1")
    private String end1;

    @ColumnInfo(name = "end_2")
    private String end2;

    @ColumnInfo(name = "end_3")
    private String end3;

        @ColumnInfo(name = "end_4")
    private String end4;

    @ColumnInfo(name = "end_5")
    private String end5;

    @ColumnInfo(name = "additional_notes")
    private String additionalNotes;

    @ColumnInfo(name = "defense_count")
    private int defenseCount;

    @Ignore
    public MatchResult(){}

    public MatchResult(
            @NonNull String matchResultKey,
            @NonNull String eventKey,
            @NonNull String matchKey,
            @NonNull String teamKey,
            boolean hasBeenSynced,
            boolean autoFlag1,
            int autoInt2,
            int autoInt3,
            @NonNull String auto4,
            @NonNull String auto5,
            int teleOpInt1,
            int teleOpInt2,
            int teleOpInt3,
            @NonNull String teleOp4,
            @NonNull String teleOp5,
            @NonNull String end1,
            @NonNull String end2,
            @NonNull String end3,
            @NonNull String end4,
            @NonNull String end5,
            String additionalNotes,
            int defenseCount
    ) {

        this.matchResultKey = StringUtils.defaultIfBlank(matchResultKey, UUID.randomUUID().toString());
        this.eventKey = eventKey;
        this.matchKey = matchKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;
        this.autoFlag1 = autoFlag1;
        this.autoInt2 = autoInt2;
        this.autoInt3 = autoInt3;
        this.auto4 = auto4;
        this.auto5 = auto5;
        this.teleOpInt1 = teleOpInt1;
        this.teleOpInt2 = teleOpInt2;
        this.teleOpInt3 = teleOpInt3;
        this.teleOp4 = teleOp4;
        this.teleOp5 = teleOp5;
        this.end1 = end1;
        this.end2 = end2;
        this.end3 = end3;
        this.end4 = end4;
        this.end5 = end5;
        this.additionalNotes = additionalNotes;
        this.defenseCount = defenseCount;
    }

    public String getMatchResultKey() { return matchResultKey; }
    public void setMatchResultKey(String matchResultKey){
        this.matchResultKey = matchResultKey;
    }

    public String getEventKey(){ return eventKey; }
    public void setEventKey(String eventKey){
        this.eventKey = eventKey;
    }

    public String getMatchKey(){ return matchKey; }
    public void setMatchKey(String matchKey){ this.matchKey = matchKey; }

    public String getTeamKey(){ return teamKey; }
    public void setTeamKey(String teamKey){
        this.teamKey = teamKey;
    }

    public boolean getHasBeenSynced(){ return hasBeenSynced; }
    public void setHasBeenSynced(boolean hasBeenSynced){
        this.hasBeenSynced = hasBeenSynced;
    }

    public boolean getAutoFlag1(){ return autoFlag1; }
    public void setAutoFlag1(boolean autoFlag1){ this.autoFlag1 = autoFlag1; }

    public int getAutoInt2(){ return autoInt2; }
    public void setAutoInt2(int autoInt2){ this.autoInt2 = autoInt2; }

    public int getAutoInt3(){ return autoInt3; }
    public void setAutoInt3(int autoInt3){ this.autoInt3 = autoInt3; }

    public String getAuto4(){ return auto4; }
    public void setAuto4(String auto4){ this.auto4 = auto4; }

    public String getAuto5(){ return auto5; }
    public void setAuto5(String auto5){ this.auto5 = auto5; }

    public int getTeleOpInt1(){ return teleOpInt1; }
    public void setTeleOpInt1(int teleOpInt1){ this.teleOpInt1 = teleOpInt1; }

    public int getTeleOpInt2(){ return teleOpInt2; }
    public void setTeleOpInt2(int teleOpInt2){ this.teleOpInt2 = teleOpInt2; }

    public int getTeleOpInt3(){ return teleOpInt3; }
    public void setTeleOpInt3(int teleOpInt3){ this.teleOpInt3 = teleOpInt3; }

    public String getTeleOp4(){ return teleOp4; }
    public void setTeleOp4(String teleOp4){ this.teleOp4 = teleOp4; }

    public String getTeleOp5(){ return teleOp5; }
    public void setTeleOp5(String teleOp5){ this.teleOp5 = teleOp5; }

    public String getEnd1(){ return end1; }
    public void setEnd1(String end1){ this.end1 = end1; }

    public String getEnd2(){ return end2; }
    public void setEnd2(String end2){ this.end2 = end2; }

    public String getEnd3(){ return end3; }
    public void setEnd3(String end3){ this.end3 = end3; }

    public String getEnd4(){ return end4; }
    public void setEnd4(String end4){ this.end4 = end4; }

    public String getEnd5(){ return end5; }
    public void setEnd5(String end5){ this.end5 = end5; }

    public String getAdditionalNotes(){ return additionalNotes; }
    public void setAdditionalNotes( String additionalNotes ){
        this.additionalNotes = additionalNotes;
    }

    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.defenseCount = defenseCount;
    }

    public static CurrentGamePoints toCurrentGamePoints(MatchResult matchResult){
        return new CurrentGamePoints(matchResult);
    }


}
