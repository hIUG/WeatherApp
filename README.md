# WeatherApp
Simple application to show weather information for your current location and other saved locations

This app demonstrates the usage of multiple technologies, design patterns, components and libraries together to perform as a Weather/Forecast app that displays data for your current location and for specific saved locations through ZIP code search.

The following is a description of the main implementation components.

- KOTLIN
  * Usage of Kotlin data classes to define all Parcelable POJOs in the app (a single line POJO with @Parcelize new annotation) as well as other cool Kotlin features.

- ARCHITECTURE
  * Clean MVP architecture with a Base Fragment, Activity, Presenter and Contract to make easier to add/remove modules. Implementing Java generic types to work with the Base classes and perform casting-safe operations.
  * Singleton instances of Manager classes and abstract classes for base components.

- NETWORK
  * Multithreading Caching Network architecture through the usage of IntentService and a thread-safe requests queue (LinkedBlockingQueue).
  * Caching requests with the implementation of a CacheManager based on the system SharedPreferences and defining an expiry time that can be easily modified.
  * Usage of Retrofit2 and OkHttp3 to handle the network calls, requests and responses (with a custom HttpLoggingInterceptor to log all requests to the logcat).
  * Usage of the @Headers Retrofit annotation to individually identify each request and it requisition to either cache the request or not.
  * Usage of Gson to serialize/deserialize objects into JSON strings.

- OTHER THIRD-PARTY LIBRARIES
  * Multithreading event communication through EventBus.
  * Material-Dialogs (from com.afollestad.material-dialogs).
  * Charts (from com.github.PhilJay:MPAndroidChart)

- ANDROID AND JAVA COMPONENTS
  * Defined animations on the SplashScreen and on the HomeScreen when the user scrolls down on the RecyclerView of saved locations (when the RV has enough elements to scroll).
  * Usage of Android's DateUtils.getRelativeTimeSpanString() method to display to the user a human-friendly relative time window.
  * Convenient usage of Java's static code initializer.
  * Defined an Android Settings screen using a PreferenceFragment to take advantage of the PreferenceScreen update the preferences on real-time to follow the Android Design guidelines.
  * RuntimePermissions requests and opening the application settings screen for the package getted from the context.
  * A simple BroadcastReceiver to demonstrate the usage of such Android component.
  * Defined Android compileSdkVersion 26, that means, no need of casting for findViewById() anymore.
