package net.hackerquacker.cities;

import net.hackerquacker.cities.guiobj.MenuBar;
import net.hackerquacker.cities.guiobj.MenuItem;
import net.hackerquacker.cities.obj.MapCanvas;
import net.hackerquacker.cities.obj.Origin;
import net.hackerquacker.cities.obj.Point;
import net.hackerquacker.cities.obj.Road;
import net.hackerquacker.cities.parser.ParseMap;
import net.hackerquacker.cities.parser.RoadDef;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class GUI extends JFrame {

    public static final String APP_NAME = "Map Viewer";
    public static final String VER_NAME = "alpha 1.2";

    // The object where the map will be drawn onto.
    public MapCanvas canvas = new MapCanvas(800, 600);

    // used when the user is dragging
    private double startDragX = 0, startDragY = 0;
    private Origin prevOrigin;

    // file name to load.
    private String mapFile = "map.cities";

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
        view.addItem("Zoom in", ()->{canvas.zoom(0.1f, new Point(0, 0));});
        view.addItem("Zoom out", ()->{canvas.zoom(-0.1f, new Point(0, 0));});
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
                Origin newOrigin = new Origin(0, 0);
                newOrigin.add(prevOrigin);
                newOrigin.add(-(int)(startDragX - e.getX()), -(int)(startDragY - e.getY()));
                canvas.setOrigin(newOrigin);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                canvas.setMouseLoc(new Point(e.getX(), e.getY()));
            }
        });

        // Scroll wheel listener. Zooms in or out the map
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                canvas.zoom(e.getUnitsToScroll() * 0.1f, new Point(e.getX(), e.getY()));
            }
        });


        // Implements the + and - keys for zoom
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_EQUALS){
                    canvas.zoom(0.1f, null);
                    System.out.println("Zoom in");
                }else if (e.getKeyCode() == KeyEvent.VK_MINUS){
                    canvas.zoom(-0.1f, null);
                    System.out.println("Zoom out");
                }
            }
        });
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
        new GUI();
    }
}
