package com.davidtschida.purduemenu.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class FoodItem {
    @SerializedName("ID")
    String Id;

    @SerializedName("Name")
    String name;

    @SerializedName("IsVegetarian")
    boolean isVegetarian;

    @SerializedName("Allergens")
    List<Allergen> allergens;

    @SerializedName("ItemSchedule")
    FoodItemSchedule itemSchedule;

    @SerializedName("Nutrition")
    List<Nutrition> nutrition;

    public boolean isFavorite(SharedPreferences prefs) {
        if(prefs != null) {
            return prefs.getBoolean(Id, false);
        }
        return false;
    }

    public void setFavorite(SharedPreferences prefs, boolean value) {
        if(prefs != null) {
            prefs.edit()
                .putBoolean(Id, value)
                .apply();
        }
    }

    int position;
}
