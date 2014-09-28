package com.afterkraft.kraftrpg.bukkit.events.roles;

import com.google.common.base.Preconditions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.afterkraft.kraftrpg.api.entity.Sentient;
import com.afterkraft.kraftrpg.api.events.roles.ExperienceChangeEvent;
import com.afterkraft.kraftrpg.api.roles.Role;
import com.afterkraft.kraftrpg.api.util.FixedPoint;
import com.afterkraft.kraftrpg.common.vector.CommonVector3d;

public class BukkitExperienceChangeEvent extends Event implements ExperienceChangeEvent, Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private final CommonVector3d vectorLocation;
    private final Location bukkitLocation;
    private final FixedPoint original;
    private final Sentient being;
    private final Role role;
    private FixedPoint change;
    private boolean cancelled;

    public BukkitExperienceChangeEvent(final CommonVector3d vectorLocation, FixedPoint original, Sentient being, Role role) {
        this.vectorLocation = vectorLocation;
        this.bukkitLocation = new Location(Bukkit.getWorld(vectorLocation.getWorld().getName()), vectorLocation.getX(), vectorLocation.getY(), vectorLocation.getZ());
        this.original = original;
        this.being = being;
        this.role = role;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public CommonVector3d getPosition() {
        return this.vectorLocation;
    }

    public Location getBukkitLocation() {
        return this.bukkitLocation.clone();
    }

    @Override
    public FixedPoint getFromExperience() {
        return this.original;
    }

    @Override
    public FixedPoint getChange() {
        return this.change;
    }

    @Override
    public void setChange(FixedPoint experience) {
        Preconditions.checkArgument(experience != null, "Cannot set a null experience for the event!");
        this.change = experience;
    }

    @Override
    public FixedPoint getFinalExperience() {
        return null;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public Sentient getEntity() {
        return this.being;
    }

    @Override
    public Sentient getSentientBeing() {
        return this.being;
    }

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
