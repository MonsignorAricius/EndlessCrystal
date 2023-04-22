package me.aricius.endlesscrystal.event;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerPointsEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final UUID playerId;
    private int change;
    private boolean cancelled;

    public PlayerPointsEvent(UUID id, int change) {
        this.playerId = id;
        this.change = change;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
