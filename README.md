FootballStats
=============

This application requires the user to enter the game ID of a football game on NFL.com. It takes the game ID and requests
the play-by-play information. The html is then parsed and separated into categories of 1st down runs/yards, 1st down passes/yards,
2nd down runs/yards, 2nd down passes/yards, 3rd down runs/yards, 3rd down passes/yards, 4th down runs/yards, and
4th down passes/yards. The plan is to add more functionality to the app when time allows.

This app uses an async task to get the html from nfl.com, and then a different async task to process the html. There is also
a timer that runs the tasks every 40 seconds for live updates during games.
