package org.fudan.asdt2023.editModule;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppendTailCommandTest {
    private MarkdownEditor editor;
    @Before
    public void beforeTest(){
        editor = MarkdownEditorSingleton.getInstance();


        // edit module
        EditModule editModule = new EditModule(v -> MarkdownEditorSingleton.getInstance().getCurFile());
        editor.addModule("edit", editModule);
    }

    @Test
    public void AppendTail(){
        editor.executeCommand("append-tail # hello");
        editor.executeCommand("append-tail ## hi");

        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals(2, file.getLines().size());
        assertEquals("# hello", file.getLines().get(0));
        assertEquals("## hi", file.getLines().get(1));
    }
}
