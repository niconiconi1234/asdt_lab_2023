package org.fudan.asdt2023.main;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class EditingFile {
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    List<String> lines;

    public EditingFile(){
        lines = new ArrayList<>();
    }

    public int numLines() { return lines.size(); }
}
