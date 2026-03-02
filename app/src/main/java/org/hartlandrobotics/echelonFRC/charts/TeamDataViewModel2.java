package org.hartlandrobotics.echelonFRC.charts;

import java.util.Map;

public class TeamDataViewModel2 {
    private int teamNumber;
    private float autoAverage;
    private float teleOpAverage;
    private float endGameAverage;
    private float oprAverage;
    private float totalAverage;
    private Map<Integer, Integer> autoScores;
    private Map<Integer, Integer> teleOpScores;
    private Map<Integer, Integer> endGameScores;
    private Map<Integer, Integer> oprScores;


    public TeamDataViewModel2(int teamNumber, float autoAverage, float teleOpAverage, float endGameAverage, float oprAverage, float totalAverage,
                              Map<Integer, Integer> autoScores, Map<Integer, Integer> teleOpScores, Map<Integer, Integer> endGameScores, Map<Integer, Integer> oprScores) {
        this.teamNumber = teamNumber;
        this.autoAverage = autoAverage;
        this.teleOpAverage = teleOpAverage;
        this.endGameAverage = endGameAverage;
        this.oprAverage = oprAverage;
        this.totalAverage = totalAverage;
        this.autoScores = autoScores;
        this.teleOpScores = teleOpScores;
        this.endGameScores = endGameScores;
        this.oprScores = oprScores;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public float getAutoAverage() {
        return autoAverage;
    }

    public float getTeleOpAverage() {
        return teleOpAverage;
    }

    public float getEndGameAverage() {
        return endGameAverage;
    }

    public float getOprAverage() {
        return oprAverage;
    }

    public float getTotalAverage() {
        return totalAverage;
    }

    public Map<Integer, Integer> getAutoScores() {
        return autoScores;
    }

    public Map<Integer, Integer> getTeleOpScores() {
        return teleOpScores;
    }

    public Map<Integer, Integer> getEndGameScores() {
        return endGameScores;
    }

    public Map<Integer, Integer> getOprScores() {
        return oprScores;
    }

}
