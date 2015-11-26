package com.davidtschida.materialdiningcourts.eventbus;

import org.joda.time.LocalDate;

/**
 * Created by david on 11/26/2015.
 */
public class DateChosenEvent {
    private final LocalDate mLocalDate;

    @Override
    public String toString() {
        return mLocalDate.toString();
    }

    public DateChosenEvent(LocalDate localDate) {
        this.mLocalDate = localDate;
    }

    public LocalDate getLocalDate() {
        return mLocalDate;
    }
}
