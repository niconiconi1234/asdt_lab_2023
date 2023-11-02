package org.fudan.asdt2023.modules.edit.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;

public class InsertCommand extends EditCommand {
    public InsertCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        String[] commandPart = command.split(" ");

        if(NumberUtil.isNumber(commandPart[1])){
            //指定行号
            int line = NumberUtil.getNumber(commandPart[1]);
            if(line < 0){
                System.out.println("非法行号");
                setStatus(ICommandExecutionStatus.EXECUTED_FAILURE);
                return;
            }

            StringBuilder builder = new StringBuilder();
            for(int i = 2; i < commandPart.length - 1; i++){
                builder.append(commandPart[i]);
                builder.append(" ");
            }
            builder.append(commandPart[commandPart.length - 1]);
            String text = builder.toString();

            if(line > context.getLines().size()){
                context.getLines().add(text);
            }
            else{
                context.getLines().add(line - 1, text);
            }

        }
        else{
            //未指定行号，插入末尾
            String text = command.substring(command.indexOf(" ") + 1);
            context.getLines().add(text);
        }
        setStatus(ICommandExecutionStatus.EXECUTED_SUCCESS);

    }

    @Override
    public void undo() {

    }
}
