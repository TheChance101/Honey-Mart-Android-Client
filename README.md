# Honey Mart Client App

> Honey Mart is a versatile platform built with Kotlin and Jetpack Compose, where various markets can easily display and sell their products. It provides a user-friendly and engaging online shopping experience.

## Table of Contents
- [Project Components](#project-components)
- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#rocket-tech-stack)
- [Architecture](#architecture)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Contributors](#contributors)
- [License](#license)
## Project Components
- **User App**: The front-facing application designed for customers and shoppers. It offers a user-friendly interface to explore products, add them to the cart, place orders, manage wishlists, and enjoy a seamless shopping experience. Users can create accounts, log in, and access their profiles. They also receive real-time notifications about their orders, making it convenient and engaging for customers.
- **Owner App**: The Owner App is tailored for market owners and administrators. It empowers them to manage their respective markets efficiently. Owners can add and update product listings, categorize products, and oversee orders placed within their markets. They also have control over coupon management, and other market-specific tasks. The Owner App streamlines market management and ensures a smooth operation for market owners.
- **Admin App**: The Admin App is responsible for market approval management. Administrators use this app to review and approve exciting new markets joining the platform and, if necessary, remove markets that no longer meet the platform's standards. This app streamlines market approval processes and ensures a curated marketplace for users.
## Features

### User App:
- **User Registration and Authentication**: Users can create accounts, log in, and access their profiles.
- **Browse Products**: Browse a wide range of products available at Honey Mart.
- **Product Details**: View detailed information about each product.
- **Add to Cart**: Easily add products to your cart for later purchase.
- **Order Management**: Place and manage orders with ease.
- **Wishlist**: Add products to your Wishlist for future reference.
- **Real-Time Notifications**: Receive notifications about order status updates.
- **Coupon Redemption**: Clip coupons to avail discounts.

### Owner App:
- **Market Management**: Owners can create and manage their markets with ease.
- **Product and Category Management**: Efficiently manage product listings and categories within their markets.
- **Order Tracking**: Keep track of orders placed within their markets and update order statuses.
- **Coupon Management**: Owners can create, distribute, and manage coupons for their markets.
- **Real-Time Notifications**: Receive alerts about market-related activities and orders.

### Admin App:
- **Market Approvals**: Admins can review and approve new markets wishing to join the platform.
- **Market Removal**: Admins can remove markets that do not meet platform standards or are no longer active.
## Screenshots
## :rocket: Tech stack
- [Recommended Architecture](https://developer.android.com/topic/architecture)
- [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiAzKqdBhAnEiwAePEjktk3ROIIxTqejhHWkDEwSaQqoE6GgrNHM8iYKw8xHx5SPPDu0oJ_DxoC8LYQAvD_BwE&gclsrc=aw.ds)
- [Hilt dependency injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Data Store](https://developer.android.com/jetpack/androidx/releases/datastore)
- [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html)
- [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [App Modularization](https://developer.android.com/topic/modularization)
## Architecture
This project uses **MVVM (Model View View-Model)**  with the recommended architecture and contains multi modules as shown.

![image](https://github.com/TheChance101/Honey-Mart-Android-Client/assets/93276124/40b2f7da-408d-49ac-a112-6ebe48c33848)
## Requirements

Before you begin, ensure you have met the following requirements:

- **Android Studio with Jetpack Compose support**:
    - [Download Android Studio](https://developer.android.com/studio)

- **Firebase Project**:
    - [Create Firebase Project](https://console.firebase.google.com/u/0/)

- **Connection to honey-mart server**:
    - [Honey-Mart-Server](https://github.com/TheChance101/Honey-Mart-Server)

## Installation

1. Clone the repository:
   ```shell
   git clone https://github.com/yourusername/honey-mart-client.git
   cd honey-mart-client
2. Add Firebase Json file for User, Owner and admin apps.
3. Add Api-Key named as "apiKey" to the local.properties file.

## Usage
-   To test the APPs, there is an APKs build  [here](https://github.com/TheChance101/Honey-Mart-Android-Client/tree/main/user/src/main/res/raw)  that you can directly download and install.


## Contributors
<a href="https://github.com/TheChance101/Honey-Mart-Server/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=TheChance101/Honey-Mart-Android-Client" />
</a>

## License
	Copyright (c) 2023 The Chance
- This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/TheChance101/Honey-Mart-Server/blob/develop/LICENSE) file for details.