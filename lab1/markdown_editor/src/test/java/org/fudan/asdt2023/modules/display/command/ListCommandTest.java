package org.fudan.asdt2023.modules.display.command;

import junit.framework.TestCase;
import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.main.MarkdownEditorSingleton;
import org.fudan.asdt2023.modules.display.DisplayModule;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ListCommandTest {
    private PrintStream console = null;
    private ByteArrayOutputStream bytes = null;

    private MarkdownEditor editor;
    @Before
    public void beforeTest(){
        editor = MarkdownEditorSingleton.getInstance();

        // edit module
        EditModule editModule = new EditModule(v -> MarkdownEditorSingleton.getInstance().getCurFile());
        editor.addModule("edit", editModule);

        DisplayModule displayModule = new DisplayModule(v->MarkdownEditorSingleton.getInstance().getCurFile());
        editor.addModule("display",displayModule);
    }

    @Before/*获取控制台输出*/
    public void init() {
        bytes = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));
    }

    @After/*恢复控制台输出*/
    public void tearDown() {
        System.setOut(console);
    }

    @Test
    public void list(){
        editor.executeCommand("append-head # hello");
        editor.executeCommand("append-tail ## java");
        editor.executeCommand("append-tail ## C++");
        editor.executeCommand("append-tail * list1");
        editor.executeCommand("append-tail 1. list2-1");
        editor.executeCommand("append-tail 2. list2-2");

        editor.executeCommand("list ");
        assertEquals("# hello\r\n" +
                "## java\r\n" +
                "## C++\r\n" +
                "* list1\r\n" +
                "1. list2-1\r\n" +
                "2. list2-2",bytes.toString().trim());
    }
}