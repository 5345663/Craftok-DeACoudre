package eu.craftok.dac.game.ui;

import eu.craftok.dac.game.settings.DGameSettings;
import eu.craftok.gameapi.GameAPI;
import eu.craftok.gameapi.game.GameManager;
import eu.craftok.gameapi.game.ui.host.GameHostUi;
import eu.craftok.utils.ItemCreator;
import eu.craftok.utils.inventory.item.ActionItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DGameHostUi extends GameHostUi {

    public DGameHostUi(Player p) {
        super(p);
    }

    @Override
    public void setupMenu() {
        super.setupMenu();

        GameAPI gameAPI = GameAPI.getInstance();
        GameManager gameManager = gameAPI.getGameManagers();
        DGameSettings settings = (DGameSettings) gameManager.getSettings();

        addActionItem(new ActionItem(24, new ItemCreator(Material.APPLE).setAmount(settings.getMinHealth()).setName("§cVie au debut de la partie: §6" + settings.getMinHealth())
                .addLore("")
                .addLore("§e§l» §7Clique droit pour ajouter 1")
                .addLore("§e§l» §7Clique gauche pour enlever 1")
                .addLore("").getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent inventoryClickEvent) {
                if (inventoryClickEvent.isLeftClick()){
                    if (settings.getMinHealth() - 1 > 0){
                        settings.setMinHealth(settings.getMinHealth() - 1);
                        player.sendMessage("§c§lCRAFTOK §8§l» §bVous avez mis la vie au debut de la partie a §f" + settings.getMinHealth());
                    }
                }else if (inventoryClickEvent.isRightClick()){
                    if (settings.getMinHealth() + 1 <= 3){
                        settings.setMinHealth(settings.getMinHealth() + 1);
                        player.sendMessage("§c§lCRAFTOK §8§l» §bVous avez mis la vie au debut de la partie a §f" + settings.getMinHealth());
                    }
                }
                openMenu();
            }
        });

        addActionItem(new ActionItem(33, new ItemCreator(Material.GOLDEN_APPLE).setAmount(settings.getMaxHealth()).setName("§cVie maximal: §6" + settings.getMaxHealth())
                .addLore("")
                .addLore("§e§l» §7Clique droit pour ajouter 1")
                .addLore("§e§l» §7Clique gauche pour enlever 1")
                .addLore("").getItemstack()) {
            @Override
            public void onClick(InventoryClickEvent inventoryClickEvent) {
                if (inventoryClickEvent.isLeftClick()){
                    if (settings.getMaxHealth() - 1 > 0){
                        settings.setMaxHealth(settings.getMaxHealth() - 1);
                        player.sendMessage("§c§lCRAFTOK §8§l» §bVous avez mis la vie maximal a §f" + settings.getMaxHealth());
                    }
                }else if (inventoryClickEvent.isRightClick()){
                    if (settings.getMaxHealth() + 1 <= 10){
                        settings.setMaxHealth(settings.getMaxHealth() + 1);
                        player.sendMessage("§c§lCRAFTOK §8§l» §bVous avez mis la vie maximal a §f" + settings.getMaxHealth());
                    }
                }
                openMenu();
            }
        });
    }
}
