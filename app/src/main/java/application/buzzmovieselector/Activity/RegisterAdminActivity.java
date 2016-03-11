package application.buzzmovieselector.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import application.buzzmovieselector.Model.UserManager;
import application.buzzmovieselector.R;

public class RegisterAdminActivity extends AppCompatActivity {
    private static UserManager userManager;
    private EditText nameEditText, emailEditText, usernameEditText, passwordEditText;
    private TextView errorView;
    private Context context;
    private static final String  ADMIN_PASSWORD = "Harmeet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        context = this;
        passwordAlert();
        userManager = new UserManager(this);
        nameEditText = (EditText) findViewById(R.id.nameText);
        emailEditText = (EditText) findViewById(R.id.emailText);
        usernameEditText = (EditText) findViewById(R.id.usernameText);
        passwordEditText = (EditText) findViewById(R.id.passwordText);
        errorView = (TextView) findViewById(R.id.errorView);
        errorView.setText("");

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String userName = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String errorString = checkError(name, email, userName, password);
        boolean registered = false;
        if(errorString == null) {
            registered = userManager.addUser(name, password, email, userName, "", true, false);
        }
        CharSequence text = "";

        if (registered) {
            text = "Registration Sucessful";
        } else {
            text = "Failed Try ajgain";
            errorView.setText(errorString);
        }

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

        if(registered) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void passwordAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Password");
        alertDialog.setMessage("Enter admin password");
        alertDialog.setCancelable(false);
        final EditText input = new EditText(RegisterAdminActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        final String passwordEntered = String.valueOf(input.getText());
        alertDialog.setIcon(R.mipmap.ic_action_https);
      //  alertDialog.setPositiveButton("Hello");

        alertDialog.setPositiveButton(
                "Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (passwordEntered.equals(ADMIN_PASSWORD)) {
                            dialog.cancel();
                        } else {
                            Toast.makeText(context,id, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

        alertDialog.setNegativeButton(
                "Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();
                    }
                });

        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }

    private String checkError(String name, String email, String userName, String password) {
        // name check
        if(name.isEmpty() || email.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            return "No fields can be left blank";
        }

        for (int i = 0; i < name.length(); i++) {
            if(name.charAt(i) < 65 || (name.charAt(i) > 90 && name.charAt(i) < 94)
                    ||(name.charAt(i) > 94 && name.charAt(i)<97)
                    || (name.charAt(i) > 122)) {
                return "No special character allowed in name";
            }
        }
        if(!email.contains("@gatech.edu")) {
            return "only @gatech.edu allowed";
        }
        if(userManager.findUserById(userName) != null) {
            return "This username has been taken";
        }
        return null;
    }

}
