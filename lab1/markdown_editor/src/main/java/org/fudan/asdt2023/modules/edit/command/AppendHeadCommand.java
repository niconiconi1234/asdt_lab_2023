package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;

public class AppendHeadCommand extends EditCommand {
    public AppendHeadCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        String text = command.substring(command.indexOf(" ") + 1);
        context.getLines().add(0, text);
        setStatus(ICommandExecutionStatus.EXECUTED_SUCCESS);
    }

    @Override
    public void undo() {

    }
}
