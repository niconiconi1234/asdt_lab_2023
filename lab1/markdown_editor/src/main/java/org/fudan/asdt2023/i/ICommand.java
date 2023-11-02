package org.fudan.asdt2023.i;

public interface ICommand {

    public enum ICommandExecutionStatus {
        NOT_EXECUTED, EXECUTED_SUCCESS, EXECUTED_FAILURE
    }

    void execute();

    ICommandExecutionStatus getStatus();

    void setStatus(ICommandExecutionStatus status);
}
