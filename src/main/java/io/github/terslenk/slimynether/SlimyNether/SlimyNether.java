package io.github.terslenk.slimynether.SlimyNether;

import io.github.terslenk.slimynether.SlimyNether.utils.SlimyItems;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import javax.annotation.Nonnull;

public class SlimyNether extends JavaPlugin implements SlimefunAddon {
    private static SlimyNether instance;

    @Override
    public void onEnable() {

        SlimyItems.setup(this);

        instance = this;
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * You will need to return a reference to your Plugin here.
         * If you are using your main class for this, simply return "this".
         */
        return this;
    }

    public static SlimyNether getInstance() {
        return instance;
    }

}
