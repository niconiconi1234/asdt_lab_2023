package org.fudan.asdt2023.editModule;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InsertCommandTest {
    private MarkdownEditor editor;
    @Before
    public void beforeTest(){
        editor = MarkdownEditorSingleton.getInstance();

        // edit module
        EditModule editModule = new EditModule(v -> MarkdownEditorSingleton.getInstance().getCurFile());
        editor.addModule("edit", editModule);
    }

    @Test
    public void insertWithLineNumber(){
        editor.executeCommand("insert 1 ## hello");
        editor.executeCommand("insert 1 # hi");

        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals(2, file.getLines().size());
        assertEquals("# hi", file.getLines().get(0));
        assertEquals("## hello", file.getLines().get(1));

    }

    @Test
    public void insertWithoutLineNumber(){
        editor.executeCommand("insert # hello");
        editor.executeCommand("insert ## hallo");

        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals("## hallo", file.getLines().get(1));
    }

    @Test
    public void insertWithIllegalLineNumber(){
        editor.executeCommand("insert -1 ## hello");

        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        assertEquals(0, file.getLines().size());
    }

}
