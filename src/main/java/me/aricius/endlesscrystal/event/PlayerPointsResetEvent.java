package me.aricius.endlesscrystal.event;

import java.util.UUID;

import org.bukkit.event.HandlerList;

public class PlayerPointsResetEvent extends PlayerPointsEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerPointsResetEvent(UUID id) {
        super(id, 0);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
