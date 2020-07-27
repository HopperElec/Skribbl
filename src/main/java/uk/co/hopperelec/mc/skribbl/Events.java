package uk.co.hopperelec.mc.skribbl;

import org.bukkit.event.EventHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
    String pre = "§4[§cSkribbl§4]§r§7 ";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Main.getBans().contains(event.getPlayer())) {
            event.getPlayer().sendMessage(pre+"You're currently banned from joining Skribbl games!");}
        if (Main.getReady()) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            event.getPlayer().teleport(new Location(Main.getWorld(),4064,160,0));
            if (!Main.getBans().contains(event.getPlayer())) {
                event.getPlayer().sendMessage(pre+"Use §c/skribbl join §7to join the party ready to start!");}}}

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Main.getParty().remove(event.getPlayer());}

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Main.getReady() || Main.getStarted()) {
            Location loc = event.getTo();
            if (loc == null) {return;}
            if (event.getTo().getX() >= 4096 || event.getTo().getX() <= 4030) {
                loc.setX(event.getFrom().getX());}
            if (event.getTo().getY() >= 192 || event.getTo().getY() <= 128) {
                loc.setY(event.getFrom().getY());}
            if (event.getTo().getZ() >= 33 || event.getTo().getZ() <= -32) {
                loc.setZ(event.getFrom().getZ());}
        }
    }
}