package speedcubing.lib.bukkit.listeners;

import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import speedcubing.lib.bukkit.SideBar;
import speedcubing.lib.bukkit.entity.Hologram;
import speedcubing.lib.bukkit.entity.NPC;
import speedcubing.lib.speedcubingLib;

public class PlayerListener implements Listener {
    @EventHandler
    public void PlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
        if (speedcubingLib.is1_8_8) {
            Player player = e.getPlayer();
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            String from = e.getFrom().getName();
            String to = player.getWorld().getName();
            for (NPC npc : NPC.all) {
                if (npc.world.contains(from))
                    npc.listener.remove(connection);
                else if (npc.world.contains(to))
                    addNPC(connection, npc);
            }
            for (Hologram hologram : Hologram.all) {
                if (hologram.world.contains(from))
                    hologram.listener.remove(connection);
                else if (hologram.world.contains(to))
                    addHologram(connection, hologram);
            }
        }
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        if (speedcubingLib.is1_8_8) {
            SideBar.perPlayerSidebar.remove(e.getPlayer());
            PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection;
            NPC.all.forEach(a -> a.listener.remove(connection));
            Hologram.all.forEach(a -> a.listener.remove(connection));
        }
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        if (speedcubingLib.is1_8_8) {
            Player player = e.getPlayer();
            String world = player.getWorld().getName();
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            for (NPC npc : NPC.all) {
                if (npc.world.contains(world))
                    addNPC(connection, npc);
            }
            for (Hologram hologram : Hologram.all) {
                if (hologram.world.contains(world))
                    addHologram(connection, hologram);
            }
        }
    }

    private void addHologram(PlayerConnection connection, Hologram hologram) {
        if (hologram.autoListen)
            hologram.listener.add(connection);
        if (hologram.autoSpawn)
            hologram.setListenerValues(connection).spawn().rollBackListenerValues();
    }

    private void addNPC(PlayerConnection connection, NPC npc) {
        if (npc.autoListen)
            npc.listener.add(connection);
        if (npc.autoSpawn)
            npc.setListenerValues(connection).spawn().rollBackListenerValues();
    }
}
