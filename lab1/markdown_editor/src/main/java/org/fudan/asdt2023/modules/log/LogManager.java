package org.fudan.asdt2023.modules.log;

import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.log.util.LogFileUtil;
import org.fudan.asdt2023.utils.TimeUtil;

import java.util.List;

public class LogManager implements CommandExecutionObserver {
    public LogManager() {
        // session开始
        initSessionLog();
    }

    private void initSessionLog() {
        LogFileUtil.initFile();
        LogFileUtil.writeLine("session start "+ TimeUtil.getCurrentTime());
    }

    @Override
    public void beforeCommandExecute(ICommand command, String cmd) {
    }

    @Override
    public void afterCommandExecute(ICommand command, String cmd) {
        // 在日志文件中记录每次command
        if (!cmd.contains("history")) {
            LogFileUtil.writeLine(TimeUtil.getCurrentTime()+" "+cmd);
        }
    }
}
