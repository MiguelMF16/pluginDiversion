package com.comugamers.spigot.repository;

import com.comugamers.quanta.modules.storage.StorageType;
import com.comugamers.quanta.modules.storage.annotation.Repository;
import com.comugamers.quanta.modules.storage.repository.QuantaRepository;
import com.comugamers.spigot.entity.TeamDataEntity;

@Repository(storageType = StorageType.IN_MEMORY)
public interface IEquipoRepository extends QuantaRepository<TeamDataEntity, String>{

    boolean existsByDisplayName(String displayName);

}
