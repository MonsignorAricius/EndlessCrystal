package me.aricius.endlesscrystal.storage;

import java.util.Collection;

public interface IStorage {
    int getPoints(String id);
    boolean setPoints(String id, int points);
    boolean playerEntryExists(String id);
    boolean removePlayer(String id);
    boolean destroy();
    boolean build();
    Collection<String> getPlayers();

}
