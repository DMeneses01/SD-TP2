package com.example.demo;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.data.Player;
import com.example.data.Team;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


@RestController
@RequestMapping("rest")
public class RESTcontroller {
    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @GetMapping("/getStatus")
    public void getStatus() throws Exception {
        // Host url
        String host = "https://v3.football.api-sports.io/";
        String charset = "UTF-8";
        // Headers for a request
        String x_rapidapi_host = "v3.football.api-sports.io";
        String x_rapidapi_key = "25044ffb7b494b8a469a7852f5e347f0"; //Type here your key

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(host.concat("/status"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        System.out.println(response.getBody().toString());
    }

    @GetMapping("/getTeams")
    public void getTeams() throws Exception {

        // Host url
        String host = "https://v3.football.api-sports.io/";
        String charset = "UTF-8";
        // Headers for a request
        String x_rapidapi_host = "v3.football.api-sports.io";
        String x_rapidapi_key = "25044ffb7b494b8a469a7852f5e347f0"; //Type here your key

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(host.concat("/teams?league=94&season=2020"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonParser jp = new JsonParser();

        JsonElement je = jp.parse(response.getBody().toString());

        JsonObject myObject = je.getAsJsonObject();
        JsonArray myArray = myObject.get("response").getAsJsonArray();
        System.out.println(myArray.toString());
        for (JsonElement resp: myArray) {
            JsonObject joT = resp.getAsJsonObject();
            JsonObject teamInfo = joT.getAsJsonObject("team");
            System.out.println(teamInfo.toString());
            String name = gson.toJson(teamInfo.get("name")).replaceAll("\"", "");
            String image = gson.toJson(teamInfo.get("logo")).replaceAll("\"", "");

            System.out.println(name + " ## " + image);

            Team t = new Team();
            t.setName(name);
            t.setImage(image);
            teamService.addTeam(t);
        }
    }

    public void getPlayersAux(HttpResponse<JsonNode> response) throws ParseException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonParser jp = new JsonParser();

        JsonElement je = jp.parse(response.getBody().toString());

        JsonObject myObject = je.getAsJsonObject();
        JsonArray myArray = myObject.get("response").getAsJsonArray();
        System.out.println(myArray.toString());

        for (JsonElement resp: myArray) {
            JsonObject joP = resp.getAsJsonObject();
            JsonObject playerInfo = joP.getAsJsonObject("player");
            String name = gson.toJson(playerInfo.get("name")).replaceAll("\"", "");

            JsonObject birthInfo = playerInfo.getAsJsonObject("birth");
            String birth = gson.toJson(birthInfo.get("date")).replaceAll("\"", "");

            JsonArray statsInfo = joP.getAsJsonArray("statistics");

            JsonElement jeN = statsInfo.get(0);
            JsonObject joStatsInfo = jeN.getAsJsonObject();

            JsonObject teamInfo = joStatsInfo.getAsJsonObject("team");
            String teamName = gson.toJson(teamInfo.get("name")).replaceAll("\"", "");

            JsonObject gamesInfo = joStatsInfo.getAsJsonObject("games");
            String position = gson.toJson(gamesInfo.get("position")).replaceAll("\"", "");

            System.out.println(name + " ## " + birth + " ## " + teamName + " ## " + position);

            Optional<Team> t = teamService.getTeamByName(teamName);
            if (t.isPresent()) {
                Player p = new Player();
                p.setT(t.get());
                p.setName(name);
                p.setPosition(position);

                Date birth_;

                if (birth.equals("null")) birth_ = new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
                else birth_ = new SimpleDateFormat("yyyy-MM-dd").parse(birth);

                p.setBirth(birth_);
                playerService.addPlayer(p);
            }
        }
    }

    @GetMapping("/getPlayers")
    public void getPlayers() throws Exception {


        // Host url
        String host = "https://v3.football.api-sports.io/";
        String charset = "UTF-8";
        // Headers for a request
        String x_rapidapi_host = "v3.football.api-sports.io";
        String x_rapidapi_key = "25044ffb7b494b8a469a7852f5e347f0"; //Type here your key


        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(host.concat("/players?league=94&season=2020&page=1"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response1 = Unirest.get(host.concat("/players?league=94&season=2020&page=2"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response1);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response2 = Unirest.get(host.concat("/players?league=94&season=2020&page=3"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response2);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response3 = Unirest.get(host.concat("/players?league=94&season=2020&page=4"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response3);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response4 = Unirest.get(host.concat("/players?league=94&season=2020&page=5"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response4);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response5 = Unirest.get(host.concat("/players?league=94&season=2020&page=6"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response5);


        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response6 = Unirest.get(host.concat("/players?league=94&season=2020&page=7"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response6);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response7 = Unirest.get(host.concat("/players?league=94&season=2020&page=8"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response7);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response8 = Unirest.get(host.concat("/players?league=94&season=2020&page=9"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response8);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response9 = Unirest.get(host.concat("/players?league=94&season=2020&page=10"))
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .asJson();

        getPlayersAux(response9);

    }

}
