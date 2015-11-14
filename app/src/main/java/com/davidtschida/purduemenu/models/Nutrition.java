package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by david on 10/22/2015.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Nutrition extends SugarRecord<Nutrition> {
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
