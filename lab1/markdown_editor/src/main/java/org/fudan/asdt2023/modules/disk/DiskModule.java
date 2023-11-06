package org.fudan.asdt2023.modules.disk;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.modules.disk.command.LoadCommand;
import org.fudan.asdt2023.modules.disk.command.SaveCommand;

import java.util.function.Function;

public class DiskModule extends Module {

    public DiskModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(MarkdownEditor.class);

        MarkdownEditor fileContext = (MarkdownEditor)context;

//解析命令
        if(command.contains("load")){
            if (!command.contains(" ")) return null;
            String[] arg = command.split(" ");
            if (!arg[0].equals("load")) return null;
            String fileName = arg[1];
            return new LoadCommand(fileContext, fileName);
        }
        else if(command.equals("save")){
            return new SaveCommand(fileContext);
        }
        return null;
    }
}
