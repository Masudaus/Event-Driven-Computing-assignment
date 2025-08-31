package test;

import regex.*;

/**
 * Comprehensive test suite for the regex engine (Pure Java - no JUnit)
 */
public class RegexEngineTest {
    private RegexEngine engine;
    private int totalTests = 0;
    private int passedTests = 0;
    
    public RegexEngineTest() {
        engine = new RegexEngine();
    }
    
    public static void main(String[] args) {
        RegexEngineTest tester = new RegexEngineTest();
        tester.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("=== Running Regex Engine Tests ===\n");
        
        testLiteralMatching();
        testConcatenation();
        testAlternation();
        testKleeneStar();
        testPlus();
        testOptional();
        testAnyCharacter();
        testParentheses();
        testComplexExpressions();
        testEscapedCharacters();
        testEmptyString();
        testCompiledPattern();
        testInvalidPatterns();
        testEdgeCases();
        
        System.out.println("\n=== Test Results ===");
        System.out.printf("Total: %d, Passed: %d, Failed: %d\n", 
            totalTests, passedTests, totalTests - passedTests);
        System.out.printf("Success Rate: %.1f%%\n", 
            (passedTests * 100.0) / totalTests);
        
        if (passedTests == totalTests) {
            System.out.println("All tests passed! ✓");
        } else {
            System.out.println("Some tests failed. Check implementation.");
        }
    }
    
    private void assertTrue(boolean condition, String testName) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else {
            System.out.println("✗ " + testName);
        }
    }
    
    private void assertFalse(boolean condition, String testName) {
        assertTrue(!condition, testName);
    }
    
    private void testLiteralMatching() {
        System.out.println("Testing Literal Matching:");
        assertTrue(engine.matches("a", "a"), "Single character match");
        assertTrue(engine.matches("hello", "hello"), "String match");
        assertFalse(engine.matches("a", "b"), "Single character no match");
        assertFalse(engine.matches("hello", "world"), "String no match");
        System.out.println();
    }
    
    private void testConcatenation() {
        System.out.println("Testing Concatenation:");
        assertTrue(engine.matches("ab", "ab"), "Two character concatenation");
        assertTrue(engine.matches("abc", "abc"), "Three character concatenation");
        assertFalse(engine.matches("ab", "ba"), "Wrong order");
        assertFalse(engine.matches("abc", "ab"), "Incomplete match");
        System.out.println();
    }
    
    private void testAlternation() {
        System.out.println("Testing Alternation:");
        assertTrue(engine.matches("a|b", "a"), "First alternative");
        assertTrue(engine.matches("a|b", "b"), "Second alternative");
        assertFalse(engine.matches("a|b", "c"), "No alternative matches");
        assertTrue(engine.matches("cat|dog", "cat"), "String alternative 1");
        assertTrue(engine.matches("cat|dog", "dog"), "String alternative 2");
        assertFalse(engine.matches("cat|dog", "bird"), "String no match");
        System.out.println();
    }
    
    private void testKleeneStar() {
        System.out.println("Testing Kleene Star:");
        assertTrue(engine.matches("a*", ""), "Zero occurrences");
        assertTrue(engine.matches("a*", "a"), "One occurrence");
        assertTrue(engine.matches("a*", "aaa"), "Multiple occurrences");
        assertTrue(engine.matches("ab*", "a"), "Zero b's");
        assertTrue(engine.matches("ab*", "ab"), "One b");
        assertTrue(engine.matches("ab*", "abbb"), "Multiple b's");
        assertFalse(engine.matches("ab*", "ba"), "Wrong order with star");
        System.out.println();
    }
    
    private void testPlus() {
        System.out.println("Testing Plus:");
        assertFalse(engine.matches("a+", ""), "Zero occurrences (should fail)");
        assertTrue(engine.matches("a+", "a"), "One occurrence");
        assertTrue(engine.matches("a+", "aaa"), "Multiple occurrences");
        assertFalse(engine.matches("ab+", "a"), "Zero b's (should fail)");
        assertTrue(engine.matches("ab+", "ab"), "One b");
        assertTrue(engine.matches("ab+", "abbb"), "Multiple b's");
        System.out.println();
    }
    
    private void testOptional() {
        System.out.println("Testing Optional:");
        assertTrue(engine.matches("a?", ""), "Zero occurrences");
        assertTrue(engine.matches("a?", "a"), "One occurrence");
        assertFalse(engine.matches("a?", "aa"), "Multiple occurrences (should fail)");
        assertTrue(engine.matches("ab?", "a"), "Optional b missing");
        assertTrue(engine.matches("ab?", "ab"), "Optional b present");
        assertFalse(engine.matches("ab?", "abb"), "Too many b's");
        System.out.println();
    }
    
    private void testAnyCharacter() {
        System.out.println("Testing Any Character (.):");
        assertTrue(engine.matches(".", "a"), "Letter");
        assertTrue(engine.matches(".", "1"), "Digit");
        assertTrue(engine.matches(".", "@"), "Symbol");
        assertFalse(engine.matches(".", ""), "Empty string");
        assertTrue(engine.matches("a.c", "abc"), "Any char between letters");
        assertTrue(engine.matches("a.c", "a1c"), "Digit between letters");
        assertFalse(engine.matches("a.c", "ac"), "Missing middle character");
        System.out.println();
    }
    
    private void testParentheses() {
        System.out.println("Testing Parentheses:");
        assertTrue(engine.matches("(ab)", "ab"), "Simple grouping");
        assertTrue(engine.matches("(a|b)*", ""), "Empty with grouped alternation");
        assertTrue(engine.matches("(a|b)*", "abab"), "Multiple with grouped alternation");
        assertTrue(engine.matches("(ab)+", "ab"), "Grouped plus");
        assertTrue(engine.matches("(ab)+", "abab"), "Multiple grouped plus");
        assertFalse(engine.matches("(ab)+", ""), "Empty with grouped plus");
        System.out.println();
    }
    
    private void testComplexExpressions() {
        System.out.println("Testing Complex Expressions:");
        assertTrue(engine.matches("a+@b+", "aaa@bbb"), "Email-like pattern");
        assertFalse(engine.matches("a+@b+", "aaa@"), "Incomplete email");
        System.out.println();
    }
    
    private void testEscapedCharacters() {
        System.out.println("Testing Escaped Characters:");
        assertTrue(engine.matches("\\*", "*"), "Escaped star");
        assertTrue(engine.matches("\\+", "+"), "Escaped plus");
        assertTrue(engine.matches("\\?", "?"), "Escaped question");
        assertTrue(engine.matches("\\|", "|"), "Escaped pipe");
        assertTrue(engine.matches("\\(", "("), "Escaped open paren");
        assertTrue(engine.matches("\\)", ")"), "Escaped close paren");
        System.out.println();
    }
    
    private void testEmptyString() {
        System.out.println("Testing Empty String:");
        assertFalse(engine.matches("a", ""), "Literal vs empty");
        assertTrue(engine.matches("a*", ""), "Star with empty");
        assertTrue(engine.matches("a?", ""), "Optional with empty");
        assertFalse(engine.matches("a+", ""), "Plus with empty");
        System.out.println();
    }
    
    private void testCompiledPattern() {
        System.out.println("Testing Compiled Pattern:");
        RegexEngine.CompiledPattern pattern = engine.compilePattern("a+b*");
        
        assertTrue(pattern.matches("a"), "Compiled pattern: a");
        assertTrue(pattern.matches("ab"), "Compiled pattern: ab");
        assertTrue(pattern.matches("aabb"), "Compiled pattern: aabb");
        assertFalse(pattern.matches("b"), "Compiled pattern: b (should fail)");
        assertFalse(pattern.matches(""), "Compiled pattern: empty (should fail)");
        System.out.println();
    }
    
    private void testInvalidPatterns() {
        System.out.println("Testing Invalid Patterns:");
        boolean exceptionThrown = false;
        
        try {
            engine.compile("");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "Empty pattern throws exception");
        
        exceptionThrown = false;
        try {
            engine.compile(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "Null pattern throws exception");
        
        exceptionThrown = false;
        try {
            engine.compile("(abc");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "Unclosed parenthesis throws exception");
        
        exceptionThrown = false;
        try {
            engine.compile("abc)");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "Unmatched closing parenthesis throws exception");
        System.out.println();
    }
    
    private void testEdgeCases() {
        System.out.println("Testing Edge Cases:");
        assertTrue(engine.matches("((a|b)*c)+", "c"), "Nested grouping: c");
        assertTrue(engine.matches("((a|b)*c)+", "abc"), "Nested grouping: abc");
        assertTrue(engine.matches("((a|b)*c)+", "abcaac"), "Nested grouping: complex");
        
        assertTrue(engine.matches("a*b+c?", "bbb"), "Multiple operators: bbb");
        assertFalse(engine.matches("a*b+c?", "aaa"), "Multiple operators: aaa (no b)");
        System.out.println();
    }
}

