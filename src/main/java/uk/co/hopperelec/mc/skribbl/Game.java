package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Game {

    public static void mapRemoval(Player remover) {
        remover.sendMessage(Main.getPre()+"Removing map; this may take a while!");
        mapRemoval();
        remover.sendMessage(Main.getPre()+"Done!");}

    public static void mapRemoval() {
        for (int x = 4029; x <= 4096; x++) for (int y = 126; y <= 193; y++) for (int z = -33; z <= 34; z++) {
            Main.getWorld().getBlockAt(x,y,z).setType(Material.AIR);}}

    public static void playerLeave(Player player) {
        if (Main.getParty().size() <= 1) {
            end("there are no longer enough players");
        } else if (Main.getCurrentDrawer() == player) {
            nextDrawer();}}

    public static void end(String reason) {
        Main.setCurrentDrawer(null);
        Main.setStarted(false);
        Main.getParty().clear();
        Main.setCurrentWord(null);
        Main.getPoints().clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(new Location(Main.getWorld(),0,Main.getWorld().getHighestBlockYAt(0,0)+1,0));
            player.sendMessage(Main.getPre()+"The Skribbl game you were playing has now been ended because "+reason+". Apologies!");}
        mapRemoval();}

    public static void nextDrawer() {
        if (Main.getCurrentDrawer() == null) {
            Main.setCurrentDrawer(Main.getParty().get(Main.getRandom().nextInt(Main.getParty().size())));
        } else {
            Main.getCurrentDrawer().teleport(new Location(Main.getWorld(),4031,161,0));
            Main.getCurrentDrawer().setFlying(false);
            int index = Main.getParty().indexOf(Main.getCurrentDrawer());
            if (index == Main.getParty().size()-1) {
                Main.setCurrentDrawer(Main.getParty().get(0));
            } else {
                Main.setCurrentDrawer(Main.getParty().get(index+1));}}
        Main.getCurrentDrawer().teleport(new Location(Main.getWorld(),4064,160,0,-90,0));
        Main.getCurrentDrawer().setAllowFlight(true);
        Main.getCurrentDrawer().setFlying(true);
        for (Player player : Main.getParty()) {
            player.sendMessage(Main.getPre()+Main.getCurrentDrawer().getDisplayName()+" is now drawing!");}

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(),() -> {
            for (Player player : Main.getParty()) {
                player.sendMessage(Main.getPre()+Main.getCurrentDrawer().getDisplayName()+" ended the round! The word was...");}
            nextDrawer();
        },200L);}
}
