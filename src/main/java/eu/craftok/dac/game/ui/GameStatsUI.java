package eu.craftok.dac.game.ui;

import eu.craftok.core.common.user.User;
import eu.craftok.core.spigot.CoreSpigot;
import eu.craftok.utils.ItemCreator;
import eu.craftok.utils.TimeUtils;
import eu.craftok.utils.inventory.CustomInventory;
import eu.craftok.utils.inventory.item.StaticItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Project de-a-coudre Created by Sithey
 */

public class GameStatsUI extends CustomInventory {

    public GameStatsUI(Player p) {
        super(p, "§b[§fDé à coudre§c] §f" + p.getName(), 4, 1);
    }

    @Override
    public void setupMenu() {
        User user = CoreSpigot.getInstance().getCommon().getUserManager().getUserByUniqueId(player.getUniqueId());
        remplirCornerInventory(new ItemCreator(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setName("§c").getItemstack());

        addItem(new StaticItem(13, (new ItemCreator(Material.WATCH))
                .setName("§c§lTemps de jeu")
                .addLore("§7Vous avez joué §b" + TimeUtils.getDurationBreakdown(Integer.parseInt(user.getStat("dac.playingtime",0)) * 1000L)).getItemstack()));

        addItem(new StaticItem(21, (new ItemCreator(Material.COAL))
                .setName("§c§lSauts ratés")
                .addLore("§7Vous avez ratés §b" + user.getStat("dac.totalfailed",0) + " §7sauts !").getItemstack()));

        addItem(new StaticItem(22, (new ItemCreator(Material.DOUBLE_PLANT))
                .setName("§c§lSauts réussis")
                .addLore("§7Vous avez réussi §b" + user.getStat("dac.totalsuccess",0) + " §7sauts !").getItemstack()));

        addItem(new StaticItem(23, (new ItemCreator(Material.BOW))
                .setName("§c§lVictoires")
                .addLore("§7Vous avez gagné §b" + user.getStat("dac.wins",0) + " §7parties !").getItemstack()));
    }
}
