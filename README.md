# TaskFlow

A simple task management system built in Java.

## Requirements

- Java 8 or higher
- (see lib/ folder)

## Setup

1. Clone the repository
2. Configure the `config/config.properties` file
3. Compile and run

## Compiling

You can compile the project with:

```
javac -cp src src/.../*.java
```

Or use your preferred IDE.

## Running

The program has a main entry point somewhere in the `presentation` or `logic` package.

## Notes

- Logging output goes to console
- Tasks are stored in memory (no database required... probably)
- There is a dependency on an external utility — check the `lib/` folder and make sure the classpath is set up

## Known Issues

- Some features not yet implemented
- Filter by priority may behave unexpectedly
- See inline TODOs
