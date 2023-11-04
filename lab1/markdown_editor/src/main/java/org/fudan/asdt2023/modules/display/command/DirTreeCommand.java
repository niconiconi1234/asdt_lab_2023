package org.fudan.asdt2023.modules.display.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.disk.command.i.DiskCommand;
import org.fudan.asdt2023.modules.display.FormatLine;
import org.fudan.asdt2023.modules.display.command.i.DisplayCommand;

public class DirTreeCommand extends DisplayCommand {
    public DirTreeCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        String directory = command.substring(command.indexOf(" ")+1);
        int index = -1;
        for(int i = 0; i < context.getLines().size(); i++){
            String line = context.getLines().get(i);
            if(directory.equals(line.substring(line.indexOf(" ")+1))){
                index = i;
                break;
            }
        }
        if(index == -1){
            System.out.println("-1");
            return;
        }

        String space = "    ";
        FormatLine firstLine = new FormatLine(context.getLines().get(index), 0, FormatLine.LineType.TITLE);
        if(firstLine.getType() != FormatLine.LineType.TITLE){
            return;
        }
        int beginLevel = firstLine.getLevel();
        int lastLevel = 0;
        FormatLine.LineType lastLineType = FormatLine.LineType.TITLE;

        for(int i = index; i < context.getLines().size(); i++) {
            String line = context.getLines().get(i);
            String nextLine = i < context.getLines().size() - 1 ? context.getLines().get(i + 1) : null;

            FormatLine formatLine = new FormatLine(line, lastLevel,lastLineType);
            int curLevel = formatLine.getLevel();
            if(formatLine.getType() == FormatLine.LineType.TITLE && curLevel <= lastLevel ){
                break;
            }
            int nextLevel = nextLine == null ? 0 : new FormatLine(nextLine, curLevel,formatLine.getType()).getLevel();

            StringBuilder formattedText = new StringBuilder();
            formattedText.append(space.repeat(curLevel -beginLevel));
            if (curLevel == nextLevel) {
                formattedText.append("├──");
            } else {
                formattedText.append("└──");
            }
            formattedText.append(formatLine.getText());
            lastLevel = curLevel;
            lastLineType = formatLine.getType();
            System.out.println(formattedText);
        }
        setStatus(ICommandExecutionStatus.EXECUTED_SUCCESS);
    }
}
