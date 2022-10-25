package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.BedwarsAddon;
import de.marcely.bedwars.api.command.CommandHandler;
import de.marcely.bedwars.api.command.CommandsCollection;
import de.marcely.bedwars.api.command.SubCommand;
import de.marcely.bedwars.api.message.DefaultMessageMappings;
import de.marcely.bedwars.api.message.MessageAPI;

public class SelectShopDesignAddon extends BedwarsAddon {

    private final SelectShopDesignPlugin plugin;

    public SelectShopDesignAddon(SelectShopDesignPlugin plugin) {
        super(plugin);

        this.plugin = plugin;
    }

    @Override
    public String getName(){
        return "SelectShopDesignAddon";
    }

    void registerMessageMappings(){
        try{
            MessageAPI.get().registerDefaultMappings(
                    DefaultMessageMappings.loadInternalYAML(this.plugin, this.plugin.getResource("messages.yml")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void registerCommands(){
        addCommand(
                getCommandsRoot(),
                "selectshopdesign",
                false,
                "",
                new OpenCommand(plugin),
                "selectshop", "ssd");
    }

    private void addCommand(CommandsCollection parent, String name, boolean onlyPlayers, String usage, CommandHandler handler, String... aliases){
        final SubCommand cmd = parent.addCommand(name);

        if(cmd == null)
            return;

        cmd.setOnlyForPlayers(onlyPlayers);
        cmd.setUsage(usage);
        cmd.setHandler(handler);
        cmd.setAliases(aliases);
    }
}
