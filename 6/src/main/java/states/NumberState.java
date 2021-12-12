package states;

import token.NumberToken;
import token.Tokenizer;

public class NumberState extends State {
    private int number;

    public NumberState(Tokenizer tokenizer) {
        super(tokenizer);
        number = 0;
    }

    @Override
    public void process(char ch) {
        if (Character.isDigit(ch)) {
            number = number * 10 + Character.digit(ch, 10);
        } else {
            tokenizer.addToken(new NumberToken(number));
            tokenizer.setState(new MainState(tokenizer));
            tokenizer.getState().process(ch);
        }
    }

    @Override
    public void end() {
        tokenizer.addToken(new NumberToken(number));
        super.end();
    }

}
