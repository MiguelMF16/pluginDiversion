package com.comugamers.spigot;


import com.comugamers.quanta.platform.paper.QuantaPaperPlugin;
import com.comugamers.quanta.annotations.EnableModules;
import com.comugamers.quanta.modules.storage.BaseStorageModule;
import com.comugamers.spigot.listener.BastionBoundaryListener;


@EnableModules({
        BaseStorageModule.class,
})

public class Main extends QuantaPaperPlugin {

    @Override
    protected void onPluginEnable() {
        getLogger().info("ExamplePlugin has been enabled! This is a placeholder plugin to demonstrate the Quanta platform.");
        getServer().getPluginManager().registerEvents(new BastionBoundaryListener(), this);
    }

    @Override
    protected void onPluginDisable() {
        getLogger().info("ExamplePlugin has been disabled! Thank you for using the Quanta platform.");
    }
}