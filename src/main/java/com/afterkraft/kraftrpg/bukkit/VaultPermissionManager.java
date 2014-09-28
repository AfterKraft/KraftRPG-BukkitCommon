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

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.util.PermissionManager;
import com.afterkraft.kraftrpg.common.CommonWorld;

public class VaultPermissionManager implements PermissionManager {

    private static VaultPermissionManager instance;
    private Permission permissionProvider;
    private RPGPlugin plugin;

    public VaultPermissionManager(RPGPlugin plugin) {
        if (instance != null) {
            throw new IllegalStateException("VaultPermissionsManager has already been initialized before!!");
        }
        this.plugin = plugin;
        instance = this;
    }

    @Override
    public boolean isOp(IEntity entity) {
        Object bukkitEntity = entity.getLinkedEntity();
        if (bukkitEntity instanceof CommandSender) {
            CommandSender sender = (CommandSender) bukkitEntity;
            return sender.isOp();
        }
        return false;
    }

    @Override
    public boolean hasPermission(IEntity entity, String permission) {
        return entity.getLinkedEntity() instanceof CommandSender && this.permissionProvider.has((CommandSender) entity.getLinkedEntity(), permission);
    }

    @Override
    public boolean hasWorldPermission(IEntity entity, CommonWorld world, String permission) {
        return entity.getLinkedEntity() instanceof Player && this.permissionProvider.playerHas(world.getName(), (Player) entity.getLinkedEntity(), permission);
    }

    @Override
    public boolean hasWorldPermission(IEntity entity, String worldName, String permission) {
        return entity.getLinkedEntity() instanceof Player && this.permissionProvider.playerHas(worldName, (Player) entity.getLinkedEntity(), permission);
    }

    @Override
    public void addGlobalPermission(IEntity entity, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerAdd((Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void addWorldPermission(IEntity entity, CommonWorld world, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerAdd(world.getName(), (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void addWorldPermission(IEntity entity, String worldName, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerAdd(worldName, (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void addTransientGlobalPermission(IEntity entity, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerAddTransient((Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void addTransientWorldPermission(IEntity entity, CommonWorld world, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerAddTransient(world.getName(), (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void addTransientWorldPermission(IEntity entity, String worldName, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerAddTransient(worldName, (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void removeGlobalPermission(IEntity entity, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerRemove((Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void removeWorldPermission(IEntity entity, CommonWorld world, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerRemove(world.getName(), (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void removeWorldPermission(IEntity entity, String worldName, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerRemove(worldName, (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void removeTransientGlobalPermission(IEntity entity, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerRemoveTransient((Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void removeTransientWorldPermission(IEntity entity, CommonWorld world, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerRemoveTransient(world.getName(), (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void removeTransientWorldPermission(IEntity entity, String worldName, String permission) {
        if (entity.getLinkedEntity() instanceof Player) {
            this.permissionProvider.playerRemoveTransient(worldName, (Player) entity.getLinkedEntity(), permission);
        }
    }

    @Override
    public void initialize() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
            this.permissionProvider = rsp.getProvider();
        }
    }

    @Override
    public void shutdown() {
        this.permissionProvider = null;
        instance = null;
    }


}
