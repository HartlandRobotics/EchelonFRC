package org.hartlandrobotics.echelonFRC.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;

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
        return new MatchScore(
                getMatchKey(), getMatchNumber(), getRedTotal(), getRedFoul(), getBlueTotal(), getBlueFoul());
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
