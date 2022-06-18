package io.github.terslenk.azap.utils;

import io.github.terslenk.azap.Azap;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Utils {

    private static final NamespacedKey azapkey = new NamespacedKey(Azap.getInstance(), "azapkey");
    public static final DecimalFormat powerFormat = new DecimalFormat("###,###.##",
            DecimalFormatSymbols.getInstance(Locale.ROOT));


    private Utils() {}

    public static void send(Player p, String message) {
        p.sendMessage(ChatColor.GOLD + "狱刑 "+ "&7&l>&r" + ChatColors.color(message));
    }

    public static ItemStack buildNonInteractable(Material material, @Nullable String name, @Nullable String... lore) {
        ItemStack nonClickable = new ItemStack(material);
        ItemMeta NCMeta = nonClickable.getItemMeta();
        if (name != null) {
            NCMeta.setDisplayName(ChatColors.color(name));
        } else {
            NCMeta.setDisplayName(" ");
        }

        if (lore.length > 0) {
            List<String> lines = new ArrayList<>();

            for (String line : lore) {
                lines.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            NCMeta.setLore(lines);
        }
        NCMeta.setCustomModelData(3169);
        nonClickable.setItemMeta(NCMeta);
        return nonClickable;
    }

    public static boolean checkNonInteractable(ItemStack item) {
        return item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 3169;
    }

    public static boolean checkAdjacent(Block b, Material material) {
        return b.getRelative(BlockFace.NORTH).getType() == material
                || b.getRelative(BlockFace.EAST).getType() == material
                || b.getRelative(BlockFace.SOUTH).getType() == material
                || b.getRelative(BlockFace.WEST).getType() == material;
    }

    public static ItemStack keyItem(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        meta.getPersistentDataContainer().set(azapkey, PersistentDataType.INTEGER, 1);
        clone.setItemMeta(meta);
        return clone;
    }

    public static ItemStack unKeyItem(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        meta.getPersistentDataContainer().remove(azapkey);
        clone.setItemMeta(meta);
        return clone;
    }

    public static boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
        return (p.hasPermission("slimefun.inventory.bypass")
                || Slimefun.getProtectionManager().hasPermission(
                p, b.getLocation(), Interaction.INTERACT_BLOCK));
    }

    // Don't use Slimefun's runsync
    public static BukkitTask runSync(Runnable r) {
        return Azap.getInstance() != null && Azap.getInstance().isEnabled() ?
                Bukkit.getScheduler().runTask(Azap.getInstance(), r) : null;
    }

    public static BukkitTask runSync(Runnable r, long delay) {
        return Azap.getInstance() != null && Azap.getInstance().isEnabled() ?
                Bukkit.getScheduler().runTaskLater(Azap.getInstance(), r, delay) : null;
    }
}
