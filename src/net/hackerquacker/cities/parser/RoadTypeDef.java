package net.hackerquacker.cities.parser;

import net.hackerquacker.cities.obj.Map;
import net.hackerquacker.cities.parser.token.Token;
import net.hackerquacker.cities.parser.token.TokenType;

import java.awt.*;
import java.util.HashMap;

public class RoadTypeDef {

    private static java.util.Map<String, Color> colors = new HashMap<>();

    private String name;
    private int defaultWidth = 5;
    private Color color;

    private Color labelBg = new Color(19, 145, 15);
    private Color labelFg = Color.WHITE;

    private Map map;

    public RoadTypeDef(Map map){
        this.map = map;

        RoadTypeDef.colors.put("BLUE", new Color(2, 50, 171));
        RoadTypeDef.colors.put("GREEN", new Color(19, 145, 15));
        RoadTypeDef.colors.put("WHITE", Color.WHITE);
        RoadTypeDef.colors.put("BLACK", Color.BLACK);

        this.parse();
    }

    public Color getColor(){
        return this.color;
    }

    public String getName(){
        return this.name;
    }

    public int getDefaultWidth(){
        return this.defaultWidth;
    }

    public Color getLabelBg(){
        return this.labelBg;
    }

    public Color getLabelFg(){
        return this.labelFg;
    }


    private void parse(){

        if (!this.map.next().equals("def"))
            throw new IllegalStateException("Expected def keyword!");

        this.name = this.map.next().getToken();

        if (!this.map.next().equals("="))
            throw new IllegalStateException("Expected = after name!");

        if (!this.map.next().equals("new"))
            throw new IllegalStateException("Expected 'new' object!");

        if (!this.map.next().equals("Road"))
            throw new IllegalStateException("Expected Road object!");

        if (!this.map.next().equals("("))
            throw new IllegalStateException("Expected ( after Road definition!");

        String red = this.getIntegerOrCrash();
        this.map.next();
        String green = this.getIntegerOrCrash();
        this.map.next();
        String blue = this.getIntegerOrCrash();

        this.color = new Color(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));

        if (this.map.next().equals(")")) {  // the ) or ,
            this.map.next();    // the ;
            return;
        }

        String wid = this.getIntegerOrCrash();    // the width number

        this.defaultWidth = Integer.parseInt(wid);

        if (this.map.next().equals(")")) {  // the ) or ,
            this.map.next();    // the ;
            return;
        }

        String bgColor = this.map.next().getToken();
        this.labelBg = RoadTypeDef.colors.getOrDefault(bgColor, RoadTypeDef.colors.get("GREEN"));

        if (this.map.next().equals(")")) {  // the ) or ,
            this.map.next();    // the ;
            return;
        }

        String fgColor = this.map.next().getToken();
        this.labelFg = RoadTypeDef.colors.getOrDefault(fgColor, RoadTypeDef.colors.get("WHITE"));

        this.map.next();    // the )
        if (!this.map.next().equals(";")) {   // the ;
            throw new IllegalStateException("Syntax error!");
        }
    }

    private String getIntegerOrCrash(){
        Token token = this.map.next();
        if (!token.equals(TokenType.INTEGER_LIT))
            throw new IllegalStateException("Expected number!");
        return token.getToken();
    }
}
