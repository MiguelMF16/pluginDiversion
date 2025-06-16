package com.comugamers.spigot.service;

import com.comugamers.spigot.service.impl.EjemploServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(EjemploServiceImpl.class)
public interface IEjemploService {
	
	void apagarServidor();

}
