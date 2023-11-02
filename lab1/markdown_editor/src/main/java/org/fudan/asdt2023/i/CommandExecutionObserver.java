package org.fudan.asdt2023.i;

public interface CommandExecutionObserver {
    void beforeCommandExecute(ICommand command);

    void afterCommandExecute(ICommand command);
}
