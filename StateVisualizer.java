package regex;

import java.util.*;

/**
 * Utility class for visualizing the ε-NFA structure (for debugging)
 */
public class StateVisualizer {
    
    public static void printNFA(EpsilonNFA nfa) {
        Set<State> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        queue.add(nfa.getStartState());
        
        System.out.println("=== ε-NFA Structure ===");
        System.out.println("Start state: " + nfa.getStartState().getId());
        System.out.println("End state: " + nfa.getEndState().getId());
        System.out.println("\nTransitions:");
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);
            
            // Print epsilon transitions
            for (State target : current.getEpsilonTransitions()) {
                System.out.printf("  %d --ε--> %d%s%n", 
                    current.getId(), 
                    target.getId(),
                    target.isAccepting() ? " [ACCEPTING]" : "");
                if (!visited.contains(target)) queue.add(target);
            }
            
            // Print character transitions
            Map<Character, Set<State>> transitions = getTransitionsMap(current);
            for (Map.Entry<Character, Set<State>> entry : transitions.entrySet()) {
                char symbol = entry.getKey();
                for (State target : entry.getValue()) {
                    System.out.printf("  %d --%c--> %d%s%n", 
                        current.getId(), 
                        symbol, 
                        target.getId(),
                        target.isAccepting() ? " [ACCEPTING]" : "");
                    if (!visited.contains(target)) queue.add(target);
                }
            }
        }
        
        System.out.println("=== End Structure ===\n");
    }
    
    private static Map<Character, Set<State>> getTransitionsMap(State state) {
        // This is a simplified approach - in a real implementation,
        // you might want to expose this through the State class
        Map<Character, Set<State>> result = new HashMap<>();
        
        // For demo purposes, we'll check common characters
        for (char c = 'a'; c <= 'z'; c++) {
            Set<State> targets = state.getTransitions(c);
            if (!targets.isEmpty()) {
                result.put(c, targets);
            }
        }
        
        for (char c = '0'; c <= '9'; c++) {
            Set<State> targets = state.getTransitions(c);
            if (!targets.isEmpty()) {
                result.put(c, targets);
            }
        }
        
        // Check some special characters
        char[] specials = {'@', '.', '-', '_', '!', '?', '*', '+', '(', ')', '|'};
        for (char c : specials) {
            Set<State> targets = state.getTransitions(c);
            if (!targets.isEmpty()) {
                result.put(c, targets);
            }
        }
        
        return result;
    }
}

