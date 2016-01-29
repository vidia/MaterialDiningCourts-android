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

import com.davidtschida.materialdiningcourts.BuildConfig;
import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.materialdiningcourts.datalogging.FoodItemTypeChangedManager;
import com.davidtschida.materialdiningcourts.eventbus.EventBus;
import com.davidtschida.materialdiningcourts.eventbus.FoodItemTypeSetEvent;
import com.davidtschida.purduemenu.models.FoodItem;

import java.util.List;

/**
 * Adapter for adding the food items to the recyclerview.
 * Contains the click logic for favorites as well.
 */
public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.ViewHolder> {

    private final SharedPreferences mSharedPreferences;
    private List<FoodItem> mFoodsList;
    private Context mContext;
    private int favoriteCount;

    public FoodItemsAdapter(Context context, List<FoodItem> foods, int favoriteCount) {
        this.mFoodsList = foods;
        this.mContext = context;
        this.favoriteCount = favoriteCount;

        this.mSharedPreferences = mContext.getSharedPreferences("FAVORITES", Context.MODE_PRIVATE); //TODO: Move this to a static "SharesPrefsManager"
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FoodItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
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

        if(BuildConfig.FLAVOR.equals("datalogger")) {
            holder.mEntreeButton.setChecked(FoodItemTypeChangedManager.getInstance(mContext).isFoodAn(mFoodsList.get(position), "Entree"));
            holder.mSideButton.setChecked(FoodItemTypeChangedManager.getInstance(mContext).isFoodAn(mFoodsList.get(position), "Side"));
            holder.mDesertButton.setChecked(FoodItemTypeChangedManager.getInstance(mContext).isFoodAn(mFoodsList.get(position), "Desert"));
        }

        //TODO: Move this to a listener class and stop saving positional data in the items.
        holder.mFavoritesButton.setOnClickListener(
                v -> {
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
                        notifyItemMoved(position, i);
                    } else {
                        favoriteCount--;
                        int i;
                        for(i = favoriteCount; i < mFoodsList.size(); i++) {
                            if(mFoodsList.get(i).getPosition() > item.getPosition()) {
                                break;
                            }
                        }
                        mFoodsList.add(i, item);
                        notifyItemMoved(position, i);
                    }
                    //notifyDataSetChanged();
                    //notifyItemMoved(0, mFoodsList.get(position).getPosition());
                    notifyItemRangeChanged(0, mFoodsList.size()); //Why does this work? IS this the intended use?
                }
        );

        if(BuildConfig.FLAVOR.equals("datalogger")) {
            holder.mEntreeButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(!buttonView.isPressed()) return;
                if(isChecked) {
                    holder.mDesertButton.setChecked(false);
                    holder.mSideButton.setChecked(false);
                }
                EventBus.getBus().post(new FoodItemTypeSetEvent(mFoodsList.get(position), "Entree", isChecked));
            });
            holder.mSideButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(!buttonView.isPressed()) return;
                if(isChecked) {
                    holder.mDesertButton.setChecked(false);
                    holder.mEntreeButton.setChecked(false);
                }
                EventBus.getBus().post(new FoodItemTypeSetEvent(mFoodsList.get(position), "Side", isChecked));
            });
            holder.mDesertButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(!buttonView.isPressed()) return;
                if(isChecked) {
                    holder.mEntreeButton.setChecked(false);
                    holder.mSideButton.setChecked(false);
                }
                EventBus.getBus().post(new FoodItemTypeSetEvent(mFoodsList.get(position), "Desert", isChecked));
            });
        }
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

        //Datalogger specific variables
        public ToggleButton mEntreeButton, mSideButton, mDesertButton;

        public ViewHolder(View parent) {
            super(parent);
            mFavoritesButton = (ToggleButton) parent.findViewById(R.id.favoritesHeart);
            mTextView = (TextView) parent.findViewById(R.id.simpleItemTextView);
            mRootView = parent;

            if(BuildConfig.FLAVOR.equals("datalogger")) {
                mEntreeButton = (ToggleButton) parent.findViewById(R.id.entreeButton);
                mSideButton = (ToggleButton) parent.findViewById(R.id.sideButton);
                mDesertButton = (ToggleButton) parent.findViewById(R.id.desertButton);
            }
        }


    }
}
