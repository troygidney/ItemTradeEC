package me.pokerman99.ItemTradeEC.Commands;

import me.pokerman99.ItemTradeEC.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Random;

public class EvoCommand implements CommandExecutor {
    static String[] evoItems = {"pixelmon:fire_stone", "pixelmon:water_stone", "pixelmon:moon_stone", "pixelmon:thunder_stone", "pixelmon:leaf_stone", "pixelmon:sun_stone", "pixelmon:dawn_stone", "pixelmon:dusk_stone"};

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;
        if (!player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
            Utils.sendMessage(src, "&cYou are not holding a evolution stone");
            return CommandResult.empty();
        }

        ItemStack heldItem = player.getItemInHand(HandTypes.MAIN_HAND).get();
        String heldItemName = heldItem.getType().getId();

        if (heldItem.getQuantity() > 1) {
            Utils.sendMessage(src, "&cPlease only have one time in the stack!");
            return CommandResult.empty();
        }

        if (!heldItemName.matches("(pixelmon:).*?(_stone)")) {
            Utils.sendMessage(src, "&cThe supplied item is not a evolution stone!");
            return CommandResult.empty();
        }

        heldItem.setQuantity(heldItem.getQuantity() - 1);
        player.getInventory().offer(heldItem);


        Random random = new Random();

        ItemStack stack = ItemStack.builder()
                .itemType(Sponge.getRegistry().getType(ItemType.class, evoItems[random.nextInt(7)+1]).get())
                .build();

        player.getInventory().offer(stack);


        return CommandResult.success();
    }
}