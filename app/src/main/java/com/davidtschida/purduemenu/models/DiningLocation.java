package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class DiningLocation {
    @SerializedName("Name")
    String name;

    @SerializedName("FormalName")
    String formalName;

    @SerializedName("Address")
    Address address;

    @SerializedName("PhoneNumber")
    String phoneNumber;

    double latitude;
    double longitude;

    @SerializedName("Images")
    List<String> images;

    @SerializedName("NormalHours")
    List<Hours> normalHours;
}
