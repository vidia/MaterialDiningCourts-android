package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by david on 10/22/2015.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FoodItemAppearance extends SugarRecord<FoodItemAppearance> {
    @SerializedName("Data")
    String data;

    @SerializedName("Location")
    String location;

    @SerializedName("Meal")
    String meal;

    @SerializedName("Station")
    String station;
}
