package net.quickwrite.miniminigames.commands;

import net.quickwrite.miniminigames.commands.debug.*;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("mdebug", null);

        addSubCommand(new DebugSaveShipConfigCommand(this));
        addSubCommand(new DebugAddShipCommand(this));
        addSubCommand(new DebugSaveItemConfig(this));
        addSubCommand(new DebugAddItemCommand(this));
        addSubCommand(new DebugTestMovementCommand(this));
    }
}