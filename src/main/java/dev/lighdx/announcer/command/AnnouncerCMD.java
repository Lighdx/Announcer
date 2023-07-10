package dev.lighdx.announcer.command;

import java.io.IOException;

import dev.lighdx.announcer.Announcer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class AnnouncerCMD extends Command {
    public AnnouncerCMD(String name, String permission, String ...aliases) {
        super (name,permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String [] args) {
        final Announcer announcerMain = Announcer.getInstance();
        final Configuration config = announcerMain.getConfig();

        if (!sender.hasPermission("announcer.reload")) {
            sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', config.getString("messages.permission"))));
        } else {
            try {
                announcerMain.reload();
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', config.getString("messages.reloaded"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
