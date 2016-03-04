package application.buzzmovieselector.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import application.buzzmovieselector.R;

/**
 * Created by harmeetbindra on 3/4/16.
 */
public class RecentListAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    public RecentListAdapter(Context context, ArrayList<String> list) {
        super(context, R.layout.activity_profile, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return convertView;
    }
}
