# Getting Started

Solving Monty Hall problem.
This web application gives few APIs that helps to play, simulate, analyse results of the quiz called 'Monty Hall problem' which based on probability theory.
 - User can play game
 - User can run game simulation and get the answer to the question: ‘Do I have a better chance to win if I change my box?’
 - User can get stats which BOX most lucky 

### Technical description and structure
Application uses MongoDB like game result storage. To make application functioning properly please install MongoDB and run it on default port `27017`
Full list of tech:
 - Java 11
 - MongoDB
 - Spring Boot
 - Lombok

Main application part have this directory structure:
````
|-controller
|-data
|-properties
|-repository
|-service
|_utils
../../resources/application.yml
````

- `controller` - web layer, contains classes with endpoint definitions 
- `data` - DB entities and presentation data objects
- `properties` - classes to get configuration properties from `application.yml`  
- `repository` - Db repositories and queries
- `service` - business logic and DB interactions
- `utils` - helpers and utils
- `application.yml` - configuration file, number of iterations for game simulation can be changed there 

All unit tests located under `test` folder.


### How to run?
Run in the root folder: `./mvnw spring-boot:run`

Application will start on port `8080`

### Endpoints and how to analyse responses

**Reminder!** Game rules: there are 3 boxes, one with money and other 2 are empty. User need to select one box. After that Monty will open one box that is empty and user need to decide does he wants to change his selected box to one that is left.

To get answer for the question **‘Do I have a better chance to win if I change my box?’** look point (2).

Game will receive as an input 2 parameters box name and flag that indicates will user want to change boxes or not:

- `selectedBox` - box name to select. String type can be: `'box_1', 'box_2', 'box_3'`
- `isChangeSelection` - change box or not. Boolean type: `true/false`

1. `POST` `http://localhost:8080/game`
   
    Play One single game.
    
   Request body:
    ```json
    {
        "selectedBox": "box_1", // can be 'box_1', 'box_2', 'box_3'
        "isChangeSelection": true
    }
    ```
   
    Response:
    ```json
    {
        "gameId": "61689146e38db85a5a720b48", // just game id, useful for DB search
        "selectedBox": "box_3", // box that user keep at the end
        "emptyBox": "box_2", // empty box that been shown 
        "winnerBox": "box_3", // winner box
        "changed": true, // did user changed initially selected box or not
        "winner": true // Does user won the game?
    }
    ```
   
2. `GET` `http://localhost:8080/simulator`
   
    Simulate game multiple times and get stats. by default system will run game `5000` times. This number can be changed in `application.yml` file. no extra parameter for this request requires.
    Here is answer for the question: **‘Do I have a better chance to win if I change my box?’**

    Response:
    ```json
    {
        "numberOfGames": 5000, // total amout of games played
        "numberOfWins": 2551, // amount of wins
        "numberOfDecisionChange": 2527, // number of times when user decided to change initially selected box
        "winsAfterDecisionChange": 1700, // amount of wins after user decided to change initially selected box
        "winsWithoutDecisionChange": 851, // amount of wins when user decided NOT to change initially selected box
        "boxChangeToNonChangeWinsRatio": 1.9976498237367804 // ratio of winsAfterDecisionChange/winsWithoutDecisionChange - shows how more ofter user won after changing initially selected box 
    }
    ```
3. `GET` `http://localhost:8080/analytics`

   Return some stats from all games stored in DB. Which box got most wins? 
   
    Response:
    ```json
    {
        "mostWinningBox": "box_2", // box that won most amount of times
        "totalNumberOfWins": 860 // amount of wins for most lucky box
    }
    ```

### Answer
After running >20000 game rounds data shows that after changing initially selected box user wins ~2 times more often.

Do I have a better chance to win if I change my box? - **YES!**

   





