package org.fudan.asdt2023.modules.disk.command.i;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.main.MarkdownEditor;

public abstract class DiskCommand implements ICommand {
    protected MarkdownEditor context;

    public DiskCommand(MarkdownEditor context) {
        this.context = context;
    }
}
