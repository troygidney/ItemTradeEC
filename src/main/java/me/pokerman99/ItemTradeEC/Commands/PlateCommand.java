package me.pokerman99.ItemTradeEC.Commands;

import com.google.common.collect.Lists;
import me.pokerman99.ItemTradeEC.ConfigVariables;
import me.pokerman99.ItemTradeEC.Main;
import me.pokerman99.ItemTradeEC.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlateCommand implements CommandExecutor {
    public static List<String> PLATEITEMNAMES = Lists.newArrayList();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Player player = (Player) src;

        if (!player.hasPermission("itemtradeec.bypass") && Utils.isOnCooldown(player)) return CommandResult.success();

        ConfigVariables configVariables = Main.getInstance().configVariables;

        if (!player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
            Utils.sendMessage(src, configVariables.getNotHoldingItemPlate());
            return CommandResult.empty();
        }

        ItemStack heldItem = player.getItemInHand(HandTypes.MAIN_HAND).get();
        String heldItemName = heldItem.getType().getName();

        if (!heldItemName.endsWith("_plate")) {
            Utils.sendMessage(src, configVariables.getNotHoldingItemPlate());
            return CommandResult.empty();
        }

        if (heldItem.getQuantity() > 1) {
            Utils.sendMessage(src, configVariables.getOnlyOneItemPlate());
            return CommandResult.empty();
        }

        Random random = new Random();
        int num = random.nextInt(PLATEITEMNAMES.size());
        String item = PLATEITEMNAMES.get(num);
        List<String> valuables = new ArrayList<>(Arrays.asList("spooky_plate", "fist_plate", "zap_plate", "toxic_plate", "insect_plate", "meadow_plate", "splash_plate" ,"icicle_plate"));

        if (valuables.contains(item)) {
            item = PLATEITEMNAMES.get(num);
        }

        if (valuables.contains(item)) {
            item = PLATEITEMNAMES.get(num);
        }

        ItemStack stack = ItemStack.builder().itemType(Sponge.getRegistry().getType(ItemType.class, item).get()).build();

        Utils.sendMessage(player, "&aSuccessfully traded your &l"
                + heldItem.getTranslation().get()
                + "&a for a &l"
                + stack.getTranslation().get() + "&a!");

        heldItem.setQuantity(0);
        player.getInventory().offer(stack);
        Utils.setCooldown(player, 6);

        return CommandResult.success();
    }
}