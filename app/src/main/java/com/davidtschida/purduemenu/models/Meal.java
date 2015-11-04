package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class Meal {
    public static String BREAKFAST = "breakfast";
    public static String LUNCH = "lunch";
    public static String LATE_LUNCH = "late lunch";
    public static String DINNER = "dinner";

    @SerializedName("Name")
    String name;

    @SerializedName("Order")
    int order;

    @SerializedName("Hours")
    MealHours hours;

    @SerializedName("Stations")
    List<Station> stations;

    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> foodItems = new ArrayList<>();
        for(Station station : getStations()) {
            foodItems.addAll(station.getItems());
        }
        return foodItems;
    }

}
