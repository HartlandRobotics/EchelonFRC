package org.hartlandrobotics.echelon2.database.currentGame;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class CurrentGamePoints {
    public MatchResult result = null;

    public CurrentGamePoints(MatchResult result) {
        this.result = result;
    }

    public int getAutoPoints() {
        int autoPoints = 0;
        autoPoints += this.getAuto1Points();
        autoPoints += this.getAuto2Points();
        autoPoints += this.getAuto3Points();
        return autoPoints;
    }

    public int getAutoCounts() {
        int autoCounts = 0;
        autoCounts += this.getAuto1Counts();
        autoCounts += this.getAuto2Counts();
        autoCounts += this.getAuto3Counts();
        autoCounts += result.getAuto4() == null ? 0 : 1;
        autoCounts += result.getAuto5() == null ? 0 : 1;
        return autoCounts;
    }

    public int getAuto1Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag1() ? 1:0;

    }
    public int getAuto1Points(){
        if( result == null ) return 0;
        return result.getAutoFlag1()? 2:0;
    }

    public int getAuto2Counts(){
        if( result == null ) return 0;
        return result.getAutoInt2();
    }

    public int getAuto2Points(){
        if( result == null ) return 0;
        return result.getAutoInt2() * 2;
    }

    public int getAuto3Counts(){
        if( result == null ) return 0;
        return result.getAutoInt3();
    }

    public int getAuto3Points(){
        if( result == null ) return 0;

        return result.getAutoInt3() * 5;
    }

    public boolean getAuto4(){
        return result.getAuto4() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setAuto4(boolean isSet){
        result.setAuto4(result.getAuto4() == null ? null : new Date().toString());
    }

    public boolean getAuto5(){
        return result.getAuto5() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setAuto5(boolean isSet){
        result.setAuto5(result.getAuto5() == null ? null : new Date().toString());
    }

    public int getTeleOpPoints() {

        int teleOpPoints = 0;
        teleOpPoints += this.getTeleOpInt1Points();
        teleOpPoints += this.getTeleOpInt2Points();
        teleOpPoints += this.getTeleOpInt3Points();
        return teleOpPoints;
    }

    public int getTeleOpCounts() {

        int teleOpCounts = 0;
        teleOpCounts += this.getTeleOpInt1Points();
        teleOpCounts += this.getTeleOpInt2Points();
        teleOpCounts += this.getTeleOpInt3Points();
        teleOpCounts += result.getTeleOp4() == null ? 0 : 1;
        teleOpCounts += result.getTeleOp5() == null ? 0 : 1;
        return teleOpCounts;
    }

    public int getTeleOpInt1Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt1();
    }

    public int getTeleOpInt1Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt1()*1;
    }

    public int getTeleOpInt2Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt2();
    }

    public int getTeleOpInt2Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt2() * 2;
    }

    public int getTeleOpInt3Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt3();
    }
    public int getTeleOpInt3Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt3()*5;
    }

    public boolean getTeleOp4(){
        return result.getTeleOp4() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setTeleOp4(boolean isSet){
        result.setTeleOp4(result.getTeleOp4() == null ? null : new Date().toString());
    }

    public boolean getTeleOp5(){
        return result.getTeleOp5() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setTeleOp5(boolean isSet){
        result.setTeleOp5(result.getTeleOp5() == null ? null : new Date().toString());
    }

    public int getDefenseCount(){
        if( result == null ) return 0;
        return result.getDefenseCount();
    }

    public int getEndPoints() {

        int endPoints = 0;
        endPoints += this.getEndFlag1Points();
        endPoints += this.getEndFlag2Points();
        endPoints += this.getEndInt3Points();
        endPoints += this.getEndFlag4Points();
        endPoints += this.getEndFlag5Points();
        return endPoints;
    }

    public int getEndCounts() {
        int endCounts = 0;
        endCounts += this.getEndFlag1Counts();
        endCounts += this.getEndFlag2Counts();
        endCounts += this.getEndInt3Counts();
        endCounts += this.getEndFlag4Counts();
        endCounts += this.getEndFlag5Counts();
        return endCounts;
    }

    public int getEndFlag1Points(){
        return result.getEndFlag1() ? 1:0;
    }

    public int getEndFlag1Counts(){
        return result.getEndFlag1() ? 1:0;
    }

    public int getEndFlag2Points(){
        return result.getEndFlag2() ? 3:0;
    }

    public int getEndFlag2Counts(){
        return result.getEndFlag2() ? 1:0;
    }

    public int getEndInt3Points(){
        return result.getEndInt3();
    }

    public int getEndInt3Counts(){
        return result.getEndInt3();
    }

    public int getEndFlag4Points(){
        return result.getEndFlag4()?2:0;
    }

    public int getEndFlag4Counts(){
        return result.getEndFlag4()? 1:0;
    }
    public int getEndFlag5Points(){
        return result.getEndFlag5()?5:0;
    }

    public int getEndFlag5Counts(){
        return result.getEndFlag5()? 1:0;
    }


}
