package io.github.terslenk.azap.utils;

import io.github.terslenk.azap.Azap;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class Category {
    public static final ItemGroup AZAP_MAIN = new ItemGroup(new NamespacedKey(Azap.getInstance(),"azap_main"),new CustomItemStack(Material.MAGMA_BLOCK,"&6狱刑"));

    public static void setup(Azap sn) {
        AZAP_MAIN.register(sn);
    }
}
