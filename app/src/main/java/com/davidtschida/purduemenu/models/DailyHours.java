package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class DailyHours {
    @SerializedName("Name")
    String name;

    @SerializedName("DayOfWeek")
    int dayOfWeek;

    @SerializedName("Meals")
    List<Meal> meals;
}
