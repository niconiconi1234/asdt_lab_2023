package org.fudan.asdt2023.modules.disk;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.MarkdownEditor;

import java.util.function.Function;

public class DiskModule extends Module {

    public DiskModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(MarkdownEditor.class);

        return null;
    }
}
