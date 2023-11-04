package org.fudan.asdt2023.modules.disk.command;


import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.modules.disk.command.i.DiskCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadCommand extends DiskCommand {

    private final String filename;

   public String getFilename(){
       return this.filename;
   }
    public LoadCommand(MarkdownEditor editor, String filename) {
        super(editor);
        this.filename = filename;
    }

    public static List<String> getFileContent(String path) throws IOException {
        List<String> strList = new ArrayList<String>();
        File file = new File(path);

        InputStreamReader read = null;
        BufferedReader reader = null;
        read = new InputStreamReader(new FileInputStream(file),"utf-8");
        reader = new BufferedReader(read);

        String line;
        while ((line = reader.readLine()) != null) {
            strList.add(line);
        }
        read.close();
        return strList;
    }


    @Override
    public void execute() {
        try {
            List<String> strList = getFileContent(filename);
            context.getCurFile().setLines(strList);
            context.getCurFile().setName(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
