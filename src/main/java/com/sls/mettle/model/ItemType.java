package com.sls.mettle.model;

import java.util.Arrays;

public enum ItemType {
    HOCKEY_PADS,
    HOCKEY_SKATES,
    HOCKEY_STICK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static ItemType valueOfIgnoreCase(String item) {
        for (ItemType d : ItemType.values()) {
            if (d.name().equalsIgnoreCase(item)) {
                return d;
            }
        }
        throw new IllegalArgumentException(
                "provided item type does not match possible values: "
                        + Arrays.toString(ItemType.values()));
    }
}
