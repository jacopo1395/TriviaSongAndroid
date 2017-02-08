package com.triviamusic.triviamusicandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triviamusic.triviamusicandroid.R;
import com.triviamusic.triviamusicandroid.resources.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jacopo on 07/02/2017.
 */


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private final String mCat;
    private ArrayList<User> mDataset;

    public RecordAdapter(List<User> myDataset, String cat) {
        mDataset = (ArrayList<User>) myDataset;
        mCat = cat;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mail;
        public TextView category;
        public TextView score;

        public ViewHolder(View v) {
            super(v);
            mail = (TextView) v.findViewById(R.id.mail);
            category = (TextView) v.findViewById(R.id.category);
            score = (TextView) v.findViewById(R.id.score);
        }
    }



    // Create new views (invoked by the layout manager)
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_record, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mail.setText(mDataset.get(position).email);
        if(mDataset.get(position).record!=null) {
            Map m = mDataset.get(position).record.scores;
            if (m != null && m.get(mCat)!=null) {
                holder.score.setText(m.get(mCat).toString());
                holder.category.setText(mCat);
            }
            else{
                holder.score.setText("0");
                holder.category.setText(mCat);
            }
        }
        else{
            holder.score.setText("0");
            holder.category.setText(mCat);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}