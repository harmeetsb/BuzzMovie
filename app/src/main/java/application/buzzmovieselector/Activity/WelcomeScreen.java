package application.buzzmovieselector.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import application.buzzmovieselector.R;

/**
 * This class represents a Welcome Screen object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class WelcomeScreen extends AppCompatActivity {
    private static WelcomeScreen instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_welcome_screen);
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
    }
    /**
     * Method to handle login clicks
     * @param view View in which login has been clicked
     */
    public void onClickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    /**
     * Method to handle register clicks
     * @param view View in which register has been clicked
     */
    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}
