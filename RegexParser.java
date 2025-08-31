package regex;

import java.util.*;

/**
 * Parses regex patterns and constructs ε-NFAs using Thompson's construction
 */
public class RegexParser {
    
    /**
     * Parses a regular expression and returns an ε-NFA
     * Supports: literals, concatenation, alternation (|), Kleene star (*), plus (+), optional (?)
     */
    public EpsilonNFA parse(String regex) {
        return parseExpression(new StringBuilder(regex));
    }
    
    private EpsilonNFA parseExpression(StringBuilder regex) {
        EpsilonNFA left = parseTerm(regex);
        
        while (regex.length() > 0 && regex.charAt(0) == '|') {
            regex.deleteCharAt(0); // consume '|'
            EpsilonNFA right = parseTerm(regex);
            left = createAlternation(left, right);
        }
        
        return left;
    }
    
    private EpsilonNFA parseTerm(StringBuilder regex) {
        EpsilonNFA result = null;
        
        while (regex.length() > 0 && regex.charAt(0) != '|' && regex.charAt(0) != ')') {
            EpsilonNFA factor = parseFactor(regex);
            
            if (result == null) {
                result = factor;
            } else {
                result = createConcatenation(result, factor);
            }
        }
        
        return result != null ? result : createEpsilon();
    }
    
    private EpsilonNFA parseFactor(StringBuilder regex) {
        EpsilonNFA base = parseAtom(regex);
        
        while (regex.length() > 0) {
            char c = regex.charAt(0);
            if (c == '*') {
                regex.deleteCharAt(0);
                base = createKleeneStar(base);
            } else if (c == '+') {
                regex.deleteCharAt(0);
                base = createPlus(base);
            } else if (c == '?') {
                regex.deleteCharAt(0);
                base = createOptional(base);
            } else {
                break;
            }
        }
        
        return base;
    }
    
    private EpsilonNFA parseAtom(StringBuilder regex) {
        if (regex.length() == 0) {
            throw new IllegalArgumentException("Unexpected end of regex");
        }
        
        char c = regex.charAt(0);
        regex.deleteCharAt(0);
        
        if (c == '(') {
            EpsilonNFA result = parseExpression(regex);
            if (regex.length() == 0 || regex.charAt(0) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            regex.deleteCharAt(0); // consume ')'
            return result;
        } else if (c == '\\' && regex.length() > 0) {
            // Handle escaped characters
            char escaped = regex.charAt(0);
            regex.deleteCharAt(0);
            return createLiteral(escaped);
        } else if (c == '.') {
            return createAnyChar();
        } else {
            return createLiteral(c);
        }
    }
    
    /**
     * Creates ε-NFA for a single character
     */
    private EpsilonNFA createLiteral(char c) {
        State start = new State();
        State end = new State(true);
        start.addTransition(c, end);
        return new EpsilonNFA(start, end);
    }
    
    /**
     * Creates ε-NFA for any character (.)
     */
    private EpsilonNFA createAnyChar() {
        State start = new State();
        State end = new State(true);
        
        // Add transitions for printable ASCII characters
        for (char c = 32; c <= 126; c++) {
            start.addTransition(c, end);
        }
        
        return new EpsilonNFA(start, end);
    }
    
    /**
     * Creates ε-NFA for epsilon (empty string)
     */
    private EpsilonNFA createEpsilon() {
        State start = new State();
        State end = new State(true);
        start.addEpsilonTransition(end);
        return new EpsilonNFA(start, end);
    }
    
    /**
     * Creates concatenation of two ε-NFAs
     */
    private EpsilonNFA createConcatenation(EpsilonNFA first, EpsilonNFA second) {
        first.getEndState().setAccepting(false);
        first.getEndState().addEpsilonTransition(second.getStartState());
        return new EpsilonNFA(first.getStartState(), second.getEndState());
    }
    
    /**
     * Creates alternation (union) of two ε-NFAs
     */
    private EpsilonNFA createAlternation(EpsilonNFA first, EpsilonNFA second) {
        State start = new State();
        State end = new State(true);
        
        start.addEpsilonTransition(first.getStartState());
        start.addEpsilonTransition(second.getStartState());
        
        first.getEndState().setAccepting(false);
        second.getEndState().setAccepting(false);
        
        first.getEndState().addEpsilonTransition(end);
        second.getEndState().addEpsilonTransition(end);
        
        return new EpsilonNFA(start, end);
    }
    
    /**
     * Creates Kleene star (zero or more repetitions)
     */
    private EpsilonNFA createKleeneStar(EpsilonNFA nfa) {
        State start = new State();
        State end = new State(true);
        
        start.addEpsilonTransition(nfa.getStartState());
        start.addEpsilonTransition(end);
        
        nfa.getEndState().setAccepting(false);
        nfa.getEndState().addEpsilonTransition(nfa.getStartState());
        nfa.getEndState().addEpsilonTransition(end);
        
        return new EpsilonNFA(start, end);
    }
    
    /**
     * Creates plus operator (one or more repetitions)
     */
    private EpsilonNFA createPlus(EpsilonNFA nfa) {
        State end = new State(true);
        
        nfa.getEndState().setAccepting(false);
        nfa.getEndState().addEpsilonTransition(nfa.getStartState());
        nfa.getEndState().addEpsilonTransition(end);
        
        return new EpsilonNFA(nfa.getStartState(), end);
    }
    
    /**
     * Creates optional operator (zero or one occurrence)
     */
    private EpsilonNFA createOptional(EpsilonNFA nfa) {
        State start = new State();
        State end = new State(true);
        
        start.addEpsilonTransition(nfa.getStartState());
        start.addEpsilonTransition(end);
        
        nfa.getEndState().setAccepting(false);
        nfa.getEndState().addEpsilonTransition(end);
        
        return new EpsilonNFA(start, end);
    }
}

