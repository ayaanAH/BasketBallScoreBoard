package com.example.basketballscoreboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView aScore, bScore;
    int scoreA=0, scoreB=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aScore=findViewById(R.id.scoreA);
        bScore=findViewById(R.id.scoreB);

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
