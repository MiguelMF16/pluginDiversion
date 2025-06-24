package com.comugamers.spigot.service.anuncio;

import com.google.inject.ImplementedBy;
import com.comugamers.spigot.service.anuncio.impl.AnuncioServiceImpl;

@ImplementedBy(AnuncioServiceImpl.class)
public interface IAnuncioService {

    /**
     * Inicia el ciclo de anuncios con el intervalo por defecto.
     */
    void iniciar();

    /**
     * Cambia el intervalo de tiempo entre anuncios en minutos.
     * 
     * @param minutos nuevo intervalo en minutos
     */
    void cambiarIntervalo(int minutos);
}
