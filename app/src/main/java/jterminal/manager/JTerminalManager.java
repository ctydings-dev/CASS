/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jterminal.manager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class JTerminalManager {

    private int rowCount;

    private int colCount;

    private int index = 0;

    private int maxSize;

    private List<String> data;

    private List<String> orig;

    private String input;

    private int inputCursor;

    private int inputHistory;

    private List<String> oldInput;

    public JTerminalManager(int size, int rowCount, int colCount) {
        this.data = new ArrayList();
        this.orig = new ArrayList();
        this.maxSize = size;
        this.input = "";
        this.rowCount = rowCount;
        this.inputCursor = 0;
        this.colCount = colCount;
        this.oldInput = new ArrayList();
        this.inputHistory = 0;
    }

    public void updateDimensions(int height, int width) {
        this.data = new ArrayList();
        List<String> toAdd = this.orig;
        this.orig = new ArrayList();

        this.rowCount = height;
        this.colCount = width;

        for (int index = 0; index < toAdd.size(); index++) {
            this.addLine(toAdd.get(index));
        }

    }

    private List<String> getOldInput() {
        return this.oldInput;
    }

    public void setOldInput() {
        if (this.getOldInput().size() < 1) {
            return;
        }

        int loc = this.getOldInput().size() - 1;
        getOldInputIndex();
        loc -= this.getOldInputIndex() - 1;
        if (loc < 0) {
            loc = 0;
        }
        if (loc >= this.getOldInput().size()) {
            loc = this.getOldInput().size() - 1;
        }
        this.setInput(this.getOldInput().get(loc));
        this.inputCursor = this.getInput().length();
    }

    private int getOldInputIndex() {
        return this.inputHistory;
    }

    public int getWidth() {
        return this.colCount;
    }

    public int getHeight() {
        return this.rowCount - 1;
    }

    public List<String> getData() {
        return this.data;
    }

    private int getIndex() {
        return this.index;
    }

    public void incrementIndex(boolean isInput) {

        if (isInput == true) {

            this.inputHistory++;
            if (this.inputHistory > this.getOldInput().size()) {
                this.inputHistory = this.getOldInput().size();
            }
            this.setOldInput();
            return;

        }

        this.index++;

        if (this.getIndex() > (this.getData().size() - this.getHeight())) {
            this.index = this.getData().size() - this.getHeight();
            if (this.index < 0) {
                this.index = 0;
            }
        }

    }

    public void decrementIndex(boolean isInput) {

        if (isInput == true) {
            this.inputHistory--;
            if (this.inputHistory < 0) {
                this.inputHistory = 0;
            }
            this.setOldInput();
            return;
        }

        this.index--;
        if (this.index < 1) {
            this.index = 1;
        }
    }

    public void resetIndex() {
        this.index = 0;
    }

    private int getMaxSize() {
        return this.maxSize;
    }

    public List<String> getOutput() {

        List<String> out = new ArrayList<String>();
        int start = this.getIndex();

        if (this.getData().size() > this.getHeight()) {
            start = this.getData().size() - this.getHeight();
        }

        if (this.getIndex() > 0) {
            start -= this.getIndex();
            if (start < 0) {
                start = 0;
            }
        }

        for (int x = start; x < this.getData().size() && out.size() < this.getHeight(); x++) {
            out.add(this.getData().get(x));
        }
        while (out.size() < this.getHeight()) {
            out.add("");
        }
        // out.add(this.getFormattedInput(showCursor));
        return out;
    }

    public List<String> getOriginal() {
        return this.orig;
    }

    private void addString(String line) {
        this.getData().add(line);
        if (this.getData().size() > this.getHeight()) {
            //    this.index++;
        }
        while (this.getData().size() > this.getMaxSize()) {
            this.getData().remove(0);
            //  this.index--;
        }

    }

    private int getSplit(String line) {

        line = line.substring(0, this.getWidth());

        if (line.indexOf(" ") < 0) {
            return this.getWidth();
        }
        int ret = this.getWidth();

        for (int index = 0; index < line.length(); index++) {
            if (line.charAt(index) == ' ') {
                ret = index;
            }
        }
        if (ret < 1) {
            ret = 1;
        }
        return ret;

    }

    public void addLine(String line) {
        this.getOriginal().add(line);

        if (line.length() == 0) {
            this.getData().add("");
            return;
        }

        while (line.length() > this.getWidth()) {
            int split = this.getSplit(line);
            String sub = line.substring(0, split);
            line = line.substring(split, line.length()).trim();
            addString(sub);
        }
        if (line.length() > 0) {
            this.addString(line);
        }
    }

    public void setInput(String toSet) {
        this.input = toSet;
    }

    public void incrementInputCursor() {
        this.inputCursor++;
        if (this.getInputCursor() > this.getInput().length()) {
            this.inputCursor = this.getInput().length();
        }

    }

    public void decrementInputCursor() {
        this.inputCursor--;
        if (this.getInputCursor() < 0) {
            this.inputCursor = 0;
        }
    }

    public void appendInput(String toAppend) {
        if (this.cursorAtEnd()) {
            this.setInput(this.getInput() + toAppend);
            this.incrementInputCursor();
            return;
        }
        String first = this.getInput().substring(0, this.getInputCursor());
        String second = this.getInput().substring(this.getInputCursor());

        this.setInput(first + toAppend + second);

        this.inputCursor++;

    }

    public String getInput() {
        return this.input;
    }

    private String getInputHeader() {
        return ":>";
    }

    private String getCursor() {
        return "_";
    }

    private int getInputCursor() {
        return this.inputCursor;
    }

    public void trimInput() {
        if (this.getInput().length() < 1) {
            return;
        }
        String toSet = this.getInput();
        toSet = toSet.substring(0, toSet.length() - 1);
        this.inputCursor--;
        this.setInput(toSet);
    }

    public void clearInput() {
        this.getOldInput().add(this.getInput());
        this.inputHistory = 0;

        this.setInput("");
    }

    private boolean cursorAtEnd() {
        return this.getInputCursor() >= this.getInput().length();
    }

    public void clearOutput() {
        this.data = new ArrayList();
    }

    public String getFormattedInput(boolean showCursor) {
        int targetWidth = this.getWidth() - this.getInputHeader().length();
        if (showCursor == true || this.cursorAtEnd() == false) {
            targetWidth -= this.getCursor().length();
        }
        if (this.cursorAtEnd() == true) {
            String line = this.input;
            if (line.length() > targetWidth) {
                int start = line.length() - targetWidth;

                line = line.substring(start, line.length());
            }
            String ret = this.getInputHeader() + line;

            if (showCursor == true) {
                ret += this.getCursor();
            }
            return ret;
        }

        String first = this.getInput().substring(0, this.getInputCursor());
        String second = this.getInput().substring(this.getInputCursor());

        String out = this.getInputHeader() + first + this.getCursor() + second;

        return out;

    }

}
