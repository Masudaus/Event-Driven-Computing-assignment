package regex;

/**
 * Demo class showing usage examples
 */
public class RegexDemo {
    public static void main(String[] args) {
        RegexEngine engine = new RegexEngine();
        
        System.out.println("=== Simple Regex Engine Demo ===\n");
        
        // Test cases
        String[][] tests = {
            {"a", "a", "true"},
            {"a", "b", "false"},
            {"ab", "ab", "true"},
            {"a|b", "a", "true"},
            {"a|b", "b", "true"},
            {"a|b", "c", "false"},
            {"a*", "", "true"},
            {"a*", "aaa", "true"},
            {"a+", "", "false"},
            {"a+", "aaa", "true"},
            {"a?", "", "true"},
            {"a?", "a", "true"},
            {"a?", "aa", "false"},
            {"(ab)*", "", "true"},
            {"(ab)*", "abab", "true"},
            {"a.c", "abc", "true"},
            {"a.c", "a1c", "true"},
            {"a.c", "ac", "false"}
        };
        
        System.out.println("Running test cases:");
        System.out.println("Pattern\t\tInput\t\tExpected\tActual\t\tResult");
        System.out.println("-".repeat(70));
        
        int passed = 0;
        int total = tests.length;
        
        for (String[] test : tests) {
            String pattern = test[0];
            String input = test[1];
            boolean expected = Boolean.parseBoolean(test[2]);
            boolean actual = engine.matches(pattern, input);
            boolean pass = expected == actual;
            
            if (pass) passed++;
            
            System.out.printf("%-12s\t%-8s\t%-8s\t%-8s\t%s%n",
                pattern, 
                input.isEmpty() ? "\"\"" : input, 
                expected, 
                actual, 
                pass ? "PASS" : "FAIL");
        }
        
        System.out.println("-".repeat(70));
        System.out.printf("Results: %d/%d tests passed (%.1f%%)%n", 
            passed, total, (passed * 100.0) / total);
        
        if (passed == total) {
            System.out.println("All tests passed! ✓");
        } else {
            System.out.println("Some tests failed. Check implementation.");
        }
        
        // Interactive demo
        System.out.println("\n=== Interactive Examples ===");
        
        // Compile once, use multiple times
        RegexEngine.CompiledPattern emailPattern = 
            engine.compilePattern("a+@b+\\.c+");
            
        String[] emails = {"user@domain.com", "a@b.c", "invalid", "@domain.com"};
        
        System.out.println("\nTesting email pattern 'a+@b+\\.c+':");
        for (String email : emails) {
            boolean matches = emailPattern.matches(email);
            System.out.printf("  %-15s -> %s%n", email, matches ? "MATCH" : "NO MATCH");
        }
    }
}
