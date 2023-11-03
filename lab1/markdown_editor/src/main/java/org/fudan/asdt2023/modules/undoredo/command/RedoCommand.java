package org.fudan.asdt2023.modules.undoredo.command;

import org.fudan.asdt2023.modules.undoredo.UndoRedoManager;
import org.fudan.asdt2023.modules.undoredo.command.i.UndoRedoCommand;

public class RedoCommand extends UndoRedoCommand {
    public RedoCommand(UndoRedoManager context) { super(context); }

    @Override
    public void execute() {
        this.context.redoOnce();
    }
}
