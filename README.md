NdejjeNest a student accommodation finder
NdejjeNest is an android mobile application developed to address the challenge of finding suitable student accommodation within the Ndejje University community. The app helps students easily discover, compare, and locate hostels and rental spaces around Ndejje Main Campus, Bombo, Luwero, and Kampala Campus. The application is built using Kotlin and Jetpack Compose, following modern Android development practices and the MVVM (Model-View-ViewModel) architecture.

Problem Statement
Students at Ndejje university often struggle to find affordable and reliable, secure hostels
and rental rooms around main and kampala campus, surrounding areas such as Bombo,
Luwero, Mengo etc.

NdejjeNest provides a centralized digital solution where:
A mobile android application that centralizes hostel and rental information helping students
to easily discover, locate, compare the available hostels or rentals.

Objectives of the Project 
General Objective
To develop a mobile android application that enables students to easily find, view and locate
available hostels around Ndejje University

Specific Objectives
1. To design and develop a user friendly mobile application using Kotlin
2. To allow students, search, filter and view hostel details.
3. To connect landlords with students searching for good accommodation.
4. To assist students make informed decisions of where to stay through ratings.

System Architecture
The application follows the MVVM (Model-View-ViewModel) architecture:
Model: Handles data structures and data sources
View: UI built using Jetpack Compose
ViewModel: Manages UI state and business logic

Project Structure
We made sure we maintain a clean project structure as shown in the screenshot below 

Benefits of a maintaining a good Project Structure 
A well-organized project structure like the MVVM setup with model, view, and viewmodel is not just about neatness. It directly improves how the app is built, maintained, and scaled.

Separation of Concerns. A good structure ensures that each part of the app has a specific responsibility ie Model → Data, View → UI, ViewModel → Logic. This prevents mixing UI and business logic, making the code cleaner and more professional.
Easier Maintenance, when your project is well structured, bugs are easier to find, updates are easier to make, code is easier to read. For example, if there’s a login issue, you know to check the ViewModel, not the UI.
Better Team Collaboration, for a group project like this, each member can work on different parts, fewer conflicts in code and clear responsibilities e.g. UI/UX  works in view, Developer works in viewmodel.
Improved Testability, a structured project makes it easier to write tests. ViewModels can be tested independently, logic is separated from UI. This is important for Testing & QA. 
Scalability, as the app grows new features can be added easily, code doesn’t become messy. Eg adding  a inchat screen with the landlord wont damage the existing code.
Reusability of Code, well-structured code allowed us to reuse components, avoid duplication. Eg utility functions in util/ can be used across the app.
Professional Coding Standards, using a structured approach like MVVM aligns with industry best practices, makes the project look professional, helps during code reviews
Better Handling of Data and State with ViewModels, UI state is managed properly, data survives configuration changes (like screen rotation)

Key Features
User Authentication (Login/Register)
Browse available hostels
View hostel details (price, location, description)
User-friendly onboarding experience
Navigation between multiple screens

Navigation
The app includes the following screens connected using Jetpack Compose as seen below:

Splash Screen, this screen is the entry point of the application. It displays the app logo and branding, it creates a good first impression and allows time for initial setup tasks (e.g., loading resources, checking user session). It improves perceived performance and provides a professional introduction to the app.

Onboarding Screen. The Onboarding Screen introduces new users to the application. It highlights key features and benefits, it guides users on how to use the app, and includes a scrollable animation to help first time users understand the app quickly, reducing confusion and improving user retention.

Login Screen. The Login Screen handles user authentication, allows users to sign in securely, ensures personalized access to features like saved hostels and profile data. It protects user data and enables customized user experiences.

Home Screen (Hostel List). The Home Screen is the main dashboard of the application, it displays a dynamic list of available hostels (using LazyColumn), and provides quick access to search and explore options. This is the core functional screen where users interact with the main service of the app.

Hostel Details Screen. The Hostel Details Screen provides detailed information about a selected hostel, it shows hostel descriptions, pricing, location, and amenities, allowing users to save or interact with the listing. It enables informed decision-making by providing all necessary details in one place.

View map button (Map Screen)
NdejjeNest includes a “View on Map” button that allows users to see the exact location of a selected hostel using map services. When a user selects a hostel and taps the “View on Map” button, the application retrieves the hostel’s location details (latitude and longitude or address). An intent is triggered to open a map application on the device such as Google Maps. The selected hostel location is displayed on the map, allowing the user to view the exact position. Get directions, this estimates distance from their current location
This feature helps students to easily locate hostels without confusion, plan routes from campus to the hostel, and compare distances between different accommodation options.

Profile Screen. The Profile Screen manages user information, displays user details (name, email, etc.), allows updates to personal information and includes a logout button. It gives users control over their account and personal data.

Saved Hostels. The Saved Hostels Screen allows users to view bookmarked hostels. It stores user-selected hostels for future reference, improves convenience when comparing options. It enhances user experience by allowing easy access to preferred listings.

The search bar
The application includes a search bar on the Home Screen to improve usability and help users quickly find hostels that match their preferences. Instead of scrolling through long lists, users can simply type keywords such as hostel names, locations, or other relevant details to narrow down results. This feature is essential for enhancing efficiency, especially as the number of available hostels increases, ensuring that users can access the information they need with minimal effort as shown below
The search bar provides real-time filtering of hostel listings. As users type, the displayed results update dynamically to show only matching entries, creating a fast and responsive experience. This is achieved by linking the search input to state management mechanisms such as ViewModel and remember, which automatically trigger UI updates. The search integrates smoothly with components like LazyColumn, ensuring that only relevant data is rendered without affecting performance.
The search bar significantly improves interaction with the application by making navigation quicker, more intuitive, and more engaging. It reduces frustration caused by excessive scrolling and allows users to focus on options that meet their needs. Additionally, the design is kept simple and prominently positioned for easy access, with potential future enhancements including advanced filtering options, search suggestions, and backend-powered search capabilities to further optimize performance and accuracy.

Notification 
NdejjeNest includes a notification button that keeps users informed whenever a new hostel is added to the system. This feature ensures that users do not miss out on newly available accommodation options, which is especially important in a dynamic environment where listings can change frequently. By providing timely updates, the app enhances user awareness and encourages continuous engagement.
The notification system alerts users through the notification screen whenever new hostel data is introduced. These notifications are triggered based on updates to the hostel database and they are displayed in a clear and organized manner for easy viewing. The notification button serves as a central access point where users can check all updates at their convenience, ensuring they stay informed without needing to manually refresh or search for new listings.
This feature adds significant value by making the application more proactive rather than reactive. Users can rely on the system to bring relevant updates directly to them, saving time and effort. It also increases user retention, as users are more likely to return to the app when they receive meaningful updates. In future improvements, the notification system could be enhanced with customization options, allowing users to control the type and frequency of notifications they receive.

Testing and Quality Assurance
Unit and integration tests were implemented to ensure application reliability. We conducted the following tests:
Login Validation Test: Ensures incorrect credentials return user-friendly error messages.
We also tested the network error message, let's say a user didn't have internet connection and they tried logging in, the display error message was very technical and not user friendly as shown below 
All tests passed successfully, confirming that core functionalities work as expected.

Data Handling and Privacy
The application handles user data such as:
Names
Email addresses
Phone numbers
Hostel information.
This data is necessary to support core application features such as authentication and hostel listings.


Measures Taken:
Only necessary data is collected, only essential information is collected. The application avoids requesting unnecessary personal details to reduce privacy risks.
No sensitive data is exposed unnecessarily, for example the person's age is not needed when a new user is registering 
User-friendly error messages to avoid system leakage.

Technologies Used
Language: Kotlin
UI Framework: Jetpack Compose
Architecture: MVVM (Model-View-ViewModel)
Navigation: Jetpack Navigation Component
Database: Room Database (or Retrofit API)
Testing: JUnit, AndroidX Test

Git Workflow
The project follows a collaborative Git workflow:
It's a group project, all group members are contributors with individual commits. There are regular commits across development stages, different were created so as each member change make changes in the work and progress could be tracked 

Design Principles
Material 3 design system
Clean and simple UI
Accessibility considerations (clear text, readable layout)
Responsive and user-friendly interface

Future Improvements
We look forward to incorporate in app chats with the landlord, this will enable the students and communicate directly with the landlord in the app
Ratings and reviews for hostels
Chat system between students and landlords

Repository Information
Public GitHub Repository
Contains full source code, documentation, and commit history
Demonstrates collaborative development

How to Run the Project
1. Clone the repository, start by downloading the project source code from GitHub to your local machine. This command creates a local copy of the project, make sure you have Git installed before running this command, alternatively, you can download the project as a ZIP file and extract it.
2. Open in Android Studio, launch Android Studio on your computer.
Click on “Open” or “Open an Existing Project” navigate to the folder where you cloned or extracted the project, select the project folder and open it, Android Studio will automatically begin indexing the project.
3. Sync Gradle, once the project opens, Android Studio will prompt you to Sync Gradle. If not, click “Sync Now” at the top of the screen. Make sure you have an active internet connection for this step.
4. Run the application on an emulator or physical device

Conclusion
NdejjeNest provides a practical digital solution to the accommodation challenges faced by students. The project demonstrates the application of modern mobile development practices, teamwork, and ethical considerations in software development.

Acknowledgements
We acknowledge the guidance provided by our lecturer Mr. Luyima Alex Cedric and the collaboration of all team members in successfully developing this project.
