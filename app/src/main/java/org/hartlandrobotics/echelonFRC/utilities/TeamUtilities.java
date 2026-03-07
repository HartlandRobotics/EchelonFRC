package org.hartlandrobotics.echelonFRC.utilities;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.charts.ChartsActivity;
import org.hartlandrobotics.echelonFRC.charts.TeamDataViewModel2;
import org.hartlandrobotics.echelonFRC.charts.TeamListViewModel;
import org.hartlandrobotics.echelonFRC.database.entities.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamUtilities {
    public static String teamNumberAsStr(String source){
        final String TEAMKEY_PREFIX = "frc";

        source = StringUtils.defaultIfBlank(source, "0");

        String teamNumber = source;
        if( source.startsWith(TEAMKEY_PREFIX) ){
            teamNumber = source.substring(TEAMKEY_PREFIX.length());
        }

        return teamNumber;
    }

    public static int teamNumber(String source){
        String teamNumberAsStr = TeamUtilities.teamNumberAsStr(source);

        return Integer.parseInt(teamNumberAsStr);
    }
    public static List<Team> visibleTeams( List<Team> sourceTeams ){
        if( sourceTeams == null || sourceTeams.isEmpty() ){ return new ArrayList<Team>(); }

        return sourceTeams.stream()
                .filter(Team::isVisible)
                .collect(Collectors.toList());
    }

    public static List<TeamListViewModel> toListViewModels( List<Team> teams) {
        List<TeamListViewModel> results = new ArrayList<>();
        if( teams == null || teams.isEmpty() ) return results;

        return teams.stream()
                .map(team -> new TeamListViewModel(String.valueOf(team.getTeamNumber()), team.isVisible(), team.isSelected()))
                .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                .collect(Collectors.toList());
    }
}
