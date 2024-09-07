package com.github.alwaysdarkk.rewards.common.repository;

import com.github.alwaysdarkk.rewards.common.data.RewardUser;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henryfabio.sqlprovider.executor.SQLExecutor;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.github.alwaysdarkk.rewards.common.RewardsConstants.*;

public class RewardUserRepository {

    private final SQLExecutor sqlExecutor;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type rewardMapType = new TypeToken<Map<String, Long>>() {}.getType();

    public RewardUserRepository(SQLExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;

        sqlExecutor.updateQuery(CREATE_TABLE_QUERY);
    }

    public void insert(RewardUser user) {
        CompletableFuture.runAsync(() -> sqlExecutor.updateQuery(INSERT_QUERY, simpleStatement -> {
            simpleStatement.set(1, user.getPlayerName());
            simpleStatement.set(2, gson.toJson(user.getRewardMap()));
        }));
    }

    public void update(RewardUser user) {
        CompletableFuture.runAsync(() -> sqlExecutor.updateQuery(UPDATE_QUERY, simpleStatement -> {
            simpleStatement.set(1, gson.toJson(user.getRewardMap()));
            simpleStatement.set(2, user.getPlayerName());
        }));
    }

    public RewardUser find(String playerName) {
        return sqlExecutor.resultQuery(FIND_QUERY, simpleStatement -> simpleStatement.set(1, playerName), resultSet -> {
            final String name = resultSet.get("playerName");

            final String rawRewardMap = resultSet.get("rewardMap");
            final Map<String, Long> rewardMap = gson.fromJson(rawRewardMap, rewardMapType);

            return new RewardUser(name, rewardMap);
        });
    }
}