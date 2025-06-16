package com.comugamers.spigot;


import com.comugamers.quanta.platform.paper.QuantaPaperPlugin;

public class Main extends QuantaPaperPlugin {

    @Override
    protected void onPluginEnable() {
        getLogger().info("ExamplePlugin has been enabled! This is a placeholder plugin to demonstrate the Quanta platform.");
    }

    @Override
    protected void onPluginDisable() {
        getLogger().info("ExamplePlugin has been disabled! Thank you for using the Quanta platform.");
    }
}