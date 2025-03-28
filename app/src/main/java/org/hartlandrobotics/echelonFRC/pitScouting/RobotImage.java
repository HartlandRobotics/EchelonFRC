package org.hartlandrobotics.echelonFRC.pitScouting;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.hartlandrobotics.echelonFRC.utilities.FileUtilities;

import java.io.File;
import java.util.ArrayList;

public class RobotImage extends PagerAdapter {
   private Context context;
   private ArrayList<String> fileNames = new ArrayList<String>();
   private int teamNumber;
   private File filePath;

   RobotImage(Context context, int teamNum){
      ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
      teamNumber = teamNum;

      filePath = FileUtilities.ensureDirectory(cw,"scouting_images/team_" + teamNumber);
      File[] files = filePath.listFiles();
      for(File file: files){
         fileNames.add(file.getAbsolutePath());
      }
      this.context = context;
   }

   static int lastCount = 0;
   @Override
   public int getCount(){
      ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
      filePath = FileUtilities.ensureDirectory(cw,"scouting_images/team_" + teamNumber);

      File[] files = filePath.listFiles();
         fileNames.clear();
         for(File file: files){
            fileNames.add(file.getAbsolutePath());
         }
         lastCount = files.length;

      return lastCount;
   }

   @NonNull
   @Override
   public Object instantiateItem(@NonNull ViewGroup container, int position) {
      String currentFileName = fileNames.get(position);
      ImageView imageView = new ImageView(context);
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      Bitmap bitmap = BitmapFactory.decodeFile(currentFileName);
      imageView.setImageBitmap( bitmap );
      container.addView(imageView, position);

      return imageView;
   }

   @Override
   public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((ImageView) object);
   }

   @Override
   public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
      return view == object;
   }

   public void setTeamNumber(int teamNumber){
      this.teamNumber = teamNumber;
   }
}

