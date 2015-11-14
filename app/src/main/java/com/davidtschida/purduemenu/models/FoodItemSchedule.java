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
public class FoodItemSchedule extends SugarRecord<FoodItemSchedule> {
    @SerializedName("ItemAppearances")
    List<FoodItemAppearance> itemAppearance;
}
