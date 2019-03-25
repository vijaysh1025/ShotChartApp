# NBA Shot Mapping Android App

## Overview
Pick an NBA game from any date and display the shot chart data of any given player from that game. Mobile app technologies have seen a new shift in how developers think and how they develop. This application utilizes the latest mobile technologies to provide the best user experience.

* Architecture - MVVM
* Key Technologies - Retrofit, RxJava, JUnit, Mockito

## Application Breakdown
### Game Selection Screen
User will use the Date Picker to select a date in history. The Date Picker is bound to `BehaviorSubject<LocalDate>` (Observable in the `DailyScheduleViewModel`) which will emit every time the date changes. Every time the date changes, a request is made to the NBA SportRadar API through Retrofit ( `@GET("/nba/trial/v5/en/games/{year}/{month}/{day}/schedule.json")`) using the given date to pull the schedule of games from that day. Retrofit GSONConverter is used to map the incoming data into an equalent DailySchedule object and a `Single<DailySchedule>` is returned by the retrofit call. This observable is sent to the Data Model to further refine the data which is eventually fed to ViewModel to pull out the relevant info required to display a clickable list of games from the given date.

*Note* - The `debounce` operator is very useful in making sure that when the user is selecting a date, multiple emissions of quick date changes don't overload the app with multiple API requests. 

### Player Shot Map Screen

Coming soon...


### App In Progress

<img src="/video/github_video.gif" width="300">
