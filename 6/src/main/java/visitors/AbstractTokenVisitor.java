package visitors;

import token.BracketToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.List;

public abstract class AbstractTokenVisitor implements TokenVisitor {
    @Override
    public void visit(Token token) {
        if (token instanceof NumberToken) {
            doVisit((NumberToken) token);
        } else if (token instanceof OperationToken) {
            doVisit((OperationToken) token);
        } else if (token instanceof BracketToken) {
            doVisit((BracketToken) token);
        }
    }

    @Override
    public void visit(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
    }

    protected abstract void doVisit(NumberToken token);

    protected abstract void doVisit(OperationToken token);

    protected abstract void doVisit(BracketToken token);
}
