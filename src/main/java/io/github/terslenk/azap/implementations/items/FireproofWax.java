package io.github.terslenk.azap.implementations.items;

import io.github.terslenk.azap.Azap;
import io.github.terslenk.azap.utils.AddonItems;
import io.github.terslenk.azap.utils.Utils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Fireproof wax prevents items from
 * burning in lava or fire
 *
 * Heavily based off of the Fireproof Rune
 * @author NCBPFluffyBear
 */

public class FireproofWax extends SimpleSlimefunItem<ToolUseHandler> {
    private static final NamespacedKey FIREPROOFWAX_KEY = new NamespacedKey(Azap.getInstance(), "waxed");
    private static final String FIREPROOFWAX_LORE = ChatColor.GOLD + "Waxed";


    public FireproofWax(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
    }

    @Nonnull
    @Override
    public ToolUseHandler getItemHandler() {
        return (e,item,inventory,wax)-> {
            Player p = e.getPlayer();

            ItemStack main = p.getInventory().getItemInMainHand();
            ItemStack off = p.getInventory().getItemInOffHand();

            if (p.isSneaking() && main.getAmount() == 1 && off.equals(AddonItems.FIREPROOF_WAX)) {
                off.setAmount(off.getAmount() - 1);
                setFireproof(main);

                Utils.send(p,"Item has been successfully waxed");
            } else {
                Utils.send(p,"Item couldn't be waxed");
            }
        };
    }

    public static void setFireproof(@Nullable ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            boolean isFireproof = isFireproof(item);
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (!isFireproof) {
                container.set(FIREPROOFWAX_KEY, PersistentDataType.BYTE, (byte) 1);
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                lore.add(FIREPROOFWAX_LORE);
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }

    public static boolean isFireproof(@Nullable ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : null;
            return hasFireproofFlag(meta);
        } else {
            return false;
        }
    }

    private static boolean hasFireproofFlag(@Nullable ItemMeta meta) {
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            return container.has(FIREPROOFWAX_KEY, PersistentDataType.BYTE);
        }
        return false;
    }
}
