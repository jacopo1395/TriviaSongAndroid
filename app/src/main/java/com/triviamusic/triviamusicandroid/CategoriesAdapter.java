package com.triviamusic.triviamusicandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by jadac_000 on 25/11/2016.
 */

public class CategoriesAdapter extends BaseAdapter {

    private final Context context;
    private String[] cat;

    public CategoriesAdapter(Context context, String[] cat) {
        this.context = context;
        this.cat = cat;

    }

    @Override
    public int getCount() {
        return cat.length;
    }

    @Override
    public Object getItem(int i) {
        String[] s = context.getResources().getStringArray(R.array.categories);
        if (i < s.length) return s[i];
        else return "";
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        if (row == null) {
            //se la convertView di quest'immagine è nulla la inizializzo
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.category_item, viewGroup, false);

        }

        TextView t = (TextView) row.findViewById(R.id.item);
        t.setText((String) getItem(i));

        return row;
    }

    public String getItemID(int i) {
        return cat[i];
    }
}
