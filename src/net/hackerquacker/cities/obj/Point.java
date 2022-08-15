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

    /**
     * Adds the x,y to this point
     * @param x the x coordinate to add
     * @param y the y coordinate to add.
     * @return this point object
     */
    public Point add(int x, int y){
        this.x += x;
        this.y += y;

        return this;
    }

    /**
     * Subtracts x & y from this point
     * @param x the x coordinate to subtract
     * @param y the y coordinate to subtract
     * @return this point object
     */
    public Point sub(int x, int y){
        this.x -= x;
        this.y -= y;

        return this;
    }

    /**
     * Adds a point to this point
     * @param o the point to add
     */
    public void add(Point o){
        this.add(o.getX(), o.getY());
    }

    /**
     * Subtracts a point from this point
     * @param o the point to add
     */
    public void sub(Point o){
        this.sub(o.getX(), o.getY());
    }

    /**
     * Sets the x and y values of this point
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x and y values to the point specified
     * @param o
     */
    public void set(Point o){
        this.set(o.getX(), o.getY());
    }

    @Override public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
