package visitors;

import token.BracketToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class CalculateTokenVisitor extends AbstractTokenVisitor {
    private final Deque<Integer> stack = new ArrayDeque<>();
    private int result;

    private final static Map<OperationToken, BiFunction<Integer, Integer, Integer>> operations = Map.of(
            OperationToken.ADD, ((x, y) -> x + y),
            OperationToken.SUB, ((x, y) -> x - y),
            OperationToken.MUL, ((x, y) -> x * y),
            OperationToken.DIV, ((x, y) -> x / y)
    );

    @Override
    protected void doVisit(NumberToken token) {
        stack.push(token.getNumber());
    }

    @Override
    protected void doVisit(OperationToken token) {
        if (stack.size() < 2) {
            throw new VisitException();
        }
        Integer first = stack.pop();
        Integer second = stack.pop();
        BiFunction<Integer, Integer, Integer> function = operations.get(token);
        if (function == null) {
            throw new VisitException();
        }
        stack.push(function.apply(first, second));
    }


    @Override
    protected void doVisit(BracketToken token) {
        throw new VisitException();
    }

    @Override
    public void visit(List<Token> inputToken) {
        super.visit(inputToken);
        if (stack.size() != 1) {
            throw new VisitException();
        }
        result = stack.pop();
    }

    public int getResult() {
        return result;
    }
}
