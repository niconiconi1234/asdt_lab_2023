package org.fudan.asdt2023.modules.log;

import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.modules.edit.EditModule;
import org.fudan.asdt2023.modules.log.util.LogFileUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HistoryCommandTest {
    private MarkdownEditor editor;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void beforeTest(){
        LogFileUtil.setLogFileName("log_test.txt");
        editor = new MarkdownEditor();

        // edit module
        EditModule editModule = new EditModule(v -> editor.getCurFile());
        editor.addModule("edit", editModule);

        // log module
        CommandExecutionObserver logManager = new LogManager();
        editor.addObserver("log", logManager);
        LogModule logModule = new LogModule(v -> editor.getObserver("log"));
        editor.addModule("log", logModule);

        editor.executeCommand("insert");
        editor.executeCommand("delete");

        System.setOut(new PrintStream(outputStream));
    }

    public void checkOutput(String output, int number) {
        String[] histories = output.split("\n");
        assertEquals(number, histories.length);
        assertTrue(histories[0].contains("delete"));
    }

    @Test
    public void historyWithMoreArgs(){
        try {
            Module module = editor.getModule("log");
            ICommand iCommand = module.parseCommand("history 1 1");
            iCommand.execute();
            Assert.fail();
        } catch (Exception e) {
            assertEquals("请输入正确的参数个数", e.getMessage());
        }
    }

    @Test
    public void historyWithWrongNumber(){
        try {
            Module module = editor.getModule("log");
            ICommand iCommand = module.parseCommand("history -1");
            iCommand.execute();
            Assert.fail();
        } catch (Exception e) {
            assertEquals("请输入大于0的整数", e.getMessage());
        }
    }

    @Test
    public void historyWithRightNumber(){
        try {
            Module module = editor.getModule("log");
            ICommand iCommand = module.parseCommand("history 1");
            iCommand.execute();
            String consoleOutput = outputStream.toString().trim();
            checkOutput(consoleOutput, 1);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void historyWithNoNumber(){
        try {
            Module module = editor.getModule("log");
            ICommand iCommand = module.parseCommand("history");
            iCommand.execute();
            String consoleOutput = outputStream.toString().trim();
            checkOutput(consoleOutput, 2);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @After
    public void afterTest(){
        System.setOut(originalOut);
        File file = new File("log_test.txt");
        try(FileWriter writer = new FileWriter(file, false)) {
            writer.write("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
