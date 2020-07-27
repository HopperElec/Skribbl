package uk.co.hopperelec.mc.skribbl;

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
    static String op = "HopperElecYT";
    static List<Player> party = new ArrayList<>();
    static List<Player> bans = new ArrayList<>();
    public static String getOp() {return op;}
    public static List<Player> getParty() {return party;}
    public static List<Player> getBans() {return bans;}
    public static Plugin getPlugin() {return plugin;}
    SkribblCommands skribblCmds = new SkribblCommands();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        cmds.add("start"); cmds.add("kick");cmds.add("ban");cmds.add("unban");cmds.add("join");cmds.add("leave");
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
