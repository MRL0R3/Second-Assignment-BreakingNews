# Project Title

A Java-based news aggregator that fetches and displays the latest headlines from NewsAPI.
## Description

This application allows users to:

* Fetch real-time news articles from NewsAPI

* Browse headlines in a user-friendly console interface

* View detailed news information

* Refresh news content on demand

* Open articles in a web browser

Built with Java 11+, the application demonstrates API integration, JSON parsing, and clean console UI design.
## Getting Started

### Dependencies

* java 17 or higher

* Gradle 7.0+

* NewsAPI account (free tier available)

* Gson library (auto-installed via Gradle)

### Installing

1. Clone the repository:

         git clone https://github.com/yourusername/news-aggregator.git 
         cd news-aggregator
   
2. Set your NewsAPI key as environment variable:
   

         # Linux/macOS
         export NEWS_API_KEY="your_api_key_here"
                  
         # Windows
         set NEWS_API_KEY="your_api_key_here"

### Executing program

1. Build the project:

         gradle build 

2. Run the applicaion:

         gradle run

3. Alternatively, run directly:

         java -jar build/libs/news-aggregator.jar
                     
## Help

# Common issues and solutions:

         Ensure your NEWS_API_KEY environment variable is set correctly      
# No News Articles Displayed

         Check your internet connection and API key validity
         The free NewsAPI tier has limited requests per day

# Broweser Doesn't Open

         The application attempts to open your default browser
         Some Linux systems may require additional configuration

To see available commands:

         java -jar build/libs/news-aggregator.jar --help
         
## Authors

* [Ahmad Lord] - [ahmadrezajafary1384@gmail.com]

* github.com/MRL0R3
  
* github.com/TheDanielTP


## Version History

* 1.0.1
    * Improved error handling
    * Added browser integration   
    * See [commit change]() or See [release history]()
* 1.0.0
    * Initial Release with basic news fetching and display

## License

This project is licensed under the MIT License - see the LICENSE file for details.


## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* [dbader](https://github.com/dbader/readme-template)
* [zenorocha](https://gist.github.com/zenorocha/4526327)
* [fvcproductions](https://gist.github.com/fvcproductions/1bfc2d4aecb01a834b46)
