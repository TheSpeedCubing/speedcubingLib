package speedcubing.lib.bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import speedcubing.lib.speedcubingLib;

public class BungeePluginMessage {
    public static void switchServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        sendPluginMessage(player, "BungeeCord", out.toByteArray());
    }

    public static void sendRawMessage(Player player, String target, String text) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("MessageRaw");
        out.writeUTF(target);
        out.writeUTF(text);
        sendPluginMessage(player, "BungeeCord", out.toByteArray());
    }

    public static void msgPlayerCount(Player player, String server) {
        if (server == null)
            server = "ALL";
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        sendPluginMessage(player, "BungeeCord", out.toByteArray());
    }

    public static void msgPlayerList(Player player, String server) {
        if (server == null)
            server = "ALL";
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerList");
        out.writeUTF(server);
        sendPluginMessage(player, "BungeeCord", out.toByteArray());
    }

    public static void sendPluginMessage(Player player, String channel, byte[] out) {
        if (player == null)
            Bukkit.getServer().sendPluginMessage(speedcubingLib.getPlugin(speedcubingLib.class), channel, out);
        else
            player.sendPluginMessage(speedcubingLib.getPlugin(speedcubingLib.class), channel, out);
    }
}
