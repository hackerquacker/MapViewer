package net.hackerquacker.cities.obj;

import java.awt.*;

/**
 * This class is responsible for drawing the Blue/Green highway numbers onto the screen.
 */
public class RoadNumber {

    // Constants
    private static final int NORTH = 0b0001;
    private static final int EAST = 0b0010;
    private static final int SOUTH = 0b0100;
    private static final int WEST = 0b1000;

    // The road that this object is attached to
    private Road road;

    public RoadNumber(Road road){
        this.road = road;
    }

    public void draw(Graphics2D g, float scale){

        Point lastPoint = null;
        for (Point p : this.road.getPoints()){
            if (lastPoint != null){
                if ((direction(lastPoint, p) & (NORTH | SOUTH)) != 0){
                    // North south direction
                    int x= p.getX();
                    int yMin = (int)(Math.min(p.getY(), lastPoint.getY())*scale);
                    int yMax = (int)(Math.max(p.getY(), lastPoint.getY())*scale);
                    double dist = Math.sqrt(Math.pow((p.getX() - lastPoint.getX()), 2) + Math.pow((p.getY() - lastPoint.getY()), 2));

                    if (dist > 80) {
                        for (int i = yMin + 20; i < yMax-20; i += 200)
                            this.drawNumber(g, (int) (x * scale), i, this.road.getType() == RoadType.MOTORWAY);
                    }
                }else{
                    // East West Direction
                    int y= (int)(p.getY()*scale);
                    int xMin = (int)(Math.min(p.getX(), lastPoint.getX())*scale);
                    int xMax = (int)(Math.max(p.getX(), lastPoint.getX())*scale);
                    double dist = Math.sqrt(Math.pow((p.getX() - lastPoint.getX()), 2) + Math.pow((p.getY() - lastPoint.getY()), 2));

                    if (dist > 80) {
                        for (int i = xMin + 20; i < xMax-20; i += 200)
                            this.drawNumber(g, i, y, this.road.getType() == RoadType.MOTORWAY);
                    }
                }
            }
            lastPoint = p;
        }

    }

    private void drawNumber(Graphics2D g, int x, int y, boolean mwy){
        if (mwy)
            g.setColor(new Color(2, 50, 171));
        else g.setColor(new Color(19, 145, 15));
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        float width = g.getFontMetrics().stringWidth(this.road.getName());

        float w = width + 10;
        float h = 19;

        g.fillRect(x - (int)(w/2), y - (int)(h/2), (int)w, (int)h);
        g.setColor(Color.WHITE);
        g.drawString(this.road.getName(), x + 5 - (int)(w/2), y + 14 - (int)(h/2));
    }

    private static int direction(Point p1, Point p2){
        if (Math.abs(p1.getX()-p2.getX()) > Math.abs(p1.getY() - p2.getY())){
            // east west
            if (p1.getX() - p2.getX() < 0)
                return EAST;
            return WEST;
        }else{
            // north south
            if (p1.getY() - p2.getY() < 0)
                return SOUTH;
            return NORTH;
        }
    }
}
