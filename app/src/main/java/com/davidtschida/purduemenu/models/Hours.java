package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by david on 10/22/2015.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Hours extends SugarRecord<Hours> {
    @SerializedName("Id")
    String HoursId;

    @SerializedName("Name")
    String name;

    @SerializedName("EffectiveDate")
    String effectiveDate;

    @SerializedName("Days")
    List<DailyHours> days;
}
