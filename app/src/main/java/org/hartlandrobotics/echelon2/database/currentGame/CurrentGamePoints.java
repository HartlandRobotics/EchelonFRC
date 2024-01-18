package org.hartlandrobotics.echelon2.database.currentGame;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;

public class CurrentGamePoints {
    public CurrentGamePoints(MatchResult result){
        super();
    }

        public int getAutoPoints(){
            return 0;
        }

        public int getTeleOpPoints(){
            return 0;
        }

        public int getEndPoints(){
            return 0;
        }
}
