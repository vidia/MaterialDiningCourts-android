package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/26/2015.
 */
@Data
public class Locations {
    @SerializedName("Location")
    List<DiningLocation> locations;
}
