# uml_design

## Workflow

1. 读取用户的输入
2. 调用`MarkdownEditor.executeCommand(String)`方法
3. `MarkdownEditor.executeCommand(String)`方法迭代所有的modules，找到能处理该命令的module，生成`ICommand`对象
4. 迭代所有的observer，调用`CommandExecutionObserver.onCommandExecuted(ICommand)`方法
5. 调用`ICommand.execute()`方法