package com.comugamers.dominios;


import com.comugamers.quanta.platform.paper.QuantaPaperPlugin;

public class DominiosEnDisputaPlugin extends QuantaPaperPlugin {

    @Override
    protected void onPluginEnable() {
        getLogger().info("Dominios en Disputa ha sido activado.");
    }

    @Override
    protected void onPluginDisable() {
        getLogger().info("Dominios en Disputa ha sido desactivado.");
    }
}
