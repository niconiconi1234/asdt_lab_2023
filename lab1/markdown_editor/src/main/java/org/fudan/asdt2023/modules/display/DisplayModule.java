package org.fudan.asdt2023.modules.display;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.main.MarkdownEditor;
import org.fudan.asdt2023.modules.display.command.DirTreeCommand;
import org.fudan.asdt2023.modules.display.command.ListCommand;
import org.fudan.asdt2023.modules.display.command.ListTreeCommand;
import org.fudan.asdt2023.modules.edit.command.AppendHeadCommand;
import org.fudan.asdt2023.modules.edit.command.AppendTailCommand;
import org.fudan.asdt2023.modules.edit.command.DeleteCommand;
import org.fudan.asdt2023.modules.edit.command.InsertCommand;

import java.util.function.Function;

public class DisplayModule extends Module {

    public DisplayModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(EditingFile.class);
        //获取上下文
        EditingFile fileContext = (EditingFile)context;

        //解析命令
        String cmd = command.substring(0, command.contains(" ") ?command.indexOf(" "):command.length());

        ICommand iCommand = switch (cmd) {
            case "list" -> new ListCommand(fileContext, command);
            case "list-tree" -> new ListTreeCommand(fileContext, command);
            case "dir-tree" -> new DirTreeCommand(fileContext, command);
            default -> null;
        };

        return iCommand;
    }
}
