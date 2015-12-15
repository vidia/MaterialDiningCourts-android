package com.davidtschida.purduemenu.util;

import android.support.annotation.Nullable;

import com.davidtschida.purduemenu.models.DayMenu;
import com.davidtschida.purduemenu.models.Meal;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by david on 12/10/2015.
 */
public class CurrentMealCollection extends ArrayList<Meal> {

    public void addFromDayMenu(DayMenu dayMenu, DateTime dateTime) {
        Meal currentMeal = dayMenu.getCurrentMealForDateTime(dateTime);
        if(currentMeal != null) {
            this.add(currentMeal);
        }
    }

    @Nullable
    public Meal getCurrentMeal() {
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
