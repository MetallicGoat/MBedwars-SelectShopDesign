package de.marcely.bedwars.selectshopdesign;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopLayoutAttributes {

    @Getter private final String id;
    @Getter private final String layoutName;
    @Getter private final String description;

    private final ItemStack icon;

    public ShopLayoutAttributes(
            String id,
            String layoutName,
            ItemStack icon,
            String description){

        this.id = id;
        this.layoutName = layoutName;
        this.description = description;
        this.icon = icon != null ? icon : new ItemStack(Material.STONE);
    }

    public ItemStack getIcon(){
        return icon.clone();
    }
}
