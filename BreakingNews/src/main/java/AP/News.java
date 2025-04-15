package AP;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class News {
    private String title;
    private String description;
    private String sourceName;
    private String author;
    private String url;
    private String publishedAt;

    public News(String title, String description, String sourceName,
                String author, String url, String publishedAt) {
        this.title = title;
        this.description = description;
        this.sourceName = sourceName;
        this.author = author;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    public void displayNews() {
        System.out.println("\n=== News Details ===");
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Source: " + sourceName);
        System.out.println("Author: " + (author != null ? author : "Unknown"));
        System.out.println("URL: " + url);

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(publishedAt, inputFormatter);
            System.out.println("Published At: " + dateTime.format(outputFormatter));
        } catch (Exception e) {
            System.out.println("Published At: " + publishedAt);
        }
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSourceName() { return sourceName; }
    public String getAuthor() { return author; }
    public String getUrl() { return url; }
    public String getPublishedAt() { return publishedAt; }
}