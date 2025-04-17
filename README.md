# [HomeTask #12](https://github.com/tiver69/hello-android-again/tree/hometask_12)

Playground for practicing with random Compose Tasks.
App handles onboarding flow (OnboardingScreen → GreetingsScreen). GreetingsScreen displays a LazyColumn of Greeting items. Each item has header with name & expand/collapse button. With smooth expansion 3 additional checkboxes and text input appears.

## Checklist:

- [x] UI state stored in ViewModel via MutableStateFlow.
- [x] State persists across recompositions & scrolling in LazyColumn.
- [x] Each Greeting has independent state, mapped by name.