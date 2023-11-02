package org.fudan.asdt2023;

import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.disk.DiskModule;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.fudan.asdt2023.modules.log.LogManager;
import org.fudan.asdt2023.modules.log.LogModule;
import org.fudan.asdt2023.modules.undoredo.UndoRedoManager;
import org.fudan.asdt2023.modules.undoredo.UndoRedoModule;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        MarkdownEditor editor = MarkdownEditorSingleton.getInstance();

        // disk module
        DiskModule diskModule = new DiskModule(v -> MarkdownEditorSingleton.getInstance());
        editor.addModule("disk", diskModule);

        // edit module
        EditModule editModule = new EditModule(v -> MarkdownEditorSingleton.getInstance().getCurFile());
        editor.addModule("edit", editModule);

        // log module
        CommandExecutionObserver logManager = new LogManager();
        editor.addObserver("log", logManager);
        LogModule logModule = new LogModule(v -> MarkdownEditorSingleton.getInstance().getObserver("log"));
        editor.addModule("log", logModule);

        // undoredo module
        CommandExecutionObserver undoRedoManager = new UndoRedoManager();
        editor.addObserver("undoredo", undoRedoManager);
        UndoRedoModule undoRedoModule = new UndoRedoModule(v -> MarkdownEditorSingleton.getInstance().getObserver("undoredu"));
        editor.addModule("undoredo", undoRedoModule);
    }
}
