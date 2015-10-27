package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class Meal {
    @SerializedName("Name")
    String name;

    @SerializedName("Order")
    int order;

    @SerializedName("Hours")
    MealHours hours;

    @SerializedName("Stations")
    List<Station> stations;

}
