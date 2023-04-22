package me.aricius.endlesscrystal.event;

import java.util.UUID;

import org.bukkit.event.HandlerList;

public class PlayerPointsChangeEvent extends PlayerPointsEvent {
    private static final HandlerList handlers = new HandlerList();

    public PlayerPointsChangeEvent(UUID id, int change) {
        super(id, change);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
