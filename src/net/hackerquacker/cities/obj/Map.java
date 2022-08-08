package net.hackerquacker.cities.obj;

import net.hackerquacker.cities.parser.*;
import net.hackerquacker.cities.parser.token.LexerToken;
import net.hackerquacker.cities.parser.token.Token;
import net.hackerquacker.cities.parser.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Lexer lexer;
    private List<Token> tokens = new ArrayList<>();
    private Token curToken;

    private String mapName;
    private List<RoadDef> roads;
    private List<RoadTypeDef> roadTypes;

    public Map(Lexer l){
        this.lexer = l;
        this.roads = new ArrayList<>();
        this.roadTypes = new ArrayList<>();

        Token prev = null;
        for (LexerToken lt : this.lexer.getTokens()){
            Token t = lt.getToken();
            t.setPrev(prev);
            tokens.add(t);
            prev = t;
        }
    }

    public Token next(){
        if (this.curToken == null){
            this.curToken = this.tokens.get(0);
            return this.curToken;
        }
        if (this.curToken.next() != null){
            this.curToken = this.curToken.next();
        }
        return this.curToken;
    }

    public boolean hasNext(){
        if (this.curToken == null)
            this.next();
        return this.curToken.next() != null;
    }

    public String getMapName(){
        return this.mapName;
    }

    public void parse(){

        if (!this.next().equals("map"))
            throw new IllegalStateException("Unexpected token!");

        this.mapName = this.next().getToken();

        if (!this.next().equals(TokenType.OPEN_BLOCK))
            throw new IllegalStateException("Unexpected token!");

        while (!this.curToken.next().equals("}") || !this.hasNext()){
            if (this.curToken.next().equals("def")){
                this.roadTypes.add(new RoadTypeDef(this));
            }else
                this.roads.add(new RoadDef(this));

            System.out.println(this.curToken);
        }
    }

    public Token getCurToken(){
        return this.curToken;
    }

    public List<RoadDef> getRoads(){
        return this.roads;
    }

    public List<RoadTypeDef> getRoadTypes(){
        return this.roadTypes;
    }

    public RoadTypeDef getRoadType(String name){
        for (RoadTypeDef types : this.roadTypes){
            if (types.getName().equals(name))
                return types;
        }
        return null;
    }
}