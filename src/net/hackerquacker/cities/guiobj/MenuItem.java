package net.hackerquacker.cities.guiobj;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItem {

    private JMenu menu;

    public MenuItem(String name){
        this.menu = new JMenu(name);
    }

    public void addSeperator(){
        this.menu.addSeparator();
    }

    public void addItem(String name, IMenuItem onClick){
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClick.onClick();
            }
        });
        this.menu.add(item);
    }

    public void addCheckboxItem(String name, ICBItem onClick){
        JMenuItem item = new JMenuItem(name);
        item.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                onClick.onChange(e);
            }
        });
        this.menu.add(item);
    }

    public JMenu getMenu(){
        return menu;
    }
}

