package controller.game;

import java.util.List;

public final class Utils {

    private Utils() { }

    public static BattleListEntry closestCreature(BattleListEntry from,
                                                  List<BattleListEntry> creatures) {
        return closestCreature(from.getPositionX(), from.getPositionY(), from.getPositionZ(),
                creatures);
    }

    public static BattleListEntry closestCreature(Integer x, Integer y, Integer z,
                                                  List<BattleListEntry> creatures) {
        if (creatures.size() == 0) {
            return null;
        }
        BattleListEntry closesCreature = null;
        int distance = Integer.MAX_VALUE;
        for (BattleListEntry battleListEntry : creatures) {
            if (battleListEntry.getPositionZ() != z) {
                continue;
            }
            int currentDistance = Math.abs(x - battleListEntry.getPositionX())
                    + Math.abs(y - battleListEntry.getPositionY());
            if (distance > currentDistance) {
                distance = currentDistance;
                closesCreature = battleListEntry;
            }
        }
        return closesCreature;
    }

    public static BattleListEntry getCreatureByName(String name, List<BattleListEntry> creatures) {
        if (creatures.size() == 0) {
            return null;
        }
        for (BattleListEntry battleListEntry : creatures) {
            if (battleListEntry.getName().equals(name)) {
                return battleListEntry;
            }
        }
        return null;
    }
}
