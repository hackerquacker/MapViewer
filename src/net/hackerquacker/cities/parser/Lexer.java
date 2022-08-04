package net.hackerquacker.cities.parser;

import net.hackerquacker.cities.parser.token.LexerToken;
import net.hackerquacker.cities.parser.token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the first stage of compilation: Lexing.
 * This takes the raw code input and converts it into LexerToken objects.
 * Each LexerToken object contains one symbol/number/identifier/etc that controls the program. This is then turned into Tokens
 * to be parsed in the Abstract Syntax Tree and converted to Object code.
 */
public class Lexer {

    /**
     * The raw code input
     */
    private final String rawInput;
    /**
     * The list of tokens after the rawInput is parsed
     */
    private List<LexerToken> tokens;

    /**
     * The current characters the lexer is looking at. Index 0 is the previous character, 1 is the current character and 2 is the next character
     */
    private char[] currentChars = new char[]{'\0', '\0', '\0'};
    /**
     * The current position in the rawInput the lexer is looking at
     */
    private int index = 0;

    /**
     * Constructs the Lexer and parses the input
     */
    public Lexer(String lexer) {
        this.rawInput = lexer;
        this.tokens = new ArrayList<>();

        this.parse();
    }

    /**
     * Returns the list of tokens that this lexer parsed.
     */
    public List<LexerToken> getTokens() {
        return this.tokens;
    }

    /**
     * Gets the 3 characters around the current index (set by the index variable) and sets the currentChars list to those values.
     */
    private void getCharacter() {
        // return if we reached the end of the input.
        if (this.index >= this.rawInput.length()) {
            this.currentChars = new char[]{'\0', '\0', '\0'};
            return;
        }

        // set the current characters. Index 0 is the previous character; 1 is the current character; 2 is the next character
        this.currentChars[0] = (this.index > 0) ? this.rawInput.charAt(this.index - 1) : '\0';
        this.currentChars[1] = this.rawInput.charAt(this.index);
        this.currentChars[2] = (this.index + 1 < this.rawInput.length()) ? this.rawInput.charAt(this.index + 1) : '\0';
    }

    /**
     * Parses the input
     * <p>
     * TODO: maybe add some validation that the next character has been reached. Else its possible a bug might happen where the loop doesn't end cause the character does not fit into any of these criteria
     */
    private void parse() {
        this.index = 0;     // reset the index back to the start
        this.getCharacter();

        while (this.currentChars[1] != '\0') {
            // Parse Comments (either // or /*)
            if (this.currentChars[1] == '/' && this.currentChars[2] == '/')
                this.parseLineComment();
            if (this.currentChars[1] == '/' && this.currentChars[2] == '*')
                this.parseMultilineComment();

            // Parse string literal
            if (this.currentChars[1] == '"')
                this.parseStringLit();

            // Parse integer litera;
            if (Lexer.isCharacterIn(this.currentChars[1], "0123456789")
                    || (!Lexer.isCharacterIn(this.currentChars[0], "0123456789") && this.currentChars[1] == '-' && Lexer.isCharacterIn(this.currentChars[2], "0123456789")))
                this.parseIntegerLit();

            // Parse identifiers
            if (Lexer.match(this.currentChars[1], "\\w"))
                this.parseIdentifier();

            // Parse symbols
            if (Lexer.isCharacterIn(this.currentChars[1], "+-*/=!<>.,:;(){}[]|&"))
                this.parseSymbol();

            // Ignore whitespace
            if (this.currentChars[1] == ' ' || this.currentChars[1] == '\n' || this.currentChars[1] == '\r' || this.currentChars[1] == '\t')
                this.index++;

            // Gets the next character. All branches above should have added one to the index variable so the program should be looking at the next character.
            this.getCharacter();
        }
    }

    /**
     * Parses the line comment (just loops until the new line character is reached; everything is ignored)
     */
    private void parseLineComment() {
        while (this.currentChars[1] != '\n') {
            this.index++;
            this.getCharacter();
        }
    }

    /**
     * Parses the multiline comment (just loops until the * / character sequence is reached; everything is ignored)
     */
    private void parseMultilineComment() {
        while (!(this.currentChars[1] == '*' && this.currentChars[2] == '/')) {
            this.index++;
            this.getCharacter();
        }

        this.index += 2;
        this.getCharacter();
    }

    /**
     * Parses a string literal. E.g. "Hello World!"
     */
    private void parseStringLit() {
        String str = "";

        // ignore the current character. This is the " character
        this.index++;
        this.getCharacter();

        // loop through the characters until the " is found, but ignore \" combination
        while (this.currentChars[1] != '"' && this.currentChars[0] != '\\') {
            str += this.currentChars[1];
            this.index++;
            this.getCharacter();
        }

        // add the token to the tokens array. Set the next token of this token to the current last token in the tokens array
        LexerToken lt = new LexerToken(str, TokenType.STRING_LIT);
        if (!this.tokens.isEmpty())
            this.tokens.get(this.tokens.size() - 1).setNext(lt);
        this.tokens.add(lt);

        // ignore the last " character
        this.index++;
        this.getCharacter();
    }

    /**
     * Parses integer numbers. E.g. -973, 0, 3.14, etc
     */
    private void parseIntegerLit() {
        String number = "";

        // loop through each character until one isnt a 0-9/./-
        while (Lexer.match(this.currentChars[1], "(\\d|\\.|\\-)")) {
            number += String.valueOf(this.currentChars[1]);
            this.index++;
            this.getCharacter();
        }

        // add the token to the tokens array. Set the next token of this token to the current last token in the tokens array
        LexerToken lt = new LexerToken(number, TokenType.INTEGER_LIT);
        if (!this.tokens.isEmpty())
            this.tokens.get(this.tokens.size() - 1).setNext(lt);
        this.tokens.add(lt);
    }

    /**
     * Parses an identifier. An identifier is anything that is a word (uses A-Za-z and _) currently, although when these are parsed
     * an identifier could be an invalid identifier and a syntax error is produced.
     */
    private void parseIdentifier() {
        String identifier = "";
        while (Lexer.match(this.currentChars[1], "\\w")) {
            identifier += String.valueOf(this.currentChars[1]);
            this.index++;
            this.getCharacter();
        }

        // add the token to the tokens array. Set the next token of this token to the current last token in the tokens array
        LexerToken lt = new LexerToken(identifier);
        if (!this.tokens.isEmpty())
            this.tokens.get(this.tokens.size() - 1).setNext(lt);
        this.tokens.add(lt);
    }

    /**
     * Parses a symbol.
     */
    private void parseSymbol() {
        LexerToken lt;
        if ((this.currentChars[1] == '+' && this.currentChars[2] == '+')
                || (this.currentChars[1] == '-' && this.currentChars[2] == '-')
                || (this.currentChars[1] == '&' && this.currentChars[2] == '&')
                || (this.currentChars[1] == '|' && this.currentChars[2] == '|')
                || (this.currentChars[2] == '=' && !Lexer.isCharacterIn(this.currentChars[1], "()[]{};,."))
                || (this.currentChars[1] == '-' && this.currentChars[2] == '>')) {
            lt = new LexerToken(String.valueOf(this.currentChars[1]) + this.currentChars[2]);
            // add one mroe to the index as the lexer is looking at 2 characters
            this.index++;
        } else
            lt = new LexerToken(String.valueOf(this.currentChars[1]));

        // add the token to the tokens array. Set the next token of this token to the current last token in the tokens array
        if (!this.tokens.isEmpty())
            this.tokens.get(this.tokens.size() - 1).setNext(lt);
        this.tokens.add(lt);

        // point to the next character after this symbol
        this.index++;
    }

    /**
     * Checks to see if the character is in the string and returns the result
     *
     * @param c     the character to look for
     * @param chars the string to check
     * @return true if the character c is in the String chars
     */
    private static boolean isCharacterIn(char c, String chars) {
        return chars.indexOf(c) != -1;
    }

    /**
     * Checks to see if the character matches a specified regular expression (regex)
     *
     * @param c     the character to check
     * @param regex the regex to apply to the character
     * @return the result of the specified regular expression
     */
    private static boolean match(char c, String regex) {
        return String.valueOf(c).matches(regex);
    }
}
