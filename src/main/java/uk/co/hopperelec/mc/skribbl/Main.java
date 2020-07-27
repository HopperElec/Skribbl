package uk.co.hopperelec.mc.skribbl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    List<String> cmds = new ArrayList<>();
    public static Plugin plugin = null;
    static boolean started = false;
    static String op = "HopperElecYT";
    static String worldname = "world";
    static List<Player> party = new ArrayList<>();
    static List<Player> bans = new ArrayList<>();
    public static boolean getStarted() {return started;}
    public static String getOp() {return op;}
    public static String getWorldname() {return worldname;}
    public static List<Player> getParty() {return party;}
    public static List<Player> getBans() {return bans;}
    public static Plugin getPlugin() {return plugin;}
    SkribblCommands skribblCmds = new SkribblCommands();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        cmds.add("start"); cmds.add("kick");cmds.add("ban");cmds.add("unban");cmds.add("join");cmds.add("leave");

        World world = Bukkit.getWorld(worldname);
        if (world == null) {
            System.out.println("Cannot find world by name "+worldname+"!");
            this.setEnabled(false); return;}
        for (int x = 4029; x <= 4096; x++) for (int y = 126; y <= 193; y++) for (int z = -33; z <= 34; z++) {
            world.getBlockAt(x,y,z).setType(Material.AIR);}
        for (int a = 1; a <= 64; a++) for (int b = 1; b <= 64; b++) {
            world.getBlockAt(4095,127+a,b-32).setType(Material.WHITE_WOOL);
            world.getBlockAt(4030,127+a,b-32).setType(Material.SEA_LANTERN);
            world.getBlockAt(4030+a,127,b-32).setType(Material.SEA_LANTERN);
            world.getBlockAt(4030+a,192,b-32).setType(Material.WHITE_STAINED_GLASS);
            world.getBlockAt(4030+a,127+b,-32).setType(Material.SEA_LANTERN);
            world.getBlockAt(4030+a,127+b,33).setType(Material.SEA_LANTERN);
            world.getBlockAt(4031,160,b-32).setType(Material.BARRIER);}
        for (int y = 161; y <= 191; y++) for (int z = -31; z <= 32; z++) {
            world.getBlockAt(4032,y,z).setType(Material.BARRIER);}
    }

    @Override
    public void onDisable() {}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
        if (author instanceof Player) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                author.sendMessage("§e§l/skribbl §r§4- §eCore command for HopperElecYT's Skribbl plugin");
                author.sendMessage("§e§l/skribbl help §r§4- §eDisplays this page");
                if (((Player) author).getDisplayName().equalsIgnoreCase(op)) {
                    author.sendMessage("§e§l/skribbl start §r§4- §eStarts a Skribbl game ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl kick §r§4- §eKicks a player from an ongoing Skribbl game but can rejoin ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl ban §r§4- §eBans a player from taking part in Skribbl games until server restart ("+op+" only!)");
                    author.sendMessage("§e§l/skribbl unban §r§4- §eUnbans a player from taking part in Skribbl games in this session ("+op+" only!)");}
                author.sendMessage("§e§l/skribbl join §r§4- §eJoins the list of players wanting to take part in the next Skribbl game");
                author.sendMessage("§e§l/skribbl leave §r§4- §eLeaves the list of players wanting to take part in the next Skribbl game");
                author.sendMessage("§0§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            } else {
                boolean found = false;
                for (String arg : cmds) {
                    if (args[0].equalsIgnoreCase(arg)) {
                        found = true; break;}}
                if (found) {
                    return skribblCmds.command((Player) author,args);
                } else {author.sendMessage("Invalid Skribbl command!"); return false;}}
        } else {return false;}
        return true;
    }
}
