/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package jterminal.manager;

import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author ctydi
 */
public interface Commandable {

    public List<String> getOutput();

    public void drawOutput(Graphics g);

    public void clear();

    public int processInput(String command);

}
