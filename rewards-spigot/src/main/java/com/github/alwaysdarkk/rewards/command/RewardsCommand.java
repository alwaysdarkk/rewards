package com.github.alwaysdarkk.rewards.command;

import com.github.alwaysdarkk.rewards.common.command.CustomCommand;
import com.github.alwaysdarkk.rewards.view.RewardsView;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardsCommand extends CustomCommand {

    private final ViewFrame viewFrame;

    public RewardsCommand(ViewFrame viewFrame) {
        super("recompensas", null, true, "coletar");
        this.viewFrame = viewFrame;
    }

    @Override
    protected void onCommand(CommandSender commandSender, String[] arguments) {
        final Player player = (Player) commandSender;
        viewFrame.open(RewardsView.class, player);
    }
}
