package org.fudan.asdt2023.modules.undoredo;

import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.modules.edit.command.i.EditCommand;

import java.util.Stack;

public class UndoRedoManager implements CommandExecutionObserver {
    private Stack<EditCommand> commandStack;
    private Stack<EditCommand> redoStack;

    @Override
    public void onCommandExecuted(ICommand command) {
        if(command instanceof EditCommand)
            recordDo((EditCommand) command);
    }

    public void recordDo(EditCommand command){
        // 保存命令
        this.commandStack.push(command);
        // 有新的命令执行，清空 redoStack
        if(!this.redoStack.empty())
            this.redoStack.clear();
    }

    public void undoOnce() {
        // 取出栈顶命令
        this.redoStack.push(this.commandStack.pop());
        // undo
        this.redoStack.peek().execute();
    }

    public void redoOnce() {
        // redoStack 为空，则不执行 redo 命令
        if(this.redoStack.empty())
            return;
        // 取出栈顶命令
        // 保存命令但不清空 redoStack
        this.commandStack.push(this.redoStack.pop());
        // 执行
        this.commandStack.peek().execute();
    }
}
