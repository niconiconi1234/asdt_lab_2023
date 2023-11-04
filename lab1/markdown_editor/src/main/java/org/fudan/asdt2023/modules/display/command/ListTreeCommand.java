package org.fudan.asdt2023.modules.display.command;

import org.fudan.asdt2023.main.EditingFile;
import org.fudan.asdt2023.modules.display.FormatLine;
import org.fudan.asdt2023.modules.display.command.i.DisplayCommand;

public class ListTreeCommand extends DisplayCommand {
    public ListTreeCommand(EditingFile context, String command) {
        super(context, command);
    }

    @Override
    public void execute() {
        String space = "    ";
        int lastLevel = 0;
        FormatLine.LineType lastLineType = FormatLine.LineType.TITLE;
        for(int i = 0; i < context.getLines().size(); i++) {
            String line = context.getLines().get(i);
            String nextLine = i < context.getLines().size() - 1 ? context.getLines().get(i + 1) : null;

            FormatLine formatLine = new FormatLine(line, lastLevel,lastLineType);
            int curLevel = formatLine.getLevel();
            int nextLevel = nextLine == null ? 0 : new FormatLine(nextLine, curLevel,formatLine.getType()).getLevel();

            StringBuilder formattedText = new StringBuilder();
            formattedText.append(space.repeat(curLevel - 1));
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
