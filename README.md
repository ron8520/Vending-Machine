# Vending Machine


# Introduction
Lite Snacks manage vending machines which sell different types of snacks. The snacks are maintained under four different categories: drinks, chocolates, chips, and lollies. Each category will have different items/products. The vending machine can maintain up to 15 items of each product. 

## Requiements
To use jenkins correctly, make sure your compiler java version and runtime java version are the same. In jenkins, the java version is java 11

# Information
The application involves JavaFX to develop the frontend and use PGAdmin as a backend database. Using Scene Builder to assist drawing the UI element and achieve User Interface. It consists of the third party module JFoenix to implement some specifical effect. For example, bubble effect for AnchorPane, drawer box to turn it or off and set up hamburger icon events. 

The jar file can export successfully and located under the build/libs folder, howver, it doesn't work. It has to run by type gradle build and run in the terminal. 

#  Screenshot of the Applications
<img width="936" alt="IMG_0495" src="https://user-images.githubusercontent.com/50691871/112773237-ee1ca380-9080-11eb-9b72-b35d38959c9a.PNG">
<img width="1248" alt="IMG_0496" src="https://user-images.githubusercontent.com/50691871/112773241-f07efd80-9080-11eb-9af0-b1041bbf2852.PNG">
<img width="937" alt="IMG_0497" src="https://user-images.githubusercontent.com/50691871/112773242-f1179400-9080-11eb-87b4-d8e7a18ed5d1.PNG">
<img width="910" alt="IMG_0498" src="https://user-images.githubusercontent.com/50691871/112773243-f1179400-9080-11eb-8e9e-9ed51dd00223.PNG">
<img width="940" alt="IMG_0499" src="https://user-images.githubusercontent.com/50691871/112773244-f1b02a80-9080-11eb-9491-afb805b4efe9.PNG">
<img width="934" alt="IMG_0500" src="https://user-images.githubusercontent.com/50691871/112773245-f248c100-9080-11eb-9a87-e9264cc5513d.PNG">
<img width="936" alt="IMG_0501" src="https://user-images.githubusercontent.com/50691871/112773247-f248c100-9080-11eb-945f-16edd4a1b9c9.PNG">
<img width="938" alt="IMG_0502" src="https://user-images.githubusercontent.com/50691871/112773249-f2e15780-9080-11eb-878e-a33223fb3b09.PNG">
<img width="939" alt="IMG_0503" src="https://user-images.githubusercontent.com/50691871/112773250-f2e15780-9080-11eb-82be-615348b6dfd7.PNG">
<img width="941" alt="IMG_0504" src="https://user-images.githubusercontent.com/50691871/112773251-f379ee00-9080-11eb-9f7d-a398e83c97f4.PNG">

## DataBase
Our database can be viewed and modified through the pgAdmin. You can access our database server by the ip address: 54.249.17.222 and the port number: 5432. The username should be “assignment” and the password should be “john”. Then you should get access to our postgre database server successfully.
Totally, there are 7 tables in our schema, which are representing cards, changes, items, sold items of transactions, transactions, cards used by users and users respectively. You can use SQL queries to view or modify data.

Our group has set up a Jenkins server for our application, which is hooked with our Github repository. The username of our Jenkins server account is “soft2412” and password is “tyson_thomas”. Then you should be able to access our project.

# References
    http://www.jfoenix.com/
    https://stackoverflow.com/questions/41893327/how-to-trigger-hamburger-in-javafx
    http://www.jfoenix.com/documentation.html#InputFields
