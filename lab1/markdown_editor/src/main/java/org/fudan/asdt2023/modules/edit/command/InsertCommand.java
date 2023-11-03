package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;

public class InsertCommand extends EditCommand {
    public InsertCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void parseCommand() {
        String[] commandPart = command.split(" ");

        if(NumberUtil.isNumber(commandPart[1])){
            //指定行号
            editLineNo = NumberUtil.getNumber(commandPart[1]);
            if(editLineNo <= 0)
                throw new RuntimeException("非法行号 " + editLineNo);
            editLineNo = Math.min(editLineNo, context.numLines() + 1);

            StringBuilder builder = new StringBuilder();
            for(int i = 2; i < commandPart.length - 1; i++){
                builder.append(commandPart[i]);
                builder.append(" ");
            }
            builder.append(commandPart[commandPart.length - 1]);
            editString = builder.toString();
        }
        else{
            //未指定行号，插入末尾
            editLineNo = context.numLines() + 1;
            editString = command.substring(command.indexOf(" ") + 1);
        }
    }

    @Override
    public void edit() {
        context.getLines().add(editLineNo - 1, editString);
    }

    @Override
    public void undo() {
        context.getLines().remove(editLineNo - 1);
    }
}
