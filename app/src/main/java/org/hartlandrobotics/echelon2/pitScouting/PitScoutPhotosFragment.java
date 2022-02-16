package org.hartlandrobotics.echelon2.pitScouting;

import static android.content.Intent.ACTION_GET_CONTENT;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.hartlandrobotics.echelon2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PitScoutPhotosFragment extends Fragment {
    public PitScoutPhotosFragment() {
        // Required empty public constructor
    }

    private Button cameraButton;
    private ViewPager robotImagesPager;
    RobotImage robotImageAdapter;
    private int teamNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pitscout_photos, container, false);

        SetupImagesArea(); // need to figure out why it's unreachable...
        populateImagesArea();


    }

    private void SetupImagesArea(){
        cameraButton.findViewById(R.id.photoButton);
        robotImagesPager.findViewById(R.id.picture_view_pager);
        robotImageAdapter = new RobotImage(getActivity().getApplicationContext(), 3536);
    }

    private void populateImagesArea(){
        robotImagesPager.setAdapter(robotImageAdapter);

        cameraButton.setOnClickListener(view -> {
            try{
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //registerForActivityResult(ActivityResultContracts.StartActivityForResult(cameraIntent, CAMERA_PIC_REQUEST) );
                //getPreviewImage.launch(cameraIntent);

                ActivityResultLauncher<Intent> pictureActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Bitmap photo = (Bitmap) data.getExtras().get( "data" );
                            SaveImage( photo );
                        }
                    }
                });

                pictureActivityResultLauncher.launch(cameraIntent);
            }
            catch(Exception e){
                Toast.makeText(getActivity().getApplicationContext(), "Couldn't load photo", Toast.LENGTH_LONG).show();
            }
        });
    }

/*
    private static final int CAMERA_PIC_REQUEST = 22;
*/

   /* @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        try {
            switch ( requestCode ) {
                case CAMERA_PIC_REQUEST:
                    if ( resultCode == RESULT_OK ) {
                        try {
                            Bitmap photo = (Bitmap) data.getExtras().get( "data" );
                            SaveImage( photo );
                        } catch ( Exception e ) {
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch ( Exception e ) {
            Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG );
        }
    }*/


    private void SaveImage(Bitmap finalBitmap) {
        if( teamNumber == 0 ){
            Toast.makeText( getActivity(), "Please Select team first...", Toast.LENGTH_LONG).show();
            return;
        }

        File externalFilesDir = getImageFilePath( teamNumber );
        externalFilesDir.mkdirs();
        String path = externalFilesDir.getAbsolutePath();
        File[] files = getImageFiles( teamNumber );


        int nextNumber = getNextFileNumber( files );

        String nextFileName = String.format( "Image%03d.jpg", nextNumber );
        File file = new File( externalFilesDir, nextFileName );


        try {
            FileOutputStream out = new FileOutputStream( file );
            finalBitmap.compress( Bitmap.CompressFormat.JPEG, 90, out );
            out.flush();
            out.close();
        } catch ( Exception e ) {
            Toast t = Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG );
            t.show();

        }
        robotImagesPager.getAdapter().notifyDataSetChanged();
    }

    private int getNextFileNumber(File[] files) {
        int largestNumber = Integer.MIN_VALUE;
        if ( files.length == 0 ) return 1;

        String[] fileNames = Arrays.stream( files )
                .map( (file) -> file.getName() )
                .toArray( String[]::new );

        for ( String currentFileName : fileNames ) {
            Matcher m = Pattern.compile( "\\d+" ).matcher( currentFileName );
            while ( m.find() ) {
                String numberString = m.group();
                largestNumber = Math.max( Integer.parseInt( numberString ), largestNumber );
            }
        }
        return largestNumber + 1;
    }

    private File[] getImageFiles(int teamNumber) {
        return getImageFilePath( teamNumber ).listFiles();
    }

    private File getImageFilePath(int teamNumber) {
        ContextWrapper cw = new ContextWrapper( getActivity().getApplicationContext() );
        return cw.getExternalFilesDir( "scouting_images/team_" + teamNumber );
    }

}