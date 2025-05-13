Feature: Pokemon API Tests

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