package org.fudan.asdt2023.main;

import org.fudan.asdt2023.i.ICommand;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class MarkdownEditorTest {

    @Test
    public void testProxyCommand() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MarkdownEditor markdownEditor = MarkdownEditorSingleton.getInstance();

        // 因为proxyCommand是private，所以这里只能用反射来调用
        var proxyCommandMethod = markdownEditor.getClass().getDeclaredMethod("proxyCommand", ICommand.class);
        proxyCommandMethod.setAccessible(true);

        // 分别创建两个ICommand对象，一个是成功执行的，一个是失败执行的（执行失败的ICommand对象的execute方法会抛出异常）
        ICommand successCmd = new ICommand() {

            private ICommandExecutionStatus status = ICommandExecutionStatus.NOT_EXECUTED;

            @Override
            public void execute() {
                System.out.println("execute success");
            }

            @Override
            public ICommandExecutionStatus getStatus() {
                return status;
            }

            @Override
            public void setStatus(ICommandExecutionStatus status) {
                this.status = status;
            }
        };
        ICommand failureCmd = new ICommand() {

            private ICommandExecutionStatus status = ICommandExecutionStatus.NOT_EXECUTED;

            @Override
            public void execute() {
                throw new RuntimeException("execute failed");
            }

            @Override
            public ICommandExecutionStatus getStatus() {
                return status;
            }

            @Override
            public void setStatus(ICommandExecutionStatus status) {
                this.status = status;
            }
        };

        // 获得两个ICommand对象的代理对象
        ICommand successCmdProxy = (ICommand) proxyCommandMethod.invoke(markdownEditor, successCmd);
        ICommand failureCmdProxy = (ICommand) proxyCommandMethod.invoke(markdownEditor, failureCmd);

        assertEquals(ICommand.ICommandExecutionStatus.NOT_EXECUTED, successCmd.getStatus());
        assertEquals(ICommand.ICommandExecutionStatus.NOT_EXECUTED, failureCmd.getStatus());

        successCmdProxy.execute();
        failureCmdProxy.execute();

        assertEquals(ICommand.ICommandExecutionStatus.EXECUTED_SUCCESS, successCmdProxy.getStatus());
        assertEquals(ICommand.ICommandExecutionStatus.EXECUTED_FAILURE, failureCmdProxy.getStatus());
    }
}
