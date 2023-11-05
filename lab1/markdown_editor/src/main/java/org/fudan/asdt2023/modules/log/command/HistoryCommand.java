package org.fudan.asdt2023.modules.log.command;

import org.fudan.asdt2023.modules.log.LogManager;
import org.fudan.asdt2023.modules.log.command.i.LogCommand;
import org.fudan.asdt2023.modules.log.util.LogFileUtil;
import org.fudan.asdt2023.utils.NumberUtil;

import java.util.List;

public class HistoryCommand extends LogCommand {

    public HistoryCommand(LogManager context, String command) {
        super(context, command);
    }

    /**
     * history [数量]
     * 数量参数可选。
     * 列出指定数量的最近历史命令，包括执⾏命令的时间戳。
     * 默认显示全部记录，但可以通过参数限制显示的数量。
     * 如果指定的数量大于存储的历史命令数量，也显示全部命令记录。
     */
    @Override
    public void execute() {
        List<String> histories = LogFileUtil.readSessionHistory();

        String[] commandPart = command.split(" ");
        if (commandPart.length == 1) {
            printHistory(histories, histories.size()-1);
        }else if (commandPart.length == 2){
            if (NumberUtil.isNumber(commandPart[1]) && NumberUtil.getNumber(commandPart[1])>0){
                int number = NumberUtil.getNumber(commandPart[1]);
                printHistory(histories, Math.min(number, histories.size() - 1));
            }else {
                throw new RuntimeException("请输入大于0的整数");
            }
        }else {
            throw new RuntimeException("请输入正确的参数个数");
        }
    }

    private void printHistory(List<String> history, int number) {
        for (int i=0;i< number;i++){
            System.out.println(history.get(i));
        }
    }
}
