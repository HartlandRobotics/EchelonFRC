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
        teleOpPoints += result.getTeleOp1() == null ? 0 : 1;
        teleOpPoints += result.getTeleOp2() == null ? 0 : 2;
        teleOpPoints += result.getTeleOp3() == null ? 0 : 5;
        return teleOpPoints;
    }

    public int getTeleOpCounts() {

        int teleOpCounts = 0;
        teleOpCounts += result.getTeleOp1() == null ? 0 : 1;
        teleOpCounts += result.getTeleOp2() == null ? 0 : 1;
        teleOpCounts += result.getTeleOp3() == null ? 0 : 1;
        teleOpCounts += result.getTeleOp4() == null ? 0 : 1;
        teleOpCounts += result.getTeleOp5() == null ? 0 : 1;
        return teleOpCounts;
    }

    public boolean getTeleOp1(){
        return result.getTeleOp1() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setTeleOp1(boolean isSet){
        result.setTeleOp1(result.getTeleOp1() == null ? null : new Date().toString());
    }

    public boolean getTeleOp2(){
        return result.getTeleOp2() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setTeleOp2(boolean isSet){
        result.setTeleOp2(result.getTeleOp2() == null ? null : new Date().toString());
    }

    public boolean getTeleOp3(){
        return result.getTeleOp3() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setTeleOp3(boolean isSet){
        result.setTeleOp3(result.getTeleOp3() == null ? null : new Date().toString());
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

    public boolean getDefenseCount(){
        return result.getDefenseCount() == null ? Boolean.FALSE:Boolean.TRUE;
    }
    public void setDefenseCount( boolean isSet ){

        result.setDefenseCount(result.getDefenseCount() == null ? null : new Date().toString());
    }

    public int getEndPoints() {

        int endPoints = 0;
        endPoints += result.getEnd1() == null ? 0 : 1;
        endPoints += result.getEnd2() == null ? 0 : 3;
        endPoints += result.getEnd3() == null ? 0 : 1;
        endPoints += result.getEnd4() == null ? 0 : 2;
        endPoints += result.getEnd1() == null ? 0 : 5;
        return endPoints;
    }

    public int getEndCounts() {
        int endCounts = 0;
        endCounts += result.getEnd1() == null ? 0 : 1;
        endCounts += result.getEnd2() == null ? 0 : 1;
        endCounts += result.getEnd3() == null ? 0 : 1;
        endCounts += result.getEnd4() == null ? 0 : 1;
        endCounts += result.getEnd5() == null ? 0 : 1;
        return endCounts;
    }

    public boolean getEnd1(){
        return result.getEnd1() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setEnd1(boolean isSet){
        result.setEnd1(result.getEnd1() == null ? null : new Date().toString());
    }

    public boolean getEnd2(){
        return result.getEnd2() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setEnd2(boolean isSet){
        result.setEnd2(result.getEnd2() == null ? null : new Date().toString());
    }

    public boolean getEnd3(){
        return result.getEnd3() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setEnd3(boolean isSet){
        result.setEnd3(result.getEnd3() == null ? null : new Date().toString());
    }

    public boolean getEnd4(){
        return result.getEnd4() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setEnd4(boolean isSet){
        result.setEnd4(result.getEnd4() == null ? null : new Date().toString());
    }

    public boolean getEnd5(){
        return result.getEnd5() == null ? Boolean.FALSE:Boolean.TRUE;
    }

    public void setEnd5(boolean isSet){
        result.setEnd5(result.getEnd5() == null ? null : new Date().toString());
    }


}
