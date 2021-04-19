# Top Score Ranking Restful service


# API Description

## Scores

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