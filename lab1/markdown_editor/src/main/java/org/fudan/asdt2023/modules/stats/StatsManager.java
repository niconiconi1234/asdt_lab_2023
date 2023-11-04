package org.fudan.asdt2023.modules.stats;

import lombok.Getter;
import lombok.Setter;
import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.stats.util.StatsFileUtil;
import org.fudan.asdt2023.utils.TimeUtil;

public class StatsManager implements CommandExecutionObserver {
    @Getter
    @Setter
    private String curFilename = "";

    @Getter
    @Setter
    private String openTime = "";

    public StatsManager() {
        // session开始
        initSessionStats();
    }

    private void initSessionStats() {
        StatsFileUtil.initFile();
        StatsFileUtil.writeLine("session start "+ TimeUtil.getCurrentTime());
    }

    @Override
    public void beforeCommandExecute(ICommand command, String cmd) {

    }

    @Override
    public void afterCommandExecute(ICommand command, String cmd) {
        if (cmd.contains("load") && command.getStatus() == ICommand.ICommandExecutionStatus.EXECUTED_SUCCESS) {
            // 成功load文件，记录文件名及打开时间
            setCurFilename(cmd.split(" ")[1]);
            setOpenTime(TimeUtil.getCurrentTime());
        } else if (cmd.contains("save") && command.getStatus() == ICommand.ICommandExecutionStatus.EXECUTED_SUCCESS) {
            // 成功save文件，计算统计信息并记录到统计文件中
            String duration = TimeUtil.getDuration(getOpenTime(), TimeUtil.getCurrentTime());
            StatsFileUtil.writeLine(getCurFilename()+" "+duration);
            setCurFilename("");
            setOpenTime("");
        }
    }
}
