# 🍹 CocktailExplorer
Welcome to CocktailExplorer! A modern and elegant Android application built with the latest technologies from the Jetpack ecosystem

---

## 📜 Description
CocktailExplorer is a mobile app that allows users to explore a vast database of cocktails. The UI, built entirely with **Jetpack Compose**, offers a fluid and declarative user experience. The app follows **Clean Architecture** principles and leverages **Hilt** for dependency injection, ensuring a modular, scalable, and maintainable codebase.

The app's aesthetic is centered around a modern dark theme, which uses dynamic gradients to create a visually appealing and immersive experience.

## ✨ Features
This application is divided into two main sections, accessible via a bottom navigation bar: **Home** and **Search**.

### 🏠 Home Screen

<img width="263" height="588" alt="image" src="https://github.com/user-attachments/assets/f04e1f31-d548-4077-869f-07ce38830bba" />
<img width="265" height="584" alt="image" src="https://github.com/user-attachments/assets/7dabc604-5a43-4cb2-857e-d75c9fd6f42a" />

The Home screen is the heart of discovery within the app. It's designed to provide a rich, interactive, and visually engaging experience.

#### **Random Ingredients Carousel**
At the top of the Home screen, you'll find a carousel showcasing random cocktail ingredients.
*   **Dynamic Background Color**: As you swipe through the ingredients, the app's background dynamically changes to match the average color of the current ingredient's image. This creates a unique and immersive theme for each item.
*   **Auto-Scroll**: The carousel features a subtle auto-scroll, encouraging users to discover new ingredients without any interaction.
*   **Click to Discover**: Tapping on an ingredient will navigate you to a list of all cocktails that can be made with it.

  <img width="255" height="595" alt="image" src="https://github.com/user-attachments/assets/05fecc45-658e-4143-879e-4f0a167e2268" />

#### **Categorized Cocktail Lists**
Below the carousel, cocktails are neatly organized into categories (e.g., "Popular," "Latest," "Alcoholic").
*   **Seamless Navigation**: Clicking on any cocktail card will take you to its detailed view, where you can find ingredients, preparation instructions, and more.
  <img width="263" height="594" alt="image" src="https://github.com/user-attachments/assets/98306d62-deee-4290-baa9-8990f0f48324" />

### 🔍 Search Screen
<img width="261" height="594" alt="image" src="https://github.com/user-attachments/assets/33a7e29c-ce63-4e82-a89f-a62fadcba0af" />


The Search screen provides a powerful and responsive way to find specific drinks.
*   **Search by Name**: Simply start typing the name of a cocktail, and the results will appear in real-time.
*   **Debouncer Implementation**: To optimize performance and reduce network requests, a **debouncer** is implemented. The app waits for the user to stop typing for a brief moment before fetching the search results, ensuring a smooth and efficient experience.

## 🛠️ Tech Stack & Architecture
This project serves as a showcase of modern Android development best practices and tools.

### Architecture
*   **Clean Architecture**: The project is structured into three main layers (`domain`, `data`, `presentation/ui`) to ensure a decoupled, testable, and maintainable codebase.
*   **MVVM (Model-View-ViewModel)**: The presentation layer pattern used to separate UI logic from business logic.

### Technology Used
*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Google's modern, declarative UI toolkit.










    
