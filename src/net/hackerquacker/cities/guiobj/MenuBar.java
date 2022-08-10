package net.hackerquacker.cities.guiobj;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuBar {

    private JMenuBar menuBar;
    private List<MenuItem> menus = new ArrayList<>();

    public MenuBar(){
        this.menuBar = new JMenuBar();
    }

    public MenuItem add(String name){
        MenuItem menuItem = new MenuItem(name);
        this.menus.add(menuItem);

        return menuItem;
    }

    public void addMenuBar(JFrame frame){
        this.menuBar = new JMenuBar();

        for (MenuItem item : menus){
            this.menuBar.add(item.getMenu());
        }

        frame.setJMenuBar(this.menuBar);
    }
}
