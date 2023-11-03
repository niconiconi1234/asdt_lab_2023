package org.fudan.asdt2023.modules.edit;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.AppendHeadCommand;
import org.fudan.asdt2023.modules.edit.command.AppendTailCommand;
import org.fudan.asdt2023.modules.edit.command.DeleteCommand;
import org.fudan.asdt2023.modules.edit.command.InsertCommand;

import java.util.function.Function;

public class EditModule extends Module {
    public EditModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(EditingFile.class);
        //获取上下文
        EditingFile fileContext = (EditingFile)context;

        //解析命令
        String cmd = !command.contains(" ") ? command : command.substring(0, command.indexOf(" "));

        ICommand iCommand;
        switch (cmd){
            case "insert": iCommand = new InsertCommand(fileContext, command); break;
            case "append-head": iCommand = new AppendHeadCommand(fileContext, command);break;
            case "append-tail": iCommand = new AppendTailCommand(fileContext, command);break;
            case "delete": iCommand = new DeleteCommand(fileContext, command);break;
            default: iCommand = null;
        }


        return iCommand;
    }
}
