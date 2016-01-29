package com.davidtschida.materialdiningcourts.eventbus;

import com.davidtschida.purduemenu.models.FoodItem;

import lombok.Data;

/**
 * Event fired when the type of a food is changed (Entree, Side, etc) by the user.
 */
@Data
public class FoodItemTypeSetEvent {

    private final FoodItem mItem;
    private final String mType;
    private final boolean mIsSet;

    public FoodItemTypeSetEvent(FoodItem item, String type, boolean isSet) {
        this.mItem = item;
        this.mType = type;
        this.mIsSet = isSet;
    }
}
