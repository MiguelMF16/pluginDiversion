package com.comugamers.spigot.service.impl;

import org.bukkit.plugin.Plugin;

import com.comugamers.quanta.annotations.Service;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEjemploService;

@Service
public class EjemploServiceImpl implements IEjemploService{

	@Autowired
	private Plugin plugin;
	
	
	@Override
	public void apagarServidor() {

		this.plugin.getServer().shutdown();
		
	}

}
