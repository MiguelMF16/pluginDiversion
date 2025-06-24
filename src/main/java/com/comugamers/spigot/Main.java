package com.comugamers.spigot;


import com.comugamers.quanta.platform.paper.QuantaPaperPlugin;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.anuncio.IAnuncioService;

public class Main extends QuantaPaperPlugin {

    @Autowired
    private IAnuncioService anuncioService;

    @Override
    protected void onPluginEnable() {
        // Iniciamos los anuncios automáticos al habilitar el plugin
        this.anuncioService.iniciar();
        getLogger().info("LaJaulaVS Promo Twitch habilitado");
    }

    @Override
    protected void onPluginDisable() {
        getLogger().info("LaJaulaVS Promo Twitch deshabilitado");
    }
}
