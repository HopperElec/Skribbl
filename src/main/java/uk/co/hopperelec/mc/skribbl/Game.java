package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Game {
    String pre = "§4[§cSkribbl§4]§r§7 ";

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
        mapRemoval();}

    public static void nextDrawer() {}
}
