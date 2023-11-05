package org.fudan.asdt2023.modules.disk.command;


import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.modules.disk.command.i.DiskCommand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveCommand extends DiskCommand {
    public SaveCommand(MarkdownEditor editor) {
        super(editor);
    }


    @Override
    public void execute() {

        List<String> strList = context.getCurFile().getLines();
        File write = new File(context.getCurFile().getName());
        FileWriter writer= null;
        try {
            writer = new FileWriter(write);
            for(String line: strList){
                writer.write(line+"\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
