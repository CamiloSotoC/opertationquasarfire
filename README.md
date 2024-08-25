# Springboot - API - Opertation Quasar Fire
## Description
This repository contains the implementation of the "Operation Quasar Fire" challenge in Java with SpringBoot.
The goal is to create a program that returns the location and rebuild the distress message of an Imperial cargo ship adrift in an asteroid field.

## Dependencies
* Java 17
* Springboot 3.4.0
* Maven 4.0.0
* Lombok 1.18.34
* Trilateration 1.0.2 (https://github.com/lemmingapex/trilateration)
* SpringDoc OpenAPI Starter WebMVC UI 2.6.0

## Getting Started

### Configuration
The application will run by default on port 8080, you can modify it in the application.properties with the following variable: 
```
server.port=8080
```
The name and location of the satellites are already configured:
#### Satellite Data:
* Kenobi: [-500, -200]
* Skywalker: [100, -100]
* Sato: [500, 100]

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
### Executing program
To run the application run the following command in the root of the project:
```
mvn spring-boot:run
```
### How to Use the  API
* To send satellite data and get the location and message of the spaceship.
Sends satellite data with an HTTP POST request to url_server/topsecret/ with a JSON payload like the following:
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
If the position or message cannot be determined, it returns:
```
RESPONSE CODE: 404
```
* To set one satellite data 
Sends one satellite data to update it. Send an HTTP POST request to url_server/topsecret_split/{satellite_name} with a JSON payload like the following:
```json
{
  "distance":150,
  "message": ["", "", "un", "mensaje"]
}
```
The response will be a JSON with update data of satellites. Consider 'satellite name' as 'kenobi', where satellite kenobi is updated, as in the following example:
```json
[
  {
    "name": "Kenobi",
    "position": {"x": -500.0, "y": -200.0},
    "distance": 150.0,
    "message": ["", "", "un", "mensaje"]
  },
  {
    "name": "Skywalker",
    "position": {"x": 100.0, "y": -100.0},
    "distance": 115.5,
    "message": ["", "es", "", "", "secreto"]
  },
  {
    "name": "Sato",
    "position": {"x": 500.0, "y": 100.0},
    "distance": 142.7,
    "message": ["este", "", "un", "", ""]
  }
]
```
* To get the location and message of the spaceship.
Send an HTTP GET request to url_server/topsecret_split/.
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
