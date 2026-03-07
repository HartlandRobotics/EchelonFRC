package org.hartlandrobotics.echelonFRC.charts;

public class TeamListViewModel {
    private String teamNumber;
    private boolean isVisible;
    private boolean isSelected;

    public TeamListViewModel( String teamNumber, boolean visible, boolean selected ){
        this.teamNumber = teamNumber;
        this.isVisible = visible;
        this.isSelected = selected;
    }

    public String getTeamNumber( ){
        return teamNumber;
    }

    public int getTeamInteger(){
        return Integer.valueOf(teamNumber);
    }

    public boolean isVisible(){
        return isVisible;
    }
    public void setIsVisible(boolean isSelected){ this.isVisible = isSelected; }

    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}
