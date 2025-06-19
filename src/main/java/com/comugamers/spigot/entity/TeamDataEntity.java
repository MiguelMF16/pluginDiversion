package com.comugamers.spigot.entity;

import java.util.List;
import java.util.UUID;


import com.comugamers.quanta.modules.storage.annotation.Entity;
import com.comugamers.quanta.modules.storage.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Players")

@Getter
@Setter
public class TeamDataEntity {
	
	@Id
    private String id;
    private String displayName;
    private UUID leader;
    private List<UUID> players;

    // Bastion zone corners. Only X and Z are stored, Y is considered unlimited.
    private Integer bastion1X;
    private Integer bastion1Z;
    private Integer bastion2X;
    private Integer bastion2Z;

}
