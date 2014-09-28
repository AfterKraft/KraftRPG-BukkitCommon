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
package com.afterkraft.kraftrpg.bukkit.events;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.events.DamageType;
import com.afterkraft.kraftrpg.api.events.EventHandler;
import com.afterkraft.kraftrpg.api.listeners.DamageCause;
import com.afterkraft.kraftrpg.api.listeners.DamageCause.VanillaDamageCause;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.common.events.CommonEvent;

public class BukkitEventTranslator extends EventHandler {

    public static DamageCause getDamageCause(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            return VanillaDamageCause.BLOCK_EXPLOSION;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.CONTACT ) {
            return VanillaDamageCause.CONTACT;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            return VanillaDamageCause.CUSTOM;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            return VanillaDamageCause.DROWNING;
        }else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return VanillaDamageCause.ENTITY_ATTACK;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            return VanillaDamageCause.ENTITY_EXPLOSION;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            return VanillaDamageCause.FALL;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
            return VanillaDamageCause.FALLING_BLOCK;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
            return VanillaDamageCause.FIRE;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            return VanillaDamageCause.FIRE_TICK;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            return VanillaDamageCause.LAVA;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            return VanillaDamageCause.LIGHTNING;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            return VanillaDamageCause.MAGIC;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.MELTING) {
            return VanillaDamageCause.MELTING;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.POISON) {
            return VanillaDamageCause.POISON;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            return VanillaDamageCause.PROJECTILE;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            return VanillaDamageCause.STARVATION;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            return VanillaDamageCause.SUFFOCATION;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
            return VanillaDamageCause.SUICIDE;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
            return VanillaDamageCause.THORNS;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            return VanillaDamageCause.VOID;
        } else if (event.getCause() == EntityDamageEvent.DamageCause.WITHER) {
            return VanillaDamageCause.WITHER;
        } else {
            return VanillaDamageCause.CUSTOM;
        }
    }

    @Override
    public boolean damageCheck(Insentient attacker, IEntity damager) {
        return false;
    }

    @Override
    public void knockBack(Insentient target, Insentient attacker, double damage) {

    }

    @Override
    public boolean healEntity(Insentient being, double tickHealth, Skill skill, Insentient applier) {
        return false;
    }

    @Override
    public void callEvent(CommonEvent event) {
        if (event instanceof Event) {
            Bukkit.getPluginManager().callEvent((Event) event);
        }
    }

    @Override
    public boolean damageEntity(Insentient target, Insentient attacker, ISkill skill, double damage, DamageCause cause, boolean knockback) {
        return damageEntity(target, attacker, skill, ImmutableMap.of(DamageType.PHYSICAL, damage), cause, knockback, false);
    }

    @Override
    public boolean damageEntity(Insentient target, Insentient attacker, ISkill skill, Map<DamageType, Double> modifiers, DamageCause cause, boolean knockback) {
        return false;
    }

    @Override
    public boolean damageEntity(Insentient target, Insentient attacker, ISkill skill, Map<DamageType, Double> modifiers, DamageCause cause, boolean knockback, boolean ignoreDamageCheck) {
        return false;
    }

    @Override
    public void setArrowDamage(IEntity arrow, double damage) {

    }
}
