package io.github.terslenk.azap.utils;

import io.github.terslenk.azap.Azap;
import io.github.terslenk.azap.implementations.items.FireproofWax;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AddonItems {
    public static final SlimefunItemStack MAGTALIUM = new SlimefunItemStack("AZAP_MAGTALIUM", Material.MAGMA_CREAM,"&cMagtalium");
    public static final SlimefunItemStack FIREPROOF_WAX = new SlimefunItemStack("AZAP_FIREPROOF_WAX",Material.HONEYCOMB_BLOCK,"&6Fireproof Wax");

    public static void setup(Azap azap) {
        new FireproofWax(Category.AZAP_MAIN, FIREPROOF_WAX, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null,null,null,
                null,new ItemStack(Material.MAGMA_CREAM),null,
                null,null,null
        }).register(azap);
        new SlimefunItem(Category.AZAP_MAIN, MAGTALIUM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null,null,null,
                null,null,null,
                null,null,null
        }).register(azap);
    }
}
