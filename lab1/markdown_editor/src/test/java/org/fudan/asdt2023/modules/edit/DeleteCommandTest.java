package org.fudan.asdt2023.modules.edit;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeleteCommandTest {
    private MarkdownEditor editor;
    @Before
    public void beforeTest(){
        editor = MarkdownEditorSingleton.getInstance();


        // edit module
        EditModule editModule = new EditModule(v -> MarkdownEditorSingleton.getInstance().getCurFile());
        editor.addModule("edit", editModule);
        editor.getCurFile().getLines().clear();
        editor.executeCommand("insert # hello");
        editor.executeCommand("insert ## hi");
        editor.executeCommand("insert # hello");
        editor.executeCommand("insert ## hallo");
        editor.executeCommand("insert * bon jour");
    }

    @Test
    public void deleteWithLineNumber(){
        editor.executeCommand("delete 1");
        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals("## hi", file.getLines().get(0));
    }

    @Test
    public void deleteWithIllegalLineNumber(){
        editor.executeCommand("delete 6");
        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals(5, file.getLines().size());
    }

    @Test
    public void deleteWithText(){
        editor.executeCommand("delete hello");
        editor.executeCommand("delete hello");
        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals("## hi", file.getLines().get(0));
        assertEquals("## hallo", file.getLines().get(1));
    }

}
