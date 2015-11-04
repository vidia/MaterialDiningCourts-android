package com.davidtschida.purduemenu.models;

import com.davidtschida.purduemenu.exceptions.MealDoesNotExistException;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class DayMenu {
    @SerializedName("Location")
    String location;

    @SerializedName("Date")
    String date;

    @SerializedName("Notes")
    String notes;

    @SerializedName("Meals")
    List<Meal> meals;

    public Meal getMealByName(String mealName) {
        for(Meal meal : getMeals()) {
            if(meal.getName().equalsIgnoreCase(mealName)) {
                return meal;
            }
        }
        throw new MealDoesNotExistException("The meal, " + mealName + ", does not exist for this day or is not a valid meal.");
    }
}
