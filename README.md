# PlayDeck

**🚀 Features**

- Fetches and displays movies from **TMDb API**
- Loads paginated users from **ReqRes API**
- Follows **MVVM architecture** with **Jetpack Compose**
- Dependency injection with **Hilt**
- Offline support using **Room**
- Retrofit-based API communication
- Material Design theme support
  

**🛠 Tech Stack**

| Layer         | Library / Tool              |
|---------------|-----------------------------|
| Language      | Kotlin                      |
| UI            | Jetpack Compose, Material 3 |
| Architecture  | MVI                        |
| DI            | Hilt                        |
| Networking    | Retrofit, OkHttp            |
| Database      | Room                        |
| Pagination    | Paging 3                    |
| Asynchronous  | Coroutines, Flow            |
| Image Loading | Coil                        |


**🔐 API Keys Setup (Important)**

To run the app locally, you must define the following API keys in your local.properties file. This file is git-ignored, ensuring that keys are kept secure.

- Add the following lines in local.properties:
**TMDB_API_KEY=your_tmdb_api_key_here**
**REQRES_API_KEY=reqres_api_key_here**


**▶️ How to Run**
Clone the repository:

- git clone https://github.com/abhishekgarala03/PlayDeck.git
- cd PlayDeck
- Add your keys in local.properties
- Sync the project in Android Studio
- Run the app on an emulator or physical device

**✅ Best Practices Followed**

- Single source of truth (ViewModel + Repository)
- UI state handling via StateFlow
- Lifecycle-safe coroutines
- Separation of concerns and SOLID principles
- Clean package structure
