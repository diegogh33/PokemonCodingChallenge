package pages;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import io.restassured.response.Response;
import utils.ApiUtils;

public class PokemonApiPage {

    private Response response;
    private String selectedPokemonName;
    private String selectedAbilityName;

    private boolean isResponseValid() {
        return response != null && response.getStatusCode() == 200;
    }

    public void getAllPokemon() {
        response = ApiUtils.getRequest("/api/v2/pokemon/");
    }

    public void getPokemonInfo(String pokemonName) {
        selectedPokemonName = pokemonName.toLowerCase();
        response = ApiUtils.getRequest("/api/v2/pokemon/" + selectedPokemonName);
    }

    public void getAbilityInfo(String abilityName) {
        selectedAbilityName = abilityName.toLowerCase();
        response = ApiUtils.getRequest("/api/v2/ability/" + selectedAbilityName);
    }

    public int getStatusCode() {
        return response != null ? response.getStatusCode() : 0;
    }

    public Integer getPokemonId() {
        return isResponseValid() ? response.jsonPath().getInt("id") : null;
    }

    public String getPokemonName() {
        return isResponseValid() ? response.jsonPath().getString("name") : null;
    }

    public Integer getBaseExperience() {
        return isResponseValid() ? response.jsonPath().getInt("base_experience") : null;
    }

    public Integer getHeight() {
        return isResponseValid() ? response.jsonPath().getInt("height") : null;
    }

    public Boolean isDefault() {
        return isResponseValid() ? response.jsonPath().getBoolean("is_default") : null;
    }

    public Integer getOrder() {
        return isResponseValid() ? response.jsonPath().getInt("order") : null;
    }

    public Integer getWeight() {
        return isResponseValid() ? response.jsonPath().getInt("weight") : null;
    }

    public List<String> getPokemonTypes() {
        if (isResponseValid()) {
            List<Map<String, Map<String, String>>> types = response.jsonPath().getList("types");
            return types.stream()
                    .map(typeMap -> typeMap.get("type").get("name"))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public String getFirstPokemonName() {
        if (isResponseValid()) {
            List<Map<String, String>> results = response.jsonPath().getList("results");
            if (!results.isEmpty()) {
                selectedPokemonName = results.get(0).get("name");
                // System.out.println("Selected Pokemon Name: " + selectedPokemonName);
                return selectedPokemonName;
            }
        }
        return null;
    }

    public String pickRandomAbilityName() {
        if (isResponseValid()) {
            List<Map<String, Map<String, String>>> abilities = response.jsonPath().getList("abilities");
            if (!abilities.isEmpty()) {
                int index = new Random().nextInt(abilities.size());
                selectedAbilityName = abilities.get(index).get("ability").get("name");
                // System.out.println("Selected Ability Name: " + selectedAbilityName);
                return selectedAbilityName;
            }
        }
        return null;
    }

    public String getAbilityName() {
        return isResponseValid() ? response.jsonPath().getString("name") : null;
    }

    public List<String> getPokemonNamesForAbility() {
        return isResponseValid() ? response.jsonPath().getList("pokemon.pokemon.name") : null;
    }

    public String getSelectedPokemonName() {
        return selectedPokemonName;
    }

    public String getSelectedAbilityName() {
        return selectedAbilityName;
    }

    public String getRawResponseBody() {
        return isResponseValid() ? response.getBody().asString() : null;
    }
}
