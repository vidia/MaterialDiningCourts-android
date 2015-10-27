package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class MealHours {
    @SerializedName("StartTime")
    String startTime;

    @SerializedName("EndTime")
    String endTime;
}
