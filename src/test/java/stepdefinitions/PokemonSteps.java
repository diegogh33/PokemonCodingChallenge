package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.PokemonApiPage;
import utils.SharedState;

import java.util.List;

public class PokemonSteps {

    private final PokemonApiPage pokemonApiPage = new PokemonApiPage();
    private String storedPokemonName;
    private String storedAbilityName;

    @Given("I look for a {string} type pokemon named {string}")
    public void iLookForAPokemonNamed(String type, String pokemonName) {
        pokemonApiPage.getPokemonInfo(pokemonName);
    }

    @Then("The search should return pokemon info")
    public void theSearchShouldReturnPokemonInfo() {
        Assertions.assertAll("Pokemon Info Validation",
                () -> Assertions.assertNotNull(pokemonApiPage.getPokemonId(), "Pokemon ID was not found."),
                () -> Assertions.assertTrue(pokemonApiPage.getPokemonId() > 0, "Pokemon ID should be greater than 0."),

                () -> Assertions.assertNotNull(pokemonApiPage.getPokemonName(), "Pokemon name was not found."),
                () -> Assertions.assertFalse(pokemonApiPage.getPokemonName().isEmpty(),
                        "Pokemon name should not be empty."),

                () -> Assertions.assertNotNull(pokemonApiPage.getBaseExperience(), "Base experience was not found."),
                () -> Assertions.assertTrue(pokemonApiPage.getBaseExperience() > 0,
                        "Base experience should be greater than 0."),

                () -> Assertions.assertNotNull(pokemonApiPage.getHeight(), "Height was not found."),
                () -> Assertions.assertTrue(pokemonApiPage.getHeight() > 0, "Height should be greater than 0."),

                () -> Assertions.assertNotNull(pokemonApiPage.isDefault(), "'is_default' value was not found."),
                () -> Assertions.assertTrue(pokemonApiPage.isDefault() instanceof Boolean,
                        "'is_default' should be a boolean."),

                () -> Assertions.assertNotNull(pokemonApiPage.getOrder(), "Order was not found."),
                () -> Assertions.assertTrue(pokemonApiPage.getOrder() > 0, "Order should be greater than 0."),

                () -> Assertions.assertNotNull(pokemonApiPage.getWeight(), "Weight was not found."),
                () -> Assertions.assertTrue(pokemonApiPage.getWeight() > 0, "Weight should be greater than 0."));
    }

    @Then("The API call should return a status code of {int}")
    public void statusCodeShouldReturn(int expectedStatusCode) {
        int actualStatusCode = pokemonApiPage.getStatusCode();
        Assertions.assertEquals(expectedStatusCode, actualStatusCode,
                "Expected status code: " + expectedStatusCode + ", but got: " + actualStatusCode);
    }

    @Then("The pokemon type is {string}")
    public void thePokemonTypeIs(String expectedType) {
        List<String> pokemonTypes = pokemonApiPage.getPokemonTypes();
        Assertions.assertNotNull(pokemonTypes, "Pokemon types were not retrieved.");
        Assertions.assertTrue(pokemonTypes.contains(expectedType.toLowerCase()),
                "Expected type '" + expectedType + "' not found in: " + pokemonTypes);
    }

    @Given("I make a call to get all pokemons")
    public void iMakeACallToGetAllPokemons() {
        pokemonApiPage.getAllPokemon();
    }

    @When("I pick the first pokemon name from the results array and store it")
    public void iPickTheFirstPokemonNameFromTheResultsArrayAndStoreIt() {
        storedPokemonName = pokemonApiPage.getFirstPokemonName();
        SharedState.storedPokemonName = storedPokemonName;
    }

    @Then("The stored pokemon name should not be empty")
    public void theStoredPokemonNameShouldNotBeEmpty() {
        storedPokemonName = SharedState.storedPokemonName;
        Assertions.assertNotNull(storedPokemonName, "The stored pokemon name is null.");
        Assertions.assertFalse(storedPokemonName.isEmpty(), "The stored pokemon name is empty.");
    }

    @Given("I make a call to the pokemon info endpoint using the stored pokemon name")
    public void iMakeASecondApiCallToThePokemonInfoEndpointUsingTheStoredPokemonName() {
        storedPokemonName = SharedState.storedPokemonName;
        if (storedPokemonName == null) {
            throw new IllegalStateException("Missing pokemon name. Make sure previous step stored it correctly.");
        }
        pokemonApiPage.getPokemonInfo(storedPokemonName);
    }

    @When("I pick one ability name from the abilities array of the retrieved pokemon and store it")
    public void iPickOneAbilityNameFromTheAbilitiesArrayOfTheRetrievedPokemonAndStoreIt() {
        storedAbilityName = pokemonApiPage.pickRandomAbilityName();
        SharedState.storedAbilityName = storedAbilityName;
    }

    @Then("The stored ability name should not be empty")
    public void theStoredAbilityNameShouldNotBeEmpty() {
        Assertions.assertNotNull(storedAbilityName, "Stored ability name is null.");
        Assertions.assertFalse(storedAbilityName.isEmpty(), "Stored ability name is empty.");
    }

    @Given("I make a call to the ability endpoint using the stored ability name")
    public void iMakeAnApiCallToTheAbilityEndpointUsingTheStoredAbilityName() {
        storedAbilityName = SharedState.storedAbilityName;
        if (storedAbilityName == null) {
            throw new IllegalStateException("Missing stored ability name. Was it picked in a previous step?");
        }
        pokemonApiPage.getAbilityInfo(storedAbilityName);
    }

    @Then("The ability name in the response should match the stored ability name")
    public void theAbilityNameInTheResponseShouldMatchTheStoredAbilityName() {
        String responseAbilityName = pokemonApiPage.getAbilityName();
        Assertions.assertEquals(storedAbilityName, responseAbilityName,
                "Expected ability name to match stored value.");
    }

    @Then("The pokemon list for the ability should include the stored pokemon name")
    public void thePokemonListForTheAbilityShouldIncludeTheStoredPokemonName() {
        String storedPokemon = SharedState.storedPokemonName;
        List<String> abilityPokemonNames = pokemonApiPage.getPokemonNamesForAbility();
        Assertions.assertTrue(abilityPokemonNames.contains(storedPokemon),
                "Expected the ability to include pokemon: " + storedPokemon);
    }

    @Given("I look for a pokemon with name {string}")
    public void iLookForAPokemonWithName(String pokemonName) {
        pokemonApiPage.getPokemonInfo(pokemonName);
    }

    @Given("I look for a pokemon with ability {string}")
    public void iLookForAPokemonWithAbility(String abilityName) {
        pokemonApiPage.getAbilityInfo(abilityName);
    }

    @Then("The ability name in the response should match with the ability {string}")
    public void theAbilityNameInTheResponseShouldMatchWithAbility(String abilityName) {
        String responseAbilityName = pokemonApiPage.getAbilityName();
        Assertions.assertEquals(abilityName, responseAbilityName,
                "Expected ability name to match given value.");
    }
}
