package org.hartlandrobotics.echelonFRC.charts;

public class TeamListViewModel {
    private String teamNumber;
    boolean isSelected;

    public TeamListViewModel( String teamNumber, boolean selected ){
        this.teamNumber = teamNumber;
        this.isSelected = selected;
    }

    public String getTeamNumber( ){
        return teamNumber;
    }

    public int getTeamInteger(){
        return Integer.valueOf(teamNumber);
    }

    public boolean getIsSelected(){
        return isSelected;
    }
    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

}
