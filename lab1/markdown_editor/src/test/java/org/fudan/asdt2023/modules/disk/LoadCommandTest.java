package org.fudan.asdt2023.modules.disk;

import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.disk.DiskModule;
import org.fudan.asdt2023.modules.disk.command.*;
import org.fudan.asdt2023.main.EditingFile;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LoadCommandTest {
    private MarkdownEditor editor;

    @Before
    public void beforeTest(){
        editor = new MarkdownEditor();
        DiskModule diskModule = new DiskModule(v -> MarkdownEditorSingleton.getInstance());
        editor.addModule("load", diskModule);

    }

    @Test
    public void LoadCommand(){
        String filename = "src\\test\\java\\org\\fudan\\asdt2023\\modules\\disk\\test.md";//见本文件夹下同名文件
        List<String> strList= List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour");
        editor.executeCommand("load "+filename);
        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        System.out.println(file.getLines());
        assertEquals(strList, file.getLines());
    }
}
