package net.hackerquacker.cities.obj;

public class Point {

    private int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X coord
     * @return
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns the Y coord
     * @return
     */
    public int getY(){
        return this.y;
    }

    /**
     * Returns the shortest distance between this point and the specified point p
     * @param p
     * @return
     */
    public double getDistance(Point p){
        return Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2));
    }

    @Override public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
