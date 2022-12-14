package net.hackerquacker.cities.obj;

import net.hackerquacker.cities.parser.RoadTypeDef;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles drawing of roads
 */
public class Road {

    private final String name;    // the road name
    private final String long_name;
    private final RoadTypeDef roadType;

    private final Color color;    // the colour of the road

    private final List<Point> points; // the coordinates for each node of this road
    private int width = -1;     // the width of this road

    private Map map;

    /**
     * Creates a new road
     * @param name  The name of this road
     * @param type  The type of this road
     * @param points    The list of nodes for this road
     * @param width     The width of this road
     */
    public Road(Map map, String name, String long_name, String type, List<Point> points, int width){
        this.map = map;
        this.long_name = long_name;

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


        if (this.width == -1)
            this.width = this.roadType.getDefaultWidth();
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
     * @return String
     */
    public String getShortName(){
        return this.name;
    }

    /**
     * Returns the long name of this road
     * @return String
     */
    public String getName(){
        return this.long_name;
    }

    /**
     * Returns the colour of this road
     * @return Color
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Returns the list of points that make up this road
     * @return List of points
     */
    public List<Point> getPoints(){
        return this.points;
    }

    /**
     * Returns the color of the label background
     * @return Color
     */
    public Color getLabelBg(){
        return this.roadType.getLabelBg();
    }

    /**
     * Returns the color of the label foreground
     * @return Color
     */
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


        // Draw outline
        for (int i = 1; i < this.points.size(); i++) {
            // draw background
            g.setColor(Road.getOutlineColor(this.color));
            g.setStroke(new BasicStroke(this.width+2));
            g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));

            /*if (this.points.get(i).getY() == lastPoint.getY()) {   // draw the horizontal label
                g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale)+(int)Math.ceil(this.width/2)+(this.width % 2 == 0 ? 0 : 1), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale)+(int)Math.ceil(this.width/2)+(this.width % 2 == 0 ? 0 : 1));
                g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale)-(int)Math.ceil(this.width/2)-(this.width % 2 == 0 ? 0 : 1), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale)-(int)Math.ceil(this.width/2)-(this.width % 2 == 0 ? 0 : 1));
            }else if (this.points.get(i).getX() == lastPoint.getX()){
                g.drawLine((int)(lastPoint.getX()*scale)+(int)Math.ceil(this.width/2)+(this.width % 2 == 0 ? 0 : 1), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale)+(int)Math.ceil(this.width/2)+(this.width % 2 == 0 ? 0 : 1), (int)(this.points.get(i).getY()*scale));
                g.drawLine((int)(lastPoint.getX()*scale)-(int)Math.ceil(this.width/2)-(this.width % 2 == 0 ? 0 : 1), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale)-(int)Math.ceil(this.width/2)-(this.width % 2 == 0 ? 0 : 1), (int)(this.points.get(i).getY()*scale));
            }*/

            lastPoint = this.points.get(i);
        }

        lastPoint = this.points.get(0);

        // Draws the actual road
        for (int i = 1; i < this.points.size(); i++) {

            g.setStroke(new BasicStroke(this.width));
            g.setColor(this.getColor());
            g.drawLine((int)(lastPoint.getX()*scale), (int)(lastPoint.getY()*scale), (int)(this.points.get(i).getX()*scale), (int)(this.points.get(i).getY()*scale));


            // Draw the road name onto the road

            double dist = this.points.get(i).getDistance(lastPoint);
            if (this.points.get(i).getY() == lastPoint.getY() && scale > 1) {   // draw the horizontal label
                if (dist > 60/scale && dist < 300) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));

                    float midPoint = Math.min(lastPoint.getX(), this.points.get(i).getX()) * scale + (Math.max(lastPoint.getX(), this.points.get(i).getX()) * scale - Math.min(lastPoint.getX(), this.points.get(i).getX()) * scale) / 2;
                    float width = g.getFontMetrics().stringWidth(this.long_name);

                    if (width < dist*scale)
                        g.drawString(this.long_name, midPoint - width / 2, (lastPoint.getY() * scale) + 3);
                } else if (dist > 300) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    for (int x = Math.min(lastPoint.getX(), this.points.get(i).getX()) + 30; x < Math.max(lastPoint.getX(), this.points.get(i).getX()) - 30; x += 300) {
                        g.drawString(this.long_name, x*scale, (lastPoint.getY() * scale) + 3);
                    }
                }
            }else if (this.points.get(i).getX() == lastPoint.getX() && scale > 1){  // draw vertical labels
                g.rotate(-Math.PI/2);
                if (dist > 60/scale && dist < 300) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));

                    float midPoint = Math.min(lastPoint.getY(), this.points.get(i).getY()) * scale + (Math.max(lastPoint.getY(), this.points.get(i).getY()) * scale - Math.min(lastPoint.getY(), this.points.get(i).getY()) * scale) / 2;
                    float width = g.getFontMetrics().stringWidth(this.long_name);

                    if (width < dist*scale)
                        g.drawString(this.long_name, -(midPoint + width/2), lastPoint.getX()*scale + 3);
                }else if (dist > 300) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
                    for (int y = Math.min(lastPoint.getY(), this.points.get(i).getY()) + 30; y < Math.max(lastPoint.getY(), this.points.get(i).getY()) - 30; y += 300) {
                        g.drawString(this.long_name, -y*scale, lastPoint.getX()*scale + 3);
                    }
                }
                g.rotate(Math.PI/2);
            }

            lastPoint = this.points.get(i);
        }
    }

    /**
     * Darkens the given color and returns the new color
     * @param roadColor the road color
     * @return the new darkened color.
     */
    private static Color getOutlineColor(Color roadColor){
        return new Color(Math.max(roadColor.getRed() - 50, 0), Math.max(roadColor.getGreen() - 50, 0), Math.max(roadColor.getBlue() - 50, 0));
    }
}
