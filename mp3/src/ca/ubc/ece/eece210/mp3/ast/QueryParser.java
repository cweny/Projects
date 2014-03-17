package ca.ubc.ece.eece210.mp3.ast;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a hand-written recursive descent parser that will build an AST for
 * our simple query language.
 * 
 * To use this class, create a new instance of SimpleParser and call the
 * getRoot() method.
 * 
 * @author Sathish Gopalakrishnan
 * 
 */
public class QueryParser {
    private static final Token END_TOKEN = new Token(TokenType.END, "END");
    final List<Token> tokenStream;
    int currentPosition;
    ASTNode root;

    static Map<TokenType, Class<? extends ASTNode>> map = new HashMap<TokenType, Class<? extends ASTNode>>();

    static {
	map.put(TokenType.AND, AndNode.class);
	map.put(TokenType.OR, OrNode.class);
	map.put(TokenType.MATCHES, MatchesNode.class);
	map.put(TokenType.IN, InNode.class);
    }

    public QueryParser(List<Token> _tokenStream) {
	tokenStream = _tokenStream;
	currentPosition = 0;
    }

    public ASTNode getRoot() {
	/* TODO: change me */
	ASTNode ast = andExpr();
	return ast;
    }

    public ASTNode orExpr() {
	/* TODO: implement me */
	return null;
    }

    protected ASTNode andExpr() {
	ASTNode current;

	ASTNode leftTree = atom();
	current = leftTree;

	Token nextToken;

	do {
	    nextToken = peek();

	    if (nextToken.getType() == TokenType.AND) {
		consume();
		ASTNode head = new AndNode(Token.getTokenInstance(nextToken.getPayload()));
		head.addChild(current);
		ASTNode rightTree = atom();
		head.addChild(rightTree);
		current = head;

	    } else {
		return current;
	    }

	} while (nextToken.getType() != TokenType.END);

	return current;

    }

    protected ASTNode atom() {
	Token nextToken = consume();

	if (nextToken.getType() == TokenType.END) {
	    return null;
	}

	if (nextToken.getType() == TokenType.L_PARAN) {
	    // Process compound expression
	    ASTNode tree = orExpr();
	    consume(); // remove RPAREN
	    return tree;
	} else {
	    return processLeaveNodes(nextToken);
	}

    }

    @SuppressWarnings("rawtypes")
    private ASTNode processLeaveNodes(Token token) {
	Class<? extends ASTNode> astClass = map.get(token.getType());
	Class[] parameters = new Class[] { Token.class };
	try {
	    Constructor cons = astClass.getConstructor(parameters);
	    Object[] arguments = new Object[] { token };
	    ASTNode ast = (ASTNode) cons.newInstance(arguments);

	    consume(); // remove LPAREN
	    Token argument = consume();
	    ast.setArguments(sanitizeString(argument));
	    consume(); // remove RPAREN

	    return ast;

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    // "Some string" -> Some string (quotes removed)
    private String sanitizeString(Token argument) {
	String payload = argument.getPayload();
	payload = payload.substring(1, payload.length() - 1);
	return payload;
    }

    private Token consume() {
	if (currentPosition == tokenStream.size())
	    return END_TOKEN;

	return tokenStream.get(currentPosition++);
    }

    private Token peek() {
	if (currentPosition == tokenStream.size())
	    return END_TOKEN;

	return tokenStream.get(currentPosition);
    }
}
