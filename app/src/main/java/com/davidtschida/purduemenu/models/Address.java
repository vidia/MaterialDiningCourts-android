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
public class Address extends SugarRecord<Address> {
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
