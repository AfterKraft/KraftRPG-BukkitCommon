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

import static com.afterkraft.kraftrpg.bukkit.BukkitCommon.getBlockLocation;
import static com.afterkraft.kraftrpg.bukkit.BukkitCommon.getBukkitBlockType;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.afterkraft.kraftrpg.common.voxel.CommonVoxelType;
import com.afterkraft.kraftrpg.common.voxel.CommonVoxel;

public class BukkitVoxel extends CommonVoxel<Material> {

    private WeakReference<Block> blockReference;

    protected BukkitVoxel(Block block) {
        super(getBlockLocation(block), getBukkitBlockType(block));
        this.blockReference = new WeakReference<Block>(block);
    }

    @Override
    public void setBlockType(CommonVoxelType<Material> material) {
        if (this.blockReference.get() != null) {
            Block block = this.blockReference.get();
            assert block != null;
            block.setType(material.getValue());
        }
    }
}
