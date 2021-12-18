package eu.craftok.dac.game;

import eu.craftok.dac.DMain;
import eu.craftok.dac.game.player.DGamePlayer;
import eu.craftok.dac.game.scoreboard.DGameScoreboard;
import eu.craftok.dac.game.settings.DGameSettings;
import eu.craftok.dac.game.ui.DGameHostUi;
import eu.craftok.dac.game.ui.GameStatsUI;
import eu.craftok.dac.game.world.DGameWorld;
import eu.craftok.gameapi.game.Game;
import eu.craftok.gameapi.game.GameManager;
import eu.craftok.gameapi.game.player.GamePlayer;
import eu.craftok.gameapi.game.world.GameWorld;
import eu.craftok.utils.CConfig;
import org.bukkit.entity.Player;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DGameManager extends GameManager {

    public DGameManager() {
        super("Dé à coudre", "DAC", new DGameSettings(), new DGameScoreboard(), DMain.getInstance());
    }

    @Override
    public void openHostInventory(Player player) {
        new DGameHostUi(player).openMenu();
    }

    @Override
    public void openStatsInventory(Player player) {
        new GameStatsUI(player).openMenu();
    }

    @Override
    public Game newGame() {
        return new DGame();
    }

    @Override
    public GameWorld newGameWorld(Game game, CConfig config) {
        return new DGameWorld((DGame) game, config);
    }

    @Override
    public GamePlayer newGPlayer(Player player) {
        return new DGamePlayer(player.getUniqueId());
    }
}
