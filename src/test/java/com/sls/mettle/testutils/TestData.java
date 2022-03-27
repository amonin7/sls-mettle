package com.sls.mettle.testutils;

import com.sls.mettle.model.Item;
import com.sls.mettle.model.ItemType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestData {
    public static final Timestamp NOW = Timestamp.from(Instant.now());

    public static String I1_UUID = "3b4890fb-bfb5-4f06-ad04-663aaef98340";
    public static String I2_UUID = "3b4890fb-bfb5-4f06-ad04-663aaef98341";
    public static String I1_CREATE_JSON = "{\"name\":\"item1\",\"description\":\"descr1\",\"type\":\"hockey_pads\",\"cost\":12.0}";
    public static String I1_UPDATE_JSON = "{\"id\":\"" + I1_UUID + "\",\"name\":\"apple\",\"description\":\"some small apple\",\"type\":\"hockey_pads\",\"cost\":13.0}";
    public static Item I1 = new Item(
            null, "item1", "descr1", ItemType.HOCKEY_PADS, 12.0,
            null, null, null);
    public static Item I1_UPD = new Item(
            UUID.fromString(I1_UUID), "apple", "some small apple", ItemType.HOCKEY_PADS, 13.0,
            null, null, null);
    public static Item I1_SAVED = new Item(
            UUID.fromString(I1_UUID), "item1", "descr1", ItemType.HOCKEY_PADS, 12.0,
            NOW, NOW, null);
    public static Item I1_UPDATED = new Item(
            UUID.randomUUID(), "apple", "some small apple", ItemType.HOCKEY_PADS, 13.0,
            NOW, NOW, null);
    public static Item I1_DELETED = new Item(
            UUID.randomUUID(), "item1", "descr1", ItemType.HOCKEY_PADS, 12.0,
            NOW, NOW, NOW);
    public static Item I2 = new Item(
            null, "item2", "descr2", ItemType.HOCKEY_SKATES, 13.0,
            null, null, null);
    public static Item I2_WITH_ID = new Item(
            UUID.fromString(I2_UUID), "item2", "descr2", ItemType.HOCKEY_SKATES, 13.0,
            null, null, null);
    public static Item I2_SAVED = new Item(
            UUID.fromString(I2_UUID), "item2", "descr2", ItemType.HOCKEY_SKATES, 13.0,
            NOW, NOW, null);
    public static Item I_INVALID = new Item(
            UUID.randomUUID(), "someVeryLongLongLongNameIsHere", "descr2", ItemType.HOCKEY_SKATES,
            -13.0, null, null, null);

    public static List<Item> ITEMS = Arrays.asList(I1_SAVED, I2_SAVED);

    public static String generateRandomLongString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
