package com.davidtschida.purduemenu.models;

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
}
