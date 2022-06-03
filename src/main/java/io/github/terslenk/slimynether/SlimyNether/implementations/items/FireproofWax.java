package io.github.terslenk.slimynether.SlimyNether.implementations.items;

import io.github.terslenk.slimynether.SlimyNether.SlimyNether;
import io.github.terslenk.slimynether.SlimyNether.utils.Utils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The Fireproof Rune prevents items from
 * burning in lava or fire
 * Heavily based off of the Soulbound Rune
 *
 * @author NCBPFluffyBear
 *
 * Worked on Soulbound Rune:
 * @author Linox
 * @author Walshy
 * @author TheBusyBiscuit
 */

public class FireproofWax extends SimpleSlimefunItem<ItemDropHandler> {
    private static final NamespacedKey FIREPROOFWAX_KEY = new NamespacedKey(SlimyNether.getInstance(), "waxed");
    private static final String FIREPROOFWAX_LORE = ChatColor.GOLD + "Waxed";


    public FireproofWax(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
    }

    @Nonnull
    @Override
    public ItemDropHandler getItemHandler() {
        return (e, p, item) -> {
            if (isItem(item.getItemStack())) {

                if (!this.canUse(p, true)) {
                    return true;
                }

                Utils.runSync(() -> activate(p, item), 20L);

                return true;
            }
            return false;
        };
    }

    private void activate(Player p, Item rune) {
        if (!rune.isValid()) {
            return;
        }

        Location l = rune.getLocation();
        Collection<Entity> entities = l.getWorld().getNearbyEntities(l, RANGE, RANGE, RANGE, this::findCompatibleItem);
        Optional<Entity> optional = entities.stream().findFirst();

        if (optional.isPresent()) {
            Item item = (Item) optional.get();
            ItemStack itemStack = item.getItemStack();

            if (itemStack.getAmount() == 1) {
                // This lightning is just an effect, it deals no damage.
                l.getWorld().strikeLightningEffect(l);

                Utils.runSync(() -> {
                    // Being sure entities are still valid and not picked up or whatsoever.
                    if (rune.isValid() && item.isValid() && itemStack.getAmount() == 1) {
                        setFireproof(itemStack);

                        Utils.send(p, "&aYour item is succesfully waxed");
                    } else {
                        Utils.send(p, "&cYour item could not be made fireproof");
                    }
                }, 10L);
            } else {
                Utils.send(p, "&cYour item could not be waxed");
            }
        }
    }

    private boolean findCompatibleItem(Entity entity) {
        if (entity instanceof Item) {
            Item item = (Item) entity;

            return !isFireproof(item.getItemStack()) && !isItem(item.getItemStack());
        }

        return false;
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
