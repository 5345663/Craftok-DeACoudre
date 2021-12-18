package eu.craftok.dac.game.scoreboard;


import eu.craftok.dac.DMain;
import eu.craftok.dac.game.DGame;
import eu.craftok.dac.game.DGameManager;
import eu.craftok.dac.game.player.DGamePlayer;
import eu.craftok.gameapi.game.player.GamePlayer;
import eu.craftok.gameapi.game.scoreboard.GameScoreboard;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Project de-a-coudre Created by Sithey
 */

public class DGameScoreboard extends GameScoreboard {

    @Override
    public void build(GamePlayer p, SidebarEditor e) {
        super.build(p, e);
        DGameManager gameManager = DMain.getInstance().getGameManagers();

        DGame game = ((DGame) p.getGame());
        if (game == null)
            return;

        e.clear();
        PlayerUtils util = new PlayerUtils(p.getPlayer());
        if (game.getGameStatus().isLobby()) {

            e.add("");
            e.add("§c§lSERVEUR");
            e.add(" §fServeurID §3» §b" + Bukkit.getServerName() + "#" + game.getUniqueId());
            e.add(" §fEn jeu §3» §b" + game.getPlayingGPlayers().size() + "/" + gameManager.getSettings().getMaxPlayers());
            e.add("");
            e.add("§c§lJEU");
            String teammode = "Solo";
            if (gameManager.getSettings().getSizeTeams() != 1)
                teammode = "To" + gameManager.getSettings().getSizeTeams();
            e.add(" §fMode §3» §b" + teammode);
            e.add(" §fMap §3» §b" + game.getGameWorld().getName());
            e.add("");
            e.add("§b[§fcraftok.fr§c]");

            if (gameManager.getSettings().isHost()){
                String pseudo = "Hors ligne !";
                Player player = gameManager.getGameHost().getHost().getPlayer();
                if (player != null){
                    pseudo = gameManager.getGameHost().getHost().getDisplayName();
                }

                util.sendActionBar("§7» §fL'host de la partie est §c" + pseudo);

            }else {
                if (game.getTimer() == gameManager.getSettings().startTimer()) {
                    util.sendActionBar("§cManque de joueurs pour commencer...");
                }
            }

        } else if (game.getGameStatus().isGame()) {

            if (game.getJumper() != null && game.getJumper() == p) {

                e.add("");
                e.add(" §fVie restante §3» §b" + ((DGamePlayer) p).getHealth());
                e.add("");
                e.add(" §fA toi de sauter");
                e.add(" §fTemps restant §b" + game.getTimer() + "§3s");
                e.add("");
                e.add("§b[§fcraftok.fr§c]");

            }else{

                e.setByScore(" ", 100);
                game.getPlayingGPlayers().forEach(gp -> e.setByScore(gp.getDisplayName() + " §3» §b" + ((DGamePlayer) gp).getHealth(), ((DGamePlayer) gp).getHealth()));
                e.setByScore("", -1);
                e.setByScore("§b[§fcraftok.fr§c]", -2);

            }

            String nextjumper;
            if (game.getWilljumpPlayers().isEmpty()){
                nextjumper = game.getPlayingGPlayers().get(0).getDisplayName();
            }else{
                nextjumper = game.getWilljumpPlayers().get(0).getDisplayName();
            }
            util.sendActionBar(" §fProchain joueur à sauter §3» §b" + nextjumper);

        } else if (game.getGameStatus().isFinish()){
            e.add("");
            if (game.getgWinners().size() == 1){
                e.add(" §fVainqueur §3» §a" + game.getgWinners().get(0).getOfflinePlayer().getName());
            }else{
                e.add(" §fVainqueur");
                for (GamePlayer gp : game.getgWinners()){
                    e.add("§3» §a" + gp.getOfflinePlayer().getName());
                }
            }
            e.add("");
            e.add("§b[§fcraftok.fr§c]");
        }
    }
}
