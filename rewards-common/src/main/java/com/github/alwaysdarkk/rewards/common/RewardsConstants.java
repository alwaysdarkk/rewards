package com.github.alwaysdarkk.rewards.common;

public class RewardsConstants {
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS rewards-users(playerName TEXT NOT NULL PRIMARY KEY, rewardMap LONGTEXT NOT NULL);";

    public static final String INSERT_QUERY = "INSERT INTO rewards-users VALUES(?,?)";
    public static final String UPDATE_QUERY = "UPDATE rewards-users SET rewardMap = ? WHERE playerName = ?";
    public static final String FIND_QUERY = "SELECT * FROM rewards-users WHERE playerName = ? LIMIT 1";
}