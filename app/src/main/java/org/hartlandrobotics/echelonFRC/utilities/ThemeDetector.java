package org.hartlandrobotics.echelonFRC.utilities;

import android.content.Context;
import android.content.res.Configuration;

public class ThemeDetector {
     public static boolean isDarkTheme(Context context){
         int currentNightMode = context.getResources().getConfiguration().uiMode  & Configuration.UI_MODE_NIGHT_MASK;

         switch (currentNightMode){
             case Configuration.UI_MODE_NIGHT_NO:
                 return false;
             case Configuration.UI_MODE_NIGHT_YES:
                 return true;
             default:
                 return false;
         }
     }
}
