package uk.co.hopperelec.mc.skribbl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
    String pre = "§4[§cSkribbl§4]§r§7 ";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(pre+"Use §c/skribbl join §7to join the party ready to start (if there is a game about to start, off course!)");}

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Main.getParty().remove(event.getPlayer());}
}