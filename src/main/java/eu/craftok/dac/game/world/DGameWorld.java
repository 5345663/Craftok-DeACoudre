package eu.craftok.dac.game.world;

import eu.craftok.dac.game.DGame;
import eu.craftok.gameapi.game.world.GameWorld;
import eu.craftok.gameapi.utils.cuboid.Cuboid;
import eu.craftok.utils.CConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DGameWorld extends GameWorld {

    private Location corner1, corner2;
    private final List<Block> waterblocks;

    public DGameWorld(DGame game, CConfig config) {
        super(game, config);
        waterblocks = new ArrayList<>();
    }

    @Override
    public void initMap() {
        super.initMap();
        getConfig().addValue("corner1", "34.5:60:32.5:-2:151");
        corner1 = stringToLocation(getConfig().getValue("corner1").toString());
        getConfig().addValue("corner2", "20.5:60:17.5:-2:151");
        corner2 = stringToLocation(getConfig().getValue("corner2").toString());
        Iterator<Block> iterator = new Cuboid(corner1, corner2).getBlocks().iterator();
        while (iterator.hasNext()){
            Block block = iterator.next();
            if (block.isLiquid())
                waterblocks.add(block);
        }
    }

    public List<Block> getWaterblocks() {
        return waterblocks;
    }
}
