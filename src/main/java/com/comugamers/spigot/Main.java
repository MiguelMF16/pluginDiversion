package com.comugamers.spigot;


import com.comugamers.quanta.platform.paper.QuantaPaperPlugin;

import com.comugamers.spigot.command.DefendersCommand;
import com.comugamers.spigot.command.TeamCommand;
import com.comugamers.spigot.team.ITeamService;
import com.comugamers.spigot.team.TeamServiceImpl;

public class Main extends QuantaPaperPlugin {

    private ITeamService teamService;

    @Override
    protected void onPluginEnable() {
        teamService = new TeamServiceImpl();
        getCommand("team").setExecutor(new TeamCommand(teamService));
        getCommand("defenders").setExecutor(new DefendersCommand(teamService, this));
        getLogger().info("TeamPlugin habilitado");
    }

    @Override
    protected void onPluginDisable() {
        getLogger().info("ExamplePlugin has been disabled! Thank you for using the Quanta platform.");
    }
}