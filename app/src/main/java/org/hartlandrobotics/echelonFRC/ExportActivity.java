package org.hartlandrobotics.echelonFRC;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.database.entities.Match;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResultWithTeamMatch;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;
import org.hartlandrobotics.echelonFRC.database.entities.Team;
import org.hartlandrobotics.echelonFRC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFRC.models.PitScoutViewModel;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;
import org.hartlandrobotics.echelonFRC.utilities.FileUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExportActivity extends EchelonActivity {

    private Button exportMatchResultsButton;
    private Button exportPitScoutResultsButton;
    private Button importCSVMatchButton;

    private MatchResultViewModel matchResultViewModel;

    public static void launch(Context context){
        Intent intent = new Intent( context, ExportActivity.class );
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setupToolbar("Export Data");

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);

        exportMatchResultsButton = findViewById(R.id.exportMatchResults);
        setupExportCSVButton();

        exportPitScoutResultsButton = findViewById(R.id.exportPitScouting);
        exportPitScoutResults();

        importCSVMatchButton = findViewById(R.id.importMatchCSV);
        setupCSVImportButton();
    }

    public void exportMatchResults() throws RuntimeException {
            Context appContext = getApplicationContext();
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            File externalFilesDir = getFilePathForMatch();
            externalFilesDir.mkdirs();
            String path = externalFilesDir.getAbsolutePath();
            File[] files = getFilePathsForMatch();
            MatchResultViewModel matchResultViewModel = new MatchResultViewModel(getApplication());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
            Date date = new Date();
            String dateForFile = dateFormat.format(date);
            String fileName = "Match_Data_" + dateForFile + ".csv";
            File file = new File( externalFilesDir, fileName);

            matchResultViewModel.getMatchResultsWithTeamMatchByEvent(status.getEventKey()).observe(this, matchResults -> {

                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    String header = "Event_Key,Match_Key,Team_Key,Match_Number,Team_Number"
                            + ",AutoFlag1 ,AutoFlag2, AutoFlag3, AutoFlag4, AutoFlag5"
                            + ",AutoInt6, AutoInt7, AutoInt8, AutoInt9, AutoInt10, AutoInt11"
                            + ",TeleOpInt6, TeleOpInt7,TeleOpInt8, TeleOpInt9, TeleOpInt10,TeleOpInt11,TeleOpInt12"
                            + ",EndFlag1,EndFlag2,EndInt3,EndFlag4, EndFlag5"
                            + ",DefensesCount"
                            + ",Match_Result_Key"
                            + ", AdditionalNotes\n";
                    outputStream.write(header.getBytes());
                    for (MatchResultWithTeamMatch matchResultWithTeamMatch : matchResults) {

                        MatchResult mr = matchResultWithTeamMatch.matchResult;
                        Match m = matchResultWithTeamMatch.match;
                        Team t = matchResultWithTeamMatch.team;

                        List<String> dataForFile = new ArrayList<>();
                        dataForFile.add(mr.getEventKey());
                        dataForFile.add(mr.getMatchKey());
                        dataForFile.add(mr.getTeamKey());
                        dataForFile.add(String.valueOf(m.getMatchNumber()));
                        dataForFile.add(String.valueOf(t.getTeamNumber()));

                        dataForFile.add(String.valueOf(mr.getAutoFlag1()));
                        dataForFile.add(String.valueOf(mr.getAutoFlag2()));
                        dataForFile.add(String.valueOf(mr.getAutoFlag3()));
                        dataForFile.add(String.valueOf(mr.getAutoFlag4()));
                        dataForFile.add(String.valueOf(mr.getAutoFlag5()));

                        dataForFile.add(String.valueOf(mr.getAutoInt6()));
                        dataForFile.add(String.valueOf(mr.getAutoInt7()));
                        dataForFile.add(String.valueOf(mr.getAutoInt8()));
                        dataForFile.add(String.valueOf(mr.getAutoInt9()));
                        dataForFile.add(String.valueOf(mr.getAutoInt10()));
                        dataForFile.add(String.valueOf(mr.getAutoInt11()));

                        dataForFile.add(String.valueOf(mr.getTeleOpInt6()));
                        dataForFile.add(String.valueOf(mr.getTeleOpInt7()));
                        dataForFile.add(String.valueOf(mr.getTeleOpInt8()));
                        dataForFile.add(String.valueOf(mr.getTeleOpInt9()));
                        dataForFile.add(String.valueOf(mr.getTeleOpInt10()));
                        dataForFile.add(String.valueOf(mr.getTeleOpInt11()));
                        dataForFile.add(String.valueOf(mr.getTeleOpInt12()));


                        dataForFile.add(String.valueOf(mr.getEndFlag1()));
                        dataForFile.add(String.valueOf(mr.getEndFlag2()));
                        dataForFile.add(String.valueOf(mr.getEndFlag3()));
                        dataForFile.add(String.valueOf(mr.getEndFlag4()));
                        dataForFile.add(String.valueOf(mr.getEndFlag5()));

                        dataForFile.add(String.valueOf(mr.getDefenseCount()));
                        dataForFile.add(mr.getMatchResultKey());
                        dataForFile.add(mr.getAdditionalNotes());

                        String outputString = dataForFile.stream().collect(Collectors.joining(",")) + "\n";
                        outputStream.write(outputString.getBytes());
                    }
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
 }

    public void setupExportCSVButton(){
        exportMatchResultsButton.setOnClickListener((view) -> {
            try {
                exportMatchResults();
                Toast.makeText(this, "export Matches: ", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void exportPitScoutResults(){
        exportPitScoutResultsButton.setOnClickListener((view) -> {
            Context appContext = getApplicationContext();
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            File externalFilesDir = getFilePathForPitScout();
            externalFilesDir.mkdirs();
            String path = externalFilesDir.getAbsolutePath();
            File[] files = getFilePathsForPitScout();
            PitScoutViewModel pitScoutViewModel = new PitScoutViewModel(getApplication());

            String fileName = "PitScout_Data.html";
            File file = new File(externalFilesDir, fileName);


            String strBegin = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<link type=\"text/css\" rel=\"stylesheet\" href=\"css/materialize.min.css\" media=\"screen,projection\"/>" +
                    "<link type=\"text/css\" rel=\"stylesheet\" href=\"css/pitscout.css\"/>" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1,0\"/>" +
                    "</head>" +
                    "<body>" +
                    "<h1> Pit Scout <h1>";
            String strEnd = "<script type=\"text/javascript\" src=\"js/materialize.min.js\"></script>" +
                    "</body>" +
                    "</html>";
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.e("In Catch for Creating File", "Exception trying to export pitscout data", e);
                    String message = e.getLocalizedMessage();
                    Toast.makeText(this, "export Pit Scout error: " + message, Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
            pitScoutViewModel.getPitScoutByEvent(status.getEventKey()).observe(this, pitScoutResults -> {
                try{
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    StringBuilder content = new StringBuilder(strBegin);

                    for(PitScout ps: pitScoutResults){
                        content.append( "<h2>" + ps.getTeamKey() + "</h2>")
                                .append( "<h3>Auto</h3>")
                                .append("<ol>")
                                .append("<li>")
                                .append ("<p class=question>Does your team perform autonomous?</p>")
                                .append ("<p class=answer>").append(ps.getHasAutonomous()).append("</p>")
                                .append("</li>")
                                .append("<li>")
                                .append ("<p class=question>Would you like help creating one?</p>")
                                .append ("<p class=answer>").append(ps.getHelpCreatingAuto()).append("</p>")
                                .append("</li>");

                                String codingLanguage = ps.getCodingLanguage();
                                if (!StringUtils.isBlank(codingLanguage)){
                                    content.append("<li>")
                                    .append ("<p class=question>What programming language do you use?</p>")
                                    .append ("<p class=answer>").append(codingLanguage).append("</p>")
                                    .append("</li>");
                                }

                                content.append("<li>")
                                .append ("<p class=question>How many points do you score in autonomous?</p>")
                                .append ("<p class=answer>").append(ps.getPointsScoredInAuto()).append("</p>")
                                .append("</li>")
                                .append("</ol>")

                                .append ( "<h3>TeleOp</h3>")
                                .append("<ol>")
                                .append("<li>")
                                .append ("<p class=question>Can you pick up off the ground?</p>")
                                .append ("<p class=answer>").append(ps.getPickOffGround()).append("</p>")
                                .append("</li>")
                                .append("<li>")
                                .append ("<p class=question>Are you willing to play defense?</p>")
                                .append ("<p class=answer>").append(ps.getCanPlayDefense()).append("</p>")
                                .append("</li>")
                                .append("<li>")
                                .append ("<p class=question>What is your preferred scoring method?</p>")
                                .append ("<p class=answer>").append(ps.getScoringMethod()).append("</p>")
                                .append("</li>")
                                .append("</ol>")


                                .append ("<h3>Endgame</h3>")
                                .append("<ol>")
                                .append("<li>")
                                .append ("<p class=question>How long does it take you to hang?</p>")
                                .append ("<p class=answer>").append(ps.getHangTime()).append("</p>")
                                .append("</li>")
                                .append("</ol>")

                                .append ("<h3>Team</h3>")
                                .append("<ol>")
                                .append("<li>")
                                .append ("<p class=question>What drive train do you have?</p>")
                                .append ("<p class=answer>").append(ps.getRobotDriveTrain()).append("</p>")
                                .append("</li>")
                                .append("<li>")
                                .append ("<p class=question>How many seasons has your driver been in?</p>")
                                .append ("<p class=answer>").append(ps.getDriverExperience()).append("</p>")
                                .append("</li>")
                                .append("<li>")
                                .append ("<p class=question>How many seasons has your operator been in?()</p>")
                                .append ("<p class=answer>").append(ps.getOperatorExperience()).append("</p>")
                                .append("</li>")
                                .append("<li>")
                                .append ("<p class=question>What is your preferred human player position?</p>")
                                .append ("<p class=answer>").append(ps.getHumanPositionPref()).append("</p>")
                                .append("</li>")
                                .append("<li>")
                                .append ("<p class=question>Additional Notes</p>")
                                .append ("<p class=answer>").append(ps.getExtraNotes()).append("</p>")
                                .append("</li>")
                                .append("</ol>");



                        //outputStream.write(" ");
                    }
                    content.append (strEnd);
                    bw.write(content.toString());
                    bw.close();
                   // outputStream.close();
                    Toast.makeText(this, "export Pit Scout: ", Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Log.e("In Catch for Pit Scout", "Exception trying to export pitscout data", e);
                    String message = e.getLocalizedMessage();
                    Toast.makeText(this, "export Pit Scout error: " + message, Toast.LENGTH_LONG).show();
                }
            });
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void importCSVMatches() throws IOException {
        File importPath = getImportPath();
        File newFile = new File(importPath.getAbsolutePath().concat("/matchResults.csv"));
        Stream<String> lines = Files.lines(newFile.toPath());
        List<String> inputLines = lines.collect(Collectors.toList());

        int timesRan = 0;
        for(int lineIndex = 1; lineIndex < inputLines.size(); lineIndex++){
            timesRan++;
            String currentLine = inputLines.get(lineIndex);
            String[] columns = currentLine.split(",");

            String eventKey = columns[0];
            String matchKey = columns[1];
            String teamKey = columns[2];
            int matchNum = Integer.parseInt(columns[3]);
            int teamNum = Integer.parseInt(columns[4]);

            String AutoFlag1 = columns[5];
            String AutoFlag2 = columns[6];
            String AutoFlag3 = columns[7];
            String AutoFlag4 = columns[8];
            String AutoFlag5 = columns[9];
            String AutoInt6 = columns[10];
            String AutoInt7 = columns[11];
            String AutoInt8 = columns[12];
            String AutoInt9 = columns[13];
            String AutoInt10 = columns[14];
            String AutoInt11 = columns[15];

            String TeleOpInt6 = columns[16];
            String TeleOpInt7 = columns[17];
            String TeleOpInt8 = columns[18];
            String TeleOpInt9 = columns[19];
            String TeleOpInt10 = columns[20];
            String TeleOpInt11 = columns[21];
            String TeleOpInt12 = columns[22];

            String EndFlag1 = columns[23];
            String EndFlag2 = columns[24];
            String EndFlag3 = columns[25];
            String EndFlag4 = columns[26];
            String EndFlag5 = columns[27];

            String teleDef =columns[28];

            String matchResultKey = columns[29];
            String AdditionalNotes = columns[30];

            MatchResult matchResult = new MatchResult(
                    matchResultKey,
                    eventKey,
                    matchKey,
                    teamKey,
                    false,

                    AutoFlag1.equalsIgnoreCase("true"),
                    AutoFlag2.equalsIgnoreCase("true"),
                    AutoFlag3.equalsIgnoreCase("true"),
                    AutoFlag4.equalsIgnoreCase("true"),
                    AutoFlag5.equalsIgnoreCase("true"),

                    Integer.parseInt(AutoInt6),
                    Integer.parseInt(AutoInt7),
                    Integer.parseInt(AutoInt8),
                    Integer.parseInt(AutoInt9),
                    Integer.parseInt(AutoInt10),
                    Integer.parseInt(AutoInt11),

                    Integer.parseInt(TeleOpInt6),
                    Integer.parseInt(TeleOpInt7),
                    Integer.parseInt(TeleOpInt8),
                    Integer.parseInt(TeleOpInt9),
                    Integer.parseInt(TeleOpInt10),
                    Integer.parseInt(TeleOpInt11),
                    Integer.parseInt(TeleOpInt12),

                    EndFlag1.equalsIgnoreCase("true"),
                    EndFlag2.equalsIgnoreCase("true"),
                    EndFlag3.equalsIgnoreCase("true"),
                    EndFlag4.equalsIgnoreCase("true"),
                    EndFlag5.equalsIgnoreCase("true"),

                    AdditionalNotes,
                    Integer.parseInt(teleDef)
                 );
            matchResultViewModel.upsert(matchResult);
        }
        Log.e(this.getLocalClassName(), "Times ran: " + timesRan);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupCSVImportButton(){
        importCSVMatchButton.setOnClickListener(v -> {
            try{
                importCSVMatches();
                Log.e("under setupCSVImportButton", "imported match results");
                Toast.makeText(this, "imported matches", Toast.LENGTH_LONG).show();
            }
            catch(Exception E){
                E.printStackTrace();
            }
        });
    }

    public File getImportPath(){
        ContextWrapper cw = new ContextWrapper( getApplicationContext() );
        return FileUtilities.ensureDirectory(cw,"imports");
    }

    private File[] getFilePathsForMatch() {
        return getFilePathForMatch().listFiles();
    }

    private File getFilePathForMatch() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext() );
        return FileUtilities.ensureDirectory(cw,"match_data");

    }

    private File[] getFilePathsForPitScout(){
        return getFilePathForPitScout().listFiles();
    }

    private File getFilePathForPitScout(){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        return FileUtilities.ensureDirectory(cw,"pitscout_data");
    }
}