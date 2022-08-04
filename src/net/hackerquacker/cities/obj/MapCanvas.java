package net.hackerquacker.cities.obj;

import net.hackerquacker.cities.GUI;
import net.hackerquacker.cities.parser.ParseMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MapCanvas extends JPanel {

    private int width, height;
    private float scale = 1.0f;
    private Origin origin = new Origin(0, 0);
    private ParseMap map;

    private Point mouseLoc = new Point(0, 0);

    private ArrayList<Road> roadList = new ArrayList<>();
    private ArrayList<RoadNumber> roadNumberList = new ArrayList<>();

    public MapCanvas(int width, int height){
        this.width = width;
        this.height = height;

        Dimension size = new Dimension(this.width, this.height);
        this.setPreferredSize(size);
    }

    public void resizeMap(Dimension dimension){
        this.width = dimension.width;
        this.height = dimension.height;
        this.setPreferredSize(dimension);
        this.repaint();
    }

    public void addRoad(Road road){
        this.roadList.add(road);

        if (!road.getName().equals(""))
            this.roadNumberList.add(new RoadNumber(road));
    }

    public Origin getOrigin(){
        return this.origin;
    }

    public void setOrigin(Origin origin){
        this.origin = origin;
        this.repaint();
    }

    public void zoom(float factor){
        if (this.scale + factor >= 0.1f && this.scale + factor < 10f)
            this.scale += factor;
        this.repaint();
    }

    public void setMap(ParseMap map){
        this.map = map;
    }

    public void setMouseLoc(Point p){
        this.mouseLoc = p;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        // draw background
        g.setColor(new Color(255, 249, 235));
        g.fillRect(0, 0, width, height);

        g.translate(origin.getX(), origin.getY());

        // draw roads
        for (Road road : this.roadList) {
            road.drawRoad((Graphics2D) g, this.scale);
        }

        // draw road numbers
        for (RoadNumber rn : this.roadNumberList) {
            rn.draw((Graphics2D) g, this.scale);
        }

        g.translate(-origin.getX(), -origin.getY());
        g.setColor(new Color(125, 125, 125));
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        g.drawString(GUI.APP_NAME + " " + GUI.VER_NAME, 8, 18);
        if (map != null) {
            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            g.drawString("Map: " + map.getMap().getMapName() + ", " + String.format("%.0f, %.0f", (this.mouseLoc.getX() - this.origin.getX())/this.scale, (this.mouseLoc.getY() - this.origin.getY())/this.scale), 7, 36);
        }
    }
}
