#pd-challenge

This app was made for a Challenge.

###The goal is 

Design a weather application. The application should allow the user to download and display today's weather in a specified city. You may use any suitable 3rd party RESTful API to supply the weather data.

###Requirements

Initially the app should show a list of 4 cities - Dublin, London, Beijing and Sydney. The user should be able to add/remove cities from this list. The app should persist these changes, so that when restarted it remembers the users chosen cities.

###What I did

To add a city to see the weather information, tap on “add city” text field and write what’s you looking for. In case of success, it will be added to your cities list, if not, 
you will see a “toast” with a message. I'm also verifying if the typed city already exists, if true a “toast” is shown.

For the UI, I used the assets and followed the specifications as asked. 

For perform the request, I used Refrofit lib because it’s very simple and reliable. 

For persists the data I’ve used SharedPreferences instead of the any DB solution, I just need to save a few data, so I made this choice. 

I used EventBus lib, with it the code is more simple, clean and reliable.

I used ButterKnife lib, it’s very cool and useful, make simple inflate layouts and the use of the listeners.

I used Glide lib, for download the images, it’s very simple and reliable, I always use 
on my projects. 

####About the APIs for get data

For get the weather information I chose Open Weather Map - 
http://openweathermap.org/api, just because it’s so simple and does what I need it 
does.

For get images, I chose the Flickr - https://www.flickr.com/services/api , I just did a 
quickly search about it, and it’s looks like a good option to use.

For get the Time Zone, I chose a Google Time Zone -
https://developers.google.com/maps/documentation/timezone/intro , It’s seems a 
good fits for what I needed so I used it.   


###3rd Party libraries

* ButterKnife – Field and method binding for Android views.

* Retrofit - A type-safe HTTP client for Android and Java.

* EventBus is publish/subscribe event bus optimized for Android.

* Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
