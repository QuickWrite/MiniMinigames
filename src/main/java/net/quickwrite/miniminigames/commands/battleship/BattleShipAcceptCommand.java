package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleShipAcceptCommand extends SubCommand {

    public BattleShipAcceptCommand(BaseCommand parent) {
        super(parent, "accept", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
            if(game == null){
                p.sendMessage(MiniMinigames.PREFIX + "§cThere is no game to play");
                return true;
            }
            game.accept(p);
        }
        return true;
    }
}
