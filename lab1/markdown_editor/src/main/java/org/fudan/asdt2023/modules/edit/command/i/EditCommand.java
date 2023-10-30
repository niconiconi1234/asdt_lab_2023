package org.fudan.asdt2023.modules.edit.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.main.EditingFile;

public abstract class EditCommand implements ICommand {
    private final EditingFile context;

    public EditCommand(EditingFile context) {
        this.context = context;
    }

    public abstract void execute();

    public abstract void undo();
}
