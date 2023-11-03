package org.fudan.asdt2023.modules.undoredo;

import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.fudan.asdt2023.modules.undoredo.UndoRedoModule;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UndoRedoCommandTest {
    private MarkdownEditor editor;

    @Before
    public void beforeTest(){
        editor = new MarkdownEditor();

        CommandExecutionObserver undoRedoManager = new UndoRedoManager();
        editor.addObserver("undoredo", undoRedoManager);
        UndoRedoModule undoRedoModule = new UndoRedoModule(v -> editor.getObserver("undoredo"));
        editor.addModule("undoredo", undoRedoModule);

        // edit module
        EditModule editModule = new EditModule(v -> editor.getCurFile());
        editor.addModule("edit", editModule);

        List<String> lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour");

        for(String text: lines){
            editor.executeCommand("insert " + text);
        }
    }

    public void checkFile(List<String> lines){
        List<String> fileLines = editor.getCurFile().getLines();
        assertEquals(lines, fileLines);
    }

    @Test
    public void undoRedoWithInsert(){
        editor.executeCommand("insert first");
        editor.executeCommand("undo");
        List<String> lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("redo");
        lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour", "first");
        checkFile(lines);
        editor.executeCommand("insert second");
        editor.executeCommand("undo");
        lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour", "first");
        checkFile(lines);

        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("redo");
        lines = List.of("# hello", "## hi", "# hello", "## hallo");
        checkFile(lines);

        editor.executeCommand("insert 3 line3");
        editor.executeCommand("insert 2 line2");
        editor.executeCommand("undo");
        editor.executeCommand("redo");
        lines = List.of("# hello", "line2", "## hi", "line3", "# hello", "## hallo");
        checkFile(lines);
        editor.executeCommand("insert 4 line4");
        editor.executeCommand("insert 3 line3_2");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("redo");
        lines = List.of("# hello", "## hi", "line3", "# hello", "## hallo");
        checkFile(lines);

        editor.executeCommand("redo");
        editor.executeCommand("redo");
        editor.executeCommand("redo");
        lines = List.of("# hello", "line2", "line3_2", "## hi", "line4", "line3", "# hello", "## hallo");
        checkFile(lines);
    }

    @Test
    public void undoRedoWithDelete(){
        editor.executeCommand("delete 1");
        editor.executeCommand("undo");
        List<String> lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("redo");
        lines = List.of("## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);

        editor.executeCommand("delete jour");
        editor.executeCommand("undo");
        lines = List.of("## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("redo");
        lines = List.of("## hi", "# hello", "## hallo");
        checkFile(lines);

        editor.executeCommand("undo");
        editor.executeCommand("undo");
        lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("redo");
        editor.executeCommand("redo");
        editor.executeCommand("redo");
        lines = List.of("## hi", "# hello", "## hallo");
        checkFile(lines);
    }

    @Test
    public void undoRedoWithAppendHead(){
        editor.executeCommand("append-head # Big Title1");
        editor.executeCommand("append-head # Big Title2");
        editor.executeCommand("append-head # Big Title3");
        editor.executeCommand("undo");
        List<String> lines = List.of("# Big Title2", "# Big Title1", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("redo");
        lines = List.of("# Big Title3", "# Big Title2", "# Big Title1", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("redo");
        lines = List.of("# Big Title3", "# Big Title2", "# Big Title1", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);

        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("redo");
        editor.executeCommand("redo");
        lines = List.of("# Big Title2", "# Big Title1", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
    }

    @Test
    public void undoRedoWithAppendTail(){
        editor.executeCommand("append-tail ### Tail1");
        editor.executeCommand("append-tail ### Tail2");
        editor.executeCommand("append-tail ### Tail3");
        editor.executeCommand("append-tail ### Tail4");
        editor.executeCommand("undo");
        List<String> lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour", "### Tail1", "### Tail2", "### Tail3");
        checkFile(lines);
        editor.executeCommand("redo");
        lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour", "### Tail1", "### Tail2", "### Tail3", "### Tail4");
        checkFile(lines);

        editor.executeCommand("append-tail ### Tail5");
        editor.executeCommand("append-tail ### Tail6");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour", "### Tail1", "### Tail2");
        checkFile(lines);
        editor.executeCommand("append-tail ### Tail7");
        editor.executeCommand("append-tail ### Tail8");
        editor.executeCommand("redo");
        editor.executeCommand("redo");
        editor.executeCommand("redo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        lines = List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour", "### Tail1");
        checkFile(lines);
    }

    @Test
    public void crossingUndoRedo1(){
        editor.executeCommand("insert 1 test");
        editor.executeCommand("delete 1");
        editor.executeCommand("append-head # Title");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        List<String> lines = List.of("test", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);

        editor.executeCommand("insert 1 test");
        editor.executeCommand("delete 1");
        editor.executeCommand("append-head # Title");
        editor.executeCommand("redo");
        lines = List.of("# Title", "test", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("undo");
        lines = List.of("test", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("undo");
        lines = List.of("test", "test", "# hello", "## hi", "# hello", "## hallo", "* bon jour");
        checkFile(lines);
        editor.executeCommand("redo");
        editor.executeCommand("delete hallo");
        editor.executeCommand("append-tail *tail");
        editor.executeCommand("append-head # head");
        editor.executeCommand("delete 4");
        lines = List.of("# head", "test", "# hello", "# hello", "* bon jour", "*tail");
        checkFile(lines);

        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("undo");
        editor.executeCommand("redo");
        lines = List.of("test", "# hello", "## hi", "# hello", "* bon jour", "*tail");
        checkFile(lines);
    }

}
