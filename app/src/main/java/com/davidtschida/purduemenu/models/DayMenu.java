package com.davidtschida.purduemenu.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import lombok.Data;
import timber.log.Timber;

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

    public LocalDate getLocalDate() {
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
        return dtf.parseLocalDate(date);
    }


    public Meal getMealByName(String mealName) {
        Timber.i("getMealByName(%s)", mealName);
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
     * @param dateTime the time to
     * @return the meal for the given time, or null if time is after all meals.
     */
    public Meal getCurrentOrNextMealForDateTime(DateTime dateTime) {
        Timber.d("getCurrentOrNextMealForDateTime(%s)); Location: %s; Date: %s", dateTime.toString(), location, date);

        Meal nextOrCurrentMeal = null;
        LocalTime localTime = dateTime.toLocalTime();
        LocalDate localDate = dateTime.toLocalDate();

        if(!getLocalDate().isEqual(localDate)) {
            return null; //The days are not the same, so there are no meals for the time
        }

        for (Meal meal : getMeals()) {
            Timber.d("Checking meal %s", meal.getName());
            if (meal.containsTime(localTime)) {
                Timber.d("Meal contains the time, returning.");
                return meal;
            } else if (meal.endsBefore(localTime)) {
                Timber.d("Meal ends before the time");
                continue;
            } else if (meal.startsAfter(localTime)) {
                Timber.d("Meal starts after the given time");
                if (nextOrCurrentMeal == null) {
                    nextOrCurrentMeal = meal;
                } else {
                    if (meal.timeUntilStartFrom(localTime) < nextOrCurrentMeal.timeUntilStartFrom(localTime)) {
                        Timber.d("Meal starts sooner than the last closest meal");
                        nextOrCurrentMeal = meal;
                    }
                }
            }
        }
        return nextOrCurrentMeal;
    }

    /**
     * Returns the current meal if the given datetime is within a
     * @param dateTime
     * @return
     */
    public Meal getCurrentMealForDateTime(DateTime dateTime) {
        LocalTime localTime = dateTime.toLocalTime();
        LocalDate localDate = dateTime.toLocalDate();

        if(!getLocalDate().isEqual(localDate)) {
            return null; //The days are not the same, so there are no meals for the time
        }

        for (Meal meal : getMeals()) {
            Log.d(TAG, "Checking meal " + meal.getName());
            if (meal.containsTime(localTime)) {
                Log.d(TAG, "Meal contains the time, returning.");
                return meal;
            }
        }
        return null; //There were no meals in progress.
    }

    public Meal getNextMealForDateTime(DateTime dateTime) {
        Timber.d("getNextMealForDateTime(%s)); Location: %s; Date: %s", dateTime.toString(), location, date);

        Meal nextOrCurrentMeal = null;
        LocalTime localTime = dateTime.toLocalTime();
        LocalDate localDate = dateTime.toLocalDate();

        if(!getLocalDate().isEqual(localDate)) {
            return null; //The days are not the same, so there are no meals for the time
        }

        for (Meal meal : getMeals()) {
            Timber.d("Checking meal %s", meal.getName());
            if (meal.startsAfter(localTime)) {
                Timber.d("Meal starts after the given time");
                if (nextOrCurrentMeal == null) {
                    nextOrCurrentMeal = meal;
                } else {
                    if (meal.timeUntilStartFrom(localTime) < nextOrCurrentMeal.timeUntilStartFrom(localTime)) {
                        Timber.d("Meal starts sooner than the last closest meal");
                        nextOrCurrentMeal = meal;
                    }
                }
            }
        }
        return nextOrCurrentMeal;
    }
}
