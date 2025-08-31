package regex;

/**
 * Main regex engine class that combines parsing and matching
 */
public class RegexEngine {
    private RegexParser parser;
    
    public RegexEngine() {
        this.parser = new RegexParser();
    }
    
    /**
     * Compiles a regex pattern into an ε-NFA
     */
    public EpsilonNFA compile(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        
        try {
            return parser.parse(pattern);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid regex pattern: " + pattern, e);
        }
    }
    
    /**
     * Tests if input matches the pattern
     */
    public boolean matches(String pattern, String input) {
        EpsilonNFA nfa = compile(pattern);
        return nfa.matches(input != null ? input : "");
    }
    
    /**
     * Creates a compiled pattern that can be reused
     */
    public CompiledPattern compilePattern(String pattern) {
        return new CompiledPattern(compile(pattern));
    }
    
    /**
     * Wrapper class for compiled patterns
     */
    public static class CompiledPattern {
        private final EpsilonNFA nfa;
        
        public CompiledPattern(EpsilonNFA nfa) {
            this.nfa = nfa;
        }
        
        public boolean matches(String input) {
            return nfa.matches(input != null ? input : "");
        }
    }
}
