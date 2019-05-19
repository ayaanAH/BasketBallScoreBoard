package com.example.basketballscoreboard;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tomer.fadingtextview.FadingTextView;

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
        fadingDispView=findViewById(R.id.teamDisp);
        createNotificationChannel();
        fadingDispView.setTexts(labels);
        fadingDispView.setTimeout(2500, FadingTextView.MILLISECONDS);

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

            default:return super.onOptionsItemSelected(item);
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
