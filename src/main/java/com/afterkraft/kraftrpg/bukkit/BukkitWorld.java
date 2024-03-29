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

import java.lang.ref.WeakReference;

import org.bukkit.World;

import com.afterkraft.kraftrpg.common.voxel.CommonVoxel;
import com.afterkraft.kraftrpg.common.CommonWorld;

/**
 * Created by gabizou on 9/16/14.
 */
public class BukkitWorld extends CommonWorld {
    private WeakReference<World> worldReference;

    public BukkitWorld(World world) {
        this.worldReference = new WeakReference<World>(world);
    }

    public World getBukkitWorld() {
        return this.worldReference.get();
    }

    @Override
    public String getName() {
        return getBukkitWorld().getName();
    }

    @Override
    public CommonVoxel getVoxelAt(int x, int y, int z) {
        return new BukkitVoxel(getBukkitWorld().getBlockAt(x, y, z));
    }

    @Override
    public boolean isPVP() {
        return false;
    }
}
