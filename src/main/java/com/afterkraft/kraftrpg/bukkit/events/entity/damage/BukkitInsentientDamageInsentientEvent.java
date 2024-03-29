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

import java.util.Map;

import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.DamageType;
import com.afterkraft.kraftrpg.api.events.entity.damage.*;

/**
 * A traditional catch all damage event to be handled by KraftRPG for any
 * {@link Insentient} being damaged for any
 * reason. This specific event does always have an attacker, but it is handled
 * with the Insentient in the event there is an Insentient being damaged for
 * any reason (including instances of CommandBlocks or
 * {@link org.bukkit.entity.Entity}).
 * <p/>
 * This is the KraftRPG counterpart to
 * {@link EntityDamageByEntityEvent} after special
 * handling and calculations being made on the damage being dealt. It is
 * guaranteed that the {@link #getDefender()} will not be null, but it is not
 * guaranteed the linked {@link org.bukkit.entity.LivingEntity} is not a real
 * entity. It is also guaranteed the {@link #getAttacker()} is not null, but
 * it is also not guaranteed that the linked
 * {@link org.bukkit.entity.LivingEntity} is not a real entity.
 * <p/>
 * Therefore the methods provided from
 * {@link Insentient} should be the only
 * ones considered "safe" to use.
 * <p/>
 * This is written due to the possibility of customizing entities through
 * {@link com.afterkraft.kraftrpg.api.entity.EntityManager#addEntity(com.afterkraft.kraftrpg.api.entity.IEntity)}
 * that may not be considered {@link org.bukkit.entity.LivingEntity}.
 */
public class BukkitInsentientDamageInsentientEvent extends BukkitInsentientDamageEvent implements InsentientDamageInsentientEvent {
    private final Insentient attacker;

    public BukkitInsentientDamageInsentientEvent(final Insentient attacker, final Insentient defender, final EntityDamageByEntityEvent event, final Map<DamageType, Double> modifiers, final boolean isVarying) {
        super(defender, event, modifiers, isVarying);
        this.attacker = attacker;
    }

    /**
     * Return the attacking
     * {@link Insentient}. It is necessary
     * to understand that the attacking Insentient may not always link to a
     * {@link org.bukkit.entity.LivingEntity} and therefor must consider to
     * use only the methods provided from
     * {@link Insentient}
     *
     * @return the attacking Insentient being
     */
    @Override
    public Insentient getAttacker() {
        return this.attacker;
    }
}
