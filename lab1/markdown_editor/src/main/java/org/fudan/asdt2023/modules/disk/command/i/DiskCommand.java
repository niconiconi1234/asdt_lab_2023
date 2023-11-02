package org.fudan.asdt2023.modules.disk.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.main.MarkdownEditor;

public abstract class DiskCommand implements ICommand {
    protected MarkdownEditor context;
    protected ICommandExecutionStatus status;

    public DiskCommand(MarkdownEditor context) {
        this.context = context;
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
