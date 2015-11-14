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
public class Allergen extends SugarRecord<Allergen> {
    @SerializedName("Name")
    String name;

    @SerializedName("Value")
    boolean value;
}
