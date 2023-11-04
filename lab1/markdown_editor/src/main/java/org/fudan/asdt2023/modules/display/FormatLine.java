package org.fudan.asdt2023.modules.display;

import lombok.Getter;
import lombok.Setter;

import static org.fudan.asdt2023.modules.display.FormatLine.LineType.*;

@Getter
@Setter
public class FormatLine {

    public enum LineType {
        TITLE, SORTED_LIST, UNSORTED_LIST
    }


    private String text;
    private int level;
    private LineType type;


    public FormatLine(String line,int lastLevel,LineType lastLineType) {
        String symbol = line.substring(0, line.indexOf(" "));
        String content = line.substring(line.indexOf(" "));
        if (symbol.startsWith("#")) {
            this.level = symbol.length();
            this.text = content;
            this.type = TITLE;
        } else if (symbol.startsWith("*") || symbol.startsWith("-") || symbol.startsWith("+")) {
            this.level = lastLineType == UNSORTED_LIST ? lastLevel : lastLevel+1;
            this.text = "·" + content;
            this.type = UNSORTED_LIST;

        } else {
            this.level = lastLineType == SORTED_LIST? lastLevel: lastLevel+1;
            this.text = line;
            this.type = SORTED_LIST;
        }


    }


}
