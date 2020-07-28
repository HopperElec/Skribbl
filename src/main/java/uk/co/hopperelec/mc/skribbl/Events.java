package uk.co.hopperelec.mc.skribbl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Main.getBans().contains(event.getPlayer())) {
            event.getPlayer().sendMessage(Main.getPre()+"You're currently banned from joining Skribbl games!");}
        if (Main.getReady()) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            event.getPlayer().teleport(new Location(Main.getWorld(),4064,160,0));
            if (!Main.getBans().contains(event.getPlayer())) {
                event.getPlayer().sendMessage(Main.getPre()+"Use §c/skribbl join §7to join the party ready to start!");}}}

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Main.getParty().remove(event.getPlayer());
        if (Main.getStarted()) {Game.playerLeave(event.getPlayer());}}

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Main.getReady() || Main.getStarted()) {
            Location loc = event.getTo();
            if (loc == null) {return;}
            if (event.getTo().getX() >= 4096 || event.getTo().getX() <= 4030) {
                loc.setX(event.getFrom().getX());}
            if (event.getTo().getY() >= 192 || event.getTo().getY() <= 128) {
                loc.setY(event.getFrom().getY());}
            if (event.getTo().getZ() >= 34 || event.getTo().getZ() <= -32) {
                loc.setZ(event.getFrom().getZ());}}}

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Main.getStarted() && Main.getParty().contains(player)) {
                event.setCancelled(true);}}}
}