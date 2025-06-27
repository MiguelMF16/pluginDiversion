package com.comugamers.dominios.service;

import com.comugamers.dominios.service.impl.DominiosServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(DominiosServiceImpl.class)
public interface IDominiosService {

        void startEvent();

}

