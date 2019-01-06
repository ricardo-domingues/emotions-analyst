# Emotions Analyser

A context-aware application that gives movies suggestions based on mood displayed on social networks like Facebook and Twitter through the analysis of photos and posts of a given user.
 
## Getting Started

These instructions will get you a demo of the project up and running on your local machine for development and testing purposes.

### Prerequisites

In order to run a local demo of the project first you need to install an android development IDE such as Android Studio or Netbeans.

* [Android Studio Download](http://www.dropwizard.io/1.0.2/docs/)
* [NetBeans Download](https://netbeans.org/downloads/)

Next you also need to install Java on your machine to use Android Studio or NetBeans. Specifically, you are going to need to download and install the [Java Development Kit (JDK)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
### Installing

#### 1 - Clone Project 
First clone the project to your local machine

```
git clone https://bitbucket.org/martacarvalho/project-facebook-emotions
```
#### 2 - Register a Facebook Application
Obtain Facebook credentials registering a facebook app that allows user´s to login with Facebook and retrieve their own post´s.

To register an app go to [Facebook Developers page](https://developers.facebook.com/apps/) copy your facebook app credentials and paste them on the folder that contains all strings in android application.

Open /app/src/main/res/values/strings.xml file and paste your facebook app ID in those two strings:
```
(...)
<string name="facebook_app_id">{APP_ID}</string>
<string name="fb_login_protocol_scheme">fb{APP_ID}</string>
```
#### 3 - Register a Twitter Application
In order to allow users to authenticate through Twitter its mandatory to register a [Twitter App](https://developer.twitter.com/).

Then access your app settings to retrieve both consumer key and consumer secret. Those keys will be used to allow your application to login and access user personal information.

Open /app/src/main/res/values/strings.xml file and fill your keys in those two strings:
```
(...)
<string name="com.twitter.sdk.android.CONSUMER_KEY">{CONSUMER_KEY}</string>
<string name="com.twitter.sdk.android.CONSUMER_SECRET">{CONSUMER_SECRET}</string>
```
#### 4 - Obtain a Google Maps API Key
You can obtain a Google Maps API Key [here](https://developers.google.com/maps/documentation/android-sdk/signup).
You will be asked to:
* Pick one or more products
* Select or create a project
* Set up a billing account

Open /app/src/main/res/values/strings.xml file and fill your Google API Keyx:
```
(...)
<string name="google_places_api_key">{GOOGLE_API_KEY}</string>
```
#### 5 - Obtain Microsoft Face API Key
First you need to create an account on [Microsoft Portal](https://portal.azure.com).
In your portal click on create a resource on the left sidebar panel and then select AI + Machine Learning and finally choose the Face API and create the resource.
Then access the resource settings and grab both microsoft face url and microsoft face api key.

Open /app/src/main/res/values/strings.xml file and fill both Keys:
```
(...)
 <string name="microsoft_face_url">{MICROSOFT_FACE_URL}</string>
 <string name="microsoft_face_api_key">{MICROSOFT_FACE_API_KEY}</string>
```
#### 6 - Obtain The MovieDB API Key
Finally register an account on [The MovieDB Developers](https://developers.themoviedb.org) and copy your API key.

Open /app/src/main/res/values/strings.xml file and paste the key:
```
(...)
 <string name="themoviedb_api_key">{THEMOVIEDB_API_KEY}</string>
```

Now you can run the project and test it. 


## Authors

* [**Marta Carvalho**](https://github.com/martaacarvalho)
* [**Ricardo Domingues**](https://github.com/ricardo-domingues)
* [**Rodrigo Alves**](https://github.com/RodrigoSAlves)

## License

This project is licensed under the MIT License

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.2532757.svg)](https://doi.org/10.5281/zenodo.2532757)

