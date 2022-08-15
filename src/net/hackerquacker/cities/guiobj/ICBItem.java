package net.hackerquacker.cities.guiobj;

import javax.swing.event.ChangeEvent;

/**
 * Event Interface for the MenuBar Check Box item.
 */
public interface ICBItem {

    /**
     * Called when the checkbox value was changed
     * @param e
     */
    public void onChange(ChangeEvent e);

}
