package net.hackerquacker.cities.obj;

public class Origin {

    private int x, y;

    public Origin(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Origin add(int x, int y){
        this.x += x;
        this.y += y;

        return this;
    }

    public void add(Origin o){
        this.add(o.getX(), o.getY());
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void set(Origin o){
        this.set(o.getX(), o.getY());
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
