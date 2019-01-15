package Client.REST;

import Client.PokemonGame.Models.Pokemon;
import Shared.REST.PokemonLibraryResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RESTCommunicator
{
    private final String url = "http://localhost:8090/pokemonLibrary";

    private final Gson gson = new Gson();

    private final int NOTDEFINED = -1;


    public Pokemon getPokemon(String name) {
        String queryGet = "/pokemon/" + name;
        PokemonLibraryResponse response = executeQueryGet(queryGet);
        return response.getPokemon().get(0);
    }

    public List<Pokemon> getAllPokemon() {
        String queryGet = "/pokemon/all";
        PokemonLibraryResponse response = executeQueryGet(queryGet);
        return response.getPokemon();
    }


    public Pokemon addPokemon(Pokemon pokemon) {

        String queryPost = "/pokemon";
        PokemonLibraryResponse response = executeQueryPost(pokemon,queryPost);
        return response.getPokemon().get(0);
    }


    public boolean changeHealth(Pokemon pokemon) {
        String queryPut = "/pet/changeHealth";
        PokemonLibraryResponse response = executeQueryPut(pokemon,queryPut);
        return response.isSuccess();
    }


    public boolean removePet(String name) {
        String queryDelete = "/pet/" + name;
        PokemonLibraryResponse response = executeQueryDelete(queryDelete);
        return response.isSuccess();
    }

    private PokemonLibraryResponse executeQueryGet(String queryGet) {

        // Build the query for the REST service
        final String query = url + queryGet;
        System.out.println("[Query Get] : " + query);

        // Execute the HTTP GET request
        HttpGet httpGet = new HttpGet(query);
        return executeHttpUriRequest(httpGet);
    }

    private PokemonLibraryResponse executeQueryPost(Pokemon pokemonRequest, String queryPost) {

        // Build the query for the REST service
        final String query = url + queryPost;
        System.out.println("[Query Post] : " + query);

        // Execute the HTTP POST request
        HttpPost httpPost = new HttpPost(query);
        httpPost.addHeader("content-type", "application/json");
        StringEntity params;
        try {
            params = new StringEntity(gson.toJson(pokemonRequest));
            httpPost.setEntity(params);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RESTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeHttpUriRequest(httpPost);
    }

    private PokemonLibraryResponse executeQueryPut(Pokemon petRequest, String queryPut) {

        // Build the query for the REST service
        final String query = url + queryPut;
        System.out.println("[Query Put] : " + query);

        // Execute the HTTP PUT request
        HttpPut httpPut = new HttpPut(query);
        httpPut.addHeader("content-type", "application/json");
        StringEntity params;
        try {
            params = new StringEntity(gson.toJson(petRequest));
            httpPut.setEntity(params);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RESTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeHttpUriRequest(httpPut);
    }

    private PokemonLibraryResponse executeQueryDelete(String queryDelete) {

        // Build the query for the REST service
        final String query = url + queryDelete;
        System.out.println("[Query Delete] : " + query);

        // Execute the HTTP DELETE request
        HttpDelete httpDelete = new HttpDelete(query);
        return executeHttpUriRequest(httpDelete);
    }

    private PokemonLibraryResponse executeHttpUriRequest(HttpUriRequest httpUriRequest) {

        // Execute the HttpUriRequest
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpUriRequest)) {
            System.out.println("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println("[Entity] : " + entityString);
            PokemonLibraryResponse pokemonLibraryResponse = gson.fromJson(entityString, PokemonLibraryResponse.class);
            return pokemonLibraryResponse;
        } catch (IOException e) {
            System.out.println("IOException : " + e.toString());
            PokemonLibraryResponse pokemonLibraryResponse = new PokemonLibraryResponse();
            pokemonLibraryResponse.setSuccess(false);
            return pokemonLibraryResponse;
        } catch (JsonSyntaxException e) {
            System.out.println("JsonSyntaxException : " + e.toString());
            PokemonLibraryResponse pokemonLibraryResponse = new PokemonLibraryResponse();
            pokemonLibraryResponse.setSuccess(false);
            return pokemonLibraryResponse;
        }
    }
}
