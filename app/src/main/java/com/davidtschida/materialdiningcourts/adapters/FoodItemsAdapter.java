package com.davidtschida.materialdiningcourts.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.purduemenu.models.FoodItem;

import java.util.List;

/**
 * Created by david on 11/3/2015.
 */
public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.ViewHolder> {

    private static final String TAG = "FoodItemsAdapter";
    private List<FoodItem> mFoodsList;

    public FoodItemsAdapter(List<FoodItem> foods) {
        this.mFoodsList = foods;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public View mRootView;
        public ViewHolder(View parent) {
            super(parent);
            mTextView = (TextView)parent.findViewById(R.id.simpleItemTextView);
            mRootView = parent;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FoodItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mRootView.setBackgroundColor((position % 2 == 0) ? Color.WHITE : Color.LTGRAY);
        holder.mTextView.setText(mFoodsList.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFoodsList.size();
    }
}
