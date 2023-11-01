package org.fudan.asdt2023.modules.edit.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.main.EditingFile;

public abstract class EditCommand implements ICommand {
    protected final EditingFile context;
    protected final String command;

    public EditCommand(EditingFile context, String command) {
        this.context = context;
        this.command = command;
    }

    public abstract void execute();

    public abstract void undo();
}
