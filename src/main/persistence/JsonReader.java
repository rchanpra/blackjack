package persistence;

import model.Card;
import model.Hand;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads player from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads player from file and returns it; throws IOException if error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int balance = jsonObject.getInt("balance");
        int initial = jsonObject.getInt("initial");
        int goal = jsonObject.getInt("goal");
        Player player = new Player(name, balance, initial, goal);
        JSONArray jsonArray = jsonObject.getJSONArray("hands");
        for (Object json : jsonArray) {
            JSONObject hand = (JSONObject) json;
            addHand(player, hand);
        }
        return player;
    }

    // MODIFIES: player
    // EFFECTS: parses hand from JSON object and adds it to player
    private void addHand(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        int bet = jsonObject.getInt("bet");
        Hand hand = new Hand();
        for (Object json : jsonArray) {
            JSONObject card = (JSONObject) json;
            addCard(hand, card);
        }
        hand.setBet(bet);
        player.addHand(hand);
    }

    // MODIFIES: hand
    // EFFECTS: parses card from JSON object and adds it to hand
    private void addCard(Hand hand, JSONObject jsonObject) {
        int rank = jsonObject.getInt("rank");
        int suit = jsonObject.getInt("suit");
        Card card = new Card(rank, suit);
        hand.addCard(card);
    }
}
