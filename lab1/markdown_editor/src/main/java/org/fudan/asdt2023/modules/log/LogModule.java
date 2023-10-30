package org.fudan.asdt2023.modules.log;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;

import java.util.function.Function;

public class LogModule extends Module {
    public LogModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(LogManager.class);
        return null;
    }
}
