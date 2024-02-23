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

    @ColumnInfo( name = "end_flag_1")
    private boolean endFlag1;

    @ColumnInfo(name = "end_flag_2")
    private boolean endFlag2;

    @ColumnInfo(name = "end_int_3")
    private int endInt3;

    @ColumnInfo(name = "end_flag_4")
    private boolean endFlag4;

    @ColumnInfo(name = "end_flag_5")
    private boolean endFlag5;

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
            boolean endFlag1,
            boolean endFlag2,
            int endInt3,
            boolean endFlag4,
            boolean endFlag5,
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
        this.endFlag1 = endFlag1;
        this.endFlag2 = endFlag2;
        this.endInt3 = endInt3;
        this.endFlag4 = endFlag4;
        this.endFlag5 = endFlag5;
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

    public boolean getEndFlag1(){ return endFlag1; }
    public void setEndFlag1(boolean endFlag1){ this.endFlag1 = endFlag1; }

    public boolean getEndFlag2(){ return endFlag2; }
    public void setEndFlag2(boolean endFlag2){ this.endFlag2 = endFlag2; }

    public int getEndInt3(){ return endInt3; }
    public void setEndInt3(int endInt3){ this.endInt3 = endInt3; }

    public boolean getEndFlag4(){ return endFlag4; }
    public void setEndFlag4(boolean endFlag4){ this.endFlag4 = endFlag4; }

    public boolean getEndFlag5(){ return endFlag5; }
    public void setEndFlag5(boolean endFlag5){ this.endFlag5 = endFlag5; }

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
