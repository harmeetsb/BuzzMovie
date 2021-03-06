package application.buzzmovieselector.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import application.buzzmovieselector.Model.User;
import application.buzzmovieselector.Model.UserManager;
import application.buzzmovieselector.R;

public class AdminProfileActivity extends AppCompatActivity {
    private ListView userList;
    private Button logoutButton;
    private ArrayList<User> users;
    private UserManager userManager;
    private Context context;

    public ListView getUserList() {
        return userList;
    }


    public Button getLogoutButton() {
        return logoutButton;
    }


    public ArrayList<User> getUsers() {
        return users;
    }


    public UserManager getUserManager() {
        return userManager;
    }


    public Context getContext() {
        return context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Panel");
        context = this;
        users = new ArrayList<>();
        userManager = new UserManager(this);
        userList = (ListView) findViewById(R.id.userListView);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        users = userManager.getUsers();

        userList.setClickable(true);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, UserInformationActivity.class);
                intent.putExtra("username", users.get(position).getUserName());
                startActivity(intent);
            }
        });
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
        userList.setAdapter(adapter);
    }

    public void onClickLogout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

}
