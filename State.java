package regex;

import java.util.*;

/**
 * Represents a state in the ε-NFA
 */
public class State {
    private static int nextId = 0;
    private final int id;
    private boolean isAccepting;
    private Map<Character, Set<State>> transitions;
    private Set<State> epsilonTransitions;
    
    public State() {
        this.id = nextId++;
        this.isAccepting = false;
        this.transitions = new HashMap<>();
        this.epsilonTransitions = new HashSet<>();
    }
    
    public State(boolean isAccepting) {
        this();
        this.isAccepting = isAccepting;
    }
    
    public void addTransition(char symbol, State target) {
        transitions.computeIfAbsent(symbol, k -> new HashSet<>()).add(target);
    }
    
    public void addEpsilonTransition(State target) {
        epsilonTransitions.add(target);
    }
    
    public Set<State> getTransitions(char symbol) {
        return transitions.getOrDefault(symbol, new HashSet<>());
    }
    
    public Set<State> getEpsilonTransitions() {
        return epsilonTransitions;
    }
    
    public boolean isAccepting() {
        return isAccepting;
    }
    
    public void setAccepting(boolean accepting) {
        this.isAccepting = accepting;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "State{" + id + (isAccepting ? " [ACCEPTING]" : "") + "}";
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
}

