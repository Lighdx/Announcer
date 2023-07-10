package dev.lighdx.announcer.broadcast;

import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Broadcast implements Runnable {
    private final List<String> announcements;
    private int index = 0;

    public Broadcast (final List<String> announcements) {
        this.announcements = announcements;
    }

    public void broadcast(final BaseComponent[] message) {
        for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            player.sendMessage(message);
        }
    }

    @Override
    public void run() {
        if (index >= announcements.size()) {
            index = 0;
        }

        if (index < announcements.size()) {
            broadcast(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', announcements.get(index))));
        }

        index++;
    }
}