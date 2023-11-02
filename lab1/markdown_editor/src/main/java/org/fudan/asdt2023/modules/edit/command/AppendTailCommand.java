package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;

public class AppendTailCommand extends EditCommand {
    public AppendTailCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        String text = command.substring(command.indexOf(" ") + 1);
        context.getLines().add(text);
        setStatus(ICommandExecutionStatus.EXECUTED_SUCCESS);

    }

    @Override
    public void undo() {

    }
}
