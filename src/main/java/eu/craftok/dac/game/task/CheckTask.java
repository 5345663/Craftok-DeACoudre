package eu.craftok.dac.game.task;

import eu.craftok.dac.game.DGame;
import eu.craftok.dac.game.player.DGamePlayer;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.STAINED_GLASS;
import static org.bukkit.Material.WOOL;

/**
 * Project de-a-coudre Created by Sithey
 */

public class CheckTask extends BukkitRunnable {

    private final DGame game;
    public CheckTask(DGame game){
        this.game = game;
    }

    @Override
    public void run() {
        PlayerUtils util = game.getPlayerUtils();
        DGamePlayer player = game.getJumper();
        if (!game.getGameStatus().isGame() || player == null || player.getPlayer() == null){
            cancel();
            return;
        }
        Block block = player.getPlayer().getLocation().getBlock();
        if (block.isLiquid()){
            if (!block.getRelative(BlockFace.EAST).isLiquid() && !block.getRelative(BlockFace.NORTH).isLiquid() && !block.getRelative(BlockFace.SOUTH).isLiquid() && !block.getRelative(BlockFace.WEST).isLiquid()) {
                if (player.getHealth() < 5){
                    player.addHealth();
                    util.sendMessage("§c§lCRAFTOK §8§l» " + player.getDisplayName() + " §fvient de gagner une vie en faisant un §6dé à coudre §f!");
                }else{
                    util.sendMessage("§c§lCRAFTOK §8§l» " + player.getDisplayName() + " §fvient de faire un dé à coudre §7§l(Vie au maximum)");
                }
                block.setType(STAINED_GLASS);
                util.sendSound(Sound.LEVEL_UP, 2f);
                player.addCoins(3, "Dé à coudre");
            }else{
                block.setType(WOOL);
                util.sendMessage("§c§lCRAFTOK §8§l» §a" + player.getDisplayName() + " §fvient de réussir son jump");
                util.sendSound(Sound.ORB_PICKUP, 2f);
            }
//            player.getStats().addSuccess();
            block.setData(player.getTeam().getDyeColor().getData());
            game.selectJumper();
            player.getPlayer().teleport(game.getGameWorld().getWaiting());
            player.getPlayer().setGameMode(GameMode.SPECTATOR);
            cancel();
        }else if (block.getType() == WOOL){
            player.onDeath(player.getPlayer(), game);
            cancel();
        }
    }
}
