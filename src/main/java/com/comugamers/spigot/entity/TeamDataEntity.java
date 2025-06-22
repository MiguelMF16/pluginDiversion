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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public UUID getLeader() {
		return leader;
	}
	public void setLeader(UUID leader) {
		this.leader = leader;
	}
	public List<UUID> getPlayers() {
		return players;
	}
	public void setPlayers(List<UUID> players) {
		this.players = players;
	}
	public Integer getBastion1X() {
		return bastion1X;
	}
	public void setBastion1X(Integer bastion1x) {
		bastion1X = bastion1x;
	}
	public Integer getBastion1Z() {
		return bastion1Z;
	}
	public void setBastion1Z(Integer bastion1z) {
		bastion1Z = bastion1z;
	}
	public Integer getBastion2X() {
		return bastion2X;
	}
	public void setBastion2X(Integer bastion2x) {
		bastion2X = bastion2x;
	}
	public Integer getBastion2Z() {
		return bastion2Z;
	}
	public void setBastion2Z(Integer bastion2z) {
		bastion2Z = bastion2z;
	}
    
    
    

}
