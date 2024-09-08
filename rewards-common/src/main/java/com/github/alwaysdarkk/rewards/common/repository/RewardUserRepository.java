package com.github.alwaysdarkk.rewards.common.repository;

import com.github.alwaysdarkk.rewards.common.data.RewardUser;
import com.github.alwaysdarkk.rewards.common.repository.adapter.RewardUserAdapter;
import com.henryfabio.sqlprovider.executor.SQLExecutor;

import java.util.concurrent.CompletableFuture;

import static com.github.alwaysdarkk.rewards.common.RewardsConstants.*;

public class RewardUserRepository {

    private final SQLExecutor sqlExecutor;

    public RewardUserRepository(SQLExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;

        sqlExecutor.updateQuery(CREATE_TABLE_QUERY);
    }

    public void insert(RewardUser user) {
        CompletableFuture.runAsync(() -> sqlExecutor.updateQuery(INSERT_QUERY, simpleStatement -> {
            simpleStatement.set(1, user.getPlayerName());
            simpleStatement.set(2, GSON.toJson(user.getRewardMap()));
        }));
    }

    public void update(RewardUser user) {
        CompletableFuture.runAsync(() -> sqlExecutor.updateQuery(UPDATE_QUERY, simpleStatement -> {
            simpleStatement.set(1, GSON.toJson(user.getRewardMap()));
            simpleStatement.set(2, user.getPlayerName());
        }));
    }

    public RewardUser find(String playerName) {
        return sqlExecutor.resultOneQuery(FIND_QUERY, simpleStatement -> simpleStatement.set(1, playerName), RewardUserAdapter.class);
    }
}