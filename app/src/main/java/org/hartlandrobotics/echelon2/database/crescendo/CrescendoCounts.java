package org.hartlandrobotics.echelon2.database.crescendo;

import org.hartlandrobotics.echelon2.database.currentGame.CurrentGameCounts;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;

public class CrescendoCounts extends CurrentGameCounts {
    int leaveLineAuto = 0;
    int ampNoteAuto = 0;
    int speakerNoteAuto = 0;

    int ampNoteTeleOp = 0;
    int neutralSpeakerNoteTeleOp = 0;
    int ampSpeakerNoteTeleOp = 0;

    int parkedEnd = 0;
    int onstageEnd = 0;
    int spotlitEnd = 0;
    int harmonyEnd = 0;
    int trapNoteEnd = 0;

    public CrescendoCounts(MatchResult result){
        leaveLineAuto   = result.getAuto1() == null?0:1;
        ampNoteAuto   = result.getAuto2() == null?0:1;
        speakerNoteAuto   = result.getAuto3() == null?0:1;
        ampNoteTeleOp   = result.getTeleOp1() == null?0:1;
        neutralSpeakerNoteTeleOp   = result.getTeleOp2() == null?0:1;
        ampSpeakerNoteTeleOp   = result.getTeleOp3() == null?0:1;
        parkedEnd   = result.getEnd1() == null?0:1;
        onstageEnd   = result.getEnd2() == null?0:1;
        spotlitEnd   = result.getEnd3() == null?0:1;
        harmonyEnd   = result.getEnd4() == null?0:1;
        trapNoteEnd   = result.getEnd5() == null?0:1;
    }
    public int getAutoCounts(){
        return leaveLineAuto + ampNoteAuto + speakerNoteAuto;
    }

    public int getTeleOpCounts(){
        return ampNoteTeleOp + neutralSpeakerNoteTeleOp + ampSpeakerNoteTeleOp;
    }

    public int getEndCounts(){
        return parkedEnd + onstageEnd + spotlitEnd + harmonyEnd + trapNoteEnd;
    }


}
