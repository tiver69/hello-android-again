# [HomeTask #8](https://github.com/tiver69/hello-android-again/tree/hometask_8)
Update App with some CI/CD features

## Checklist:

- [ ] Git Hooks
- [x] Flavours
- [ ] Sign with release keys
- [ ] Enable Proguard with obfuscation
- [x] Integrate with Firebase Analytics, Crashlytics
- [ ] Git CD with build and Firebase Distribution

---

 # [HomeTask #6](https://github.com/tiver69/hello-android-again/tree/hometask_6_coroutines)
Update App from [HomeTask #5](https://github.com/tiver69/hello-android-again/tree/hometask_5?tab=readme-ov-file#hometask-5) to use Coroutines instead of RxJava

---
 
# [HomeTask #5](https://github.com/tiver69/hello-android-again/tree/hometask_5)
Update App from [HomeTask #4](https://github.com/tiver69/hello-android-again/tree/hometask_4_dagger?tab=readme-ov-file#hometask-4) to Hilt DI and MVVM Architecture

## Checklist:

- [x] Hilt DI
- [x] MVVM
---

# [HomeTask #4](https://github.com/tiver69/hello-android-again/tree/hometask_4_dagger)
Extend App from [HomeTask #3.2](https://github.com/tiver69/hello-android-again/tree/hometask_3_clean?tab=readme-ov-file#hometask_3_clean) with DI using Dagger2 

---

# [HomeTask #3.2 Clean Architecture](https://github.com/tiver69/hello-android-again/tree/hometask_3_clean)
Change App from [HomeTask #2](https://github.com/tiver69/hello-android-again/tree/hometask_2?tab=readme-ov-file#hometask-2) to follow Clean Architecture:
* __Presentation Layer:__ tournament(fragment+presenter+contract), navigation and ui-related components 

  Fragment -> Presenter

* __Domain Layer:__ usecases and repository interfaces

  Use Case Implementation -> Repository Interface

* __Data Layer:__ repository's implementation model and mappers 
 
  Repository Implementation -> Local/Remote Data Sources (via entities)

---

# [HomeTask #3.1 MVP](https://github.com/tiver69/hello-android-again/tree/hometask_3_mvp)
Change App from [HomeTask #2](https://github.com/tiver69/hello-android-again/tree/hometask_2?tab=readme-ov-file#hometask-2) to follow MVP architecture

---

# [HomeTask #2](https://github.com/tiver69/hello-android-again/tree/hometask_2)

Extend App from [HomeTask #1](https://github.com/tiver69/hello-android-again/tree/hometask_2?tab=readme-ov-file#hometask-1) with following functionality:
* __Tournaments List__. Each tournament item should be extended by displaying small image (logo of tournament). Image Id should be saved alongside with tournament data and image itself should be cached locally and restored on app launch.  
* __Adding New Tournament__. Medium tournament logo should be added to screen with button "Load another image" that will reload next image. Chosen image should remain associated with created tournament further. 
* __Common__. Clicking to image on any screen should open it in Chrome Tab.

__To be used:__
* Unsplash API Endpoints & RxJava & Retrofit: search / get by id
* SQLite DB for image caching
* Observable for tournaments in service
* Chrome tab
* Glide/Picaso

## Checklist:

- [x] TournamentService.tournaments to Observable
- [x] Load random image from */search/photos?query=tennis* for initial Logo in Add Tournament screen
- [x] Save image information for each tournament to be able to show small image in list and support opening in ChromeTab
- [x] Load small image for each tournament in list
- [x] Cache and restore images
- [x] Open Chrome Tab on any image click

---

# [HomeTask #1](https://github.com/tiver69/hello-android-again/tree/hometask_1)

Create an App that consists of two fragments:
* __Tournaments List__. Each tournament should display: the tournament name, amount of participants, date, and button _Delete_. All outdated tournaments should have a grey border and a light-grey text color. Upcoming tournaments - black border and text color. _Delete_ button removes the item from the list.  FAB for adding a tournament takes the size of the tournaments list and send it to the second fragment.
* __Adding New Tournament__. To add a tournament, a user should enter the tournament name, amount of participants, and date. If there were 5 tournaments in the list, then the new tournament name should have initial value "Tournament 6" that user can change.

__To be used:__
* DiffUtils for updating the list
* Different ViewHolder Types for outdated and upcoming tournaments
* Single activity
* View bindings for getting access to views
* Constraint Layout for the list item

__Bonus:__
* Add a Toolbar that will support button Back from the second fragment to the first one.
* Also, it should have a button "Close the app" that will save the tournaments list content to SharedPreferences before closing the App.
* Restore the list on the App launch.
* Support 2 languages
* Support day/night theme

## Checklist:

- [x] Single Activity
- [x] Both Fragments Created
- [x] ViewBinding
- [x] Constraint Layout
- [x] Different ViewHolders for active/outdated tournaments
- [x] Actual add and remove actions with list, add diffUtils
- [x] Survive configuration changes
- [x] Apply correct icons
- [x] Transform all strings to recourses
- [x] Toolbar Back button action
- [x] Saving&Restoring to/from SharedPreferences
- [x] Second language
- [x] D/N themes
- [x] General design
- [x] Toolbar improvements
