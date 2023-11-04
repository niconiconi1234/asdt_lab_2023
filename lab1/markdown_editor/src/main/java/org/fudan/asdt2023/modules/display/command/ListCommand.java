package org.fudan.asdt2023.modules.display.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.display.command.i.DisplayCommand;

public class ListCommand extends DisplayCommand {
    public ListCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        for(String line:context.getLines()){
            System.out.println(line);
        }
        setStatus(ICommandExecutionStatus.EXECUTED_SUCCESS);
    }
}
