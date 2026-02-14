package org.hartlandrobotics.echelonFRC.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFRC.database.entities.Team;

import java.util.ArrayList;

public class SyncMatchScore {
    @JsonProperty("key")
    private String matchKey;

    @JsonProperty("match_number")
    private int matchNumber;

    @JsonProperty("score_breakdown")
    private  ScoreBreakdown scoreBreakdown;

    public String getMatchKey(){
        return matchKey;
    }

    public int getMatchNumber(){
        return matchNumber;
    }

    public int getRedTotal(){
        return scoreBreakdown.redAlliance.totalPoints;
    }

    public int getRedFoul(){
        return scoreBreakdown.redAlliance.foulPoints;
    }
    public int getBlueTotal(){
        return scoreBreakdown.blueAlliance.totalPoints;
    }

    public int getBlueFoul(){
        return scoreBreakdown.blueAlliance.foulPoints;
    }

    public MatchScore toMatchScore(){
        MatchScore score = new MatchScore(
                getMatchKey(), getMatchNumber(), getRedTotal(), getRedFoul(), getBlueTotal(), getBlueFoul());
        return score;
    }

    public static class ScoreBreakdown {
        @JsonProperty("blue")
        private MatchAlliance blueAlliance;

        @JsonProperty("red")
        private MatchAlliance redAlliance;
    }

    public static class MatchAlliance {
        @JsonProperty("autoCoralCount")
        int autoCoralCount;
        @JsonProperty("foulPoints")
        int foulPoints;
        @JsonProperty("totalPoints")
        int totalPoints;
    }


}
