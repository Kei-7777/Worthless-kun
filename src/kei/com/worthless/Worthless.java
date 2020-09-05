package kei.com.worthless;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;
import skinsrestorer.shared.exception.SkinRequestException;

public class Worthless extends JavaPlugin implements Listener {

    //Black Skin
    //https://ja.namemc.com/skin/3bb7b782e4955b00

    static SkinsRestorer skinsRestorer;
    static SkinsRestorerBukkitAPI skinsRestorerBukkitAPI;
    static String skin = "steve";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        skin = getConfig().getString("skin");
        skinsRestorer = JavaPlugin.getPlugin(SkinsRestorer.class);
        skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();
        Bukkit.getPluginCommand("worthless").setExecutor(new MainCommand(this));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(MainCommand.players.contains(e.getPlayer().getName())){
            try {
                Worthless.skinsRestorerBukkitAPI.setSkin(e.getPlayer().getName(), Worthless.skin);
                Worthless.skinsRestorerBukkitAPI.applySkin(e.getPlayer());
            } catch (SkinRequestException ex) {
                ex.printStackTrace();
            }
        } else {
            skinsRestorerBukkitAPI.removeSkin(e.getPlayer().getName());
            skinsRestorerBukkitAPI.applySkin(e.getPlayer());
        }
    }
}
