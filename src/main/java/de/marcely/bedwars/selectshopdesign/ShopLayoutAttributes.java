package de.marcely.bedwars.selectshopdesign;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class ShopLayoutAttributes {

    @Getter private final String id;
    @Getter private final String layoutName;
    @Getter private final List<String> lore;

    private final ItemStack icon;

    public ShopLayoutAttributes(
            String id,
            String layoutName,
            ItemStack icon,
            List<String> lore){

        this.id = id;
        this.layoutName = layoutName;
        this.icon = icon != null ? icon : new ItemStack(Material.STONE);
        this.lore = lore != null ? lore : Collections.singletonList("Missing Lore");
    }

    public ItemStack getIcon(){
        return icon.clone();
    }
}
