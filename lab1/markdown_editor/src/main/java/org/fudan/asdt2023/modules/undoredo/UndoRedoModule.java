package org.fudan.asdt2023.modules.undoredo;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.undoredo.command.RedoCommand;
import org.fudan.asdt2023.modules.undoredo.command.UndoCommand;

import java.util.function.Function;

public class UndoRedoModule extends Module {
    public UndoRedoModule(Function<Void, Object> fetchContext) { super(fetchContext); }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(UndoRedoManager.class);
        UndoRedoManager undoRedoManager = (UndoRedoManager) context;
        if(command.equals("undo"))
            return new UndoCommand(undoRedoManager);
        else if(command.equals("redo"))
            return new RedoCommand(undoRedoManager);
        else return null;
    }
}
