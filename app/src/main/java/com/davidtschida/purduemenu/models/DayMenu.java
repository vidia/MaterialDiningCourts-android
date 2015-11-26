package com.davidtschida.purduemenu.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.joda.time.LocalTime;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class DayMenu {
    private static final String TAG = "DayMenu";

    @SerializedName("Location")
    String location;

    @SerializedName("Date")
    String date;

    @SerializedName("Notes")
    String notes;

    @SerializedName("Meals")
    List<Meal> meals;

    public Meal getMealByName(String mealName) {
        for (Meal meal : getMeals()) {
            if (meal.getName().equalsIgnoreCase(mealName)) {
                return meal;
            }
        }
        return null;
        //throw new MealDoesNotExistException("The meal, " + mealName + ", does not exist for this day or is not a valid meal.");
    }

    /**
     * Returns the meal that is either in progress or is next at this location, relative to the given time.
     * <p/>
     * NOTE: If the given time is after all meals for this day, NULL is returned.
     *
     * @param localTime the time to
     * @return the meal for the given time, or null if time is after all meals.
     */
    public Meal getMealForTime(LocalTime localTime) {
        Meal nextOrCurrentMeal = null;

        Log.d(TAG, "getMealForTime(" + localTime.toString() + "); Location: " + location + "; Date: " + date);
        for (Meal meal : getMeals()) {
            Log.d(TAG, "Checking meal " + meal.getName());
            if (meal.containsTime(localTime)) {
                Log.d(TAG, "Meal contains the time, returning.");
                return meal;
            } else if (meal.endsBefore(localTime)) {
                Log.d(TAG, "Meal ends before the time");
                continue;
            } else if (meal.startsAfter(localTime)) {
                Log.d(TAG, "Meal starts after the given time");
                if (nextOrCurrentMeal == null) {
                    nextOrCurrentMeal = meal;
                } else {
                    if (meal.timeUntilStartFrom(localTime) < nextOrCurrentMeal.timeUntilStartFrom(localTime)) {
                        Log.d(TAG, "Meal starts sooner than the last closest meal");
                        nextOrCurrentMeal = meal;
                    }
                }
            }
        }
        return nextOrCurrentMeal;
    }
}
