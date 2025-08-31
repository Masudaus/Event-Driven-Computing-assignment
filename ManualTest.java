package test;

import regex.*;

/**
 * Manual testing class for quick experimentation
 */
public class ManualTest {
    public static void main(String[] args) {
        RegexEngine engine = new RegexEngine();
        
        // Quick tests
        testPattern(engine, "a*", new String[]{"", "a", "aa", "aaa"});
        testPattern(engine, "a+", new String[]{"", "a", "aa", "aaa"});
        testPattern(engine, "a?b", new String[]{"b", "ab", "aab"});
        testPattern(engine, "(a|b)*", new String[]{"", "a", "b", "ab", "ba", "abab"});
        testPattern(engine, "a.b", new String[]{"aab", "a1b", "a@b", "ab"});
        
        // Visualize a simple pattern
        System.out.println("\n=== Visualization Example ===");
        try {
            EpsilonNFA nfa = engine.compile("a*b");
            StateVisualizer.printNFA(nfa);
        } catch (Exception e) {
            System.out.println("Error creating NFA: " + e.getMessage());
        }
    }
    
    private static void testPattern(RegexEngine engine, String pattern, String[] inputs) {
        System.out.printf("\nTesting pattern: '%s'%n", pattern);
        for (String input : inputs) {
            boolean matches = engine.matches(pattern, input);
            System.out.printf("  '%s' -> %s%n", 
                input.isEmpty() ? "ε" : input, 
                matches ? "MATCH" : "NO MATCH");
        }
    }
}
