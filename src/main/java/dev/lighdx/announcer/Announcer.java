package dev.lighdx.announcer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dev.lighdx.announcer.broadcast.Broadcast;
import dev.lighdx.announcer.command.AnnouncerCMD;
import dev.lighdx.announcer.config.ConfigBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class Announcer extends Plugin {
    private Configuration config;

    public Configuration getConfig() {
        return config;
    }

    private void unload() {
        final ProxyServer proxyServer = this.getProxy();

        proxyServer.getPluginManager().unregisterCommands(this);
        proxyServer.getScheduler().cancel(this);
    }

    private void load() throws IOException {
        final ProxyServer proxyServer = this.getProxy();
        final ConfigBungee bungeeConfiguration = new ConfigBungee(this, "config.yml");

        bungeeConfiguration.saveDefaults();
        config = bungeeConfiguration.load();

        proxyServer.getPluginManager().registerCommand(this, new AnnouncerCMD("announcer", "announcer.reload", ""));

        final long time = config.getInt("Time");
        final List<String> announcements = config.getStringList("announcements");

        proxyServer.getScheduler().schedule(this, new Broadcast(announcements), time, time, TimeUnit.SECONDS);
    }

    public void reload() throws IOException {
        unload();
        load();
    }

    @Override
    public void onEnable() {
        Announcer.instance = this;

        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Announcer instance;

    public static Announcer getInstance() {
        return Announcer.instance;
    }
}