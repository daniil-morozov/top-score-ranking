# Top Score Ranking Restful service
This API will be used to keep scores for a certain game for a group of player.<br>
Thus must allow the following actions:

 * Create Score
 * Get score by id
 * Get list of scores by the filter with paging supported
 * Get players' history
* **Get player's top score**
* **Get player's lowest score**
* **Get player's average score**
* **Get all the score of the player**

## Tech Stack

 * Java 11
 * Gradle 6.8
 * Spring MVC
 * Spring Boot 2.4.5
 * Postgres
 * JUnit 5

### DB Schema

``` sql
CREATE SCHEMA testdb;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE score (
    id uuid DEFAULT uuid_generate_v4(),
    player VARCHAR NOT NULL,
    score SMALLINT NOT NULL,
    "time" timestamp NOT NULL,
    PRIMARY KEY (id)
);
```

## Installation

Clone the project from git repository
```shell
https://github.com/daniil-morozov/top-score-ranking.git
```


## Prerequisites

 * JDK 14 or higher
 * Gradle 
 * Docker or Postgres installed locally with default port 5432

## Usage

Just set up the environment and run the shadow jar file

```shell
# Go to the cloned repo folder
# If no local Postgres was installed then 
#   Run docker compose file to set up the environment
docker-compose up
# Otherwise 
#   Make sure the local postgres is up and
#   Execute sql/init.sql to create the schema
\i sql/init.sql

# Build the project
gradle build
# Change dir
cd build/libs/
# Run the shadow jar
java -jar top-score-ranking-1.0-SNAPSHOT-all.jar
# Optionally, you can change src/main/resources/application.properties
# to connect to another/remote postgres
# ---
# Now you can call the API by accessing localhost:8080/scores or localhost:8080/playerscorehistory 
```

## Testing

### There are unit and integration tests

```shell
# For tests run
gradle test # from root project folder
```
### Also, there is a Postman Collection in the /postman folder
```shell
# Export the collection into Postman and run it
postman/top-score.postman_collection.json
```
# API Description

## Scores

**Get score by the id**
----
Get a score by its id

* **URL**

  ```http
  GET /scores:scoreId
  ```

* **URL Params**

  | Parameter | Type | Description |
  | :--- | :--- | :--- |
  | `scoreId` | `UUID` | **Required**. Score id |


* **Data Params**

  None

* **Success Response:**

    * **Code:** 200 OK <br />
      **Content:**
      ```json
      {
        "id": "c206fd8d-a105-4ba5-aadd-1efb946a8d84",
        "player": "danya",
        "score": 100,
        "time": "2021-04-19T11:39:35"
      }
      ```

* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
      **Content:**
      ```json
      null
      ```

* **Sample Call:**

    ```shell
    curl --location --request GET '{{url:port}}/scores/c206fd8d-a105-4ba5-aadd-1efb946a8d84' \
  --header 'Content-Type: application/json'
  ```

**Get score by a filter (with paging)**
----
Get a score by a specified filter and paging. <br>
The filter is in request body and optional.<br>
It allows filtering scores by player names, start and end time <br>
If the request body is empty/null, then the method returns all scores<br>
* **Remarks**

  Player name will be converted to lowercase, so `Edo` and `edo` are the same

* **URL**

  ```http
  GET /scores/all:pageId&size
  ```
  
* **URL Params**

    | Parameter | Type | Description |
    | :--- | :--- | :--- |
    | `page` | `integer` | **Required**. 0-based page number |
    | `size` | `integer` | **Required**. page size |
  
* **Data Params**

  **Non required:**

  JSON Payload

  **Content:**
  
    | Parameter | Type | Description |
    | :--- | :--- | :--- |
    | `players` | `array of string` | **Optional**. player names |
    | `after` | `date:yyyy-MM-ddTdd:mm:ss` | **Optional**. start date |
    | `before` | `date:yyyy-MM-ddTdd:mm:ss` | **Optional**. end date |
  

* **Success Response:**

    * **Code:** 200 OK <br />
      **Content:**
      ```json
      [
      {
            "id": "c206fd8d-a105-4ba5-aadd-1efb946a8d84",
            "player": "danya",
            "score": 100,
            "time": "2021-04-19T11:39:35"
          },
          {
            "id": "fd0af66f-3bdd-4fef-80f2-d22f5539ba0e",
            "player": "danya",
            "score": 100,
            "time": "2021-04-19T12:25:57"
        }
      ]
      ```

* **Error Response:**

  None

* **Sample Call:**

    ```shell
    curl --location --request GET '{{url:port}}/scores/all?page=0&size=12' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "players": ["danya"],
    "after": "2021-04-19T11:00:00",
    "before":  "2021-04-19T19:01:00"
    }'
  ```
  
**Add score**
----
Add a new score for specified player at certain time
* **Remarks**

  Player name will be converted to lowercase, so `Edo` and `edo` are the same

* **URL**

  ```http
  POST /scores
  ```

* **URL Params**

  None

* **Data Params**

  **Required:**
  
    JSON Payload
  
    **Content:**

    | Parameter | Type | Description |
        | :--- | :--- | :--- |
    | `player` | `string` | **Required**. player name |
    | `time` | `date:yyyy-MM-ddTdd:mm:ss` | **Required**. score time |
    | `score` | `integer` | **Required**. score value. must be > 0 |

* **Success Response:**

    * **Code:** 201 CREATED <br />
      **Content:**
      ```json
      "Added"
      ```

* **Error Response:**

    * **Code:** 400 BAD REQUEST <br />
      **Content:**
      ```json
      "Incorrect score value: must be more than 0"
      ```

* **Sample Call:**

    ```shell
    curl --location --request POST '{{url:port}}/scores/' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "player": "Danya",
        "score": 99,
        "time": "2021-04-19T10:00:00"
    }'
    ```

**Delete score**
----
Delete a score with a specified id

* **URL**

  ```http
  DELETE /scores:scoreId
  ```
  
*  **URL Params**
   
    | Parameter | Type | Description |
    | :--- | :--- | :--- |
    | `scoreId` | `UUID` | **Required**. score id |

* **Data Params**

  None

* **Success Response:**

    * **Code:** 200 OK <br />
      **Content:** 
      ```json
      "Deleted"
      ```

* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
      **Content:**
      ```json
      "No score found with id=[scoreId]"
      ```

* **Sample Call:**

    ```shell
    curl --location --request DELETE '{{url:port}}/scores/2945ae9c-aadd-4beb-8803-97bb926c8c01' \
    --header 'Content-Type: application/json''
    ```

##Player history

**Get all scores for a player**
----
Returns json array of scores for a player.
* **Remarks**

  Player name will be converted to lowercase, so `Edo` and `edo` are the same

* **URL**

  ```http
  GET /playerscorehistory/:player
  ```

*  **URL Params**

   | Parameter | Type | Description |
   | :--- | :--- | :--- |
   | `player` | `string` | **Required**. player name |

* **Data Params**

  None

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** 
      ```json
      [
        {
          "time": "2021-04-19T11:39:35",
          "score": 90
        },
        {
          "time": "2021-04-19T12:25:57",
          "score": 100
        }
      ]
      ```

* **Error Response:**
  
    None

* **Sample Call:**

  ```shell
    curl --location --request GET '{{url:port}}/playerscorehistory/danya' \
  --header 'Content-Type: application/json'
  ```

**Get the top score for a player**
----
Returns a json object representing a top score for a player.
* **Remarks**

  Player name will be converted to lowercase, so `Edo` and `edo` are the same

* **URL**

  ```http
  GET /playerscorehistory/top/:player
  ```

*  **URL Params**

    | Parameter | Type | Description |
    | :--- | :--- | :--- |
    | `player` | `string` | **Required**. player name |

* **Data Params**

  None

* **Success Response:**

    * **Code:** 200 <br />
      **Content:**
      ```json
        {
          "time": "2021-04-19T12:25:57",
          "score": 100
        }
      ```

* **Error Response:**

  None

* **Sample Call:**

  ```shell
    curl --location --request GET '{{url:port}}/playerscorehistory/top/danya' \
  --header 'Content-Type: application/json'
  ```

**Get the lowest score for a player**
----
Returns a json object representing a top score for a player.
* **Remarks**

  Player name will be converted to lowercase, so `Edo` and `edo` are the same

* **URL**

  ```http
  GET /playerscorehistory/lowest/:player
  ```

*  **URL Params**

    | Parameter | Type | Description |
    | :--- | :--- | :--- |
    | `player` | `string` | **Required**. player name |

* **Data Params**

  None

* **Success Response:**

    * **Code:** 200 <br />
      **Content:**
      ```json
        {
          "time": "2021-04-19T11:39:35",
          "score": 90
        }
      ```

* **Error Response:**

  None

* **Sample Call:**

  ```shell
    curl --location --request GET '{{url:port}}/playerscorehistory/lowest/danya' \
  --header 'Content-Type: application/json'
  ```

**Get the average score for a player**
----
Returns a number representing a top score for a player.
* **Remarks**

  Player name will be converted to lowercase, so `Edo` and `edo` are the same

* **URL**

  ```http
  GET /playerscorehistory/avg/:player
  ```

*  **URL Params**

    | Parameter | Type | Description |
    | :--- | :--- | :--- |
    | `player` | `string` | **Required**. player name |

* **Data Params**

  None

* **Success Response:**

    * **Code:** 200 <br />
      **Content:**
      ```json
       95
      ```

* **Error Response:**

  None

* **Sample Call:**

  ```shell
    curl --location --request GET '{{url:port}}/playerscorehistory/avg/danya' \
  --header 'Content-Type: application/json'
  ```
  
# Things to improve

 * No serious validation except score > 0. The user may add a score with and empty name player 
 * Overloaded get scores by filter method. Probably, can be optimized
 * No comments on the code, although the methods are descriptive enough
 * No integration tests with a real DB, but there is a Postman collection  