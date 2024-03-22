# megaverse-exercise

tonilopezmr.com Megaverse exercise

I have created an empty Spring boot project thinking I'd create an API, but finally, I have created a simple App.

That's why to run the project you can use springBoot features.

## Code Entry Point

[TonilopezmrApplication.kt](src%2Fmain%2Fkotlin%2Fcom%2Fcrossmint%2Ftonilopezmr%2FTonilopezmrApplication.kt)
is the code entry point where everything starts.

You can configure the `TONILOPEZMR_CANDIDATE_ID` with a different `CANDIDATE-ID`.

The App Creates a Megaverse from a Goal map returned by the Megaverse API.
It sends one request per second to not receive a rate limit, It can be adjusted in [Coroutines.kt](src%2Fmain%2Fkotlin%2Fcom%2Fcrossmint%2Ftonilopezmr%2FCoroutines.kt) changing how much requests (`batchSize`) you want to do in parallel per second (`delayInSeconds`).

The App can be run in parallel if you increase the `batchSize`, but also It can be done sequentially by changing the Coroutine Context.

## Programming Style

I have tried to create a simple app meanwhile I showcase what I think is important.

### Errors

I have decided to `throws` errors to stop the App, there are many ways to handle errors, but this way the code is easy to read for this test.

In Kotlin/Java I prefer to use `Result` which is a `Either<A, Exception>` than throwing errors because the methods give more transparency than throwing errors.

### Structure

As It's a small App, I'm not sure how structured it is, I didn't want to create so many folders / subfolders.

`domain` package: Domain models of the App
`usecases` package: App Actions like `Get Goal Map` and `Create Megaverso`
`services` package: External Services like `MegaverseAPI`


### Domain

```kotlin
sealed class AstralObject(val x: Int, val y: Int)
data class EmptySpace(val row: Int, val column: Int) : AstralObject(row, column)
data class POLYanet(val row: Int, val column: Int) : AstralObject(row, column)
data class SOLoon(val color: Color, val row: Int, val column: Int) : AstralObject(row, column)
data class ComETH(val direction: Direction, val row: Int, val column: Int) : AstralObject(row, column)
```

For each AstralObject I save the x,y position for easy Objects manipulations.
AstralObject is a `sealed class` to be able to know the inheritance in compile time and don't miss any case or the build won't compile.

### Decoupling

`App Layer` -> `Bussines logic` -> `External Services`

It's important to decouple **App layer** from **business logic layer** and **External Services layer**, that's why each layer
uses different DTO's and Objects through mappers. Using `Megaverse` instead of `Array<Array<String>>` or `SOLoon` instead of `BLUE_SOLOON` or `SOLoonBody`.

### Extension

```kotlin
object AstralObjectBuilder {

  private val possibleAstralObjects = listOf(
    EmptySpaceProcessor(),
    POLYanetProcessor(),
    SOLoonProcessor(),
    ComETHProcessor()
  )

  fun build(rawAstralObject: String, row: Int, column: Int): AstralObject =
    possibleAstralObjects.firstNotNullOfOrNull { it.process(rawAstralObject, row, column) }
      ?: throw UnidentifiedAstralObject(rawAstralObject)
}
```

If in the future there are new Astral Objects from the API, we would create a new `AstralObjectProcessor` and add it to the `possibleAstralObjects` list.

```kotlin
private fun create(candidateId: String, megaverse: Megaverse, astralObject: AstralObject) = when (astralObject) {
    is ComETH -> apiService.createCometh(candidateId, astralObject)
    is POLYanet -> apiService.createPolyanet(candidateId, astralObject)
    is SOLoon -> create(candidateId, megaverse, astralObject)
    is EmptySpace -> false
  }
```

Probably that will require we need to create a new `AstralObject` child, and our **Build** will fail because we don't contemplate the option in our `when`.

## Tests

I have been creating the tests at the same time I develop, creating smaller cases to make sure what I'm coding is right.

90% of the tests are unit tests except:

* [MegaverseAPIServiceIntegrationTest.kt](src%2Ftest%2Fkotlin%2Fcom%2Fcrossmint%2Ftonilopezmr%2Fintegration%2Fservices%2FMegaverseAPIServiceIntegrationTest.kt) which makes Network API calls.
* [TonilopezmrMegaverseAppTest.kt](src%2Ftest%2Fkotlin%2Fcom%2Fcrossmint%2Ftonilopezmr%2FTonilopezmrMegaverseAppTest.kt) which makes an Integration App tests, except Making Network Calls.

⚠️ [MegaverseAPIServiceIntegrationTest.kt](src%2Ftest%2Fkotlin%2Fcom%2Fcrossmint%2Ftonilopezmr%2Fintegration%2Fservices%2FMegaverseAPIServiceIntegrationTest.kt) doesn't work because, I created the tests when I was implementing the API, once the challenge is solved, the API returns 500 error.

## How to run it

Execute the TonilopezmrApplication:

 ```
./gradlew bootRun
```

## How to execute the tests

```
 ./gradlew test
```

## Code linter

Check code style:

```
./gradlew ktlintCheck
```

Check and solve the code style problems that can be solved automatically:
```
./gradlew ktlintFormat
```
