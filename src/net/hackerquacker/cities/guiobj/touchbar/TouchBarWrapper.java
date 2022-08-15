package net.hackerquacker.cities.guiobj.touchbar;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarSlider;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.view.action.TouchBarViewAction;
import com.thizzer.jtouchbar.slider.SliderActionListener;

/**
 * This class wraps around the JTouchBar library to make it easier to
 * interface with the touchbar.
 *
 * @author Jonah Sellwood
 */
public class TouchBarWrapper {

    private JTouchBar touchBar;

    /**
     * Creates a new touchbar handler.
     * @param identifier A unique value to identify this touchbar object
     */
    public TouchBarWrapper(String identifier){
        this.touchBar = new JTouchBar();
        this.touchBar.setCustomizationIdentifier(identifier);
    }

    /**
     * Creates a new touchbar handler with a random identifier
     */
    public TouchBarWrapper(){
        this("Touchbar" + (char)(Math.random()*32 + 65));
    }

    /**
     * Returns true if the touchbar should be enabled (ie the program is running on Mac OS)
     */
    public boolean shouldEnable(){
        return System.getProperty("os.name").equals("Mac OS X");
    }

    /**
     * Sets the touchbar spacing to flexible
     */
    public void setFlexible(){
        this.touchBar.addItem(new TouchBarItem(TouchBarItem.NSTouchBarItemIdentifierFlexibleSpace));
    }

    /**
     * Sets the sizes of elements based on a fixed spacing
     */
    public void setFixed(){
        this.touchBar.addItem(new TouchBarItem(TouchBarItem.NSTouchBarItemIdentifierFixedSpaceSmall));
    }

    /**
     * Adds text to the touchbar
     * @param identifier a unique identifier string to identify this object
     * @param text the text to display
     * @return a TouchBarTextField object
     */
    public TouchBarTextField addText(String identifier, String text){
        TouchBarTextField name = new TouchBarTextField();
        name.setStringValue(text);

        this.touchBar.addItem(new TouchBarItem(identifier, name, true));

        return name;
    }

    /**
     * Adds a button to the touchbar
     * @param identifier a unique identifier string to identify this object
     * @param text the text to display on the button
     * @param onClick the onClick handler
     * @return TouchBarButton
     */
    public TouchBarButton addButton(String identifier, String text, TouchBarEvent onClick){
        TouchBarButton touchBarButtonImg = new TouchBarButton();
        touchBarButtonImg.setTitle(text);
        touchBarButtonImg.setAction(new TouchBarViewAction() {
            @Override public void onCall( TouchBarView view ) {
                onClick.onClick();
            }
        });

        this.touchBar.addItem(new TouchBarItem(identifier, touchBarButtonImg, true));

        return touchBarButtonImg;
    }

    /**
     * Adds a slider to the touchbar
     * @param identifier a unique identifier string to identify this object
     * @param min the minimum value
     * @param max the maximum value
     * @param e event handler
     * @return TouchBarSlider
     */
    public TouchBarSlider addSlider(String identifier, double min, double max, TouchBarSliderEvent e){
        TouchBarSlider slider = new TouchBarSlider();
        slider.setMaxValue(max);
        slider.setMinValue(min);
        slider.setActionListener(new SliderActionListener() {
            @Override
            public void sliderValueChanged(TouchBarSlider touchBarSlider, double v) {
                e.onChange(v);
            }
        });

        this.touchBar.addItem(new TouchBarItem(identifier, slider, true));

        return slider;
    }

    /**
     * Adds a seperator to the touchbar
     * @param identifier a unique indentifier string to identify this object
     * @param width the width of the seperator
     */
    public void addSeperator(String identifier, int width){
        TouchBarTextField seperator = new TouchBarTextField();
        String str = "";
        for (int i = 0; i < width; i++){
            str += "\t";
        }
        seperator.setStringValue(str);
        this.touchBar.addItem(new TouchBarItem(identifier, seperator, false));
    }

    /**
     * Returns the touchbar object
     * @return JTouchBar
     */
    public JTouchBar getTouchBar(){
        return this.touchBar;
    }
}
