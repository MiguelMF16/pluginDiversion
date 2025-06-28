package gg.lajaulavs.bastion;

import org.bukkit.Location;

public class Territory {
    private final String id;
    private Location corner1;
    private Location corner2;
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
    public void setCorner1(Location corner1) { this.corner1 = corner1; }
    public void setCorner2(Location corner2) { this.corner2 = corner2; }
    public Location getCenter() {
        double x = (corner1.getX() + corner2.getX()) / 2.0;
        double y = (corner1.getY() + corner2.getY()) / 2.0;
        double z = (corner1.getZ() + corner2.getZ()) / 2.0;
        return new Location(corner1.getWorld(), x, y, z);
    }
    public String getOwnerTeam() { return ownerTeam; }
    public void setOwnerTeam(String ownerTeam) { this.ownerTeam = ownerTeam; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public long getLastClaimedTick() { return lastClaimedTick; }
    public void setLastClaimedTick(long lastClaimedTick) { this.lastClaimedTick = lastClaimedTick; }
}
