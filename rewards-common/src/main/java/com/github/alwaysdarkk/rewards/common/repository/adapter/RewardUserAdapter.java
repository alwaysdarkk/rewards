package com.github.alwaysdarkk.rewards.common.repository.adapter;

import com.github.alwaysdarkk.rewards.common.data.RewardUser;
import com.google.common.reflect.TypeToken;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;

import java.lang.reflect.Type;
import java.util.Map;

import static com.github.alwaysdarkk.rewards.common.RewardsConstants.GSON;

public class RewardUserAdapter implements SQLResultAdapter<RewardUser> {

    private final Type rewardMapType = new TypeToken<Map<String, Long>>() {}.getType();

    @Override
    public RewardUser adaptResult(SimpleResultSet simpleResultSet) {
        final String playerName = simpleResultSet.get("playerName");

        final String rewardMapRaw = simpleResultSet.get("rewardMap");
        final Map<String, Long> rewardMap = GSON.fromJson(rewardMapRaw, rewardMapType);

        return new RewardUser(playerName, rewardMap);
    }
}
