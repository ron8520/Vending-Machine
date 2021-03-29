# Vending Machine


# Introduction
Lite Snacks manage vending machines which sell different types of snacks. The snacks are maintained under four different categories: drinks, chocolates, chips, and lollies. Each category will have different items/products. The vending machine can maintain up to 15 items of each product. 

## Requiements
To use jenkins correctly, make sure your compiler java version and runtime java version are the same. In jenkins, the java version is java 11

# Information
The application involves JavaFX to develop the frontend and use PGAdmin as a backend database. Using Scene Builder to assist drawing the UI element and achieve User Interface. It consists of the third party module JFoenix to implement some specifical effect. For example, bubble effect for AnchorPane, drawer box to turn it or off and set up hamburger icon events. 

The jar file can export successfully and located under the build/libs folder, howver, it doesn't work. It has to run by type gradle build and run in the terminal. 

#  Screenshot of the Applications
![[IMG_0500.png]]
![[IMG_0495.png]]
![[IMG_0499.png]]
![[IMG_0498.png]]
![[IMG_0496.png]]
![[IMG_0503.png]]
![[IMG_0502.png]]
![[IMG_0501.png]]
![[IMG_0504.png]]
![[IMG_0497.png]]

## DataBase
Our database can be viewed and modified through the pgAdmin. You can access our database server by the ip address: 54.249.17.222 and the port number: 5432. The username should be “assignment” and the password should be “john”. Then you should get access to our postgre database server successfully.
Totally, there are 7 tables in our schema, which are representing cards, changes, items, sold items of transactions, transactions, cards used by users and users respectively. You can use SQL queries to view or modify data.

Our group has set up a Jenkins server for our application, which is hooked with our Github repository. The username of our Jenkins server account is “soft2412” and password is “tyson_thomas”. Then you should be able to access our project.

# References
    http://www.jfoenix.com/
    https://stackoverflow.com/questions/41893327/how-to-trigger-hamburger-in-javafx
    http://www.jfoenix.com/documentation.html#InputFields
