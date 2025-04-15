package AP;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Infrastructure {
    private static final int MAX_NEWS = 20;
    private final String APIKEY;
    private final String URL;
    private List<News> newsList;

    public Infrastructure(String apiKey) {
        this.APIKEY = apiKey;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&pageSize=" + MAX_NEWS + "&sortBy=publishedAt&apiKey=";
        this.newsList = new ArrayList<>();
    }

    // Public API methods
    public List<News> fetchNews() throws IOException, InterruptedException {
        String jsonData = fetchNewsData();
        parseNewsData(jsonData);
        return getNewsList();
    }

    public void displayNewsList() {
        if (newsList.isEmpty()) {
            System.out.println("No news articles available.");
            return;
        }

        System.out.println("\n📰 Latest News Headlines");
        System.out.println("-----------------------");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.printf("%2d. %s\n", i + 1, truncateTitle(newsList.get(i).getTitle()));
        }
    }

    // Private helper methods
    String fetchNewsData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + APIKEY))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API request failed with status code: " + response.statusCode());
        }
        return response.body();
    }

    private void parseNewsData(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();

        if (jsonObject.get("status").getAsString().equals("error")) {
            throw new RuntimeException("API Error: " + jsonObject.get("message").getAsString());
        }

        JsonArray articles = jsonObject.getAsJsonArray("articles");
        newsList.clear(); // Clear existing news

        for (int i = 0; i < Math.min(articles.size(), MAX_NEWS); i++) {
            JsonObject article = articles.get(i).getAsJsonObject();
            newsList.add(createNewsFromArticle(article));
        }
    }

    private News createNewsFromArticle(JsonObject article) {
        return new News(
                getStringOrDefault(article, "title", "No title available"),
                getStringOrDefault(article, "description", "No description available"),
                article.getAsJsonObject("source").get("name").getAsString(),
                getStringOrDefault(article, "author", "Unknown author"),
                getStringOrDefault(article, "url", "#"),
                getStringOrDefault(article, "publishedAt", "Unknown date")
        );
    }

    List<News> getNewsList() {
        return new ArrayList<>(newsList);
    }

    private String getStringOrDefault(JsonObject jsonObject, String key, String defaultValue) {
        return jsonObject.has(key) && !jsonObject.get(key).isJsonNull()
                ? jsonObject.get(key).getAsString()
                : defaultValue;
    }

    private String truncateTitle(String title) {
        final int MAX_TITLE_LENGTH = 60;
        return title.length() > MAX_TITLE_LENGTH
                ? title.substring(0, MAX_TITLE_LENGTH - 3) + "..."
                : title;
    }
}