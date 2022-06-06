# synctech-test
SyncTech test

Here i have used the MVVMP architecture, with some third party lib use as below

1.Retrofit - to consume the API
2.Jetpack - Viewmodel, Livedata, workermanager
3.Koin - Dependecy injection 
4.Rxjava - Asyncronous call 
5.Databing - Used to update the UI

What is MVVMP?
MVVMP is nothing but model view viewmodel presenter, an hybride version of MVVM architecure.

Why MVVMP? 
Here we use presenter work as a helper for viewmodel to segregate the some of the bussiniess logic from Viewmodel, so that Viewmodel looks clean.

App Description
===============
The main purose of the is to fetch the weather forcase from the "OpenWeatherMap" API, and shown the result in the screen and alogn with the periodically fetching the weather report on every 2 hours. Onec we ge the weather repot we persist in the local preference for future use. And all this API call should happend only if the device connected with the WIFI network.

link : https://lucid.app/lucidchart/1edad07b-8b31-433b-be4d-7ba595b191db/edit?invitationId=inv_28ae3a03-202f-47d2-875b-98f25da7a316&page=0_0#

Permission Used
==============
    <uses-permission android:name="android.permission.INTERNET"/> -  for network connectivity
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> - to check the network type
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> 
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> - corese and fine used for location permission.

