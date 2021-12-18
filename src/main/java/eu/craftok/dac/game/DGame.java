package eu.craftok.dac.game;

import eu.craftok.dac.DMain;
import eu.craftok.dac.game.player.DGamePlayer;
import eu.craftok.dac.game.settings.DGameSettings;
import eu.craftok.dac.game.task.CheckTask;
import eu.craftok.dac.game.task.GameTask;
import eu.craftok.dac.game.world.DGameWorld;
import eu.craftok.gameapi.GameAPI;
import eu.craftok.gameapi.game.Game;
import eu.craftok.gameapi.game.player.GamePlayer;
import eu.craftok.gameapi.game.status.EGameStatus;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DGame extends Game {

    private DGamePlayer jumper;
    private List<DGamePlayer> willjumpPlayers;

    public DGame() {
        this.jumper = null;
        this.willjumpPlayers = new ArrayList<>();
        setGameStatus(EGameStatus.LOBBY.getGameStatus());
    }

    public DGamePlayer getJumper() {
        return jumper;
    }

    public void setJumper(DGamePlayer jumper) {
        this.jumper = jumper;
    }

    public List<DGamePlayer> getWilljumpPlayers() {
        return willjumpPlayers;
    }

    @Override
    public void onStart() {
        super.onStart();

        broadcastMessage("§7§m-------------------------------");
        broadcastMessage("§fBienvenue en §cDe a coudre §f!");
        broadcastMessage("§fLe but du jeu est simple : Sautez dans l'eau");
        broadcastMessage("§fsans prendre de degat de chute pour faire aparaitre une laine");
        broadcastMessage("§fpour être le dernier survivant et gagner la partie");
        broadcastMessage("§7§m-------------------------------");

        if (GameAPI.getInstance().getGameManagers().getSettings().getSizeTeams() == 1)
            GameAPI.getInstance().getGameTeamManager().fillAllTeams(this);

        getPlayingGPlayers().forEach(gp -> {
            gp.getPlayer().setGameMode(GameMode.SPECTATOR);
           if (gp.isPlaying()){
               ((DGamePlayer) gp).setHealth(((DGameSettings) DMain.getInstance().getGameManagers().getSettings()).getMinHealth());
           }
           gp.getPlayer().teleport(getGameWorld().getWaiting());
        });

        new GameTask(this).runTaskTimer(DMain.getInstance(), 0, 20);

        selectJumper();
    }

    @Override
    public void onWin(List<GamePlayer> winners) {
        super.onWin(winners);

        winners.forEach(w -> w.addCoins(20, "Victoire"));
    }

    public DGamePlayer chooseJumper(){
        if (getWilljumpPlayers().isEmpty()){
            getPlayingGPlayers().forEach(gp -> getWilljumpPlayers().add((DGamePlayer) gp));
        }
        return getWilljumpPlayers().remove(0);
    }

    public void selectJumper(){
        boolean equality = true;
        for (Block block : ((DGameWorld) getGameWorld()).getWaterblocks()){
            if (block.isLiquid())
                equality = false;
        }

        if (equality){
            onWin(getPlayingGPlayers());
            return;
        }

        setJumper(chooseJumper());
        jumper.getPlayer().setGameMode(GameMode.ADVENTURE);
        jumper.getPlayer().teleport(getGameWorld().getWaiting());
        PlayerUtils util = new PlayerUtils(jumper.getPlayer());
        util.sendSound(Sound.NOTE_BASS_GUITAR, 2f);
        new CheckTask(this).runTaskTimer(DMain.getInstance(), 0, 3);
        setTimer(30);
    }
}
