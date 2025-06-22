package com.comugamers.spigot.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

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
	
}
