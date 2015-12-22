package com.davidtschida.materialdiningcourts.eventbus;

/**
 * Created by david on 12/4/2015.
 */
public class ShowSnackbarEvent {
    private String mMessage;

    public ShowSnackbarEvent(String message) {
        this.mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
