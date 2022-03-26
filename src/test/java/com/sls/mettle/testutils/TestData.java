package com.sls.mettle.testutils;

import com.sls.mettle.model.Item;
import com.sls.mettle.model.ItemType;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestData {
    public static final Timestamp NOW = Timestamp.from(Instant.now());

    public static String I1_UUID = "3b4890fb-bfb5-4f06-ad04-663aaef98340";
    public static String I1_CREATE_JSON = "{\"id\":\"" + I1_UUID + "\",\"name\":\"item1\",\"description\":\"descr1\",\"type\":\"hockey_pads\",\"cost\":12.0}";
    public static String I1_UPDATE_JSON = "{\"id\":\"" + I1_UUID + "\",\"name\":\"apple\",\"description\":\"some small apple\",\"type\":\"hockey_pads\",\"cost\":13.0}";
    public static Item I1 = new Item(
            UUID.fromString(I1_UUID), "item1", "descr1", ItemType.HOCKEY_PADS, 12.0,
            null, null, null);
    public static Item I1_SAVED = new Item(
            UUID.randomUUID(), "item1", "descr1", ItemType.HOCKEY_PADS, 12.0,
            NOW, NOW, null);
    public static Item I1_UPDATED = new Item(
            UUID.randomUUID(), "apple", "some small apple", ItemType.HOCKEY_PADS, 13.0,
            NOW, NOW, null);
    public static Item I1_DELETED = new Item(
            UUID.randomUUID(), "item1", "descr1", ItemType.HOCKEY_PADS, 12.0,
            NOW, NOW, NOW);
    public static Item I2 = new Item(
            UUID.randomUUID(), "item2", "descr2", ItemType.HOCKEY_SKATES, 13.0,
            null, null, null);

    public static List<Item> ITEMS = Arrays.asList(I1, I2);

}