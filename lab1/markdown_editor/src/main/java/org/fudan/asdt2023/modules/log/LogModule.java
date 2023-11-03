package org.fudan.asdt2023.modules.log;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.modules.log.command.HistoryCommand;

import java.util.function.Function;

public class LogModule extends Module {
    public LogModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }
    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(LogManager.class);

        //获取上下文
        LogManager observerContext = (LogManager)context;

        //解析命令
        String cmd = !command.contains(" ") ? command : command.substring(0, command.indexOf(" "));

        ICommand iCommand;
        if ("history".equals(cmd)) {
            iCommand = new HistoryCommand(observerContext, command);
        } else {
            iCommand = null;
        }


        return iCommand;
    }
}
