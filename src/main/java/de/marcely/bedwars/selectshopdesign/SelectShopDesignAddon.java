package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.BedwarsAddon;
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
}
