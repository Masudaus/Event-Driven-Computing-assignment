package regex;

import java.util.*;

/**
 * Represents an ε-NFA (epsilon-Non-deterministic Finite Automaton)
 */
public class EpsilonNFA {
    private State startState;
    private State endState;
    
    public EpsilonNFA(State startState, State endState) {
        this.startState = startState;
        this.endState = endState;
    }
    
    public State getStartState() {
        return startState;
    }
    
    public State getEndState() {
        return endState;
    }
    
    /**
     * Computes epsilon closure for a set of states
     */
    public Set<State> epsilonClosure(Set<State> states) {
        Set<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);
        
        while (!stack.isEmpty()) {
            State current = stack.pop();
            for (State epsilonTarget : current.getEpsilonTransitions()) {
                if (!closure.contains(epsilonTarget)) {
                    closure.add(epsilonTarget);
                    stack.push(epsilonTarget);
                }
            }
        }
        
        return closure;
    }
    
    /**
     * Simulates the ε-NFA on input string
     */
    public boolean matches(String input) {
        Set<State> currentStates = epsilonClosure(Set.of(startState));
        
        for (char c : input.toCharArray()) {
            Set<State> nextStates = new HashSet<>();
            
            for (State state : currentStates) {
                nextStates.addAll(state.getTransitions(c));
            }
            
            currentStates = epsilonClosure(nextStates);
            
            if (currentStates.isEmpty()) {
                return false;
            }
        }
        
        // Check if any current state is accepting
        for (State state : currentStates) {
            if (state.isAccepting()) {
                return true;
            }
        }
        
        return false;
    }
}
