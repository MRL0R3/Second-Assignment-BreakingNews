package AP;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String apiKey = System.getenv("NEWS_API_KEY");
        System.out.println("Welcome to News Aggregator!");
        System.out.print("Enter your NewsAPI key: ");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("❌ ERROR: API key not found in environment variables!");
            System.exit(1);
        }


        List<News> newsList = null;
        String url;
        Infrastructure newsFetcher = new Infrastructure(apiKey);

        try {
            System.out.println("\nFetching latest news...");
            String jsonData = newsFetcher.fetchNewsData();
            newsList = newsFetcher.getNewsList();
            boolean running = true;
            while (running) {
                displayMenu(newsList);
                System.out.print("\nEnter your choice (0 to exit): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 0) {
                    running = false;
                } else if (choice > 0 && choice <= newsList.size()) {
                    newsList.get(choice - 1).displayNews();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error fetching news: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("Thank you for using News Aggregator!");
        }
    }

    private static void displayMenu(List<News> newsList) {
        System.out.println("\n=== Latest News Headlines ===");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, newsList.get(i).getTitle());
        }
    }
}