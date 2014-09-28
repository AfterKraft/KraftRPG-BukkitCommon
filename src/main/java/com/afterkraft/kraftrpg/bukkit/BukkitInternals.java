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

import org.bukkit.Bukkit;

import com.afterkraft.kraftrpg.api.RPGCommon;
import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.handler.ServerInternals;

public abstract class BukkitInternals extends ServerInternals {

    private static BukkitInternals internals;

    protected BukkitInternals(RPGPlugin plugin, ServerType type) {
        super(plugin, type);
    }

    public static BukkitInternals getInterface() {
        if (internals == null) {
            // Get minecraft version
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);
            if (version.equals("craftbukkit")) {
                version = "pre";
            }
            String serverString = Bukkit.getServer().getVersion().split("-")[1].toLowerCase();
            if (serverString.equalsIgnoreCase("bukkit") || serverString.equalsIgnoreCase("craftbukkit")) {
                serverType = ServerType.BUKKIT;
            } else if (serverString.equalsIgnoreCase("spigot")) {
                serverType = ServerType.SPIGOT;
            } else if (serverString.equalsIgnoreCase("tweakkit")) {
                serverType = ServerType.TWEAKKIT;
            }
            if (serverType == null) {
                Bukkit.getLogger().info("KraftRPG could not detect your server mod type.");
                Bukkit.getLogger().info("It detected " + serverString + " which isn't known to KraftRPG.");
                Bukkit.getLogger().info("But don't worry! We're falling back on Bukkit compatibility");
                serverType = ServerType.BUKKIT;
            }
            try {
                Class<?> clazz = Class.forName("com.afterkraft.kraftrpg.compat." + version + ".RPGHandler");
                if (BukkitInternals.class.isAssignableFrom(clazz)) {
                    internals = (BukkitInternals) clazz.getConstructor(RPGPlugin.class, ServerType.class).newInstance(RPGCommon.getPlugin(), serverType);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return internals;
    }


}
