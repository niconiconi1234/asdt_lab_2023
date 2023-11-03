package org.fudan.asdt2023.modules.undoredo.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.undoredo.UndoRedoManager;

public abstract class UndoRedoCommand implements ICommand {
    protected UndoRedoManager context;
    protected ICommandExecutionStatus status = ICommandExecutionStatus.NOT_EXECUTED;
    public UndoRedoCommand(UndoRedoManager context) { this.context = context; }

    @Override
    public void setStatus(ICommandExecutionStatus status) { this.status = status; }

    @Override
    public ICommandExecutionStatus getStatus() { return status; }

    public abstract void execute();
}
