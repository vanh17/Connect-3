package edu.vanh17hanover.firstapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    int player = 0;
    int[] gamestate = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningposition = {{0, 1, 2}, {0, 3, 6}, {0, 4, 8}, {1, 4, 7}, {2, 5, 8}, {3, 4, 5},
            {6, 7 ,8}, {2, 4, 6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedSquare = Integer.parseInt(counter.getTag().toString());
        if (gamestate[tappedSquare] == 2) {
            gamestate[tappedSquare] = player;
            counter.setTranslationY(-1000f);
            if (player == 0) {
                counter.setImageResource(R.drawable.yellow);
                player = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                player = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            boolean gameend = true;
            for (int[] winningpos : winningposition) {
                if (gamestate[winningpos[0]] == gamestate[winningpos[1]] &&
                        gamestate[winningpos[1]] == gamestate[winningpos[2]] &&
                        gamestate[winningpos[0]] != 2) {
                        gameend = false;
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainButton);
                        layout.setVisibility(View.VISIBLE);
                        TextView winner = (TextView) findViewById(R.id.winnerMessage);
                        if (gamestate[winningpos[0]] == 0) {
                            winner.setText("Player 1 has won!");
                        }else {
                            winner.setText("Player 2 has won!");
                        }
                }
            }

            for (int i = 0; i < gamestate.length; i++) {
                if (gamestate[i] == 2) {
                    gameend = false;
                }
            }
            if (gameend) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainButton);
                layout.setVisibility(View.VISIBLE);
                TextView winner = (TextView) findViewById(R.id.winnerMessage);
                winner.setText("Draw!!!");
            }
        }
    }

    public void playAgain (View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainButton);
        layout.setVisibility(View.INVISIBLE);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gamestate.length; i++) {
            gamestate[i] = 2;
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        player = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.vanh17hanover.firstapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.vanh17hanover.firstapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
