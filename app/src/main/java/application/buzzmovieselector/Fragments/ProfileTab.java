package application.buzzmovieselector.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import application.buzzmovieselector.Activity.EditUserActivity;
import application.buzzmovieselector.Activity.ProfileActivity;
import application.buzzmovieselector.Model.User;
import application.buzzmovieselector.Model.UserManager;
import application.buzzmovieselector.R;

/**
 * This class represents a profile tab fragment
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class ProfileTab extends Fragment implements View.OnClickListener {
    private View rootView;
    private Button editButton;
    private String userName;
    private UserManager userManager;

    private TextView nameView;
    private TextView userNameView;
    private TextView ratedView;
    private TextView dateView;
    private Context context;
    private User user;

    public View getRootView() {
        return rootView;
    }

    public Button getEditButton() {
        return editButton;
    }


    public String getUserName() {
        return userName;
    }



    public UserManager getUserManager() {
        return userManager;
    }



    public TextView getNameView() {
        return nameView;
    }



    public TextView getUserNameView() {
        return userNameView;
    }



    public TextView getRatedView() {
        return ratedView;
    }



    public TextView getDateView() {
        return dateView;
    }



    public Context getContext() {
        return context;
    }



    public User getUser() {
        return user;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        editButton = (Button) rootView.findViewById(R.id.editProfileButton);
        userManager = new UserManager(getActivity());
        userName = ProfileActivity.getUserName();
        editButton.setOnClickListener(this);
        user = userManager.findUserById(userName);

        nameView = (TextView) rootView.findViewById(R.id.nameView);
        nameView.setText(user.getName());

        userNameView = (TextView) rootView.findViewById(R.id.usernameView);
        userNameView.setText(user.getUserName());

        ratedView = (TextView) rootView.findViewById(R.id.ratedView);
        dateView = (TextView) rootView.findViewById(R.id.dateView);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editProfileButton:
                Intent intent = new Intent(getActivity(), EditUserActivity.class);
                intent.putExtra("username", userName);
                startActivity(intent);
                break;
        }
    }
}