package com.davidtschida.materialdiningcourts.datalogging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.davidtschida.materialdiningcourts.eventbus.EventBus;
import com.davidtschida.materialdiningcourts.eventbus.FoodItemTypeSetEvent;
import com.davidtschida.materialdiningcourts.eventbus.ShowSnackbarEvent;
import com.davidtschida.purduemenu.models.FoodItem;
import com.squareup.otto.Subscribe;

import java.util.Map;

import timber.log.Timber;

/**
 * Manager for the food type logic. Probably should be a service of
 * some sort if this moves into PROD.
 */
public class FoodItemTypeChangedManager {

    private static FoodItemTypeChangedManager INSTANCE;

    public static FoodItemTypeChangedManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FoodItemTypeChangedManager(context);
        }
        return INSTANCE;
    }

    private final Context mContext;
    private final SharedPreferences mSharedPrefs;

    @SuppressLint("WorldReadableFiles")
    public FoodItemTypeChangedManager(Context context) {
        this.mContext = context;
        this.mSharedPrefs = mContext.getSharedPreferences("FoodTypes", Context.MODE_WORLD_READABLE);//TODO: Change this when leaving alpha
    }

    @Subscribe public void onFoodItemTypeChanged(FoodItemTypeSetEvent event) {
        if(event.isMIsSet()) {
            mSharedPrefs.edit().putString(event.getMItem().getId(), event.getMType()).apply();
            EventBus.getBus().post(new ShowSnackbarEvent("Set " + event.getMItem().getName() + " to " + event.getMType() + " ."));
        } else {
            mSharedPrefs.edit().remove(event.getMItem().getId()).apply();
        }
    }

    public void exportToEmail(Context activityContext) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"dmtschida1+menusdata@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "[Purdue Dining Courts] Exported food item types");

        String mMessageBody = "";
        Map<String, ?> allEntries = mSharedPrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Timber.d("TAG", entry.getKey() + ": " + entry.getValue().toString());
            mMessageBody += entry.getKey() + ": " + entry.getValue().toString() + "\n";
        }

        i.putExtra(Intent.EXTRA_TEXT, mMessageBody);
        try {
            activityContext.startActivity(Intent.createChooser(i, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex) {
            EventBus.getBus().post(new ShowSnackbarEvent("There are no email clients installed."));
        }
    }

    public boolean isFoodAn(FoodItem item, String type) {
        String itemsType = mSharedPrefs.getString(item.getId(), null);
        return itemsType != null && type.equals(itemsType);

    }
}
