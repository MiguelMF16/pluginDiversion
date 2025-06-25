package com.comugamers.spigot;


import com.comugamers.quanta.platform.paper.QuantaPaperPlugin;
import com.comugamers.quanta.annotations.EnableModules;
import com.comugamers.quanta.modules.storage.BaseStorageModule;
import com.comugamers.quanta.modules.storage.json.JsonStorageModule;
import com.comugamers.spigot.listener.BastionBoundaryListener;
import com.comugamers.spigot.listener.AttackLeaderDeathListener;
import com.comugamers.spigot.listener.AttackJoinListener;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;


@EnableModules({
        BaseStorageModule.class,
        JsonStorageModule.class,
})

public class Main extends QuantaPaperPlugin {

    @Autowired
    private IEquipoService equipoService;

    @Override
    protected void onPluginEnable() {
        getLogger().info("ExamplePlugin has been enabled! This is a placeholder plugin to demonstrate the Quanta platform.");
        getServer().getPluginManager().registerEvents(new BastionBoundaryListener(equipoService), this);
        getServer().getPluginManager().registerEvents(new AttackLeaderDeathListener(equipoService), this);
        getServer().getPluginManager().registerEvents(new AttackJoinListener(equipoService), this);
        equipoService.initScoreboard();
    }

    @Override
    protected void onPluginDisable() {
        getLogger().info("ExamplePlugin has been disabled! Thank you for using the Quanta platform.");
    }
}