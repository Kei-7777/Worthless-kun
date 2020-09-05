package kei.com.worthless;

import net.minecraft.server.v1_15_R1.CommandListenerWrapper;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.ICommandListener;
import net.minecraft.server.v1_15_R1.PlayerSelector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_15_R1.command.VanillaCommandWrapper;
import org.bukkit.entity.Player;
import skinsrestorer.shared.exception.SkinRequestException;

import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor {

    static List<String> players;

    public MainCommand(Worthless worthless) {
        players = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Require OP.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "/" + cmd.getName() + " <player>");
            return true;
        }

        if (Bukkit.getPlayer(args[0]) == null && !players.contains(args[0])) {
            sender.sendMessage(ChatColor.RED + args[0] + " is not online.");
            return true;
        } else if (Bukkit.getPlayer(args[0]) == null && players.contains(args[0])) {
            players.remove(args[0]);
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if (players.contains(p.getName())) {
            unset(p);
            players.remove(args[0]);
        } else {
            set(p);
            players.add(args[0]);
        }
        return true;
    }

    void set(Player p) {
        try {
            Worthless.skinsRestorerBukkitAPI.setSkin(p.getName(), Worthless.skin);
            Worthless.skinsRestorerBukkitAPI.applySkin(p);
        } catch (SkinRequestException e) {
            e.printStackTrace();
        }
        Bukkit.broadcastMessage(ChatColor.AQUA + p.getName() + "の写す価値がなくなりました。");
    }

    void unset(Player p) {
        Worthless.skinsRestorerBukkitAPI.applySkin(p, Worthless.skinsRestorerBukkitAPI.getSkinData(p.getName()));
        Bukkit.broadcastMessage(ChatColor.AQUA + p.getName() + "の写す価値が戻りました。");
    }
}
