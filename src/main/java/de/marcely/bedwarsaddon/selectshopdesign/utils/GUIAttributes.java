package de.marcely.bedwarsaddon.selectshopdesign.utils;

import de.marcely.bedwars.api.game.shop.layout.ShopLayoutType;
import de.marcely.bedwarsaddon.selectshopdesign.BedwarsAddonSelectShopDesign;
import de.marcely.bedwarsaddon.selectshopdesign.utils.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUIAttributes {

    private static String key;

    private static final BedwarsAddonSelectShopDesign plugin = BedwarsAddonSelectShopDesign.getInstance();

    public GUIAttributes(ShopLayoutType layoutType){
        ConfigurationSection sect = plugin.getConfig().getConfigurationSection("Layouts");
        assert sect != null;
        for(String key: sect.getKeys(false)){
            if(layoutType.name().equalsIgnoreCase(key)){
                GUIAttributes.key = key;
                break;
            }
        }
    }

    public ItemStack getIconMaterial(){
        String materialName = plugin.getConfig().getString("Layouts." + GUIAttributes.key + ".Material");
        assert materialName != null;
        if(XMaterial.matchXMaterial(materialName).isPresent()){
            return new ItemStack(Objects.requireNonNull(XMaterial.matchXMaterial(materialName).get().parseMaterial()));
        }
        return BedwarsAddonSelectShopDesign.DESIGN_ICON_MISSING;
    }

    public String getIconName(){
        String name = plugin.getConfig().getString("Layouts." + key + ".Display-Name");
        assert name != null;
        return ChatColor.translateAlternateColorCodes('&', name);
    }

    public List<String> getIconLore(){
        List<String> unformattedLore = plugin.getConfig().getStringList("Layouts." + key + ".Lore");
        List<String> formattedLore = new ArrayList<>();
        unformattedLore.forEach(unformattedString -> formattedLore
                .add(ChatColor.translateAlternateColorCodes('&', unformattedString)));
        return formattedLore;
    }

    public int getIconSlot(){
        return plugin.getConfig().getInt("Layouts." + key + ".Slot");
    }
}