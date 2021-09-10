package de.marcely.bedwarsaddon.selectshopdesign;

import de.marcely.bedwars.api.game.shop.layout.ShopLayoutType;
import de.marcely.bedwarsaddon.selectshopdesign.utils.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class Config {

    private static final BedwarsAddonSelectShopDesign plugin = BedwarsAddonSelectShopDesign.getInstance();

    public void loadIcons(){
        ConfigurationSection sect = plugin.getConfig().getConfigurationSection("Layouts");

        assert sect != null;
        sect.getKeys(false).forEach(key -> {

            for (ShopLayoutType shopLayoutType:ShopLayoutType.values()){
                if(shopLayoutType.name().equalsIgnoreCase(key)){
                    String materialName = plugin.getConfig().getString("Layouts." + key + ".Display-Name");
                    assert materialName != null;
                    if(XMaterial.matchXMaterial(materialName).isPresent()) {
                        Material material = XMaterial.matchXMaterial(materialName).get().parseMaterial();
                        assert material != null;
                        //BedwarsAddonSelectShopDesign.DESIGN_ICON.put(shopLayoutType, new ItemStack(material));
                    }
                }
            }
        });
    }

	/*
	
	public static ConfigManager cm = BedwarsAddonSelectShopDesign.bedwarsAddon.getDataFolder();
	
	public static void load(){
		cm.load();
		
		// load enabled designs
		for(de.marcely.configmanager2.objects.Config c:cm.getConfigsWhichStartWith("design-enabled-")){
			final ShopDesignData design = BedwarsAPI.getShopDesign(c.getName().replaceFirst("design-enabled-", ""));
			
			if(design != null && Util.isBoolean(c.getValue()) && Boolean.valueOf(c.getValue()) == false)
				BedwarsAddonSelectShopDesign.DESIGN_DISABLED.add(design);
		}
		
		// load icons
		for(de.marcely.configmanager2.objects.Config c:cm.getConfigsWhichStartWith("design-icon-")){
			final ShopDesignData design = BedwarsAPI.getShopDesign(c.getName().replaceFirst("design-icon-", ""));
			final ItemStack is = Util.getItemItemstackByName(c.getValue());
			
			if(design != null && is != null)
				BedwarsAddonSelectShopDesign.DESIGN_ICON.put(design, is);
		}
		
		// load messages
		for(de.marcely.configmanager2.objects.Config c:cm.getConfigsWhichStartWith("message-")){
			final Message msg = Message.getByName(c.getName().replaceFirst("message-", ""));
			
			if(msg != null)
				msg.setCustomMessage(Language.stringToChatColor(c.getValue()));
		}
		
		cm.clear();
	}
	
	public static void save(){
		cm.clear();
		
		cm.addComment("Enable/disable shop designs");
		
		// write enabled/disabled designs
		for(ShopDesignData data:BedwarsAPI.getShopDesigns()){
			if(!data.getType().isBeta())
				cm.addConfig("design-enabled-" + data.getName(), !BedwarsAddonSelectShopDesign.DESIGN_DISABLED.contains(data));
		}
		
		cm.addEmptyLine();
		cm.addComment("Change the icon of the shop designs");
		
		// write icons
		for(ShopDesignData data:BedwarsAPI.getShopDesigns()){
			if(!data.getType().isBeta()){
				cm.addConfig("design-icon-" + data.getName(), 
						BedwarsAddonSelectShopDesign.DESIGN_ICON.containsKey(data) ?
								Util.itemstackToConfigName(BedwarsAddonSelectShopDesign.DESIGN_ICON.get(data)) : Util.itemstackToConfigName(BedwarsAddonSelectShopDesign.DESIGN_ICON_MISSING));
			}
		}
		
		cm.addEmptyLine();
		cm.addComment("Change messages");
		
		// write messages
		for(Message msg:Message.values())
			cm.addConfig("message-" + msg.name(), Language.chatColorToString(msg.getMessage()));
		
		cm.save();
	}

	 */
}
