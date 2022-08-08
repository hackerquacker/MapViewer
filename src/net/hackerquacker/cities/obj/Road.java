package net.hackerquacker.cities.obj;

import net.hackerquacker.cities.parser.RoadTypeDef;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This object handles drawing of roads
 */
public class Road {

    private String name;    // the road name
    private RoadTypeDef roadType;

    private Color color;    // the colour of the road
    private RoadType type;  // the type of this road

    private List<Point> points; // the coordinates for each node of this road
    private int width = -1;     // the width of this road

    private Color labelBg;
    private Color labelFg;

    private Map map;

    /**
     * Creates a new road.
     * @param name  The name of the road
     * @param type  The type of this road
     */
    public Road(String name, RoadType type){
        this.name = name;
        this.type = type;

        this.color = this.type.getColor();
        this.points = new ArrayList<>();
    }

    /**
     * Creates a new road
     * @param name  The name of this road
     * @param type  The type of this road
     * @param points    The list of nodes for this road
     * @param width     The width of this road
     */
    public Road(Map map, String name, String type, List<Point> points, int width){
        /*if (type.equals("motorway"))
            this.type = RoadType.MOTORWAY;
        if (type.equals("highway"))
            this.type = RoadType.HIGHWAY;
        if (type.equals("route"))
            this.type = RoadType.ROUTE;
        if (type.equals("local"))
            this.type = RoadType.LOCAL;
        if (this.type == null)
            this.type = RoadType.LOCAL;*/

        this.map = map;

        this.roadType = this.map.getRoadType(type);

        if(this.roadType == null)
            throw new IllegalStateException("Unknown road type: " + type);

        this.name = name;

        this.color = this.roadType.getColor();
        this.points = new ArrayList<>();

        if (width != -1)
            this.width = width;

        for (Point p : points){
            this.addPoint(p.getX(), p.getY());
        }
    }

    /**
     * Appends a new node to the end of this road
     * @param x the x coord
     * @param y the y coord
     */
    public void addPoint(int x, int y){
        this.points.add(new Point(x, y));
    }

    /**
     * Returns the name of this road
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the colour of this road
     * @return
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Returns the list of points that make up this road
     * @return
     */
    public List<Point> getPoints(){
        return this.points;
    }

    /**
     * Returns the type of this road
     * @deprecated
     * @return
     */
    public RoadType getType(){
        return this.type;
    }

    public Color getLabelBg(){
        return this.roadType.getLabelBg();
    }

    public Color getLabelFg(){
        return this.roadType.getLabelFg();
    }

    /**
     * Draws the road onto the canvas
     * @param g         The graphics context
     * @param scale     The scale of the map
     */
    public void drawRoad(Graphics2D g, float scale){
        Point lastPoint = this.points.get(0);

        if (this.roadType == null)
            return;

        for (int i = 1; i < this.points.size(); i++) {
            g.setColor(this.getColor());
            /*if (this.type == RoadType.ROUTE){
                if (this.width == -1)
                    this.width = 3;
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(this.width + 2));
                g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));
                g.setStroke(new BasicStroke(this.width));
                g.setColor(this.getColor());
            }else if (this.type == RoadType.HIGHWAY){
                if (this.width == -1)
                    this.width = 3;
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(this.width+2));
                g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));
                g.setStroke(new BasicStroke(this.width));
                g.setColor(this.getColor());
            }else if (this.type == RoadType.MOTORWAY) {
                if (this.width == -1)
                    this.width = 6;
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(this.width+2));
                g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));
                g.setStroke(new BasicStroke(this.width));
                g.setColor(this.getColor());
            }*/

            if (this.width == -1)
                this.width = this.roadType.getDefaultWidth();

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(this.width + 2));
            g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));
            g.setStroke(new BasicStroke(this.width));
            g.setColor(this.getColor());

            g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));
            lastPoint = this.points.get(i);
        }
    }
}
