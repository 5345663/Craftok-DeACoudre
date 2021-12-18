package eu.craftok.dac.game.player;

import eu.craftok.dac.game.DGame;
import eu.craftok.gameapi.game.Game;
import eu.craftok.gameapi.game.player.GamePlayer;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DGamePlayer extends GamePlayer {

    private int health;
    public DGamePlayer(UUID uuid) {
        super(uuid);
        this.health = 1;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth() {
        setHealth(health + 1);
    }

    @Override
    public void onDeath(Player player, Game game) {
        super.onDeath(player, game);

        if (!game.getGameStatus().isGame()){
            return;
        }

        setHealth(health - 1);
        player.setGameMode(GameMode.SPECTATOR);

        if (getHealth() == 0){
            onLost(player, game);
        }else if (!isWin(game)){
            DGame dGame = (DGame) game;
            if (dGame.getJumper().equals(this)) {
                dGame.selectJumper();
                PlayerUtils util = game.getPlayerUtils();
                util.sendMessage("§c§lCRAFTOK §8§l» §cOutch... §f" + getPlayer().getName() + " §fvient de perdre une vie, il lui reste §c" + health + " vie(s) !");
                util.sendSound(Sound.VILLAGER_DEATH, 2f);
            }
        }
    }

    @Override
    public void onLost(Player player, Game game) {
        game.getPlayerUtils().sendSound(Sound.GHAST_SCREAM, 2f);
        game.broadcastMessage("§c§lCRAFTOK §8§l» §c§l" + getDisplayName() + " §fvient d'être eliminé car il lui reste aucune vie !");
        super.onLost(player, game);
        if (!isWin(game)){
            DGame dGame = (DGame) game;
            if (dGame.getJumper().equals(this)) {
                dGame.selectJumper();
            }
        }
    }

    @Override
    public void onQuit(Player player, Game game) {
        DGame dGame = (DGame) game;
        if (game.getGameStatus().isGame()) {
            if (isPlaying()) {
                setHealth(1);
            }
            dGame.getWilljumpPlayers().remove(this);
        }
        super.onQuit(player, game);

    }

    @Override
    public void onDamage(Player player, EntityDamageEvent event, Game game) {
        super.onDamage(player, event, game);
        if (((DGame) game).getJumper() == this) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                onDeath(player, game);
                event.setCancelled(true);
            }
        }
    }

    public void setHealth(int health) {
        this.health = health;
        if (health == 0){
            getPlayer().setPlayerListName(getPlayer().getName());
            return;
        }
        StringBuilder builder = new StringBuilder(" ");
        for (int i = 0; i < health; i++){
            builder.append(ChatColor.RED + "❤");
        }
        if (getTeam() != null)
        getPlayer().setPlayerListName(getTeam().getPrefix() + getPlayer().getName() + builder.toString());
    }
}
