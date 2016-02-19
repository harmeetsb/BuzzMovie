package application.buzzmovieselector;

import android.content.Intent;
import android.media.effect.Effect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import Model.User;
import Model.UserManager;

/**
 * This class represents a ProfileActivity object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class ProfileActivity extends AppCompatActivity {
    private User user;
    private UserManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        manager = new UserManager(this);
        Intent intent = getIntent();
       String userName =  intent.getStringExtra("userName");
        user = manager.findUserById(userName);
        displayUser();
    }

    private void displayUser() {
        EditText e1 = (EditText) findViewById(R.id.editText);
        String name = user.getName();
        e1.setText(name);
        EditText e2 = (EditText) findViewById(R.id.editText2);
        e2.setText(user.getEmail());
        TextView e3 = (TextView) findViewById(R.id.editText3);
        e3.setText(user.getUserName());
    }
    /**
     * Method to handle logout requests
     * @param view View in which clear has been clicked
     */
    public void logout(View view) {
        Intent intent = new Intent(this,WelcomeScreen.class);
        startActivity(intent);
    }
    /**
     * Method to handle edits that are made to user profile
     * @param view View in which clear has been clicked
     */
    public void edit(View view) {
        EditText e1 = (EditText) findViewById(R.id.editText);
        String name = String.valueOf(e1.getText());
        EditText e2 = (EditText) findViewById(R.id.editText2);
        String email = String.valueOf(e2.getText());
        TextView e3 = (TextView) findViewById(R.id.editText3);
        user.setName(name);
        user.setEmail(email);
        CharSequence text = "Edit Sucessful";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
        manager.updateUser(user);
    }
}
