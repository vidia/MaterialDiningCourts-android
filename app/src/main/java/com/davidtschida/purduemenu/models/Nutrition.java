package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class Nutrition {
    @SerializedName("Name")
    String name;

    @SerializedName("Value")
    double value;

    @SerializedName("LabelValue")
    String labelValue;

    @SerializedName("DailyValue")
    String dailyValue;

    @SerializedName("Ordinal")
    int ordinal;
}
