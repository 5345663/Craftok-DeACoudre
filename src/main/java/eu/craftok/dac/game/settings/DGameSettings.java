package eu.craftok.dac.game.settings;

import eu.craftok.dac.DMain;
import eu.craftok.gameapi.game.settings.GameSettings;
import eu.craftok.utils.CConfig;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DGameSettings extends GameSettings {

    private int minHealth, maxHealth;

    public DGameSettings() {
        super(new CConfig("configuration.yml", DMain.getInstance()));
    }

    @Override
    public void initSettings() {
        super.initSettings();

        getConfig().addValue("health.base", 1);
        minHealth = (int) getConfig().getValue("health.base");
        getConfig().addValue("health.maximum", 5);
        maxHealth = (int) getConfig().getValue("health.maximum");
    }

    public int getMinHealth() {
        return minHealth;
    }

    public void setMinHealth(int minHealth) {
        this.minHealth = minHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
