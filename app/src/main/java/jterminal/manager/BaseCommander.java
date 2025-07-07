/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jterminal.manager;

import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class BaseCommander implements Commandable {

    private JTerminalManager manager;

    public BaseCommander(JTerminalManager manager) {
        this.setJTerminalManager(manager);
        this.setSplash();
    }

    public void setJTerminalManager(JTerminalManager toSet) {
        this.manager = toSet;
    }

    public JTerminalManager getManager() {
        return this.manager;
    }

    protected String getName() {
        return "BASIC COMMAND FUNCTIONALITY";
    }

    protected void setSplash() {

        this.getManager().addLine("WELCOME TO JAVA TERMINAL SYSTEM");
        this.getManager().addLine("VERSION 0.0");
        this.getManager().addLine("(C) 2025 CHRISTOPHER TYDINGS");
        this.getManager().addLine("CURRENT MODE: " + this.getName());

    }

    @Override
    public void clear() {
        this.getManager().clearOutput();
        this.setSplash();
    }

    @Override
    public int processInput(String command) {
        this.getManager().clearInput();
        this.getManager().resetIndex();

        command = command.trim().toUpperCase();
        if (command.equals("!CLEAR")) {
            this.clear();
            return 1;
        }

        return 0;

    }

    @Override
    public void drawOutput(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<String> getOutput() {
        return this.getManager().getOutput();
    }
}
