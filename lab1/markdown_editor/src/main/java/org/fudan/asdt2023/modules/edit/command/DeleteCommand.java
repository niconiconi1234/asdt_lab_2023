package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;
import org.fudan.asdt2023.utils.NumberUtil;

public class DeleteCommand extends EditCommand {
    public DeleteCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        String[] commandPart = command.split(" ");

        if(NumberUtil.isNumber(commandPart[1])){
            //删除指定行号
            int line = NumberUtil.getNumber(commandPart[1]);
            if(line < 0 || line >= context.getLines().size()){
                System.out.println("非法行号");
                setStatus(ICommandExecutionStatus.EXECUTED_FAILURE);
                return;
            }
            context.getLines().remove(line - 1);
            setStatus(ICommandExecutionStatus.EXECUTED_SUCCESS);
        }
        else{
            //直接删除
            String text = command.substring(command.indexOf(" ") + 1);
            for(String str: context.getLines()){
                if(str.endsWith(text)){
                    context.getLines().remove(str);
                    setStatus(ICommandExecutionStatus.EXECUTED_SUCCESS);
                    return;
                }
            }
            //没有找到相应的文本，删除失败
            setStatus(ICommandExecutionStatus.EXECUTED_FAILURE);
        }

    }

    @Override
    public void undo() {

    }
}
