package org.fudan.asdt2023.main;

public class MarkdownEditorSingleton {
    private MarkdownEditorSingleton() {
    }

    private static final class MarkdownEditorHolder {
        static final MarkdownEditor instance = new MarkdownEditor();
    }

    public static MarkdownEditor getInstance() {
        return MarkdownEditorHolder.instance;
    }
}
