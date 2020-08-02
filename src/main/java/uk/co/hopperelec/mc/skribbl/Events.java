package uk.co.hopperelec.mc.skribbl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
    Main main;
    public Events(Main mainConstruct) {main = mainConstruct;}

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (main.getBans().contains(event.getPlayer())) {
            event.getPlayer().sendMessage(main.getPre()+"You're currently banned from joining Skribbl games!");}
        if (main.getReady()) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            event.getPlayer().teleport(new Location(main.getWorld(),4064,160,0));
            if (!main.getBans().contains(event.getPlayer())) {
                event.getPlayer().sendMessage(main.getPre()+"Use §c/skribbl join §7to join the party ready to start!");}
        } else {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.getPlayer().teleport(new Location(main.getWorld(),0,main.getWorld().getHighestBlockYAt(0,0),0));}}

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        main.getParty().remove(event.getPlayer());
        if (main.getStarted()) {main.getGame().playerLeave(event.getPlayer());}}

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (main.getReady() || main.getStarted()) {
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
            if (main.getStarted() && main.getParty().contains(player)) {
                event.setCancelled(true);}}}

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer() == main.getCurrentDrawer()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("You can't chat when you're the drawer!");
        } else if (main.getCorrectGuessers().contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("You can't chat when you've already guessed the word!");
        } else if (event.getMessage().equalsIgnoreCase(main.getCurrentWord())) {
            event.setCancelled(true);
            main.getCorrectGuessers().add(event.getPlayer());
            main.getPoints().put(event.getPlayer(),main.getPoints().get(event.getPlayer())+(main.getParty().size()-main.getCorrectGuessers().size())*(int)main.getRoundLength());
            main.getPoints().put(main.getCurrentDrawer(),main.getPoints().get(main.getCurrentDrawer())+(int)(main.getRoundLength()*50000000-(System.nanoTime()-main.getDrawingStartTime()))/5000000/main.getParty().size());
            for (Player player : main.getParty()) {
                player.sendMessage(main.getPre()+event.getPlayer().getDisplayName()+" has guessed the word!");}}}
}
