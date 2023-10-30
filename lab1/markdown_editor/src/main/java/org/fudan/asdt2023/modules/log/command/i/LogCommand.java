package org.fudan.asdt2023.modules.log.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.log.LogManager;

public abstract class LogCommand implements ICommand {
    private LogManager context;

    public LogCommand(LogManager context) {
        this.context = context;
    }

    @Override
    public abstract void execute();
}
