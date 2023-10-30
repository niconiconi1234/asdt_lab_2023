package org.fudan.asdt2023.modules.edit;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;
import org.fudan.asdt2023.main.EditingFile;

import java.util.function.Function;

public class EditModule extends Module {
    public EditModule(Function<Void, Object> fetchContext) {
        super(fetchContext);
    }

    @Override
    public ICommand parseCommand(String command) {
        Object context = fetchContext.apply(null);
        assert context.getClass().equals(EditingFile.class);

        return null;
    }
}
