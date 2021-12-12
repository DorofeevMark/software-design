import token.Token;
import token.Tokenizer;
import visitors.CalculateTokenVisitor;
import visitors.ParseTokenVisitor;
import visitors.PrintTokenVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String str = "(23 + 10) * 5";
        List<Token> tokens = new Tokenizer().tokenize(str);

        PrintTokenVisitor printTokenVisitor = new PrintTokenVisitor();
        printTokenVisitor.visit(tokens);

        ParseTokenVisitor parseTokenVisitor = new ParseTokenVisitor();
        parseTokenVisitor.visit(tokens);
        List<Token> parsedTokens = parseTokenVisitor.getResult();
        printTokenVisitor.visit(parsedTokens);

        CalculateTokenVisitor calculateTokenVisitor = new CalculateTokenVisitor();
        calculateTokenVisitor.visit(parsedTokens);
        System.out.println(calculateTokenVisitor.getResult());
    }
}
