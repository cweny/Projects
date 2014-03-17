package ca.ubc.ece.eece210.mp3.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tokenizes a String representing the query and returns a list of
 * tokens. To use this class call the static method tokenizeInput on a String.
 * 
 * This class has no error corrections and is a very simplistic tokenizer.
 * 
 * @author Sathish Gopalakrishnan
 * 
 */
public class QueryTokenizer {
    private static final char STRING_ESCAPE_CHAR = '\\';
    private static final char BOGUS_PLACEHOLDER = ' ';
    private final char[] characters;
    private int currentPosition;
    boolean inStringLiteralMode;

    public static List<Token> tokenizeInput(String query) {
	QueryTokenizer tokenizer = new QueryTokenizer(query);
	List<Token> list = new ArrayList<Token>();

	Token token = tokenizer.nextToken();
	while (token.getType() != TokenType.END) {
	    list.add(token);
	    token = tokenizer.nextToken();
	}

	return list;
    }

    public QueryTokenizer(String query) {
	// Remove trailing white spaces that can choke this tokenizer
	query = query.trim();

	characters = query.toCharArray();
	currentPosition = 0;
	inStringLiteralMode = false;
    }

    public Token nextToken() {

	if (currentPosition == characters.length)
	    return Token.getTokenInstance("END");

	char nextChar = consume();
	StringBuilder builder = new StringBuilder();
	builder.append(nextChar);

	if (isAlpha(nextChar)) {

	    while (isAlpha(nextChar = peek())) {
		nextChar = consume();
		builder.append(nextChar);
	    }

	} else if (nextChar == '&' || nextChar == '|') {
	    nextChar = consume();
	    builder.append(nextChar);

	} else if (nextChar == '"') {
	    consumeStringLiteral(builder);
	}

	return Token.getTokenInstance(builder.toString());
    }

    private void consumeStringLiteral(StringBuilder builder) {
	char nextChar;
	inStringLiteralMode = true;

	char previousChar = BOGUS_PLACEHOLDER;
	while (((nextChar = consume()) != '"') || (previousChar == STRING_ESCAPE_CHAR)) {
	    previousChar = nextChar;
	    builder.append(nextChar);
	}
	builder.append(nextChar); // append final "

	inStringLiteralMode = false;
    }

    private boolean isAlpha(char c) {
	// checks if this is lowercase or uppercase
	return (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')));
    }

    private boolean isWhiteSpace(char c) {
	// there shouldn't be any newline characters
	return (c == ' ' || c == '\t');
    }

    private char consume() {
	char c = characters[currentPosition++];

	if (!inStringLiteralMode) {
	    while (isWhiteSpace(c)) {
		c = characters[currentPosition++];
	    }
	}

	return c;
    }

    private char peek() {
	char c = characters[currentPosition];

	if (!inStringLiteralMode) {
	    while (isWhiteSpace(c)) {
		c = characters[++currentPosition];
	    }
	}

	return c;

    }
}
