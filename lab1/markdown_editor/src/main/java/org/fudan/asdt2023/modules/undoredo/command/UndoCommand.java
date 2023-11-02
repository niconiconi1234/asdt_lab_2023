package org.fudan.asdt2023.modules.undoredo.command;

import org.fudan.asdt2023.modules.undoredo.UndoRedoManager;
import org.fudan.asdt2023.modules.undoredo.command.i.UndoRedoCommand;

public class UndoCommand extends UndoRedoCommand {
    public UndoCommand(UndoRedoManager context) { super(context); }

    @Override
    public void execute() {
        this.context.undoOnce();
    }
}
