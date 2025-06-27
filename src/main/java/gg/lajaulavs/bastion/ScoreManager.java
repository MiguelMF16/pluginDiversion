package gg.lajaulavs.bastion;

import java.util.HashMap;
import java.util.Map;

/**
 * Lleva la puntuación de cada equipo.
 */
public class ScoreManager {
    private final Map<String, Integer> teamPoints = new HashMap<>();

    public int addPoints(String team, int points) {
        int newScore = teamPoints.getOrDefault(team, 0) + points;
        teamPoints.put(team, newScore);
        return newScore;
    }

    public int getPoints(String team) {
        return teamPoints.getOrDefault(team, 0);
    }
}
