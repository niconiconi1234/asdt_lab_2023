package org.fudan.asdt2023.modules.disk;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.disk.command.SaveCommand;
import org.fudan.asdt2023.modules.disk.command.LoadCommand;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SaveCommandTest {
    private MarkdownEditor editor;

    @Before
    public void beforeTest(){
        editor = new MarkdownEditor();
        DiskModule diskModule = new DiskModule(v -> MarkdownEditorSingleton.getInstance());
        editor.addModule("save", diskModule);

    }

    @Test
    public void SaveCommand() throws IOException {
        String filename = "src\\test\\java\\org\\fudan\\asdt2023\\modules\\disk\\test.md";//见本文件夹下同名文件
        EditingFile file = MarkdownEditorSingleton.getInstance().getCurFile();
        file.setName(filename);
        System.out.println("1" + filename);
        List<String> strList= List.of("# hello", "## hi", "# hello", "## hallo", "* bon jour");
        file.setLines(strList);
        editor.executeCommand("save");
        assertEquals(strList, LoadCommand.getFileContent(file.getName()));
    }
}
