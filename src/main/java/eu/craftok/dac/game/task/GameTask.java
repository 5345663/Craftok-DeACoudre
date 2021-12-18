package eu.craftok.dac.game.task;

import eu.craftok.dac.game.DGame;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Project de-a-coudre Created by Sithey
 */

public class GameTask extends BukkitRunnable {

    private DGame game;

    public GameTask(DGame game) {
        this.game = game;
    }

    @Override
    public void run() {

        if (!game.getGameStatus().isGame()){
            cancel();
            return;
        }

        if (game.getTimer() == 0){
            cancel();
            game.getJumper().onDeath(game.getJumper().getPlayer(), game);
            return;
        }

        game.remTimer();
    }
}
