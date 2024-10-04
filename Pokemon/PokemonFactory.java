package Pokemon;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PokemonFactory {

    private static List<Pokemon> pokemons = new ArrayList<>();

    public static void chargerPokemons(String fichierJSON) {
        try {
            String contenu = new String(Files.readAllBytes(Paths.get(fichierJSON)));

            JSONParser parser = new JSONParser();
            JSONArray pokemonData = (JSONArray) parser.parse(contenu);

            for (Object obj : pokemonData) {
                JSONObject data = (JSONObject) obj;

                int id = ((Long) data.get("id")).intValue();
                String name = (String) data.get("name");
                JSONArray typesArray = (JSONArray) data.get("type");

                Type primaryType = Type.valueOf(((String) typesArray.get(0)).toUpperCase());

                Type secondaryType = null;
                if (typesArray.size() > 1) {
                    secondaryType = Type.valueOf(((String) typesArray.get(1)).toUpperCase());
                }

                int pv = ((Long) data.get("pv")).intValue();
                int attack = ((Long) data.get("attack")).intValue();
                int defense = ((Long) data.get("defense")).intValue();
                int speed = ((Long) data.get("speed")).intValue();
                String evolution = (String) data.get("evolution");

                int evolutionLevel = data.get("evolutionLevel") != null
                        ? ((Long) data.get("evolutionLevel")).intValue()
                        : 0;

                Pokemon pokemon = new Pokemon(
                    id,
                    name,
                    primaryType,
                    secondaryType,
                    pv,
                    attack,
                    defense,
                    speed,
                    evolution,
                    evolutionLevel
                );

                pokemons.add(pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Pokemon obtenirPokemon(String nom) {
        for (Pokemon p : pokemons) {
            if (p.getName().equals(nom)) {
                return p;
            }
        }
        return null;
    }

    public static List<Pokemon> obtenirTousPokemons() {
        return pokemons;
    }
}
