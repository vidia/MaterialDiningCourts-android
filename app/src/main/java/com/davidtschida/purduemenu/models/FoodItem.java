package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by david on 10/22/2015.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FoodItem extends SugarRecord<FoodItem> {
    @SerializedName("Id")
    String FoodItemId;

    @SerializedName("Name")
    String name;

    @SerializedName("isVegetarian")
    boolean isVegetarian;

    @SerializedName("Allergens")
    List<Allergen> allergens;

    @SerializedName("ItemSchedule")
    FoodItemSchedule itemSchedule;

    @SerializedName("Nutrition")
    List<Nutrition> nutrition;
}
