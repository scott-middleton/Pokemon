### Senior Android Developer Test Project

This is a development task for a Senior Android developer role at Zap-Map. If you have any questions, please email benrosen@zap-map.com.

## The task

A junior  developer wrote a Pokemon app according to the [requirements (described in a following section)](#the-pokemon-app-requirements).

Whilst the app is functional, it is not written in a way that is testable or flexible for future changes.

We would like you to try and improve it by doing the following:

**We completely understand if you do not have time to complete everything, if there was anything you would have done if you had more time, please document it.**

- Make sure all requirements are met correctly, and fix any issues found.
- Refactor the code so it is clean and testable.
- Make sure the code is written so that in the future, we could easily switch to use different 3rd party libraries.
- Make sure the code is written so that in the future, we could easily add a database backup for the Pokemon data.
- Add unit tests, the higher the coverage the better.
- Bonus: make sure the code is written so that the list and details code don't know about each other.
- Document the approach to your changes, and anything else you would change if you had more time.

Rules:

- Don't change any code in the zoogle_analytics package
- Don't add any more functionality, we want the code to be ready for more features, but not for them to be added.
- Keep using the traditional View System. No need for Jetpack Compose in this project

## The Pokemon App Requirements

I would like to be presented with a list of Pokemon names from the https://pokeapi.co/ API and be able to find out some details about them. The API does not require an API key.

**Note, please capitalise the `name` values in the JSON rather than fetching the localised version.**

## Summary of Changes

This update addresses several issues in the original codebase and implements best practices for a more organized, maintainable, and efficient app. The following improvements have been made:

Paging Library: The use of Android's Paging Library has been introduced to handle the loading and display of data in a more efficient manner. This eliminates the need for manual scroll listeners.

Repository Pattern: The repository pattern has been implemented with a PokemonRepository interface and a PokemonRepositoryImpl class. This abstraction improves modularity and testability while making it easier to switch between different data sources and would gracefully support a local data source.

ViewModels: ViewModels (PokemonListViewModel and PokemonDetailViewModel) have been introduced to separate UI-related logic from the rest of the app. This allows for better separation of concerns and improves the handling of activity lifecycles.

Improved Data Passing: Instead of passing the entire RemotePokemonItem object between activities, only the Pokémon ID is passed to the PokemonDetailActivity. This simplifies data handling and reduces the potential for inconsistencies.

Error Handling and loading states: Proper error handling has been added for failed API requests, providing feedback to the user in case of an issue.

Efficient List Updates: The use of DiffUtil and ListAdapter has been implemented in the PokemonAdapter and TypesAdapter classes to enable efficient list updates, reducing the need for full list refreshes.

Dependency Injection: The app now uses Hilt for dependency injection, ensuring that dependencies are managed efficiently and consistently across the application.

Coroutines: The code utilizes Kotlin Coroutines and Retrofit to fetch data from the API in a more efficient and organized manner, improving the overall performance and structure of the app.

Screen orientation: Layout changes better support landscape sceeen orientation

Code Clean-up: Unused annotations and redundant code have been removed for better readability and maintainability.

## Additonal improvements if I had more time

Test Coverage: Increase end-to-end and UI tests to ensure app reliability and stability. I could also test more varied scenarios and edge cases in the Unit tests.

Dark Theme Styling: Refine dark theme for a polished user interface.

Navigation: Introduce Navigation component or Jetpack Compose for streamlined navigation and simplified UI development.

Offline Support: Implement caching and local data storage (e.g., using Room) to allow users to access previously fetched data when offline.

Accessibility: Improve accessibility support by ensuring proper contrast, adding content descriptions, and supporting dynamic text sizes.

Error Handling: Introduce error handling for the paging implementation.

Dependencies: Update to the latest compatible versions
