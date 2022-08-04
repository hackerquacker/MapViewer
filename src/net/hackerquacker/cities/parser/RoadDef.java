package net.hackerquacker.cities.parser;

import net.hackerquacker.cities.obj.Map;
import net.hackerquacker.cities.obj.Point;
import net.hackerquacker.cities.parser.token.Token;
import net.hackerquacker.cities.parser.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class RoadDef {

    private String roadName;
    private String roadFullName;

    private List<Point> points = new ArrayList<>();
    private String type;

    private int width = -1;

    private Map parser;

    public RoadDef(Map parser) {
        this.parser = parser;

        this.type = parser.next().getToken();

        if (!parser.next().equals(TokenType.OPEN_BRACKET))
            throw new IllegalStateException("Expected ( after road type");

        this.roadName = parser.next().getToken();
        parser.next(); // the ,
        this.roadFullName = parser.next().getToken();
        parser.next();

        if (this.parser.getCurToken().next().equals(TokenType.INTEGER_LIT)){
            this.width = Integer.parseInt(this.parser.next().getToken());
        }

        while (!this.parser.getCurToken().equals(";")) {
            if (this.parser.next().equals(TokenType.OPEN_BRACKET)) {

                Token xToken = parser.next();
                parser.next();
                Token yToken = parser.next();

                if (!parser.next().equals(")"))
                    throw new IllegalStateException("Expected )");

                this.points.add(new Point(Integer.parseInt(xToken.getToken()), Integer.parseInt(yToken.getToken())));

            } else continue;
        }
    }

    public String getType() {
        return this.type;
    }

    public String getRoadName() {
        return this.roadName;
    }

    public String getRoadFullName() {
        return this.roadFullName;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public int getWidth(){
        return this.width;
    }
}
