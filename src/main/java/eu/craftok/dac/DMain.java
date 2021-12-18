package eu.craftok.dac;

import eu.craftok.dac.game.DGameManager;
import eu.craftok.gameapi.GameAPI;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DMain extends GameAPI {

    private static DMain instance;

    private DGameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;

        this.gameManager = new DGameManager();

        GameAPI.getInstance().registerGame(gameManager, null, null).getGameManagers().setupServer();
    }

    public static DMain getInstance() {
        return instance;
    }

    @Override
    public DGameManager getGameManagers() {
        return gameManager;
    }
}
