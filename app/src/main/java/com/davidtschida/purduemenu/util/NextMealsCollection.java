package com.davidtschida.purduemenu.util;

import android.support.annotation.Nullable;

import com.davidtschida.purduemenu.models.DayMenu;
import com.davidtschida.purduemenu.models.Meal;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by david on 12/14/2015.
 */
public class NextMealsCollection extends ArrayList<Meal> {

    public void addFromDayMenu(DayMenu dayMenu, DateTime dateTime) {
        Meal currentMeal = dayMenu.getNextMealForDateTime(dateTime);
        if(currentMeal != null) {
            this.add(currentMeal);
        }
    }

    @Nullable
    public Meal getNextMeal() {
        if(isEmpty()) {
            return null;
        }

        Meal earliest = this.get(0);
        for(Meal meal : this) {
            if(meal.getHours().getStartLocalTime().isBefore(earliest.getHours().getStartLocalTime())) {
                earliest = meal;
            }
        }
        return earliest;
    }
}
