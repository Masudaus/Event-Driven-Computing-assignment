# Simple Regex Engine with ε-NFA

A simple regular expression engine implemented in Java using epsilon-Non-deterministic Finite Automata (ε-NFA) and Thompson's construction algorithm. This project demonstrates the fundamental concepts behind regex engines and finite automata theory.

## Features

- **Literals**: Basic character and string matching (`a`, `hello`)
- **Concatenation**: Sequential character matching (`ab`, `abc`)
- **Alternation**: Choice between alternatives (`a|b`, `cat|dog`)
- **Kleene Star**: Zero or more repetitions (`a*`)
- **Plus Operator**: One or more repetitions (`a+`)
- **Optional**: Zero or one occurrence (`a?`)
- **Any Character**: Wildcard matching (`.`)
- **Grouping**: Parentheses for precedence (`(ab)*`, `(a|b)+`)
- **Escaped Characters**: Literal special characters (`\*`, `\+`, `\?`, etc.)

## Project Structure

```
regex-engine/
├── regex/                      # Main package
│   ├── State.java              # Individual state in the ε-NFA
│   ├── EpsilonNFA.java         # ε-NFA implementation with epsilon closure
│   ├── RegexParser.java        # Thompson's construction parser
│   ├── RegexEngine.java        # Main engine API
│   ├── RegexDemo.java          # Demo program with examples
│   └── StateVisualizer.java    # Debug utility for visualizing NFAs
├── test/                       # Test package
│   ├── RegexEngineTest.java    # Comprehensive test suite (no JUnit)
│   └── ManualTest.java         # Quick manual testing
└── README.md                   # This file
```

## How It Works

### Thompson's Construction
The engine uses Thompson's construction algorithm to convert regular expressions into ε-NFAs:

1. **Parse** the regex into components (literals, operators, groups)
2. **Build** ε-NFA fragments for each component
3. **Combine** fragments using epsilon transitions
4. **Simulate** the ε-NFA to match input strings

### Key Algorithms
- **Epsilon Closure**: Computes all states reachable via ε-transitions
- **NFA Simulation**: Tracks multiple possible states during matching
- **Recursive Descent Parsing**: Builds the automaton from regex syntax

## Quick Start

### 1. Compile the Code
```bash
# Compile all Java files
javac -d . regex/*.java test/*.java

# Or compile specific files
javac -d . regex/RegexEngine.java regex/RegexDemo.java
```

### 2. Run the Demo
```bash
java regex.RegexDemo
```

### 3. Run Tests
```bash
# Run comprehensive test suite
java test.RegexEngineTest

# Run manual tests
java test.ManualTest
```

## Usage Examples

### Basic Usage
```java
import regex.RegexEngine;

RegexEngine engine = new RegexEngine();

// Simple matching
boolean matches = engine.matches("a*b+", "aaabbb"); // true
boolean matches = engine.matches("cat|dog", "cat");  // true
boolean matches = engine.matches("a.c", "abc");      // true
```

### Compiled Patterns (Reusable)
```java
// Compile once, use multiple times for better performance
RegexEngine.CompiledPattern pattern = engine.compilePattern("a+@b+\\.");

boolean match1 = pattern.matches("user@domain.com"); // false (missing .)
boolean match2 = pattern.matches("a@b.");            // true
```

### Complex Patterns
```java
// Email-like pattern
engine.matches("a+@b+\\.c+", "user@domain.com");  // true

// Optional groups
engine.matches("(https?://)?a+\\.b+", "http://site.com");  // true
engine.matches("(https?://)?a+\\.b+", "site.com");         // true

// Nested patterns
engine.matches("((a|b)*c)+", "abcaac");  // true
```

## Supported Regex Syntax

| Operator | Description | Example | Matches |
|----------|-------------|---------|---------|
| `a` | Literal character | `cat` | `cat` |
| `ab` | Concatenation | `abc` | `abc` |
| `a\|b` | Alternation | `cat\|dog` | `cat`, `dog` |
| `a*` | Zero or more | `ab*` | `a`, `ab`, `abbb` |
| `a+` | One or more | `ab+` | `ab`, `abbb` |
| `a?` | Optional | `ab?` | `a`, `ab` |
| `.` | Any character | `a.c` | `abc`, `a1c`, `a@c` |
| `(...)` | Grouping | `(ab)*` | ``, `ab`, `abab` |
| `\x` | Escape | `\*` | `*` |

## Testing

### Automated Tests
The `RegexEngineTest` class provides comprehensive testing without external dependencies:

```bash
java test.RegexEngineTest
```

**Test Categories:**
- Literal matching
- Concatenation
- Alternation (OR operations)
- Kleene star (zero or more)
- Plus operator (one or more)
- Optional operator
- Any character (dot)
- Parentheses and grouping
- Escaped characters
- Edge cases and error handling

### Manual Testing
For quick experiments:

```bash
java test.ManualTest
```

### Adding Your Own Tests
```java
RegexEngine engine = new RegexEngine();

// Test your patterns
System.out.println(engine.matches("your_pattern", "test_input"));

// Visualize the NFA (for debugging)
EpsilonNFA nfa = engine.compile("a*b");
StateVisualizer.printNFA(nfa);
```

## Architecture Details

### Core Components

1. **State**: Represents individual automaton states
   - Tracks accepting status
   - Manages character and epsilon transitions
   - Unique ID for debugging

2. **EpsilonNFA**: The finite automaton implementation
   - Computes epsilon closures
   - Simulates NFA execution
   - Handles multiple active states

3. **RegexParser**: Converts regex to ε-NFA
   - Recursive descent parser
   - Thompson's construction
   - Error handling for malformed patterns

4. **RegexEngine**: Main API
   - Pattern compilation
   - Match testing
   - Compiled pattern caching

### Performance Characteristics
- **Compilation**: O(m) where m is pattern length
- **Matching**: O(mn) where m is pattern length, n is input length
- **Space**: O(m) states in worst case

## Limitations

- **Character Classes**: `[a-z]`, `\d`, `\w` not supported
- **Quantifiers**: `{n,m}` syntax not implemented
- **Anchors**: `^`, `$` not supported
- **Unicode**: Limited to ASCII characters
- **Backreferences**: Not supported (would require backtracking)

## Extension Ideas

1. **Character Classes**: Add support for `[abc]`, `[a-z]`, `\d`, `\w`
2. **Quantifiers**: Implement `{n}`, `{n,}`, `{n,m}`
3. **Anchors**: Add `^` (start) and `$` (end) anchors
4. **Non-greedy**: Support lazy quantifiers (`*?`, `+?`)
5. **Capture Groups**: Extract matched substrings
6. **Unicode Support**: Extend beyond ASCII
7. **DFA Conversion**: Convert ε-NFA to DFA for better performance

## Learning Resources

### Finite Automata Theory
- **Thompson's Construction**: Original algorithm for regex to ε-NFA conversion
- **Epsilon Closure**: Computing transitive closure of ε-transitions
- **NFA Simulation**: Running non-deterministic automata
- **Subset Construction**: Converting NFA to DFA (not implemented here)

### Implementation Details
- **Recursive Descent Parsing**: Top-down parsing technique
- **State Management**: Handling multiple active states
- **Memory Management**: Avoiding cycles and leaks

## Troubleshooting

### Common Issues

**Pattern won't compile:**
```java
// Check for unmatched parentheses
engine.compile("(abc");  // Throws IllegalArgumentException
```

**Unexpected matching behavior:**
```java
// Remember operator precedence: concatenation binds tighter than alternation
engine.matches("ab|cd", "ab");    // true
engine.matches("a(b|c)d", "abd"); // true
```

**Escaping special characters:**
```java
// Use backslash to escape
engine.matches("\\*", "*");  // true
engine.matches("\\.", ".");  // true
```

### Debugging Tips

1. **Visualize the NFA:**
```java
EpsilonNFA nfa = engine.compile("your_pattern");
StateVisualizer.printNFA(nfa);
```

2. **Test incrementally:**
```java
// Build complex patterns step by step
engine.matches("a", "a");      // Start simple
engine.matches("a*", "aaa");   // Add operators
engine.matches("(a*)", "aaa"); // Add grouping
```

3. **Use the demo program:**
```bash
java regex.RegexDemo  # See working examples
```

## Contributing

This is an educational project demonstrating regex engine fundamentals. Feel free to:

- Add more operators and features
- Optimize performance
- Improve error messages
- Add more test cases
- Extend documentation

## License

This project is provided for educational purposes. Use and modify freely for learning about finite automata and regex engines.

---

**Note**: This is a simplified regex engine for educational purposes. For production use, consider established libraries like Java's `java.util.regex` package.
