package net.hackerquacker.cities;

import net.hackerquacker.cities.obj.MapCanvas;
import net.hackerquacker.cities.obj.Origin;
import net.hackerquacker.cities.obj.Point;
import net.hackerquacker.cities.obj.Road;
import net.hackerquacker.cities.parser.ParseMap;
import net.hackerquacker.cities.parser.RoadDef;

import javax.swing.*;
import java.awt.event.*;

public class GUI extends JFrame {

    public static final String APP_NAME = "Map Viewer";
    public static final String VER_NAME = "alpha 1.0";


    public MapCanvas canvas = new MapCanvas(800, 600);

    private double startDragX = 0, startDragY = 0;
    private Origin prevOrigin;

    private String mapFile = "map.cities";

    public GUI(){
        super("Cities Server Map");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(canvas);

        this.setVisible(true);

        this.addRoads();

        this.repaint();

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                canvas.resizeMap(componentEvent.getComponent().getSize());
            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

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

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
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
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                canvas.zoom(e.getUnitsToScroll() * 0.1f);
            }
        });


        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_EQUALS){
                    canvas.zoom(0.1f);
                    System.out.println("Zoom in");
                }else if (e.getKeyCode() == KeyEvent.VK_MINUS){
                    canvas.zoom(-0.1f);
                    System.out.println("Zoom out");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void addRoads(){
        ParseMap map = new ParseMap(this.mapFile);

        canvas.setMap(map);

        System.out.println("Loading data for map " + map.getMap().getMapName());

        for (RoadDef defs : map.getMap().getRoads()){
            this.canvas.addRoad(new Road(defs.getRoadName(), defs.getType(), defs.getPoints(), defs.getWidth()));
        }

        /*Road roadTest = new Road("M1", RoadType.MOTORWAY);
        roadTest.addPoint(525, 327);
        roadTest.addPoint(-365, 327);

        this.canvas.addRoad(roadTest);*/
    }


    public static void main(String[] args){
        new GUI();
    }
}
