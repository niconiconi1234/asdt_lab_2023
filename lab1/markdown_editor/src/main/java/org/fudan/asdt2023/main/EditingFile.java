package org.fudan.asdt2023.main;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class EditingFile {
    String name;
    @Getter
    List<String> lines;

    public EditingFile(){
        lines = new ArrayList<>();
    }
}
