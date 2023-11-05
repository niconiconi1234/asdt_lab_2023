package org.fudan.asdt2023.modules.stats;

import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.modules.disk.DiskModule;
import org.fudan.asdt2023.modules.stats.util.StatsFileUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class StatsCommandTest {
    private MarkdownEditor editor;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void beforeTest(){
        StatsFileUtil.setStatsFileName("stats_test.txt");
        editor = new MarkdownEditor();

        // disk module
        DiskModule diskModule = new DiskModule(v -> editor);
        editor.addModule("disk", diskModule);

        // stats module
        CommandExecutionObserver statsManager = new StatsManager();
        editor.addObserver("stats", statsManager);
        StatsModule statsModule = new StatsModule(v -> editor.getObserver("stats"));
        editor.addModule("stats", statsModule);
    }

    @Test
    public void statsWithMoreArgs(){
        try {
            Module module = editor.getModule("stats");
            ICommand iCommand = module.parseCommand("stats 1 1");
            iCommand.execute();
            Assert.fail();
        } catch (Exception e) {
            assertEquals("请输入正确的参数个数", e.getMessage());
        }
    }

    @Test
    public void statsWithWrongArg(){
        try {
            Module module = editor.getModule("stats");
            ICommand iCommand = module.parseCommand("stats test");
            iCommand.execute();
            Assert.fail();
        } catch (Exception e) {
            assertEquals("参数仅可选择all/current", e.getMessage());
        }
    }

    @Test
    public void statsWithNoOpenFile() {
        try {
            System.setOut(new PrintStream(outputStream));

            Module module = editor.getModule("stats");
            ICommand iCommand = module.parseCommand("stats");
            iCommand.execute();

            String consoleOutput = outputStream.toString().trim();
            String[] output = consoleOutput.split("\n");
            assertEquals(2, output.length);
            assertEquals("当前没有打开的Markdown文件", output[1]);

            System.setOut(originalOut);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void statsCurrentSuccessful () {
        try {
            // load文件
            editor.executeCommand("load test.md");

            // 查看统计信息
            System.setOut(new PrintStream(outputStream));

            Module module = editor.getModule("stats");
            ICommand iCommand = module.parseCommand("stats");
            iCommand.execute();

            String consoleOutput = outputStream.toString().trim();
            System.setOut(originalOut);

            String[] output = consoleOutput.split("\n");
            assertEquals(2, output.length);
            Assert.assertTrue(output[1].contains("当前文件：test.md"));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void statsAllSuccessful () {
        try {
            Module module = editor.getModule("stats");

            // load文件
            editor.executeCommand("load test.md");

            // 查看统计信息
            System.setOut(new PrintStream(outputStream));
            ICommand iCommand1 = module.parseCommand("stats all");
            iCommand1.execute();

            String consoleOutput1 = outputStream.toString().trim();
            System.setOut(originalOut);

            String[] output1 = consoleOutput1.split("\n");
            assertEquals(1, output1.length);

            // save文件
            editor.executeCommand("save");

            // 查看统计信息
            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream2));
            ICommand iCommand2 = module.parseCommand("stats all");
            iCommand2.execute();

            String consoleOutput2 = outputStream2.toString().trim();
            System.setOut(originalOut);

            String[] output2 = consoleOutput2.split("\n");
            assertEquals(2, output2.length);
            Assert.assertTrue(output2[1].startsWith("test.md"));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @After
    public void afterTest(){
        File file = new File("stats_test.txt");
        try(FileWriter writer = new FileWriter(file, false)) {
            writer.write("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
