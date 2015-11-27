package com.davidtschida.materialdiningcourts.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by david on 11/26/2015.
 */
public class EventBus {
    private static Bus OTTO_INSTANCE;

    public static Bus getBus() {
        if(OTTO_INSTANCE == null) {
            OTTO_INSTANCE = new Bus();
        }
        return OTTO_INSTANCE;
    }
}
