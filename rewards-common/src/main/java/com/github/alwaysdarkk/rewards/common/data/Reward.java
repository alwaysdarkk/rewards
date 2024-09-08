package com.github.alwaysdarkk.rewards.common.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

@Data
@RequiredArgsConstructor
public class Reward {
    private final String id, name, permission, command;
    private final ItemStack itemStack;
    private final long delay;
    private final int slot;
}