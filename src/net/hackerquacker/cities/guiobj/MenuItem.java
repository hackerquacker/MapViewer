package net.hackerquacker.cities.guiobj;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is a wrapper around JMenu objects.
 */
public class MenuItem {

    private JMenu menu;

    /**
     * Creates a new top-level menu with the string name.
     * @param name the name of this menu
     */
    public MenuItem(String name){
        this.menu = new JMenu(name);
    }

    /**
     * Adds a seperator to the menu
     */
    public void addSeperator(){
        this.menu.addSeparator();
    }

    /**
     * Adds a new option item to this menu with an onClick handler
     * @param name the text of this item
     * @param onClick the handler for when this item is clicked.
     */
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

    /**
     * Adds a new checkbox item to this menu with an onChange handler
     * @param name the text of this item
     * @param onClick the handler for when this item is checked/unchecked.
     */
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

    /**
     * Returns the JMenu object.
     * @return JMenu
     */
    public JMenu getMenu(){
        return menu;
    }
}

