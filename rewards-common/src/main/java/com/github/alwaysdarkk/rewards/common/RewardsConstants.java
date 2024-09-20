package com.github.alwaysdarkk.rewards.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RewardsConstants {
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS rewards_users(playerName VARCHAR(32) NOT NULL PRIMARY KEY, rewardMap LONGTEXT NOT NULL)";

    public static final String INSERT_QUERY = "INSERT INTO rewards_users VALUES(?,?)";
    public static final String UPDATE_QUERY = "UPDATE rewards_users SET rewardMap = ? WHERE playerName = ?";
    public static final String FIND_QUERY = "SELECT * FROM rewards_users WHERE playerName = ?";

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}