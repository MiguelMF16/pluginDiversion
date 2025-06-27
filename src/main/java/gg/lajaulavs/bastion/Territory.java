package gg.lajaulavs.bastion;

import org.bukkit.Location;

public class Territory {
    private final String id;
    private final Location corner1;
    private final Location corner2;
    private String ownerTeam;
    private int points;
    private long lastClaimedTick;

    public Territory(String id, Location corner1, Location corner2) {
        this.id = id;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public String getId() { return id; }
    public Location getCorner1() { return corner1; }
    public Location getCorner2() { return corner2; }
    public String getOwnerTeam() { return ownerTeam; }
    public void setOwnerTeam(String ownerTeam) { this.ownerTeam = ownerTeam; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public long getLastClaimedTick() { return lastClaimedTick; }
    public void setLastClaimedTick(long lastClaimedTick) { this.lastClaimedTick = lastClaimedTick; }
}
