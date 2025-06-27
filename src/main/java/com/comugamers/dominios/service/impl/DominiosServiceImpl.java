package com.comugamers.dominios.service.impl;

import org.bukkit.plugin.Plugin;

import com.comugamers.quanta.annotations.Service;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.dominios.service.IDominiosService;

@Service
public class DominiosServiceImpl implements IDominiosService {

        @Autowired
        private Plugin plugin;
	
	
        @Override
        public void startEvent() {
                plugin.getLogger().info("El evento Dominios en Disputa ha comenzado.");
        }

}

