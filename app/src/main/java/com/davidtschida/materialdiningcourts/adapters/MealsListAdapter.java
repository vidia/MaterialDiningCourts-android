package com.davidtschida.materialdiningcourts.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.purduemenu.models.Meal;

import java.util.List;

/**
 * Created by david on 10/31/2015.
 */
public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.ViewHolder> {

    private static final String TAG = "Adapter";
    private List<Meal> mMealsList;

    public MealsListAdapter(List<Meal> meals) {
        this.mMealsList = meals;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(LinearLayout ll, TextView v) {
            super(ll);
            mTextView = v;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MealsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        Log.d(TAG, "onCreateVieHolder()");
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meals_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((LinearLayout) v, (TextView)v.findViewById(R.id.mealsTextView));
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mMealsList.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
            return mMealsList.size();
        }
}
