/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.tranfer;

/**
 *
 * @author ctydi
 */
public abstract class Outputter {

    public abstract void print(String out);

    public void println(String out) {
        this.print(out + "\n");
    }

}
