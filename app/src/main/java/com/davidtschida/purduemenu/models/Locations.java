package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by david on 10/26/2015.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Locations extends SugarRecord<Locations> {
    @SerializedName("Location")
    List<DiningLocation> locations;
}
