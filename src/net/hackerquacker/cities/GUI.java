package net.hackerquacker.cities;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.view.TouchBarSlider;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import net.hackerquacker.cities.guiobj.MenuBar;
import net.hackerquacker.cities.guiobj.MenuItem;
import net.hackerquacker.cities.guiobj.touchbar.TouchBarWrapper;
import net.hackerquacker.cities.obj.MapCanvas;
import net.hackerquacker.cities.obj.Point;
import net.hackerquacker.cities.obj.Road;
import net.hackerquacker.cities.parser.ParseMap;
import net.hackerquacker.cities.parser.RoadDef;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/**
 * The main class for the Map Viewer Program
 */
public class GUI extends JFrame {

    public static final String APP_NAME = "Map Viewer";
    public static final String VER_NAME = "alpha 1.3.1";

    // The object where the map will be drawn onto.
    public MapCanvas canvas = new MapCanvas(1024, 768);

    // used when the user is dragging
    private double startDragX = 0, startDragY = 0;
    private Point prevOrigin;

    // file name to load.
    private String mapFile = "map.cities";

    // Touchbar tings
    private JTouchBar touchBar;
    private TouchBarTextField zoomField, coordField;

    public GUI(){
        // set up the GUY
        super("Map Viewer");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuBar menuBar = new MenuBar();
        MenuItem file = menuBar.add("File");
        MenuItem view = menuBar.add("View");
        MenuItem window = menuBar.add("Window");

        // File menu
        file.addItem("Open Map", ()->{
            JFileChooser fc = new JFileChooser();
            int res = fc.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION){
                File f = fc.getSelectedFile();
                mapFile = f.getAbsolutePath();
                addRoads();
                canvas.repaint();
            }
        });
        file.addItem("Save Map", ()->{
            System.out.println("Save Map");
        });
        file.addSeperator();
        file.addItem("Close", ()->{System.exit(0);});

        // View Menu
        view.addItem("Zoom in", ()->{
            canvas.zoom(0.1f);
            if (zoomField != null)
                zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
        });
        view.addItem("Zoom out", ()->{
            canvas.zoom(-0.1f);

            if (zoomField != null)
                zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
        });
        view.addSeperator();
        view.addItem("Move up", ()->{canvas.setOrigin(canvas.getOrigin().add(0, 100));});
        view.addItem("Move down", ()->{canvas.setOrigin(canvas.getOrigin().add(0, -100));});
        view.addItem("Move left", ()->{canvas.setOrigin(canvas.getOrigin().add(100, 0));});
        view.addItem("Move right", ()->{canvas.setOrigin(canvas.getOrigin().add(-100, 0));});

        // Window Menu
        window.addItem("About " + APP_NAME, () -> {

        });

        menuBar.addMenuBar(this);

        this.add(canvas);

        this.setVisible(true);

        this.addRoads();

        this.repaint();

        // the window resize listener. Resizes the canvas to take up the whole window
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                canvas.resizeMap(componentEvent.getComponent().getSize());
            }
        });

        // Click listener. Used currently for the dragging functionality.
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDragX = e.getX();
                startDragY = e.getY();

                prevOrigin = canvas.getOrigin();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                prevOrigin = canvas.getOrigin();
                startDragX = 0;
                startDragY = 0;
            }
        });

        // Mouse drag listener. Moves the map origin
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point newOrigin = new Point(0, 0);
                newOrigin.add(prevOrigin);
                newOrigin.add(-(int)(startDragX - e.getX()), -(int)(startDragY - e.getY()));
                canvas.setOrigin(newOrigin);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                canvas.setMouseLoc(new Point(e.getX(), e.getY()));
                if (coordField != null)
                    coordField.setStringValue(canvas.getMapCoords().toString());
            }
        });

        // Scroll wheel listener. Zooms in or out the map
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                canvas.zoom(e.getUnitsToScroll() * 0.1f);
                if (zoomField != null)
                    zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
            }
        });


        // Implements the + and - keys for zoom
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_EQUALS){
                    canvas.zoom(0.1f);
                    if (zoomField != null)
                        zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
                }else if (e.getKeyCode() == KeyEvent.VK_MINUS){
                    canvas.zoom(-0.1f);
                    System.out.println("Zoom out");
                    if (zoomField != null)
                        zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
                }
            }
        });

        // Add touchbar support
        if(System.getProperty("os.name").equals("Mac OS X")){
            TouchBarWrapper touchbar = new TouchBarWrapper("MapViewerIdentifier");

            if (touchbar.shouldEnable()) {
                touchbar.setFlexible();
                // Add the name of the program to the left of the touchbar
                touchbar.addText("APP_NAME_0", APP_NAME + " " + VER_NAME + " ");
                touchbar.addSeperator("SEP_0", 1);

                this.coordField = touchbar.addText("COORD_TF", canvas.getMapCoords().toString());
                touchbar.addSeperator("SEP_1", 1);

                // add zoom text
                zoomField = touchbar.addText("ZOOM_TEXT", "Zoom: " + canvas.getScale());

                // add a zoom slider
                TouchBarSlider slider = touchbar.addSlider("ZOOM_SLIDER", 0.1, 10, (v) -> {
                    canvas.zoomAbsolute(v);
                    zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
                });

                touchbar.addButton("Zoom_In", "+", () -> {
                    canvas.zoom(0.1f);
                    zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
                });

                touchbar.addButton("Zoom_Out", "-", () -> {
                    canvas.zoom(-0.1f);
                    zoomField.setStringValue("Zoom: " + String.format("%.1f", canvas.getScale()));
                });

                touchbar.addSeperator("SEP_2", 1);

                touchbar.getTouchBar().show(this);
            }
        }
    }

    /**
     * This method parses the map file and adds the roads into the canvas
     */
    public void addRoads(){
        ParseMap map = new ParseMap(this.mapFile);  // parse the map file into RoadDef objects

        canvas.loadMap(map);

        // add the roads onto the canvas.
        for (RoadDef defs : map.getMap().getRoads())
            this.canvas.addRoad(new Road(map.getMap(), defs.getRoadName(), defs.getRoadFullName(), defs.getType(), defs.getPoints(), defs.getWidth()));
    }

    public static void main(String[] args){
        // Improve performance on Mac & Linux
        System.setProperty("sun.java2d.opengl", "true");
        // Add the menus onto the OS X menubar
        if(System.getProperty("os.name").equals("Mac OS X"))
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        new GUI();
    }
}
