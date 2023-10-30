package org.fudan.asdt2023.modules.disk.command;

import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.modules.disk.command.i.DiskCommand;

public class LoadCommand extends DiskCommand {

    private final String filename;

    public LoadCommand(MarkdownEditor editor, String filename) {
        super(editor);
        this.filename = filename;
    }

    @Override
    public void execute() {
    }
}
