package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;
import org.fudan.asdt2023.utils.NumberUtil;

import java.util.List;

public class DeleteCommand extends EditCommand {
    public DeleteCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void parseCommand() {
        String[] commandPart = command.split(" ");
        List<String> fileLines = context.getLines();

        if(NumberUtil.isNumber(commandPart[1])){
            //删除指定行号
            editLineNo = NumberUtil.getNumber(commandPart[1]);
            if(editLineNo <= 0 || editLineNo > context.getLines().size())
                throw new RuntimeException("非法行号： " + editLineNo);
            editString = fileLines.get(editLineNo - 1);
        }
        else{
            //直接删除
            String text = command.substring(command.indexOf(" ") + 1);
            for(editLineNo = 1;editLineNo <= context.numLines(); editLineNo++){
                if(fileLines.get(editLineNo - 1).endsWith(text)) {
                    editString = fileLines.get(editLineNo - 1);
                    break;
                }
            }
            if(editLineNo > context.numLines())
                // 没有找到相应的文本，删除失败，但不打印日志
                throw new RuntimeException("");
        }
    }

    @Override
    public void edit() {
        context.getLines().remove(editLineNo - 1);
    }

    @Override
    public void undo() {
        context.getLines().add(editLineNo - 1, editString);
    }
}
