package org.fudan.asdt2023.modules.display.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.main.EditingFile;

public class DisplayCommand implements ICommand {

    protected final EditingFile context;
    protected final String command;
    protected ICommandExecutionStatus status;

    public DisplayCommand(EditingFile context, String command) {
        this.context = context;
        this.command = command;
    }

    @Override
    public void execute() {
    }

    @Override
    public ICommandExecutionStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(ICommandExecutionStatus status) {
        this.status = status;
    }
}
