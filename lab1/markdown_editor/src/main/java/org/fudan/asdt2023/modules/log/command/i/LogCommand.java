package org.fudan.asdt2023.modules.log.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.log.LogManager;

public abstract class LogCommand implements ICommand {
    private LogManager context;
    protected final String command;
    protected ICommandExecutionStatus status;

    public LogCommand(LogManager context, String command) {
        this.context = context;
        this.command = command;
    }

    @Override
    public abstract void execute();

    @Override
    public ICommandExecutionStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ICommandExecutionStatus status) {
        this.status = status;
    }
}
