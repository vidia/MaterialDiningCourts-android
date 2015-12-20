package com.davidtschida.materialdiningcourts.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.purduemenu.models.FoodItem;

import java.util.List;

import timber.log.Timber;

/**
 * Created by david on 11/3/2015.
 */
public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.ViewHolder> {

    private List<FoodItem> mFoodsList;
    private Context mContext;
    private int favoriteCount;

    public FoodItemsAdapter(Context context, List<FoodItem> foods, int favoriteCount) {
        this.mFoodsList = foods;
        this.mContext = context;
        this.favoriteCount = favoriteCount;

        this.mSharedPreferences = mContext.getSharedPreferences("FAVORITES", Context.MODE_PRIVATE);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(position % 2 == 0) {
            holder.mRootView.setBackgroundColor(Color.WHITE);
        } else {
            holder.mRootView.setBackgroundResource(R.color.foodItemBackgroundColorEven);
        }
        holder.mTextView.setText(mFoodsList.get(position).getName());
        holder.mFavoritesButton.setChecked(mFoodsList.get(position).isFavorite(mSharedPreferences));

        holder.mFavoritesButton.setOnClickListener(
                new ToggleButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean favorite = !(mFoodsList.get(position).isFavorite(mSharedPreferences));
                        mFoodsList.get(position).setFavorite(mSharedPreferences, favorite);
                        FoodItem item = mFoodsList.remove(position);
                        if(favorite) {
                            int i;
                            for(i = 0; i < favoriteCount; i++) {
                                if(mFoodsList.get(i).getPosition() > item.getPosition()) {
                                    break;
                                }
                            }
                            favoriteCount++;
                            mFoodsList.add(i, item);
                        } else {
                            favoriteCount--;
                            int i;
                            for(i = favoriteCount; i < mFoodsList.size(); i++) {
                                if(mFoodsList.get(i).getPosition() > item.getPosition()) {
                                    break;
                                }
                            }
                            mFoodsList.add(i, item);
                        }
                        notifyItemMoved(0, mFoodsList.get(position).getPosition());
                        notifyItemRangeChanged(0, mFoodsList.size());
                    }
                }
        );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFoodsList.size();
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public View mRootView;
        public ToggleButton mFavoritesButton;

        public ViewHolder(View parent) {
            super(parent);
            mFavoritesButton = (ToggleButton) parent.findViewById(R.id.favoritesHeart);
            mTextView = (TextView) parent.findViewById(R.id.simpleItemTextView);
            mRootView = parent;

        }
    }
}
