package com.davidtschida.materialdiningcourts.eventbus;

/**
 * Created by david on 11/26/2015.
 */
public class MealChosenEvent {
    @Override
    public String toString() {
        return mMeal;
    }

    private final String mMeal;

    public MealChosenEvent(String meal) {
        this.mMeal = meal;
    }

    public String getMeal() {
        return mMeal;
    }
}
