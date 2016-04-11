package application.buzzmovieselector.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import application.buzzmovieselector.Model.User;
import application.buzzmovieselector.Model.UserManager;
import application.buzzmovieselector.R;

public class EditUserActivity extends AppCompatActivity {
    private EditText n;
    private EditText em;
    private TextView un;
    private EditText p;
    private TextView error;
    private Spinner spinner;
    private User user;
    private UserManager userManager;
    private String major;

    public EditText getN() {
        return n;
    }


    public EditText getEm() {
        return em;
    }


    public TextView getUn() {
        return un;
    }


    public EditText getP() {
        return p;
    }


    public TextView getError() {
        return error;
    }


    public Spinner getSpinner() {
        return spinner;
    }


    public User getUser() {
        return user;
    }


    public UserManager getUserManager() {
        return userManager;
    }


    public String getMajor() {
        return major;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        userManager = new UserManager(this);
        Intent intent = getIntent();
        user = userManager.findUserById(intent.getStringExtra("username"));
        spinner = (Spinner) findViewById(R.id.majorSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.majors, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        n = (EditText) findViewById(R.id.nameText);
        n.setText(user.getName());
        em = (EditText) findViewById(R.id.emailText);
        em.setText(user.getEmail());
        un = (TextView) findViewById(R.id.usernameText);
        un.setText(user.getUserName());
        p = (EditText) findViewById(R.id.passwordText);
        p.setText(user.getPassword());
        error = (TextView) findViewById(R.id.errorView);
        major = user.getMajor();
        error.setText("");
        if (major.equals("Computer Science")) {
            spinner.setSelection(1);
        } else if (major.equals("Computer Engineering")) {
            spinner.setSelection(2);
        } else if (major.equals("Math")) {
            spinner.setSelection(3);
        } else if (major.equals("Physics")) {
            spinner.setSelection(4);
        } else if (major.equals("Business")) {
            spinner.setSelection(5);
        }

    }

    public void onClickSubmit(View view) {
        String name = n.getText().toString();
        String email = em.getText().toString();
        String password = p.getText().toString();
        if (!spinner.getSelectedItem().equals("Select Major")) {
            major = String.valueOf(spinner.getSelectedItem());
        }
        String errorString = checkError(name, email, password, major);
        if (errorString == null) {
            User newUser = new User(name, password, email, user.getUserName(), major, false,
                    false, false, false);
            userManager.updateUser(newUser);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("userName", user.getUserName());
            startActivity(intent);
        } else {
            error.setText(errorString);
        }

    }

    private String checkError(String name, String email, String password, String major) {
        // name check
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return "No fields can be left blank";
        }
        if (major.equalsIgnoreCase("Select Major")) {
            return "Select a major";
        }
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) < 65 || (name.charAt(i) > 90 && name.charAt(i) < 94)
                    || (name.charAt(i) > 94 && name.charAt(i) < 97)
                    || (name.charAt(i) > 122)) {
                return "No special character allowed in name";
            }
        }
        if (!email.contains("@gatech.edu")) {
            return "only @gatech.edu allowed";
        }
        return null;
    }

    /**
     * Method to handle back clicks
     *
     * @param view View in which back has been clicked
     */
    public void onClickBack(View view) {
        super.onBackPressed();
    }

    /**
     * Method to handle clear clicks
     *
     * @param view View in which clear has been clicked
     */
    public void onClickClear(View view) {
        clearFields();
    }

    private void clearFields() {
        EditText n = (EditText) findViewById(R.id.nameText);
        EditText em = (EditText) findViewById(R.id.emailText);
        EditText p = (EditText) findViewById(R.id.passwordText);
        TextView e = (TextView) findViewById(R.id.errorView);
        n.setText("");
        em.setText("");
        p.setText("");
        e.setText("");
    }
}