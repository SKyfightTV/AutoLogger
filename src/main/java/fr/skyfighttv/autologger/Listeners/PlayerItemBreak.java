package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;

import java.text.SimpleDateFormat;

public class PlayerItemBreak implements Listener {
    @EventHandler
    private void onPlayerItemBreak(PlayerItemBreakEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerItemBreak.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%item%", event.getBrokenItem().getType().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerItemBreak"), text);

        Main.sendDebug("PlayerItemBreak event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerItemBreak"))
            Main.getInstance().webhookClients.get("PlayerItemBreak").send(text);
    }
}
