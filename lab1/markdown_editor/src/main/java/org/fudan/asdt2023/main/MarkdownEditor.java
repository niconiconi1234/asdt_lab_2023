package org.fudan.asdt2023.main;

import lombok.Getter;
import lombok.Setter;
import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;

import java.util.HashMap;
import java.util.Map;

public class MarkdownEditor {
    private final Map<String, Module> modules = new HashMap<>();
    private final Map<String, CommandExecutionObserver> observers = new HashMap<>();

    @Getter
    @Setter
    private EditingFile curFile = new EditingFile();

    public void addModule(String name, Module module) {
        modules.put(name, module);
    }

    public void removeModule(String name) {
        modules.remove(name);
    }

    public void addObserver(String name, CommandExecutionObserver observer) {
        observers.put(name, observer);
    }

    public void removeObserver(String name) {
        observers.remove(name);
    }

    public CommandExecutionObserver getObserver(String name) {
        return observers.get(name);
    }

    public void executeCommand(String command) {
        // 迭代modules，看看哪个module能够处理这个command
        ICommand iCommand = null;
        for (Module m : modules.values()) {
            iCommand = m.parseCommand(command);
            if (iCommand != null) {
                break;
            }
        }

        // 如果没有module能够处理这个command，那么就直接返回
        if (iCommand == null) {
            System.out.println("No module can handle this command");
            return;
        }

        // 通知observers，command开始执行了
        for (CommandExecutionObserver o : observers.values()) {
            o.onCommandExecuted(iCommand);
        }

        // 执行command
        iCommand.execute();
    }


}
