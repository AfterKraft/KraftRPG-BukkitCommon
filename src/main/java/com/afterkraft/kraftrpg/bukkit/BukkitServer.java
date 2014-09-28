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
package com.afterkraft.kraftrpg.bukkit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;

import com.afterkraft.kraftrpg.common.entity.CommonPlayer;
import com.afterkraft.kraftrpg.common.CommonServer;

public final class BukkitServer implements CommonServer<Material> {

    private static BukkitServer instance;

    public BukkitServer() {
        Preconditions.checkArgument(instance == null, "BukkitServer has already been initialized!");
        instance = this;
    }

    public static BukkitServer getInstance() {
        return instance;
    }

    public Server getBukkitServer() {
        return Bukkit.getServer();
    }

    @Override
    public Collection<CommonPlayer> getOnlinePlayers() {
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public CommonPlayer getPlayerByName(String name) {
        return new BukkitPlayer(Bukkit.getPlayer(name));
    }

    @Override
    public Set<BukkitVoxelType> getTransparentBlocks() {
        Set<BukkitVoxelType> set = new HashSet<BukkitVoxelType>();
        for (Material mat : Material.values()) {
            if (mat.isTransparent()) {
                set.add(new BukkitVoxelType(mat));
            }
        }
        return set;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Set<Byte> getTransparentBlockIDs() {
        Set<Byte> set = new HashSet<Byte>();
        for (Material mat : Material.values()) {
            if (mat.isTransparent()) {
                set.add((byte) mat.getId());
            }
        }
        return set;
    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger("Minecraft");
    }
}
