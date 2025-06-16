package com.comugamers.spigot.team;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Representa un equipo de jugadores.
 */
public class Team {

    private final String name;
    private final UUID leader;
    private final Set<UUID> members = new HashSet<>();

    public Team(String name, UUID leader) {
        this.name = name;
        this.leader = leader;
        this.members.add(leader);
    }

    public String getName() {
        return name;
    }

    public UUID getLeader() {
        return leader;
    }

    public Set<UUID> getMembers() {
        return members;
    }
}
