package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class Hours {
    @SerializedName("Id")
    String Id;

    @SerializedName("Name")
    String name;

    @SerializedName("EffectiveDate")
    String effectiveDate;

    @SerializedName("Days")
    List<DailyHours> days;
}
