package org.fudan.asdt2023.modules.edit;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppendHeadCommandTest {
    private MarkdownEditor editor;
    @Before
    public void beforeTest(){
        editor = MarkdownEditorSingleton.getInstance();


        // edit module
        EditModule editModule = new EditModule(v -> MarkdownEditorSingleton.getInstance().getCurFile());
        editor.addModule("edit", editModule);
    }

    @Test
    public void AppendHead(){
        editor.executeCommand("append-head # hello");
        editor.executeCommand("append-head ## hi");

        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals(2, file.getLines().size());
        assertEquals("# hello", file.getLines().get(1));
        assertEquals("## hi", file.getLines().get(0));
    }
}
