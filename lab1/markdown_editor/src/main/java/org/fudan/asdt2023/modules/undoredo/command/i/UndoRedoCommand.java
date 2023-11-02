package org.fudan.asdt2023.modules.undoredo.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.undoredo.UndoRedoManager;

public abstract class UndoRedoCommand implements ICommand {
    protected UndoRedoManager context;
    public UndoRedoCommand(UndoRedoManager context) { this.context = context; }

    public abstract void execute();
}
