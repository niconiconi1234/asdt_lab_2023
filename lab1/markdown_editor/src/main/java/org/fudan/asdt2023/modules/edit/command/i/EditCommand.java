package org.fudan.asdt2023.modules.edit.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.main.EditingFile;

public abstract class EditCommand implements ICommand {
    protected final EditingFile context;
    protected final String command;
    protected int editLineNo;
    protected String editString;
    protected ICommandExecutionStatus status = ICommandExecutionStatus.NOT_EXECUTED;

    public EditCommand(EditingFile context, String command) {
        this.context = context;
        this.command = command;
    }

    public abstract void parseCommand();

    public abstract void edit();

    public void execute() {
        parseCommand();
        edit();
    }

    public abstract void undo();

    @Override
    public ICommandExecutionStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ICommandExecutionStatus status) {
        this.status = status;
    }
}
