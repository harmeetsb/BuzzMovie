package application.buzzmovieselector.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import application.buzzmovieselector.Activity.LoginActivity;
import application.buzzmovieselector.R;


public class Home extends Fragment implements View.OnClickListener {
    private View rootView;
    Spinner spinner;
    Button logoutButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        logoutButton = (Button)rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);
        spinner = (Spinner) rootView.findViewById(R.id.spinnerRecommend);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.recommend, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return rootView;
    }

    public void onClick(View view) {
        System.out.println();
        switch (view.getId())// on run time get id what button os click and get id
        {
            case R.id.logoutButton:        // it mean if button1 click then this work
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}

