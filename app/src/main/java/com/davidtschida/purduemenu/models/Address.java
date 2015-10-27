package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class Address {
    @SerializedName("Street")
    String street;

    @SerializedName("City")
    String city;

    @SerializedName("State")
    String state;

    @SerializedName("ZipCode")
    String zipcode;

    @SerializedName("Country")
    String country;

    @SerializedName("CountryCode")
    String countryCode;
}
