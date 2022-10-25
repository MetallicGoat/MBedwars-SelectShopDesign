package de.marcely.bedwars.selectshopdesign;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Config {

    public static String guiTitle = "%gui_title%";
    public static String selectedLayout = "%design_choose%";

    public static String selectedLayoutTitle = "&7{design}";
    public static List<String> selectedLayoutLore = Arrays.asList("", "%design_selected%", "", "&7{description}");

    public static String unselectedLayoutTitle = "&7{design}";
    public static List<String> unselectedLayoutLore = Arrays.asList("", "%design_unselected%", "", "&7{description}");

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

        guiTitle = config.getString("gui.title", guiTitle);
        selectedLayout = config.getString("gui.selected-layout", selectedLayout);

        selectedLayoutTitle = config.getString("gui.selected-layout-title");
        if(config.contains("gui.selected-layout-lore"))
            selectedLayoutLore = config.getStringList("gui.selected-layout-lore");

        selectedLayoutTitle = config.getString("gui.unselected-layout-title");
        if(config.contains("gui.unselected-layout-lore"))
            selectedLayoutLore = config.getStringList("gui.unselected-layout-lore");

        shopLayouts.clear();
        final ConfigurationSection layoutsSection = config.getConfigurationSection("layouts");

        if (layoutsSection != null) {
            for (String layoutId : layoutsSection.getKeys(false)) {
                final ConfigurationSection layoutSection = layoutsSection.getConfigurationSection(layoutId);

                if (layoutSection == null)
                    continue;

                final ShopLayoutAttributes layoutAttributes = new ShopLayoutAttributes(
                        layoutId,
                        config.getString("display-name", "UNNAMED"),
                        Helper.get().parseItemStack(config.getString("material", "stone")),
                        config.getString("lore", "Missing Description")
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
        config.set("gui.selected-layout", selectedLayout);
        config.set("gui.selected-layout-title", selectedLayoutTitle);
        config.set("gui.selected-layout-lore", selectedLayoutLore);
        config.set("gui.unselected-layout-title", unselectedLayoutTitle);
        config.set("gui.unselected-layout-lore", unselectedLayoutLore);

        config.addEmptyLine();

        config.addComment("Layouts displayed in the GUI");
        for (ShopLayoutAttributes layoutAttributes : shopLayouts) {
            final String path = "layouts." + layoutAttributes.getId() + ".";

            config.set(path + "display-name", layoutAttributes.getLayoutName());
            config.set(path + "material", Helper.get().composeItemStack(layoutAttributes.getIcon()));
            config.set(path + "lore", layoutAttributes.getDescription());
        }

        // save
        config.save(getFile(plugin));
    }

    private static void loadDefaults() {
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.HYPIXEL.name().toLowerCase(),
                ShopLayoutType.HYPIXEL.getLayout().getName(),
                Helper.get().parseItemStack("APPLE"),
                "Older Hypixel Layout"
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.HYPIXEL_V2.name().toLowerCase(),
                ShopLayoutType.HYPIXEL_V2.getLayout().getName(),
                Helper.get().parseItemStack("GOLDEN_APPLE"),
                "Modern Hypixel Layout"
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.HIVEMC.name().toLowerCase(),
                ShopLayoutType.HIVEMC.getLayout().getName(),
                Helper.get().parseItemStack("BLAZE_POWDER"),
                "HiveMC Layout"
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.GOMMEHD.name().toLowerCase(),
                ShopLayoutType.GOMMEHD.getLayout().getName(),
                Helper.get().parseItemStack("BONE"),
                "GommeHD Layout"
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.BERGWERKLABS.name().toLowerCase(),
                ShopLayoutType.BERGWERKLABS.getLayout().getName(),
                new ItemStack(Material.STONE),
                "BergwerkLabs Layout"
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.MINESUCHT.name().toLowerCase(),
                ShopLayoutType.MINESUCHT.getLayout().getName(),
                Helper.get().parseItemStack("BOOK"),
                "Minesucht Layout"
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.REWINSIDE.name().toLowerCase(),
                ShopLayoutType.REWINSIDE.getLayout().getName(),
                Helper.get().parseItemStack("GLOWSTONE_DUST"),
                "Rewinside Layout"
        ));
        shopLayouts.add(new ShopLayoutAttributes(
                ShopLayoutType.NORMAL.name().toLowerCase(),
                ShopLayoutType.NORMAL.getLayout().getName(),
                Helper.get().parseItemStack("EGG"),
                "Normal Layout"
        ));
    }
}
