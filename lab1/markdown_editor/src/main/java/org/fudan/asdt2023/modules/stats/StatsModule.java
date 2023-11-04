package org.fudan.asdt2023.modules.stats;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.modules.stats.command.StatsCommand;

import java.util.function.Function;

public class StatsModule extends Module {
    public StatsModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(StatsManager.class);

        //获取上下文
        StatsManager observerContext = (StatsManager)context;

        //解析命令
        String cmd = !command.contains(" ") ? command : command.substring(0, command.indexOf(" "));

        ICommand iCommand;
        if ("stats".equals(cmd)) {
            iCommand = new StatsCommand(observerContext, command);
        } else {
            iCommand = null;
        }


        return iCommand;
    }
}
