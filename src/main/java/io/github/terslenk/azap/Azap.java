package io.github.terslenk.azap;

import io.github.terslenk.azap.utils.Setup;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import javax.annotation.Nonnull;

public class Azap extends JavaPlugin implements SlimefunAddon {
    private static Azap instance;

    @Override
    public void onEnable() {
        Setup.setup(this);

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

    public static Azap getInstance() {
        return instance;
    }

}
