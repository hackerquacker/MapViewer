package net.hackerquacker.cities.parser;

import net.hackerquacker.cities.obj.Map;
import net.hackerquacker.cities.parser.Lexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParseMap {

    private String data;

    private Map map;

    public ParseMap(String filename){

        this.data = "";

        File file = new File(filename);
        try {
            Path filePath = Path.of(file.toURI());
            this.data = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.parse();
    }

    private void parse(){
        Lexer lexer = new Lexer(this.data);

        Map parse = new Map(lexer);
        parse.parse();

        this.map = parse;
    }

    public Map getMap(){
        return this.map;
    }
}

