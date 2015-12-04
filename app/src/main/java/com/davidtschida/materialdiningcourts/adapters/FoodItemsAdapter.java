package com.davidtschida.materialdiningcourts.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.purduemenu.models.FoodItem;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.List;

import timber.log.Timber;

/**
 * Created by david on 11/3/2015.
 */
public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.ViewHolder> {

    private final SharedPreferences mSharedPreferences;
    private List<FoodItem> mFoodsList;
    private Context mContext;
    IconDrawable redHeart, greyHeart;


    public FoodItemsAdapter(Context context, List<FoodItem> foods) {
        this.mFoodsList = foods;
        this.mContext = context;
        redHeart = new IconDrawable(mContext, FontAwesomeIcons.fa_heart).colorRes(R.color.favoriteColor).sizeDp(24);
        greyHeart = new IconDrawable(mContext, FontAwesomeIcons.fa_heart).colorRes(R.color.favoriteNotSelected).sizeDp(24);

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
        holder.mRootView.setBackgroundColor((position % 2 == 0) ? Color.WHITE : Color.LTGRAY);
        holder.mTextView.setText(mFoodsList.get(position).getName());
        holder.mFavoritesButton.setChecked(mSharedPreferences
                .getBoolean(mFoodsList.get(position).getId(), false));

        holder.mFavoritesButton.setBackgroundDrawable(null);

        if(holder.mFavoritesButton.isChecked()) {
            Timber.d("Heart is on: %s", mFoodsList.get(position).getName());
            holder.mFavoritesButton.setButtonDrawable(redHeart);
        } else {
            Timber.d("Heart is off: %s", mFoodsList.get(position).getName());
            holder.mFavoritesButton.setButtonDrawable(greyHeart);
        }

        holder.mFavoritesButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mSharedPreferences
                                .edit()
                                .putBoolean(mFoodsList.get(position).getId(), isChecked)
                                .apply();
                        if(isChecked) {
                            Timber.d("Heart is on: %s", mFoodsList.get(position).getName());
                            buttonView.setButtonDrawable(redHeart);
                        } else {
                            Timber.d("Heart is off: %s", mFoodsList.get(position).getName());
                            buttonView.setButtonDrawable(greyHeart);
                        }
                    }
                });
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
