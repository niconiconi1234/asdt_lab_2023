package org.fudan.asdt2023.i;

import java.util.function.Function;

public abstract class Module {
    protected final Function<Void, Object> fetchContext;

    public Module(Function<Void, Object> fetchContext) {
        this.fetchContext = fetchContext;
    }

    public abstract ICommand parseCommand(String command);
}
