package application.buzzmovieselector.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import application.buzzmovieselector.Fragments.Home;
import application.buzzmovieselector.Fragments.ProfileTab;
import application.buzzmovieselector.Fragments.RecentDvd;
import application.buzzmovieselector.Fragments.RecentMovieTab;
import application.buzzmovieselector.Fragments.SearchTab;

/**
 * This class represents a TabListAdapter
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class TabListAdapter extends FragmentPagerAdapter {

    public TabListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new Home();
        } else if (position == 1) {
            fragment = new SearchTab();
        } else if (position == 2) {
            fragment = new ProfileTab();
        } else if (position == 3) {
            fragment = new RecentMovieTab();
        } else if (position == 4) {
            fragment = new RecentDvd();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}