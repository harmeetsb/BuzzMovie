package application.buzzmovieselector.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import application.buzzmovieselector.Model.User;
import application.buzzmovieselector.Model.UserManager;
import application.buzzmovieselector.R;

public class UserInformationActivity extends AppCompatActivity {
    TextView nameView;
    TextView userNameView;
    TextView statusView;
    TextView searchView;
    TextView ratedView;
    TextView dateView;
    Button banButton;
    Button backButton;
    Context context;
    User user;
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
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
        context = this;
        Intent intent = getIntent();
        userManager = new UserManager(this);
        TextView tv = (TextView) findViewById(R.id.username);
        user = userManager.findUserById(intent.getStringExtra("username"));
        nameView = (TextView) findViewById(R.id.nameView);
        nameView.setText(user.getName());

        userNameView = (TextView) findViewById(R.id.usernameView);
        userNameView.setText(user.getName());

        statusView = (TextView) findViewById(R.id.statusView);
        if (user.getIsBanned()) statusView.setText("Banned");
        else if (user.getIsLocked()) statusView.setText("Locked");
        else if (user.getIsActive()) statusView.setText("Active");
        else statusView.setText("InActive");

        searchView = (TextView) findViewById(R.id.searchView);
        ratedView = (TextView) findViewById(R.id.ratedView);
        dateView = (TextView) findViewById(R.id.dateView);
        banButton = (Button) findViewById(R.id.BanButton);
        if (user.getIsBanned()) banButton.setText("Unban User");
        else banButton.setText("Ban User");
        backButton = (Button) findViewById(R.id.backButton);
    }

    public void onClickBack(View view) {
        super.onBackPressed();
    }

    public void onClickLogout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onClickBan(View view) {
        CharSequence text = "";
        if (user.getIsBanned()) {
            user.setIsBanned(false);
            text = "User Unbanned";
            banButton.setText("ban User");
        } else {
            user.setIsBanned(true);
            text = "User Banned";
            banButton.setText("Unban User");
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        userManager.updateUser(user);
    }

    public void onClickUnlockUser(View view) {
        if (user.getIsLocked()) {
            user.setIsLocked(false);
            Toast.makeText(this, "User unlocked", Toast.LENGTH_SHORT).show();
            userManager.updateUser(user);
            if (user.getIsBanned()) statusView.setText("Banned");
            else if (user.getIsLocked()) statusView.setText("Locked");
            else if (user.getIsActive()) statusView.setText("Active");
            else statusView.setText("InActive");
        } else {
            Toast.makeText(this, "User is Already Unlocked", Toast.LENGTH_SHORT).show();
        }
    }

}
