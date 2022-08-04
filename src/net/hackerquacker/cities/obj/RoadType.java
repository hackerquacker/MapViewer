package net.hackerquacker.cities.obj;

import java.awt.*;

public enum RoadType {

    LOCAL(new Color(100, 100, 100)), ROUTE(new Color(255, 255, 255)), HIGHWAY(new Color(252, 212, 33)), MOTORWAY(new Color(252, 190, 33));

    private Color c;
    RoadType(Color c){
        this.c = c;
    }

    public Color getColor(){
        return c;
    }
}

