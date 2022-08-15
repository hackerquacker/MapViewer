package net.hackerquacker.cities.guiobj.touchbar;

/**
 * Interface for calling touch bar events (sliders).
 *
 * @author Jonah Sellwood
 */
public interface TouchBarSliderEvent {

    /**
     * Called when the slider value is changed.
     * @param val The new slider value
     */
    public void onChange(double val);
}
