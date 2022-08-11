package net.hackerquacker.cities.guiobj.touchbar;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarSlider;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.view.action.TouchBarViewAction;
import com.thizzer.jtouchbar.slider.SliderActionListener;

public class TouchBarWrapper {

    private JTouchBar touchBar;

    public TouchBarWrapper(String identifier){
        this.touchBar = new JTouchBar();
        this.touchBar.setCustomizationIdentifier(identifier);
    }

    /**
     * Returns true if the touchbar should be enabled (ie the program is running on Mac OS)
     */
    public boolean shouldEnable(){
        return System.getProperty("os.name").equals("Mac OS X");
    }

    public void setFlexible(){
        this.touchBar.addItem(new TouchBarItem(TouchBarItem.NSTouchBarItemIdentifierFlexibleSpace));
    }

    public void setFixed(){
        this.touchBar.addItem(new TouchBarItem(TouchBarItem.NSTouchBarItemIdentifierFixedSpaceSmall));
    }

    public TouchBarTextField addText(String identifier, String text){
        TouchBarTextField name = new TouchBarTextField();
        name.setStringValue(text);

        this.touchBar.addItem(new TouchBarItem(identifier, name, true));

        return name;
    }

    public void addButton(String identifier, String text, TouchBarEvent onClick){
        TouchBarButton touchBarButtonImg = new TouchBarButton();
        touchBarButtonImg.setTitle(text);
        touchBarButtonImg.setAction(new TouchBarViewAction() {
            @Override
            public void onCall( TouchBarView view ) {
                onClick.onClick();
            }
        });

        this.touchBar.addItem(new TouchBarItem(identifier, touchBarButtonImg, true));
    }

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

    public void addSeperator(String identifier, int width){
        TouchBarTextField seperator = new TouchBarTextField();
        String str = "";
        for (int i = 0; i < width; i++){
            str += "\t";
        }
        seperator.setStringValue(str);
        this.touchBar.addItem(new TouchBarItem(identifier, seperator, false));
    }

    public JTouchBar getTouchBar(){
        return this.touchBar;
    }
}
