package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;

public class AppendTailCommand extends InsertCommand {
    public AppendTailCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void parseCommand() {
        editLineNo = context.numLines();
        editString = command.substring(command.indexOf(" ") + 1);
    }

}
