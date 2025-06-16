package com.comugamers.spigot.command;

import org.bukkit.command.CommandSender;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEjemploService;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;

@Command("apagarserver")
public class EjemploCommand extends BaseCommand {
	
	@Autowired
	private IEjemploService ejemploService;
	
	@Default
	public void execute(CommandSender commandSender) {
		this.ejemploService.apagarServidor();
	}

}
