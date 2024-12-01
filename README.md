# HomeTask #1

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

---

## Checklist:

- [x] Single Activity
- [x] Both Fragments Created
- [x] ViewBinding
- [x] Constraint Layout
- [x] Different ViewHolders for active/outdated tournaments
- [x] Actual add and remove actions with list, add diffUtils
- [ ] Survive configuration changes
- [ ] Apply correct icons
- [ ] Transform all strings to recourses
- [x] Toolbar Back button action
- [ ] Saving&Restoring to/from SharedPreferences
- [ ] Second language
- [ ] D/N themes
- [ ] General design

