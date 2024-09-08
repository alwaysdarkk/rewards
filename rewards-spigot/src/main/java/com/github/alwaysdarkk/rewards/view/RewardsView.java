package com.github.alwaysdarkk.rewards.view;

import com.github.alwaysdarkk.rewards.common.configuration.InventoryValue;
import com.github.alwaysdarkk.rewards.common.configuration.MessagesValue;
import com.github.alwaysdarkk.rewards.common.data.Reward;
import com.github.alwaysdarkk.rewards.common.data.RewardUser;
import com.github.alwaysdarkk.rewards.common.item.ItemBuilder;
import com.github.alwaysdarkk.rewards.common.registry.RewardRegistry;
import com.github.alwaysdarkk.rewards.common.registry.RewardUserRegistry;
import com.github.alwaysdarkk.rewards.common.repository.RewardUserRepository;
import com.github.alwaysdarkk.rewards.common.util.TimeFormatter;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewItemHandler;
import me.saiintbrisson.minecraft.ViewSlotClickContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RewardsView extends View {

    private final RewardRegistry rewardRegistry;
    private final RewardUserRegistry userRegistry;
    private final RewardUserRepository userRepository;

    public RewardsView(
            RewardRegistry rewardRegistry, RewardUserRegistry userRegistry, RewardUserRepository userRepository) {
        super(InventoryValue.get(InventoryValue::inventorySize), InventoryValue.get(InventoryValue::inventoryName));

        setCancelOnClick(true);
        setCancelOnDrag(true);
        setCancelOnDrop(true);
        setCancelOnPickup(true);

        this.rewardRegistry = rewardRegistry;
        this.userRegistry = userRegistry;
        this.userRepository = userRepository;
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {
        final RewardUser user = userRegistry.find(context.getPlayer().getName());
        if (user == null) {
            context.close();
            return;
        }

        rewardRegistry.findAll().forEach(reward -> context.slot(reward.getSlot())
                .onRender(getRewardIcon(reward, user))
                .onClick(handleRewardClick(reward, user)));
    }

    private ViewItemHandler getRewardIcon(Reward reward, RewardUser user) {
        return context -> {
            final String rewardDelay = TimeFormatter.format(reward.getDelay());

            final Player player = context.getPlayer();
            if (!player.hasPermission(reward.getPermission())) {
                final List<String> lore = InventoryValue.get(InventoryValue::noPermissionLore);
                final ItemStack itemStack = new ItemBuilder(reward.getItemStack())
                        .hideAll()
                        .displayName(reward.getName())
                        .loreWithPlaceholder(lore, Map.of("{reward-delay}", rewardDelay))
                        .build();

                context.setItem(itemStack);
                return;
            }

            if (!user.canCollect(reward.getId())) {
                final String remainingTime = TimeFormatter.format(user.getRemainingTime(reward));

                final List<String> lore = InventoryValue.get(InventoryValue::delayLore);
                final ItemStack itemStack = new ItemBuilder(reward.getItemStack())
                        .hideAll()
                        .displayName(reward.getName())
                        .loreWithPlaceholder(
                                lore, Map.of("{reward-delay}", rewardDelay, "{remaining_time}", remainingTime))
                        .build();

                context.setItem(itemStack);
                return;
            }

            final List<String> lore = InventoryValue.get(InventoryValue::collectLore);
            final ItemStack itemStack = new ItemBuilder(reward.getItemStack())
                    .hideAll()
                    .displayName(reward.getName())
                    .loreWithPlaceholder(lore, Map.of("{reward-delay}", rewardDelay))
                    .build();

            context.setItem(itemStack);
        };
    }

    private Consumer<ViewSlotClickContext> handleRewardClick(Reward reward, RewardUser user) {
        return context -> {
            final Player player = context.getPlayer();
            context.close();

            if (!player.hasPermission(reward.getPermission())) {
                player.sendMessage(MessagesValue.get(MessagesValue::noPermission));
                return;
            }

            if (!user.canCollect(reward.getId())) {
                final String inDelayMessage = MessagesValue.get(MessagesValue::inDelay)
                        .replace("{remaining-time}", TimeFormatter.format(user.getRemainingTime(reward)));

                player.sendMessage(inDelayMessage);
                return;
            }

            final String command = reward.getCommand().replace("{player}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

            user.addReward(reward);
            userRepository.update(user);

            final String collectedMessage =
                    MessagesValue.get(MessagesValue::rewardCollected).replace("{reward-name}", reward.getName());

            player.sendMessage(collectedMessage);
        };
    }
}