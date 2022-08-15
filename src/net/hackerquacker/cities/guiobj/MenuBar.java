package net.hackerquacker.cities.guiobj;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a wrapper around the JMenuBar object.
 */
public class MenuBar {

    private JMenuBar menuBar;
    private List<MenuItem> menus = new ArrayList<>();

    /**
     * Creates a new MenuBar object
     */
    public MenuBar(){
        this.menuBar = new JMenuBar();
    }

    /**
     * Creates a new top-level menu
     * @param name the name of the menu
     * @return the MenuItem object to add options to this menu
     */
    public MenuItem add(String name){
        MenuItem menuItem = new MenuItem(name);
        this.menus.add(menuItem);

        return menuItem;
    }

    /**
     * Adds the menu bar onto the window
     * @param frame the JFrame to add this menubar to.
     */
    public void addMenuBar(JFrame frame){
        this.menuBar = new JMenuBar();

        for (MenuItem item : menus){
            this.menuBar.add(item.getMenu());
        }

        frame.setJMenuBar(this.menuBar);
    }
}
