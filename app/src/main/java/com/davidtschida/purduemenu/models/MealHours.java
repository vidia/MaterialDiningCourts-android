package com.davidtschida.purduemenu.models;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import lombok.Data;

/**
 * Created by david on 10/22/2015.
 */
@Data
public class MealHours {
    @SerializedName("StartTime")
    String startTime;

    @SerializedName("EndTime")
    String endTime;

    public LocalTime getStartLocalTime() {
        DateTimeZone eastern = DateTimeZone.forID("America/Indiana/Indianapolis");

        DateTime startDateTime = DateTime.parse(getStartTime(),
                DateTimeFormat.forPattern("HH:mm:ss"));

        return new LocalTime(startDateTime, eastern);
    }

    public LocalTime getEndLocalTime() {
        DateTimeZone eastern = DateTimeZone.forID("America/Indiana/Indianapolis");

        DateTime endDateTime = DateTime.parse(getEndTime(),
                DateTimeFormat.forPattern("HH:mm:ss"));

        return new LocalTime(endDateTime, eastern);
    }
}
