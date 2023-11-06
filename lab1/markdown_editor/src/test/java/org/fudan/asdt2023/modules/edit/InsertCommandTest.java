package org.fudan.asdt2023.modules.edit;

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
        editor = new MarkdownEditor();

        // edit module
        EditModule editModule = new EditModule(v -> editor.getCurFile());
        editor.addModule("edit", editModule);
        //单例模式，所以要显示清除
        editor.getCurFile().getLines().clear();
    }

    @Test
    public void insertWithLineNumber(){
        editor.executeCommand("insert 1 ## hello");
        editor.executeCommand("insert 1 # hi");

        EditingFile file = editor.getCurFile();
        assertEquals(2, file.getLines().size());
        assertEquals("# hi", file.getLines().get(0));
        assertEquals("## hello", file.getLines().get(1));

    }

    @Test
    public void insertWithoutLineNumber(){
        editor.executeCommand("insert # hello");
        editor.executeCommand("insert ## hallo");

        EditingFile file = editor.getCurFile();
        assertEquals("## hallo", file.getLines().get(1));
    }

    @Test
    public void insertWithIllegalLineNumber(){
        editor.executeCommand("insert -1 ## hello");

        EditingFile file = editor.getCurFile();
        assertEquals(0, file.getLines().size());
    }

}
