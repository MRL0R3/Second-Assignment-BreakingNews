package AP;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayWelcomeBanner();

        String apiKey = getApiKeyFromUser();
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("\n❌ No API key provided. Exiting...");
            System.exit(1);
        }

        try {
            Infrastructure newsFetcher = new Infrastructure(apiKey);
            List<News> newsList = newsFetcher.fetchNews();

            if (newsList.isEmpty()) {
                System.out.println("\n⚠️ No news articles found. Please try again later.");
                return;
            }

            runNewsMenu(newsList);

        } catch (IOException e) {
            System.err.println("\n⚠️ Network Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n⚠️ Unexpected Error: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("\nThank you for using News Aggregator! Goodbye 👋");
        }
    }

    private static void displayWelcomeBanner() {
        System.out.println("\n====================================");
        System.out.println("      📰 NEWS AGGREGATOR v1.0 📰     ");
        System.out.println("====================================");
        System.out.println("Stay updated with the latest headlines");
        System.out.println("====================================\n");
    }

    private static String getApiKeyFromUser() {
        System.out.print("🔑 Enter your NewsAPI key (or press Enter to use environment variable): ");
        String inputKey = scanner.nextLine().trim();

        if (inputKey.isEmpty()) {
            String envKey = System.getenv("NEWS_API_KEY");
            if (envKey == null || envKey.isEmpty()) {
                System.out.println("\n⚠️ No API key found in environment variables.");
                return null;
            }
            return envKey;
        }
        return inputKey;
    }

    private static void runNewsMenu(List<News> newsList) {
        boolean running = true;

        while (running) {
            clearScreen();
            displayNewsMenu(newsList);

            try {
                System.out.print("\nEnter your choice (0 to exit, 'r' to refresh): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("0")) {
                    running = false;
                    continue;
                }

                if (input.equalsIgnoreCase("r")) {
                    System.out.println("\n🔃 Refreshing news...");
                    return; // Exit to fetch fresh news
                }

                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= newsList.size()) {
                    displayNewsDetails(newsList.get(choice - 1));
                    waitForUserInput();
                } else {
                    System.out.println("\n⚠️ Invalid choice. Please try again.");
                    waitForUserInput();
                }

            } catch (NumberFormatException e) {
                System.out.println("\n⚠️ Please enter a valid number.");
                waitForUserInput();
            }
        }
    }

    private static void displayNewsMenu(List<News> newsList) {
        System.out.println("\n📰 LATEST NEWS HEADLINES");
        System.out.println("-----------------------");

        for (int i = 0; i < newsList.size(); i++) {
            String title = newsList.get(i).getTitle();
            System.out.printf("%2d. %s\n", i + 1,
                    title.length() > 60 ? title.substring(0, 57) + "..." : title);
        }

        System.out.println("\n-----------------------");
        System.out.println("0. Exit \t r. Refresh");
        System.out.println("-----------------------");
    }

    private static void displayNewsDetails(News news) {
        clearScreen();
        System.out.println("\n✨ NEWS DETAILS ✨");
        System.out.println("----------------");
        news.displayNews();
        System.out.println("\n----------------");
        System.out.println("1. Open in browser  2. Back to list");
        System.out.println("----------------");

        try {
            System.out.print("Choose option: ");
            String option = scanner.nextLine();

            if (option.equals("1")) {
                openInBrowser(news.getUrl());
            }
        } catch (Exception e) {
            System.out.println("Couldn't open browser: " + e.getMessage());
        }
    }

    private static void openInBrowser(String url) {
        try {
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                System.out.println("\n🌐 Opening in your browser...");
            } else {
                System.out.println("\n⚠️ Browser opening not supported on this platform");
            }
        } catch (Exception e) {
            System.out.println("\n⚠️ Couldn't open browser: " + e.getMessage());
        }
        waitForUserInput();
    }

    private static void waitForUserInput() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clear fails, just print some newlines
            System.out.println("\n".repeat(50));
        }
    }
}