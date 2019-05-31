package com.example.basketballscoreboard;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tomer.fadingtextview.FadingTextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity
{
    private static final String CHANNEL_ID = "1";
    TextView aScore, bScore;
    FadingTextView fadingDispView;
    int scoreA=0, scoreB=0;
    String[ ] labels;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        labels = res.getStringArray( R.array.displayTeams ) ;
        aScore=findViewById(R.id.scoreA);
        bScore=findViewById(R.id.scoreB);
//        fadingDispView=findViewById(R.id.teamDisp);
        createNotificationChannel();
//        fadingDispView.setTexts(labels);
//        fadingDispView.setTimeout(2500, FadingTextView.MILLISECONDS);

    }

    public Bitmap getScreenShot()
    {
        View rootShareView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootShareView.getRootView();
        rootShareView.setDrawingCacheEnabled(true);
        Bitmap scoreSS = Bitmap.createBitmap(rootShareView.getDrawingCache());
        rootShareView.setDrawingCacheEnabled(false);
        return scoreSS;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.resetMenu : onReset();
            return true;
            case R.id.ShareSSt  : shareScreenShot();
            return true;

            default:return super.onOptionsItemSelected(item);
        }

    }

    private void shareScreenShot()
    {
        Bitmap screenShot = getScreenShot();
        Log.i("Original   dimensions", screenShot.getWidth()+" "+screenShot.getHeight());
        Toast toast=Toast.makeText(getApplicationContext(),"Sceenshot Taken",Toast.LENGTH_SHORT);
        toast.show();
        String fileName = "teamAvsteamB";

        String dirPath = store(screenShot, fileName);
        File file = new File(dirPath, fileName);
        shareImage(file);
    }


    public static String  store(Bitmap src, String fileName)
    {

        String dirPath = Environment.getRootDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try
        {
            FileOutputStream fOut = new FileOutputStream(file);
            src.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }
    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try
        {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    public void displayScoreA()
    {


        aScore.setText(String.valueOf(scoreA));
    }

    public void displayScoreB()
    {
        bScore.setText(String.valueOf(scoreB));
    }

    public void threePointerA(View view)
    {
        scoreA+=3;
        displayScoreA();
    }
    public void twoPointerA(View view)
    {
        scoreA+=2;
        displayScoreA();
    }

    public void freeThrowA(View view)
    {
        scoreA+=1;
        displayScoreA();
    }

    public void threePointerB(View view)
    {
        scoreB+=3;
        displayScoreB();
    }
    public void twoPointerB(View view)
    {
        scoreB+=2;
        displayScoreB();
    }

    public void freeThrowB(View view)
    {
        scoreB+=1;
        displayScoreB();
    }

    public void reset(View view)
    {
        onReset();
    }

    public void onReset()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to Reset all Scores ?");

        // Set Alert Title
        builder.setTitle("Reset Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // When the user click yes button
                // then app will close
                scoreA=0;
                scoreB=0;
                displayScoreA();
                displayScoreB();
                dialog.dismiss();
            }
        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                // If user click no
                // then dialog box is canceled.
                dialog.cancel();
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

}
