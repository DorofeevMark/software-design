package visitors;

import token.BracketToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParseTokenVisitor extends AbstractTokenVisitor {
    private final List<Token> tokens = new ArrayList<>();
    private final Deque<Token> stack = new ArrayDeque<>();

    @Override
    protected void doVisit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    protected void doVisit(OperationToken token) {
        while (!stack.isEmpty()) {
            Token tmp = stack.peek();
            if (tmp instanceof OperationToken) {
                OperationToken opToken = (OperationToken) tmp;
                if (opToken.getPriority() > token.getPriority()) {
                    tokens.add(opToken);
                    stack.pop();
                    continue;
                }
            }
            break;
        }
        stack.push(token);
    }

    @Override
    protected void doVisit(BracketToken token) {
        if (token == BracketToken.LEFT) {
            stack.push(token);
        } else {
            while (true) {
                if (stack.isEmpty()) {
                    throw new RuntimeException();
                }
                Token tmp = stack.pop();
                if (tmp == BracketToken.LEFT) {
                    break;
                }
                tokens.add(tmp);
            }
        }
    }

    @Override
    public void visit(List<Token> inputToken) {
        super.visit(inputToken);
        while (!stack.isEmpty()) {
            Token tmp = stack.pop();
            if (tmp instanceof OperationToken) {
                tokens.add(tmp);
            } else {
                throw new VisitException();
            }
        }
    }

    public List<Token> getResult() {
        return tokens;
    }
}