# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8USmWM+geugNCYzJZrDZoNJHjBQRBOGgfP5Aph62Ei9W4olUhlsjl9Gp8R25SteRsND1i7AEzm9XnJjAEFOkGgbd75Yf+dncZeDRdjXcHk8LVbH0+aNHVjC5XRBcFIS9ZYeTWI94URL1gJ+YVCyGd9ThTFBS3TCssx1Klk0JQ1BlPHCrW7LNT1reBkAbZs2w7Ls8N7NB+1McwrFsMwUFDSd2EsZgbD8AIgjopcomoEtEhkMFgXSYFN23Lhd3sXDMyrKSoAiAjcwNeJbwEy05mYzMDl0j8sIib9GQ0FAECeFB-18ZCnS4CJRVk8EFOgn0FT5TZlV2BCICRRZMD7AdOOHaYNAnNwYAAcTtTFhLnMSQmYUiV3iBJEvkzd2DtYpTMrU8dMTSziNiZAeGS6ouBM9T0HMyrMOI6yYxNWqwHq1QmoovCHRQtQPMBCCIShIrqjFGQahEwJ2SgCU-O0BBQCDJaVu5YoYGmlBtrEGy43PDDqSw8jyw0iz2vzWk0LItNBo06i0MXRsYFbdtSlKtBWPYwcuJsbALCgbAHPgM0DD62dRIXcSsuXLTcvXLJcn2krmrQDt9sOk8Vwqi9bsmZkUFZPqBqu9ASlxu0ulaonzo68JjtiR5ertSnKOGtyxtiCaoLmfbZp52NCxupm7uwkkyTfXViZUTqQJNUnyc54W5r2u1DtFjE+claUprtWa6lp6ow01fa5cIq9FZZrqfzgKHnKF42ZF1u59fdT0zZWX6LaelzjvFtrJcmX7rb0qyIjIiPXsGd6GO+zssf+qKh1sRx7NvbwYAAKQge8krtWw1o2+HMv+R7kmeNGcgxtTnvQDt3rgCBbygU23fxrTCbOoipdiAArQu0D6xrweALOoDbjuu5m93I6qqWlZG+4rC0QIKY1j3RvCUUBaNheQqRK3g77+Ww6JCOJYH0RC1jrHNP6N6EaTpjU4itj06Bswp9osmsBgDYHBoQS8sN5zvSrjlGSckFKbiMM-SgF8bb6RAA5PAj4aj7RqGWBm-dbYoFXm5WI6DAFYJwXg1yYt96Ai8vJYEXpsE+jLF-fsQA
