package org.fudan.asdt2023.modules.undoredo;

import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;

import java.util.Stack;

public class UndoRedoManager implements CommandExecutionObserver {
    private Stack<EditCommand> commandStack;
    private Stack<EditCommand> redoStack;

    @Override
    public void beforeCommandExecute(ICommand command) {

    }

    @Override
    public void afterCommandExecute(ICommand command) {
        if(command.getStatus() == ICommand.ICommandExecutionStatus.EXECUTED_SUCCESS)
            if(command instanceof EditCommand)
                recordDo((EditCommand) command);
    }

    public void recordDo(EditCommand command){
        // 保存命令
        commandStack.push(command);
        // 有新的命令执行，清空 redoStack
        if(!redoStack.empty())
            redoStack.clear();
    }

    public void undoOnce() {
        // 取出栈顶命令
        EditCommand topCommand = commandStack.peek();
        try{
            topCommand.undo();
            redoStack.push(topCommand);
        } catch (Exception e){
            // 正常情况下不会抛出异常
            throw new RuntimeException("undo 出现异常：" + e.getMessage());
        }
    }

    public void redoOnce() {
        // redoStack 为空，则不执行 redo 命令
        if(redoStack.empty())
            return;
        // 取出栈顶命令
        // 保存命令但不清空 redoStack
        EditCommand topCommand = redoStack.peek();
        // 执行
        try{
            topCommand.execute();
            commandStack.push(topCommand);
        } catch (Exception e) {
            // 正常情况下不会抛出异常
            throw new RuntimeException("redo 出现异常：" + e.getMessage());
        }
    }
}
