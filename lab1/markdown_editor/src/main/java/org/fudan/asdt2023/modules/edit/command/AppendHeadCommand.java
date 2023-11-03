package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;

public class AppendHeadCommand extends InsertCommand {
    public AppendHeadCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        editLineNo = 1;
        editString = command.substring(command.indexOf(" ") + 1);
    }
}
