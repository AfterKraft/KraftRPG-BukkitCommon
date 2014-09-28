/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.bukkit.events.entity.damage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

import org.apache.commons.lang.Validate;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.DamageType;
import com.afterkraft.kraftrpg.api.events.entity.damage.InsentientDamageEvent;
import com.afterkraft.kraftrpg.api.listeners.DamageCause;
import com.afterkraft.kraftrpg.api.listeners.DamageCause.VanillaDamageCause;
import com.afterkraft.kraftrpg.bukkit.events.entity.BukkitInsentientEvent;

/**
 * A traditional catch all damage event to be handled by KraftRPG for any
 * {@link Insentient} being damaged for any
 * reason. This specific event does not always have an attacker, but it is
 * handled with the Insentient in the event there is an Insentient being
 * damaged for any reason (including instances of CommandBlocks or
 * {@link org.bukkit.entity.Entity}).
 * <p/>
 * This is the KraftRPG counterpart to
 * {@link EntityDamageEvent} after special handling
 * and calculations being made on the damage being dealt. It is guaranteed
 * that the {@link #getDefender()} will not be null, but it is not guaranteed
 * the linked {@link org.bukkit.entity.LivingEntity} is not a real entity.
 * Therefore the methods provided from
 * {@link Insentient} should be the only
 * ones considered "safe" to use.
 * <p/>
 * This is written due to the possibility of customizing entities through
 * {@link com.afterkraft.kraftrpg.api.entity.EntityManager#addEntity(com.afterkraft.kraftrpg.api.entity.IEntity)}
 * that may not be considered {@link org.bukkit.entity.LivingEntity}.
 */
public class BukkitInsentientDamageEvent extends BukkitInsentientEvent implements Cancellable, InsentientDamageEvent {
    private static final DamageType[] CUSTOM_MODIFIERS = DamageType.values();
    private static final HandlerList handlers = new HandlerList();
    private final DamageCause cause;
    private final Insentient defender;
    private final Map<DamageType, Double> originals;
    private final Map<DamageType, Double> modifiers;
    private final EntityDamageEvent bukkitEvent;
    private boolean isVaryingDamageEnabled;
    private boolean cancelled;

    public BukkitInsentientDamageEvent(final Insentient defender, final EntityDamageEvent event, final Map<DamageType, Double> customModifiers, final boolean isVaryingEnabled) {
        super(defender);
        Validate.notNull(event, "Cannot associate to a null EntityDamageEvent!");
        Validate.notNull(customModifiers, "Cannot have a null modifier map!");
        Validate.isTrue(!customModifiers.isEmpty(), "Cannot have an empty modifier map!");
        Validate.noNullElements(customModifiers.values(), "Cannot have null modifiers!");
        this.defender = defender;
        this.originals = new HashMap<DamageType, Double>(customModifiers);
        this.modifiers = customModifiers;
        this.isVaryingDamageEnabled = isVaryingEnabled;
        this.cause = VanillaDamageCause.valueOf(event.getCause().name());
        this.bukkitEvent = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Get the defending {@link Insentient}
     * that is being damaged.
     *
     * @return the defending Insentient being
     */
    @Override
    public Insentient getDefender() {
        return this.defender;
    }

    /**
     * Get the default damage (unmodified) of this event as calculated by KraftRPG or a skill
     *
     * @return the default calculated damage
     */
    @Override
    public double getOriginalDamage() {
        double damage = 0;
        for (DamageType damageType : CUSTOM_MODIFIERS) {
            damage += getOriginalDamage(damageType);
        }
        return damage;
    }

    /**
     * Gets the default damage for the specified DamageType.
     *
     * @param type The type of Damage to get
     * @return The raw amount of damage for the specified type
     * @throws IllegalArgumentException If the damage type is null
     */
    @Override
    public double getOriginalDamage(DamageType type) {
        Validate.notNull(type, "Cannot have null DamageType");
        final Double damage = this.originals.get(type);
        return damage == null ? 0 : damage;
    }

    /**
     * Get the final damage after other listeners have handled this event.
     * This is usually to handle for specific customizations depending on the
     * {@link DamageCause}
     *
     * @return the final damage to be inflicted on the defending Insentient
     */
    @Override
    public final double getTotalDamage() {
        double damage = 0;
        for (DamageType damageType : CUSTOM_MODIFIERS) {
            damage += getDamage(damageType);
        }
        return damage;
    }

    /**
     * Gets the final damage after all damage reductions are applied.
     *
     * @return The amount of damage caused by the event
     */
    @Override
    public final double getFinalDamage() {
        double damage = 0;
        for (DamageModifier modifier : DamageModifier.values()) {
            damage += this.bukkitEvent.getDamage(modifier);
        }
        for (DamageType damageType : CUSTOM_MODIFIERS) {
            damage += getDamage(damageType);
        }
        return damage;
    }

    /**
     * Gets the damage change for the damage type.
     *
     * @param modifier The modifier in question
     * @return The damage for the specified modifier
     * @throws IllegalArgumentException If the modifier is null
     */
    @Override
    public final double getDamage(final DamageType modifier) {
        Validate.notNull(modifier, "Cannot have a null modifier!");
        return this.modifiers.get(modifier);
    }

    @Override
    public final void setModifier(DamageType damageType, double damage) {
        Validate.notNull(damageType, "Cannot have a null DamageType!");
        if (!isApplicable(damageType)) {
            throw new UnsupportedOperationException(damageType + "is not applicable to this event!");
        }
        this.modifiers.put(damageType, damage);
    }

    /**
     * Checks if the provided damage type is applicable to this event
     * @param type The type of Damage to check
     * @return True if the damage type is applicable
     */
    @Override
    public final boolean isApplicable(DamageType type) {
        Validate.notNull(type, "Cannot have a null DamageType!");
        return this.modifiers.containsKey(type);
    }

    /**
     * Check if this damage was varied, if true, the default damage from
     * {@link #getOriginalDamage()} is that calculated varied damage. If not,
     * the damage is a precise number.
     *
     * @return true if KraftRPG was configured to have varied damage
     */
    @Override
    public final boolean isVaryingDamageEnabled() {
        return this.isVaryingDamageEnabled;
    }

    /**
     * Return the damage cause for this event. The cause is pulled from
     * {@link EntityDamageEvent} and further processed
     * from KraftRPG in the event a
     * {@link com.afterkraft.kraftrpg.api.skills.ISkill} dealt damage.
     *
     * @return the damage cause for this event.
     */
    @Override
    public final DamageCause getCause() {
        return this.cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    public final boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public final void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}
