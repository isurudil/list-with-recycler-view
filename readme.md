# Readme of the menu-designer-android

## Build Requirements

* Java 1.7
* Gradle 2.2.1

## How the build the project

* Build the project with the command 'gradle build' from the project root directory

* Currently the apk is not signed, therefore deploy the app-debug.apk from
{menu-designer-app-base-dir}/app/build/outputs/apk

##ow to use the the app

* Create a menu with the menu designer
* Get the url to retrieve the json response of the created menu
* Change the property service.url in file {menu-designer-app}/app/src/main/res/raw/app.properties
with the url of the menu designer response


