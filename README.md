# Springboot - API - Opertation Quasar Fire 🚀
This repository contains the implementation of the "Operation Quasar Fire" challenge in Java with SpringBoot.
The goal is to create a program that returns the location and rebuild the distress message of an Imperial cargo ship adrift in an asteroid field.

## 🫀 Location Search Algorithm
To obtain the location of the spaceship, an external library was used[https://github.com/lemmingapex/trilateration]👆, which performs trilateralization through a non-linear least squares system. This requires at least three positions (of the satellites) and three distances (from the satellites to the spaceship) to obtain an approximate location of the spaceship.

## 🫀 Rebuild Message Algorithm
To rebuild the message, the following steps are performed:
* First, have the messages obtained by the satellites.
* Then, find the maximum length to determine the number of positions in the original message.
* A list of lists 'A' is created where each internal list represents a position in the original message.
* The input messages are iterated over and each word is added to the corresponding internal list of 'A' according to its position.
* Then, 'A' is iterated over and the most common word for each position is obtained (If the word previously obtained as the most common appears again in the next internal list of 'A', it is removed once from that internal list).
* The most common word for each position is added to the reconstructed message list.
* Finally, the rebuilded message is returned as a string, with the words separated by spaces.

## ✨ Dependencies
* Java 17
* Springboot 3.4.0
* Maven 4.0.0
* Lombok 1.18.34
* Trilateration 1.0.2 (https://github.com/lemmingapex/trilateration)👆
* SpringDoc OpenAPI Starter WebMVC UI 2.6.0

## ☁️ Deployed
The application is deployed in Railway:
[https://opertationquasarfire-production.up.railway.app/satellites/]👆

## ⚙️ Configuration
### Port:
The application will run by default on port 8080, you can modify it in the application.properties with the following variable: 
```
server.port=8080
```
### Satellite Data:
The name and location of the satellites are already configured:
```
Kenobi: [-500, -200]
Skywalker: [100, -100]
Sato: [500, 100]
```
If you need to modify this information you can edit the application.properties variables:
```
#Satellite Data
satellite.kenobiX=-500.0
satellite.kenobiY=-200
satellite.skywalkerX=100
satellite.skywalkerY=-100
satellite.satoX=500
satellite.satoY=100
```
## 💻 Executing program
To run the application run the following command in the root of the project `mvn spring-boot:run`6

## 💁‍♀️ How to Use the  API
### 🟢 Endpoint 1
To send satellite data and get the location and message of the spaceship.
Sends satellite data with an HTTP POST request to:
```http
POST /topsecret/
```
With a JSON payload like the following:
```json
{
  "satellites": [
    {
      "name": "kenobi",
      "distance": 100.0,
      "message": ["este", "", "", "mensaje", ""]
    },
    {
      "name": "skywalker",
      "distance": 115.5,
      "message": ["", "es", "", "", "secreto"]
    },
    {
      "name": "sato",
      "distance": 142.7,
      "message": ["este", "", "un", "", ""]
    }
  ]
}
```
The response will be a JSON with the position and message of the spaceship.
```json
{
  "position": {
    "x": -58.315252587138595,
    "y": -69.55141837312165
  },
  "message": "este es un mensaje secreto"
}
```
### 🟢 Endpoint 2
To set one satellite data 
Sends one satellite data to update it. Send an HTTP POST request to:
```http
POST /topsecret_split/{satellite_name}
```
With a JSON payload like the following:
```json
{
  "distance":150,
  "message": ["", "", "un", "mensaje"]
}
```
The response will be a JSON with updated satellite data. For the following response example consider the POST request:
```http
POST /topsecret_split/kenobi
```
Where satellite 'kenobi' is updated:
```json
{
    "name": "Kenobi",
    "position": {"x": -500.0, "y": -200.0},
    "distance": 150.0,
    "message": ["", "", "un", "mensaje"]
}
```
### 🟢 Endpoint 3
To get the location and message of the spaceship.
Send an HTTP GET request to:
```http
GET /topsecret_split/
```
The response will be a JSON with the position and message of the emitter.
```json
{
  "position": {
    "x": -58.315252587138595,
    "y": -69.55141837312165
  },
  "message": "este es un mensaje secreto"
}
```
### 🟢 Endpoint 4
To get all satellite data
Send an HTTP GET request to:
```http
GET /satellites/
```
The response will be a JSON with all the satellite data, for the following response example consider that the application has not been updated with the spaceship data.
```json
[
  {
    "name": "Kenobi",
    "position": {"x": -500.0, "y": -200.0},
    "distance": null,
    "message": null
  },
  {
    "name": "Skywalker",
    "position": {"x": 100.0, "y": -100.0},
    "distance": null,
    "message": null
  },
  {
    "name": "Sato",
    "position": {"x": 500.0, "y": 100.0},
    "distance": null,
    "message": null
  }
]
```
## 📝 Documentation & Testing - Swagger UI
For more documentation and testing you can access swagger-ui:
```http
GET /swagger-ui/index.html
```
Url to the application deployed:
[https://opertationquasarfire-production.up.railway.app/swagger-ui/index.html]👆