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
public class DiningLocation extends SugarRecord<DiningLocation> {
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
