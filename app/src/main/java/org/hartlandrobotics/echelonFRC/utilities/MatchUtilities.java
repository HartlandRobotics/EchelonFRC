package org.hartlandrobotics.echelonFRC.utilities;

import org.hartlandrobotics.echelonFRC.charts.TeamDataViewModel2;
import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MatchUtilities {
    public static List<TeamDataViewModel2> toViewModels(List<MatchResult> matchResults){
        List<TeamDataViewModel2> results = new ArrayList<>();
        if( matchResults == null || matchResults.isEmpty() ) return results;

        Map<String, List<MatchResult>> matchResultsByTeam = new HashMap<>();
        for (MatchResult matchResult : matchResults) {
            String teamKey = matchResult.getTeamKey();
            matchResultsByTeam.computeIfAbsent(teamKey, value -> new ArrayList<>());
            matchResultsByTeam.get(teamKey).add(matchResult);
        }

        // sort by team
        for( Map.Entry<String, List<MatchResult>> entry : matchResultsByTeam.entrySet() ){
            int autoTotal = 0;
            int teleOpTotal = 0;
            int endGameTotal = 0;
            int oprTotal = 0;
            int total = 0;

            Map<Integer, Integer> autoScores = new HashMap<>();
            Map<Integer, Integer> teleOpScores = new HashMap<>();
            Map<Integer, Integer> endGameScores = new HashMap<>();
            Map<Integer, Integer> oprScores = new HashMap<>();

            String key = entry.getKey();
            int teamNumber = Integer.valueOf(entry.getKey().substring(3));

            List<MatchResult> teamMatchResults = entry.getValue();
            for (MatchResult matchResult : teamMatchResults) {
                CurrentGamePoints currentGamePoints = MatchResult.toCurrentGamePoints(matchResult);
                Integer matchNumber = Integer.valueOf(matchResult.getMatchKey().replace(matchResult.getEventKey() + "_qm", ""));

                if (currentGamePoints.getContribution() == 0) {

                    int matchAuto = 0;
                    matchAuto += currentGamePoints.getAutoPoints();
                    autoScores.put(matchNumber, matchAuto);
                    autoTotal += matchAuto;


                    int matchTeleOp = 0;
                    matchTeleOp += currentGamePoints.getTeleOpPoints();
                    teleOpScores.put(matchNumber, matchTeleOp);
                    teleOpTotal += matchTeleOp;


                    int matchEndGame = 0;
                    matchEndGame += currentGamePoints.getEndPoints();
                    endGameTotal += matchEndGame;
                    endGameScores.put(matchNumber, matchEndGame);

                    int matchOpr = 0;
                    oprScores.put(matchNumber, matchOpr);

                    total = autoTotal + teleOpTotal + endGameTotal;
                } else {
                    autoScores.put(matchNumber, 0);
                    teleOpScores.put(matchNumber, 0);
                    endGameScores.put(matchNumber, 0);

                    int matchOpr = 0;
                    matchOpr += currentGamePoints.getContribution();
                    oprTotal += matchOpr;
                    oprScores.put(matchNumber, matchOpr);

                    total = oprTotal;
                }
            }

            // size is only used to calculate averages.
            // 1 is default since it is multiplicitive identity
            int size = matchResults.isEmpty() ? 1 : matchResults.size();
            TeamDataViewModel2 teamData = new TeamDataViewModel2(
                    teamNumber,
                    (float) autoTotal / size,
                    (float) teleOpTotal / size,
                    (float) endGameTotal / size,
                    (float) oprTotal / size,
                    (float) total / size,
                    autoScores,
                    teleOpScores,
                    endGameScores,
                    oprScores
            );

            results.add(teamData);
        }

        return  results;
    }

    public static TeamDataViewModel2 teamData(int teamNumber, List<TeamDataViewModel2> teamsData){
        Optional<TeamDataViewModel2> teamData = teamsData.stream()
                .filter( tdvm -> tdvm.getTeamNumber() == teamNumber )
                .findFirst();

        return teamData.orElse(null);
    }

}
