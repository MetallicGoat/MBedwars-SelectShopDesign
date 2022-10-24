package de.marcely.bedwars.selectshopdesign;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.game.shop.layout.ShopLayout;
import de.marcely.bedwars.api.game.shop.layout.ShopLayoutType;
import de.marcely.bedwars.tools.Helper;
import de.marcely.bedwars.tools.YamlConfigurationDescriptor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Config {

    public static String guiTitle = "Choose Shop Layout";
    public static int guiHeight = 1;
    public static String selectedLayout = "&a&lSELECTED";

    public static List<ShopLayoutAttributes> shopLayouts = new ArrayList<>();

    private static final byte VERSION = 1;

    private static File getFile(SelectShopDesignPlugin plugin) {
        return new File(plugin.getAddon().getDataFolder(), "configs.yml");
    }

    public static void load(SelectShopDesignPlugin plugin) {
        synchronized (Config.class) {
            try {
                loadUnchecked(plugin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadUnchecked(SelectShopDesignPlugin plugin) throws Exception {
        final File file = getFile(plugin);

        if (!file.exists()) {
            loadDefaults();
            save(plugin);
            return;
        }

        // load it
        final FileConfiguration config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }


        shopLayouts.clear();
        final ConfigurationSection layoutsSection = config.getConfigurationSection("layouts");

        if (layoutsSection != null) {
            for (String layoutId : layoutsSection.getKeys(false)) {
                final ShopLayout layout = BedwarsAPI.getGameAPI().getShopLayout(layoutId);
                final ConfigurationSection layoutSection = layoutsSection.getConfigurationSection(layoutId);

                if (layout == null || layoutSection == null)
                    continue;

                final ShopLayoutAttributes layoutAttributes = new ShopLayoutAttributes(
                        layoutId,
                        config.getString("display-name", "UNNAMED"),
                        Helper.get().parseItemStack(config.getString("material", "stone")),
                        config.getStringList("lore"),
                        config.getInt("slot", -1)
                );

                shopLayouts.add(layoutAttributes);
            }
        }

        // auto update file if newer version
        {
            final int currentVersion = config.getInt("file-version", -1);

            if (currentVersion != VERSION)
                save(plugin);
        }
    }

    private static void save(SelectShopDesignPlugin plugin) throws Exception {
        final YamlConfigurationDescriptor config = new YamlConfigurationDescriptor();

        config.addComment("Used for auto-updating the config file. Ignore it");
        config.set("file-version", VERSION);

        config.addEmptyLine();

        config.addComment("Using this addon, players can choose their own shop layout.");
        config.addComment("You may choose from the following");
        for (ShopLayout layout : GameAPI.get().getShopLayouts())
            config.addComment(layout.getType().name());

        config.addEmptyLine();

        config.addComment("GUI Settings");
        config.set("gui.title", guiTitle);
        config.set("gui.height", guiHeight);
        config.set("selected-layout", selectedLayout);

        config.addEmptyLine();

        config.addComment("Layouts displayed in the GUI");
        for (ShopLayoutAttributes layoutAttributes : shopLayouts) {
            final String path = "layouts." + layoutAttributes.getId() + ".";

            config.set(path + "display-name", layoutAttributes.getLayoutName());
            config.set(path + "material", Helper.get().composeItemStack(layoutAttributes.getIcon()));
            config.set(path + "slot", layoutAttributes.getPosition());
            config.set(path + "lore", layoutAttributes.getLore());
        }

        // save
        config.save(getFile(plugin));
    }

    private static void loadDefaults() {
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.HYPIXEL.name(),
                ShopLayoutType.HYPIXEL.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("Older Hypixel Layout"),
                1
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.HYPIXEL_V2.name(),
                ShopLayoutType.HYPIXEL_V2.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("Modern Hypixel Layout"),
                2
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.HIVEMC.name(),
                ShopLayoutType.HIVEMC.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("HiveMC Layout"),
                3
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.GOMMEHD.name(),
                ShopLayoutType.GOMMEHD.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("GommeHD Layout"),
                4
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.BERGWERKLABS.name(),
                ShopLayoutType.BERGWERKLABS.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("BergwerkLabs Layout"),
                4
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.MINESUCHT.name(),
                ShopLayoutType.MINESUCHT.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("Minesucht Layout"),
                6
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.REWINSIDE.name(),
                ShopLayoutType.REWINSIDE.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("Rewinside Layout"),
                7
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.NORMAL.name(),
                ShopLayoutType.NORMAL.name(),
                new ItemStack(Material.STONE),
                Collections.singletonList("Normal Layout"),
                8
        ));
    }
}
