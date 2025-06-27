package com.comugamers.dominios.command;

import org.bukkit.command.CommandSender;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.dominios.service.IDominiosService;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;

@Command("dominios")
public class DominiosCommand extends BaseCommand {
	
        @Autowired
        private IDominiosService dominiosService;
	
        @Default
        public void execute(CommandSender commandSender) {
                dominiosService.startEvent();
        }

}

