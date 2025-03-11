package org.hartlandrobotics.echelonFRC.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
//import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGameCounts;
import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;

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

    @ColumnInfo(name="has_been_synced")
    private boolean hasBeenSynced;


    @ColumnInfo(name="auto_flag_1")
    private boolean autoFlag1;




    @ColumnInfo(name="auto_flag_2")
    private boolean autoFlag2;
    @ColumnInfo(name="auto_flag_3")
    private boolean autoFlag3;
    @ColumnInfo(name="auto_flag_4")
    private boolean autoFlag4;
    @ColumnInfo(name="auto_flag_5")
    private boolean autoFlag5;

    @ColumnInfo(name = "auto_int_6")
    private int autoInt6;
    @ColumnInfo( name = "auto_int_7")
    private int autoInt7;
    @ColumnInfo(name = "auto_int_8")
    private int autoInt8;
    @ColumnInfo(name = "auto_int_9")
    private int autoInt9;
    @ColumnInfo(name = "auto_int_10")
    private int autoInt10;
    @ColumnInfo(name = "auto_int_11")
    private int autoInt11;


    @ColumnInfo( name = "teleop_int_6")
    private int teleOpInt6;

    @ColumnInfo( name = "teleop_int_7")
    private int teleOpInt7;

    @ColumnInfo( name = "teleop_int_8")
    private int teleOpInt8;

    @ColumnInfo( name = "teleop_int_9")
    private int teleOpInt9;

    @ColumnInfo( name = "teleop_int_10")
    private int teleOpInt10;

    @ColumnInfo( name = "teleop_int_11")
    private int teleOpInt11;
    @ColumnInfo( name = "teleop_int_12")
    private int teleOpInt12;

    @ColumnInfo( name = "end_flag_1")
    private boolean endFlag1;

    @ColumnInfo(name = "end_flag_2")
    private boolean endFlag2;

    @ColumnInfo(name = "end_flag_3")
    private boolean endFlag3;

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
            boolean autoFlag2,
            boolean autoFlag3,
            boolean autoFlag4,
            boolean autoFlag5,

            int autoInt6,
            int autoInt7,
            int autoInt8,
            int autoInt9,
            int autoInt10,
            int autoInt11,


            int teleOpInt6,
            int teleOpInt7,
            int teleOpInt8,
            int teleOpInt9,
            int teleOpInt10,
            int teleOpInt11,
            int teleOpInt12,


            boolean endFlag1,
            boolean endFlag2,
            boolean endFlag3,
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
        this.autoFlag2 = autoFlag2;
        this.autoFlag3 = autoFlag3;
        this.autoFlag4 = autoFlag4;
        this.autoFlag5 = autoFlag5;

        this.autoInt6 = autoInt6;
        this.autoInt7 = autoInt7;
        this.autoInt8 = autoInt8;
        this.autoInt9 = autoInt9;
        this.autoInt10 = autoInt10;
        this.autoInt11 = autoInt11;

        this.teleOpInt6 = teleOpInt6;
        this.teleOpInt7 = teleOpInt7;
        this.teleOpInt8 = teleOpInt8;
        this.teleOpInt9 = teleOpInt9;
        this.teleOpInt10 = teleOpInt10;
        this.teleOpInt11 = teleOpInt11;
        this.teleOpInt12 = teleOpInt12;


        this.endFlag1 = endFlag1;
        this.endFlag2 = endFlag2;
        this.endFlag3 = endFlag3;
        this.endFlag4 = endFlag4;
        this.endFlag5 = endFlag5;

        this.additionalNotes = additionalNotes.replaceAll(",",".").replaceAll("\"",StringUtils.EMPTY).replaceAll("\n",StringUtils.EMPTY);
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

    public boolean getHasBeenSynced() { return hasBeenSynced; }
    public void setHasBeenSynced(boolean hasBeenSynced){ this.hasBeenSynced = hasBeenSynced; }

    public boolean getAutoFlag1() { return autoFlag1; }
    public void setAutoFlag1(boolean autoFlag1) { this.autoFlag1 = autoFlag1; }

    public boolean getAutoFlag2() { return autoFlag2; }
    public void setAutoFlag2(boolean autoFlag2) { this.autoFlag2 = autoFlag2; }

    public boolean getAutoFlag3() { return autoFlag3; }
    public void setAutoFlag3(boolean autoFlag3) { this.autoFlag3 = autoFlag3; }

    public boolean getAutoFlag4() { return autoFlag4; }
    public void setAutoFlag4(boolean autoFlag4) { this.autoFlag4 = autoFlag4; }

    public boolean getAutoFlag5() { return autoFlag5; }
    public void setAutoFlag5(boolean autoFlag5) { this.autoFlag5 = autoFlag5; }

    public int getAutoInt6() { return autoInt6; }
    public void setAutoInt6(int autoInt6) { this.autoInt6 = autoInt6; }

    public int getAutoInt7() { return autoInt7; }
    public void setAutoInt7(int autoInt7) { this.autoInt7 = autoInt7; }

    public int getAutoInt8() { return autoInt8; }
    public void setAutoInt8(int autoInt8) { this.autoInt8 = autoInt8; }

    public int getAutoInt9() { return autoInt9; }
    public void setAutoInt9(int autoInt9) { this.autoInt9 = autoInt9; }

    public int getAutoInt10() { return autoInt10; }
    public void setAutoInt10(int autoInt10) { this.autoInt10 = autoInt10; }

    public int getAutoInt11() { return autoInt11; }
    public void setAutoInt11(int autoInt11) { this.autoInt11 = autoInt11; }

    public int getTeleOpInt6() { return teleOpInt6; }
    public void setTeleOpInt6(int teleOpInt6) { this.teleOpInt6 = teleOpInt6; }

    public int getTeleOpInt7() { return teleOpInt7; }
    public void setTeleOpInt7(int teleOpInt7) { this.teleOpInt7 = teleOpInt7; }

    public int getTeleOpInt8() { return teleOpInt8; }
    public void setTeleOpInt8(int teleOpInt8) { this.teleOpInt8 = teleOpInt8; }

    public int getTeleOpInt9() { return teleOpInt9; }
    public void setTeleOpInt9(int teleOpInt9) { this.teleOpInt9 = teleOpInt9; }

    public int getTeleOpInt10() { return teleOpInt10; }
    public void setTeleOpInt10(int teleOpInt10) { this.teleOpInt10 = teleOpInt10; }

    public int getTeleOpInt11() { return teleOpInt11; }
    public void setTeleOpInt11(int teleOpInt11) { this.teleOpInt11 = teleOpInt11; }

    public int getTeleOpInt12() { return teleOpInt12; }
    public void setTeleOpInt12(int teleOpInt12) { this.teleOpInt12 = teleOpInt12; }

    public boolean getEndFlag1() { return endFlag1; }
    public void setEndFlag1(boolean endFlag1) { this.endFlag1 = endFlag1; }

    public boolean getEndFlag2() { return endFlag2; }
    public void setEndFlag2(boolean endFlag2) { this.endFlag2 = endFlag2; }

    public boolean getEndFlag3() { return endFlag3; }
    public void setEndFlag3(boolean endFlag3) { this.endFlag3 = endFlag3; }

    public boolean getEndFlag4() { return endFlag4; }
    public void setEndFlag4(boolean endFlag4) { this.endFlag4 = endFlag4; }

    public boolean getEndFlag5() { return endFlag5; }
    public void setEndFlag5(boolean endFlag5) { this.endFlag5 = endFlag5; }

    public String getAdditionalNotes(){
        if(StringUtils.isBlank(additionalNotes)){
            additionalNotes = "empty";
        }
        return additionalNotes.replaceAll(",",".");
    }
    public void setAdditionalNotes( String additionalNotes ){
        if(StringUtils.isBlank(additionalNotes)){
            additionalNotes = "empty";
        }
        this.additionalNotes = additionalNotes.replaceAll(",",".");

    }

    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.defenseCount = defenseCount;
    }

    public static CurrentGamePoints toCurrentGamePoints(MatchResult matchResult){
        return new CurrentGamePoints(matchResult);
    }


}
