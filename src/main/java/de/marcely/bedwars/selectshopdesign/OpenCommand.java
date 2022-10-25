package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.command.CommandHandler;
import de.marcely.bedwars.api.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OpenCommand implements CommandHandler {

    private final SelectShopDesignPlugin plugin;

    public OpenCommand(SelectShopDesignPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onRegister(SubCommand subCommand) { }

    @Override
    public void onFire(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player)
            SelectionGUI.open((Player) commandSender);
    }

    @Override
    public @Nullable List<String> onAutocomplete(CommandSender commandSender, String[] strings) {
        return null;
    }
}
