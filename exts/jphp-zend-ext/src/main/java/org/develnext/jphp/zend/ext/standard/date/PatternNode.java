package org.develnext.jphp.zend.ext.standard.date;

import java.util.function.Consumer;
import java.util.regex.Pattern;

class PatternNode extends SymbolNode {
    private final Pattern pattern;
    private final Consumer<DateTimeParserContext> adjuster;

    private PatternNode(Pattern pattern, Symbol symbol, Consumer<DateTimeParserContext> adjuster) {
        super(symbol);
        this.pattern = pattern;
        this.adjuster = adjuster;
    }

    static PatternNode ofDigits(Pattern pattern, Consumer<DateTimeParserContext> adjuster) {
        return new PatternNode(pattern, Symbol.DIGITS, adjuster);
    }

    static PatternNode ofDigits(Pattern pattern) {
        return new PatternNode(pattern, Symbol.DIGITS, dateTimeParserContext -> {});
    }

    static PatternNode of(Pattern pattern, Symbol symbol, Consumer<DateTimeParserContext> adjuster) {
        return new PatternNode(pattern, symbol, adjuster);
    }

    @Override
    public boolean matchesInternal(DateTimeParserContext ctx) {
        return pattern.matcher(ctx.readCharBufferAtCursor()).matches();
    }

    @Override
    void apply(DateTimeParserContext ctx) {
        adjuster.accept(ctx);
        ctx.cursor().inc();
    }
}
