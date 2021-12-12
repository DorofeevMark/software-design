package states;

import token.BracketToken;
import token.OperationToken;
import token.Token;
import token.Tokenizer;

public class MainState extends State {

    public MainState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void process(char ch) {
        Token token = getToken(ch);
        if (token != null) {
            tokenizer.addToken(token);
            return;
        }
        if (Character.isWhitespace(ch)) {
            return;
        }
        if (Character.isDigit(ch)) {
            tokenizer.setState(new NumberState(tokenizer));
            tokenizer.getState().process(ch);
            return;
        }
        throw new StateProcessException("Unexpected character " + ch);
    }

    private Token getToken(char ch) {
        switch (ch) {
            case '+':
                return OperationToken.ADD;
            case '-':
                return OperationToken.SUB;
            case '*':
                return OperationToken.MUL;
            case '/':
                return OperationToken.DIV;
            case '(':
                return BracketToken.LEFT;
            case ')':
                return BracketToken.RIGHT;
            default:
                return null;
        }
    }
}
