# Top Score Ranking Restful service
This API will be used to keep scores for a certain game for a group of player.<br>
Thus must allow the following actions:

 * Create Score
 * Get score by id
 * Get list of scores by filter
 * Get players' history
* **Get player's top score**
* **Get player's lowest score**
* **Get player's average score**
* **Get all the score of the player**
 
## Installation

Clone the project from git repository
```bash
https://github.com/daniil-morozov/top-score-ranking.git
```


## Prerequisites

 * JDK 16
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

  /scores:scoreId

* **Method:**

  `GET`

* **URL Params**

  **Required:**

  `scoreId=[UUID]`

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
If the request body is empty/null, then the method returns all scores

* **URL**

  /scores/all:pageId&size

* **Method:**

  `GET`

* **URL Params**

  **Required:**

  `page=[Integer]`<br>
  `size=[Integer]`

* **Data Params**

  **Non required:**

  JSON Payload

  **Content:**
  ```json
  {
    "players": [Optional array of Strings],
    "before": [Optional date:yyyy-MM-ddTdd:mm:ss],
    "after": [Optional date:yyyy-MM-ddTdd:mm:ss]
  }
  ```

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

* **URL**

  /scores

* **Method:**

  `POST`

* **URL Params**

  None

* **Data Params**

  **Required:**
  
    JSON Payload
  
    **Content:**
  ```json
  {
    "player": [String],
    "time": [Date:yyyy-MM-ddTdd:mm:ss],
    "score": [Integer]>0
  }
  ```

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

  /scores:scoreId

* **Method:**

  `DELETE`
  
*  **URL Params**

   **Required:**

   `scoreId=[UUID]`

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

* **URL**

  /playerscorehistory/:player

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `player=[String]`

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

* **URL**

  /playerscorehistory/top/:player

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `player=[String]`

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

* **URL**

  /playerscorehistory/lowest/:player

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `player=[String]`

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

* **URL**

  /playerscorehistory/avg/:player

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `player=[String]`

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