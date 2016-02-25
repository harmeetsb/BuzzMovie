package application.buzzmovieselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Model.User;
import Model.UserManager;

/**
 * This class represents a RegisterActivity object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity {
    public static DatabaseHelper dbHelper;
    private static UserManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        manager = new UserManager(this);
    }
    /**
     * Method to handle register clicks
     * @param view View in which register has been clicked
     */
    public void onClickRegister(View view) {
        EditText n = (EditText) findViewById(R.id.nameText);
        EditText em = (EditText) findViewById(R.id.emailText);
        EditText un = (EditText) findViewById(R.id.usernameText);
        EditText p = (EditText) findViewById(R.id.passwordText);
        TextView error = (TextView) findViewById(R.id.errorView);
        error.setText("");
        String name = n.getText().toString();
        String email = em.getText().toString();
        String userName = un.getText().toString();
        String password = p.getText().toString();
        String errorString = checkError(name, email, userName, password);
        boolean registered = false;
        // if there is no error, then only register the user
        if(errorString == null) {
            registered = manager.addUser(name, password, email, userName, "", false, false);
        }
        CharSequence text = "";

        if (registered) {
            text = "Registration Sucessful";
        } else {
            text = "Failed Try again";
            error.setText(errorString);
        }

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

        if(registered) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private String checkError(String name, String email, String userName, String password) {
        // name check
        if(name.isEmpty() || email.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            return "No fields can be left blank";
        }
        for (int i = 0; i < name.length(); i++) {
            if(name.charAt(i) < 65 || (name.charAt(i) > 90 && name.charAt(i) < 97)
                    || (name.charAt(i) > 122)) {
                return "No special character allowed in name";
            }
        }
        if(!email.contains("@gatech.edu")) {
            return "only @gatech.edu allowed";
        }
        if(manager.findUserById(userName) != null) {
            return "This username has been taken";
        }
        return null;
    }

    /**
     * Method to handle back clicks
     * @param view View in which back has been clicked
     */
    public void onClickBack(View view) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }
    /**
     * Method to handle clear clicks
     * @param view View in which clear has been clicked
     */
    public void onClickClear(View view) {
        clearFields();
    }
    private void clearFields() {
        EditText n = (EditText) findViewById(R.id.nameText);
        EditText em = (EditText) findViewById(R.id.emailText);
        EditText un = (EditText) findViewById(R.id.usernameText);
        EditText p = (EditText) findViewById(R.id.passwordText);
        TextView e = (TextView) findViewById(R.id.errorView);
        n.setText("");
        em.setText("");
        un.setText("");
        p.setText("");
        e.setText("");
    }
}
