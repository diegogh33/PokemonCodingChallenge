
# PokemonCodingChallenge

This project is a coding challenge for a Senior QA Engineer position. It is built using:

- Java
- Rest Assured (for HTTP requests)
- Cucumber with Gherkin syntax (for BDD-style test definitions)
- JUnit 5

## 📌 Objective

Automate and validate endpoints from the [PokeAPI](https://pokeapi.co), including:
- Listing all Pokémon
- Retrieving information about a specific Pokémon
- Accessing ability data for a given Pokémon

Website info: https://pokeapi.co/docs/v2 

## ⚙️ Setup Instructions

1. Clone the repository.
2. Ensure you have Java 17+ and Maven installed.
3. From the project root, run:

```bash
mvn clean verify
```

This will build and execute the test suite.

## 🧪 Scenarios Structure

In this solution, the testing flow has been split across **different separate Cucumber scenarios**:

```gherkin
  Scenario: Search for a specific pokemon and verify its type
    Given I look for a "flying" type pokemon named "charizard"
    Then The API call should return a status code of 200
    And The search should return pokemon info
    And The pokemon type is "flying"


  Scenario: Pick the first pokemon name from the list
    Given I make a call to get all pokemons
    Then The API call should return a status code of 200
    When I pick the first pokemon name from the results array and store it
    Then The stored pokemon name should not be empty


  Scenario: Pick an ability name from a specific pokemon
    Given I make a call to the pokemon info endpoint using the stored pokemon name
    Then The API call should return a status code of 200
    When I pick one ability name from the abilities array of the retrieved pokemon and store it
    Then The stored ability name should not be empty


  Scenario: Validate ability details using the stored ability name
    Given I make a call to the ability endpoint using the stored ability name
    Then The API call should return a status code of 200
    Then The ability name in the response should match the stored ability name
    And The pokemon list for the ability should include the stored pokemon name


  Scenario Outline: Validate information for a given pokemon
    Given I look for a pokemon with name "<pokemonName>"
    Then The API call should return a status code of 200
    And The search should return pokemon info
    Examples:
      | pokemonName |
      | bulbasaur   |
      | charmander  |


  Scenario Outline: Validate ability appears in the response
    Given I look for a pokemon with ability "<abilityName>"
    Then The API call should return a status code of 200
    And The ability name in the response should match with the ability "<abilityName>"
    Examples:
      | abilityName |
      | overgrow    |
      | chlorophyll |
```

## ⚠️ Design Note: Sharing State Between Scenarios

Cucumber is designed to isolate state between scenarios to ensure test independence and reliability. However, in this challenge, we intentionally split the test logic across multiple scenarios **to showcase modular and reusable test steps**.

To allow subsequent scenarios to access data (like the selected Pokémon or ability name) from previous ones, we introduced a `SharedState` class:

```java
public class SharedState {
    public static String storedPokemonName;
    public static String storedAbilityName;
}
```

This static class holds shared variables between scenarios. While **not a recommended practice for production environments**, it's acceptable here for the purpose of demonstrating control over test flow and data reuse.

> In real-world BDD frameworks, consider keeping such flows within a single `Scenario` or using a `Scenario Outline` to ensure test isolation and better maintainability.

## 📂 Project Structure Overview

```
src
├── main
│   └── java
│       └── api, pages, utils
├── test
│   └── java
│       └── stepdefinitions
└── resources
    └── features
```

## ✅ Validations Performed

Each API call validates:
- HTTP status code (expected 200)
- Required fields are present and meet expected conditions
- Dynamic data is reused and verified across multiple endpoints

## 📊 Generating Allure Report

This project supports [Allure Reports](https://docs.qameta.io/allure/). To view test results with a rich UI:

1. Ensure Allure is installed:
   ```bash
   npm install -g allure-commandline --save-dev
   ```

2. Run the test suite:
   ```bash
   mvn clean verify
   ```

3. To generate and view the report, run:
   ```bash
   allure serve target/allure-results
   ```

   This will:
   - Generate the report in a temporary `allure-report/` directory at the **root** of the project (this is expected behavior).
   - Open the report in your default web browser.
   - Delete the report once the browser session is closed.

### 📁 Want to keep the report?

If you prefer to persist the report inside the `target/` folder, run:

```bash
allure generate target/allure-results -o target/allure-report
```

You can then manually open the HTML index:
```
target/allure-report/index.html
```

---

Feel free to customize this project or add additional test scenarios to expand coverage and showcase further QA engineering skills.
