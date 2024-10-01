# NewsApp-Using-MVVM-Architecture
This is a News application with MVVM Architecture and Jetpack Components with unit and instrumentation testing.

### Major Highlights
- MVVM Architecture
- Kotlin
- Hilt dependancy injection
- Room Database
- Retrofit
- Coroutines
- Flow
- StateFlow
- ViewBinding
- Unit Test

<p align="center">
<img alt="mvvm-architecture"  src="https://github.com/user-attachments/assets/196d9a7b-f4f0-4e98-a132-851618c660d1">
</p>

## Feature implemented:
- Top News
- Instant search using Flow operators
- Offline news
- Unit Test
- TopHeadlines of the news
- Multiple sources wise news

## Dependency Used:
- Recycler View for listing
```
implementation "androidx.recyclerview:recyclerview:1.2.1"
implementation 'androidx.recyclerview:recyclerview-selection:1.1.0' //multi item selection
```
- Glide for image loading
```
implementation 'com.github.bumptech.glide:glide:4.11.0'
```
- Retrofit for networking
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.8.0'
```
- Android Lifecycle aware component
```
implementation 'android.arch.lifecycle:extensions:1.1.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
```
- Hilt for dependency Injection
```
implementation "com.google.dagger:hilt-android:2.51.1"
kapt "com.google.dagger:hilt-compiler:2.51.1"
```
- For WebView browser
```
implementation 'androidx.browser:browser:1.4.0'
```
- Room Database
```
implementation "androidx.room:room-runtime:2.4.2"
kapt "androidx.room:room-compiler:2.4.2"
// optional - Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:2.4.2"
```
- Local Unit test
```
testImplementation 'junit:junit:4.13.2'
testImplementation "org.mockito:mockito-core:4.9.0"
kaptTest "com.google.dagger:dagger-compiler:2.42"
testImplementation "android.arch.core:core-testing:1.1.1"
testImplementation "org.hamcrest:hamcrest-library:2.1"
testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1'
testImplementation 'app.cash.turbine:turbine:0.12.1'
testImplementation 'androidx.test.espresso:espresso-core:3.5.0'

```
## The Complete Project Folder Structure
```
├── NewsApplication.kt
├── data
│   ├── api
│   ├── local
│   ├── model
│   └── repository
├── di
│   ├── component
│   ├── module
│   ├── qualifiers.kt
│   └── scopes.kt
├── ui
│   ├── MainActivity.kt
│   ├── base
│   ├── newsListScreen
│   ├── search
│   ├── sources
│   └── topheadline
└── utils
    ├── AppConstant.kt
    ├── DispatcherProvider.kt
    ├── Extentions.kt
    ├── NetworkHelper.kt
    ├── Resource.kt
    ├── Status.kt
    ├── TypeAliases.kt
    └── network

```

<p align="center">
<img alt="main_screen" src="https://github.com/user-attachments/assets/8e945a13-f27a-4953-856b-bac4213b3b3e" width="360"  height="640"> &nbsp;&nbsp;&nbsp;&nbsp;
    <img alt="top_headline" src="https://github.com/user-attachments/assets/79eb1518-4375-4c68-8d9e-d744cc6e2d11" width="360"  height="640">
</p>

<p align="center">
<img alt="news_source" src="https://github.com/user-attachments/assets/83d7d725-37a6-4c5a-8576-00a912c065ab" width="360"  height="640"  marginLeft="20">
    <img alt="search_screen" src="https://github.com/user-attachments/assets/e82181e9-962c-4887-a039-d8c0919ac636" width="360"  height="640" marginLeft="20">
</p>

<P align="center">
<img alt="news_heading_list" src="https://github.com/user-attachments/assets/1bd66aa2-c7ee-42d0-9fc1-29c8f72985eb" width="360"  height="640"  marginLeft="20">
<img alt="news_details" src="https://github.com/user-attachments/assets/6301c279-8b12-4641-9013-0f700368e8d9" width="360"  height="640"  marginLeft="20">
</p>


