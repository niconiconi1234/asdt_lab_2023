package org.fudan.asdt2023.modules.stats.command;

import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.stats.StatsManager;
import org.fudan.asdt2023.modules.stats.util.StatsFileUtil;
import org.fudan.asdt2023.utils.NumberUtil;
import org.fudan.asdt2023.utils.TimeUtil;

import java.util.List;
import java.util.Objects;

public class StatsCommand implements ICommand {
    private final StatsManager context;
    protected final String command;
    protected ICommandExecutionStatus status;

    public StatsCommand(StatsManager context, String command) {
        this.context = context;
        this.command = command;
    }

    /**
     * stats [all | current]
     * all 或 current 参数可选。
     * 显示当前回话中指定文件或所有文件的工作时长统计。
     * 默认为 `current`，显示当前文件的统计信息。
     * 根据不同时长，以可读的方式显示不同的时长单位。例如，65 分钟显示为 1 小时 5 分钟。
     */
    @Override
    public void execute() {
        List<String> statistics = StatsFileUtil.readSessionStats();

        String[] commandPart = command.split(" ");
        if (commandPart.length == 1) {
            System.out.println(statistics.get(0));
            printCurrentStats();
        }else if (commandPart.length == 2){
            if (Objects.equals(commandPart[1], "all")){
                printStats(statistics);
            }else if (Objects.equals(commandPart[1], "current")) {
                System.out.println(statistics.get(0));
                printCurrentStats();
            }else {
                throw new RuntimeException("参数仅可选择all/current");
            }
        }else {
            throw new RuntimeException("请输入正确的参数个数");
        }
    }

    private void printStats(List<String> stats) {
        for (String stat : stats) {
            System.out.println(stat);
        }
    }

    private void printCurrentStats() {
        if (Objects.equals(context.getCurFilename(), "")){
            System.out.println("当前没有打开的Markdown文件");
        } else {
            String duration = TimeUtil.getDuration(context.getOpenTime(), TimeUtil.getCurrentTime());
            System.out.println("当前文件："+context.getCurFilename()+" "+duration);
        }
    }

    @Override
    public ICommandExecutionStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ICommandExecutionStatus status) {
        this.status = status;
    }
}
