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
package com.afterkraft.kraftrpg.bukkit.events.skills;

import org.apache.commons.lang.Validate;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.events.skills.SkillCastEvent;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.bukkit.BukkitItemStack;
import com.afterkraft.kraftrpg.common.inventory.CommonItemStack;


public class BukkitSkillCastEvent extends BukkitInsentientEvent implements SkillCastEvent, Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final ISkill skill;
    private double manaCost;
    private double healthCost;
    private double exhaustionCost;
    private ItemStack reagent;
    private boolean cancelled;


    public BukkitSkillCastEvent(SkillCaster caster, ISkill skill, double manaCost, double healthCost, double exhaustionCost, ItemStack reagentCost) {
        super(caster);
        Validate.notNull(skill, "Cannot have a null skill!");
        this.skill = skill;
        this.manaCost = manaCost;
        this.healthCost = healthCost;
        this.exhaustionCost = exhaustionCost;
        if (reagentCost != null)
            this.reagent = new ItemStack(reagentCost);
        else
            this.reagent = null;
        this.cancelled = false;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public SkillCaster getCaster() {
        return (SkillCaster) this.getEntity();
    }

    @Override
    public ISkill getSkill() {
        return this.skill;
    }

    @Override
    public double getManaCost() {
        return this.manaCost;
    }

    @Override
    public void setManaCost(double manaCost) {
        this.manaCost = manaCost;
    }

    @Override
    public double getHealthCost() {
        return this.healthCost;
    }

    @Override
    public void setHealthCost(double healthCost) {
        this.healthCost = healthCost;
    }

    @Override
    public double getStaminaCostAsExhaustion() {
        return this.exhaustionCost;
    }

    @Override
    public void setStaminaCostAsExhaustion(double staminaCost) {
        this.exhaustionCost = staminaCost;
    }

    @Override
    public double getStaminaCostAsFoodBar() {
        return this.exhaustionCost / 4;
    }

    @Override
    public void setStaminaCostAsFoodBar(double staminaCost) {
        this.exhaustionCost = staminaCost * 4;
    }

    @Override
    public CommonItemStack getItemCost() {
        return this.reagent == null ? null : new BukkitItemStack(this.reagent.clone());
    }

    public void setItemCost(ItemStack item) {
        this.reagent = new ItemStack(item);
    }

    @Override
    public void setItemCost(CommonItemStack item) {
        if (item instanceof BukkitItemStack) {
            this.reagent = new ItemStack(((BukkitItemStack) item).getItemStack());
        }
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
