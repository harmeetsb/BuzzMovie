package application.buzzmovieselector.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.buzzmovieselector.R;

/**
 * This class represents a profile tab fragment
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class ProfileTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile_tab, container, false);
    }


}
