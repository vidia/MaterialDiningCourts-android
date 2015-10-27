package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class FoodItemAppearance {
    @SerializedName("Data")
    String data;

    @SerializedName("Location")
    String location;

    @SerializedName("Meal")
    String meal;

    @SerializedName("Station")
    String station;
}
