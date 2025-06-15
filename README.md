# Bowling score calculator

This project is a simple command-line application that calculates the score of a bowling game based on the input provided by the user.

**_Design considerations_**:
- **Separation of concern**:
  The Model contains business logic and state.
  The Service classes handle application logic, I/O and user interaction.

- **Single responsibility**:
  I tried to structure the code so that each class has a clear and focused responsibility, so that the code is easier to read and follow, and extend.

- **Encapsulation**:
  State and logic is encapsulated within the relevant classes.

- **Inheritance**:
  Came in handy for handling the FinalFrame with its special rules.

- **Loose coupling**:
  Services are loosely coupled, using DI, allowing easier testing via mocking

**Testing**:
- Unit test: focus on verifying both business logic and application logic, method behavior, edge cases, error handling (or logging).
- IntegrationTest: BowlingIT is just to showcase an integration test in the form of QuarkusTest, which boots up the Quarkus application in a test mode and runs the test against the application context, with injected dependencies (or mocks of them, when given). So components are tested as they would behave "in production."


**Model**:
- `Game`: Holds the players and logic for:
  - moving to the next player
  - calculating whether the game is finished
- `Player`: Holds player name and scoreLine
  - forwards roll to the scoreLine
- `ScoreLine`: Holds a list of frames and the index of the current frame.
  - adds roll to the current frame
  - advances to the next frame after round is finished
  - calculates total score with bonuses
- `Frame`: Holds rolls (pins knocked down). Flags for STRIKE or SPARE.  Holds bonus.
  - calculates if it is STRIKE or SPARE
  - calculates if it is complete
  - calculates total score for frame with bonus
- `FinalFrame`: inherits from Frame and overrides isComplete with custom rules.

**Services**:
- `GameService`: Has the role of initiating and running the game till it is finished
- `InputReaderService`: Simply reads input from the console. No validation.
- `PinDeckService`: Keeps status on the PinDeck (where the pins are standing). Decreases remaining pins with the amount of knocked down pins. Resets once all pins are knocked down.
- `PlayerService`: Initialises list of players (currently with hard coded names, but it could be integrating to something providing that input)
- `PrintService`: Prints errors, prompts, results.
- `RollService`: Performs a roll. Gets hits either via user input (`InputReaderService`) or generates randomly (to make manual test easier). Contains input validation, as validation is in regards to the roll.

`BowlingMain` - application. Gets players and starts a game for them.



This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/command-mode-quickstart-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.
