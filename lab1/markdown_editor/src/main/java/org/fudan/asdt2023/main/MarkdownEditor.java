package org.fudan.asdt2023.main;

import lombok.Getter;
import lombok.Setter;
import org.fudan.asdt2023.i.CommandExecutionObserver;
import org.fudan.asdt2023.i.ICommand;
import org.fudan.asdt2023.i.Module;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
        iCommand.setStatus(ICommand.ICommandExecutionStatus.NOT_EXECUTED);

        // 动态代理，在代理类中处理掉execute产生的异常，并设置command的状态
        ICommand proxiedCommand = proxyCommand(iCommand);

        // 通知observers，command开始执行了
        for (CommandExecutionObserver o : observers.values()) {
            o.beforeCommandExecute(iCommand);
        }

        // 执行command
        proxiedCommand.execute();

        // 通知observers，command执行完毕了
        for (CommandExecutionObserver o : observers.values()) {
            o.afterCommandExecute(iCommand);
        }
    }

    private ICommand proxyCommand(ICommand command) {

        // 动态代理
        // 如果调用的是execute方法，则在代理类中处理掉execute产生的异常，并设置command的状态
        var executeInvocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method execute = ICommand.class.getMethod("execute");
                if (method.equals(execute)) {
                    return invokeExecute(proxy, method, args);
                } else {
                    return invokeOther(proxy, method, args);
                }
            }

            public Object invokeExecute(Object proxy, Method method, Object[] args) throws Throwable {
                ICommand cmd = (ICommand) proxy;
                try {
                    method.invoke(command, args);
                    cmd.setStatus(ICommand.ICommandExecutionStatus.EXECUTED_SUCCESS);
                } catch (Exception e) {
                    cmd.setStatus(ICommand.ICommandExecutionStatus.EXECUTED_FAILURE);
                }
                return null;
            }

            public Object invokeOther(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(command, args);
            }
        };

        return (ICommand) java.lang.reflect.Proxy.newProxyInstance(
                command.getClass().getClassLoader(),
                command.getClass().getInterfaces(),
                executeInvocationHandler);
    }
}
