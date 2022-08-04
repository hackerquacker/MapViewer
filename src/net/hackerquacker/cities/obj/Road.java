package net.hackerquacker.cities.obj;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Road {

    private String name;
    private String number;

    private Color color;
    private RoadType type;

    private List<Point> points;
    private int width = -1;

    public Road(String name, RoadType type){
        this.name = name;
        this.type = type;

        this.color = this.type.getColor();
        this.points = new ArrayList<>();
    }

    public Road(String name, String type, List<Point> points, int width){
        if (type.equals("motorway"))
            this.type = RoadType.MOTORWAY;
        if (type.equals("highway"))
            this.type = RoadType.HIGHWAY;
        if (type.equals("route"))
            this.type = RoadType.ROUTE;
        if (type.equals("local"))
            this.type = RoadType.LOCAL;
        if (this.type == null)
            this.type = RoadType.LOCAL;

        this.name = name;

        this.color = this.type.getColor();
        this.points = new ArrayList<>();

        if (width != -1)
            this.width = width;

        for (Point p : points){
            this.addPoint(p.getX(), p.getY());
        }
    }

    public void addPoint(int x, int y){
        this.points.add(new Point(x, y));
    }

    public String getName(){
        return this.name;
    }

    public Color getColor(){
        return this.color;
    }

    public List<Point> getPoints(){
        return this.points;
    }

    public RoadType getType(){
        return this.type;
    }

    public void drawRoad(Graphics2D g, float scale){
        Point lastPoint = this.points.get(0);


        for (int i = 1; i < this.points.size(); i++) {
            if (this.type == RoadType.ROUTE){
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
            }
            g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));
            lastPoint = this.points.get(i);
        }
    }
}
