# javaLab3 （Design Pattern）
UwU
1.Name the two patterns you wish to implement and explain what advantages you hope to achieve with them.

For this project to Lab 4, I chose to implement the following two design patterns:
Decorator Pattern and Observer Pattern

I chose the Decorator Pattern because I wanted a clean and flexible way to extend the appearance and behavior of my UI components—especially the statistics panel—without modifying the original code. Using decorators lets me dynamically add visual enhancements like borders, colors, or other UI behaviors while keeping the base logic clean and reusable. 0v0
The Observer Pattern made sense for improving the interactivity of the app. I wanted components like the chart panel and statistics panel to update automatically when the table’s filter is changed. Instead of tightly coupling components together, I used this pattern to broadcast changes from the filter panel to all subscribed panels in a modular and maintainable way. 

2.Describe how you implement the patterns, including additional interfaces, classes, and how these will integrate with the classes that you already have.

To implement the Decorator Pattern, I created an interface called StatsDisplay, which returns a JPanel. The base class BasicStatsPanel implements this interface and shows raw statistics. Then, I created decorators like BorderedStatsPanel and DarkModeStatsPanel that wrap around StatsDisplay and enhance its visual styling.

Here is the Classes that I added:

StatsDisplay – the core interface

BasicStatsPanel – base implementation

StatsDecorator – abstract decorator

BorderedStatsPanel – adds a white border

DarkModeStatsPanel – adds dark styling

In the Observer Pattern
I wanted the statistics and chart panels to automatically update when users apply a filter. For this, I created a subject class called DataFilterSubject and an interface DataObserver. The chart and stats panel both implement DataObserver, and they get notified when filtered data changes.

Here is the Classes I added:

DataObserver – interface for components that need to react to data changes

DataFilterSubject – manages a list of observers and notifies them

StatsObserver and ChartObserver – listen for filter changes and update accordingly
