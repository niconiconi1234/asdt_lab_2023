package org.fudan.asdt2023.i;

public interface CommandExecutionObserver {
    void beforeCommandExecute(ICommand command, String cmd);

    void afterCommandExecute(ICommand command, String cmd);
}
