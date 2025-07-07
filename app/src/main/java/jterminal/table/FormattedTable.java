/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jterminal.table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class FormattedTable {

    private FormattedString[] columns;

    private List<FormattedString[]> data;

    private Integer[] widths;

    public FormattedTable(String[] columns) {
        FormattedString[] formatted = new FormattedString[columns.length];
        for (int index = 0; index < formatted.length; index++) {
            formatted[index] = new FormattedString(columns[index]);
        }

        this.columns = formatted;
        this.data = new ArrayList();
        this.addDataLine(columns);
        //  this.data.add(formatted);
        this.widths = new Integer[this.columns.length];
    }

    public List<FormattedString[]> getData() {
        return this.data;
    }

    public void addDataLine(String[] toAdd) {
        FormattedString[] line = new FormattedString[toAdd.length];
        for (int x = 0; x < line.length; x++) {
            if (toAdd[x] == null) {
                toAdd[x] = "";
            }
            line[x] = new FormattedString(toAdd[x]);
        }

        this.getData().add(line);
    }

    private FormattedString getMaxForColumn(int column) {
        return this.getData().get(this.getMaxIndexForColumn(column))[column];
    }

    private int getMaxIndexForColumn(int column) {
        int max = 0;
        int index = -1;
        for (int x = 0; x < this.getData().size(); x++) {
            if (this.getData().get(x)[column].length() > max || index < 0) {
                max = this.getData().get(x)[column].length();
                index = x;
            }
        }
        return index;
    }

    private char getVerticalBorder() {
        return '|';
    }

    private char getHorizontalBorder() {
        return '-';
    }

    private char getCross() {
        return '+';
    }

    private Integer[] getWidths() {
        return this.widths;
    }

    private int sumWidths() {
        int total = 0;
        for (int x = 0; x < this.getWidths().length; x++) {
            total += this.getWidths()[x];
        }
        return total;

    }

    private int countColumns() {
        return this.columns.length;
    }

    private int countRows() {
        return this.getData().size();
    }

    private int sumArray(int[] toSum) {
        int ret = 0;
        for (int column = 0; column < toSum.length; column++) {
            ret += toSum[column];
        }
        return ret;
    }

    public void calculateWidths(int targetWidth) {

        double[] avg = new double[this.countColumns()];
        targetWidth -= this.countColumns();
        targetWidth--;

        double total = 0;

        for (int column = 0; column < this.countColumns(); column++) {
            double value = this.getTotalForColumn(column) * 1.0;
            value = value / this.countRows();
            total += value;
            avg[column] = value;

        }

        this.widths = new Integer[this.countColumns()];
        for (int index = 0; index < this.countColumns(); index++) {
            double value = avg[index] / total;
            int toSet = (int) (targetWidth * value);
            int max = this.getMaxForColumn(index).length();
            if (toSet > max) {
                toSet = max;
            }
            if (toSet < 1) {
                toSet = 1;
            }
            this.widths[index] = toSet;
        }

        int sum = this.sumWidths();
        if (sum < targetWidth) {
            int rem = targetWidth - sum;
            for (int x = 0; x < this.countColumns(); x++) {
                if (rem <= 0) {
                    return;
                }
                int max = this.getMaxForColumn(x).length();
                while (max > widths[x] && rem > 0) {
                    widths[x]++;
                    rem--;
                }
            }
        }
    }

    public List<String> getOutput() {
        List<String> formatted = new ArrayList();
        formatted.add(this.createHorizontalBorder());
        for (int row = 0; row < this.countRows(); row++) {
            boolean center = row == 0;
            List<String[]> toAdd = this.getLine(row);
            for (int sub = 0; sub < toAdd.size(); sub++) {

                formatted.add(this.formatRow(toAdd.get(sub), center));
            }
            if (row == 0) {
                formatted.add(this.createHorizontalBorder());
            }

        }
        formatted.add(this.createHorizontalBorder());
        return formatted;
    }

    private String createHorizontalBorder() {
        String ret = "" + this.getCross();

        for (int x = 0; x < this.countColumns(); x++) {
            String toAdd = "" + this.getHorizontalBorder();
            while (toAdd.length() < this.widths[x]) {
                toAdd = toAdd + this.getHorizontalBorder();
            }

            toAdd = toAdd + this.getCross();
            ret += toAdd;
        }

        return ret;
    }

    public String formatRow(String[] row, boolean center) {
        String ret = "" + this.getVerticalBorder();
        for (int index = 0; index < row.length; index++) {
            int width = this.widths[index];
            String toAdd = row[index];
            boolean left = true;

            while (toAdd.length() < width) {
                if (center && left) {
                    toAdd = " " + toAdd;
                } else {
                    toAdd += " ";
                }
                left = !left;
            }

            ret = ret + toAdd + this.getVerticalBorder();
        }

        return ret;
    }

    private int getTotalForColumn(int column) {
        int index = 0;
        try {
            for (int x = 0; x < this.getData().size(); x++) {
                index += this.getData().get(x)[column].length();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return index;
    }

    private String[] createEmptyRow() {
        String[] ret = new String[this.countColumns()];
        for (int index = 0; index < ret.length; index++) {
            ret[index] = "";
        }
        return ret;
    }

    private List<String[]> getLine(int line) {

        FormattedString[] row = this.getData().get(line);
        List<String[]> ret = new ArrayList();
        ret.add(this.createEmptyRow());
        for (int index = 0; index < this.countColumns(); index++) {
            int width = this.getWidths()[index];

            FormattedString value = row[index];

            int counter = 0;

            while (value.length() > width) {

                int split = value.getSplit(width);

                FormattedString sub = value.substring(0, split);
                value = value.substring(split, value.length());

                if (ret.size() <= counter) {
                    ret.add(this.createEmptyRow());
                }

                String toAdd = sub.getValue();

                ret.get(counter)[index] = toAdd;
                counter++;

            }

            if (value.length() > 0) {
                if (ret.size() <= counter) {
                    ret.add(this.createEmptyRow());
                }
                String toAdd = value.getValue();
                ret.get(counter)[index] = toAdd;
            }
        }

        return ret;
    }

    private class FormattedString {

        private String value;

        public FormattedString(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public int length() {
            return this.getValue().length();
        }

        public FormattedString substring(int start, int end) {

            String line = this.getValue().substring(start, end);
            return new FormattedString(line);

        }

        public FormattedString substring(int index) {
            return this.substring(0, index);
        }

        private boolean isSplit(int index) {
            if (this.getValue().charAt(index) == ' ') {
                return true;
            }
            if (this.getValue().charAt(index) == '-') {
                return true;
            }

            return false;
        }

        public int getSplit(int start) {
            int index = start;
            while (index >= 1 && this.isSplit(index) == false) {

                index--;

            }

            if (index < 1) {
                return start;
            }
            return index;
        }

    }

}
