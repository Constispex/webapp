class Player {
    constructor(playerId, playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }
}

const player1 = document.getElementById('player1');
const player2 = document.getElementById('player2');
const svUrl = "/schiffe-versenken";

const urlParams = new URLSearchParams(window.location.search);
const playerName = urlParams.get('playerName');
const gameId = urlParams.get('gameId');
let player_id = 0;

const gameBoard = document.getElementById('gameBoard');
const boardSize = 10; // Angenommen, das Spielfeld ist 10x10

getPlayerId();
initiateBoard();

function onReady() {
    fetch(svUrl + "/game/" + gameId + "/setReady/" + player_id,
        {
            method: "POST",
        })
        .then(response => response.json())
        .then(data => {
            if (data) {
                console.log("Player ready: " + data)
            } else {
                console.error("Received invalid data from server: ", data);
            }
        })
        .catch(error => console.error(error));
}

function onStart() {
    fetch(svUrl + "/game/" + gameId + "/start",
        {
            method: "POST",
        })
        .then(response => response.json())
        .then(data => {
            if (data) {
                console.log("Game started: " + data)
            } else {
                console.error("Received invalid data from server: ", data);
            }
        })
        .catch(error => console.error(error));

}


function initiateBoard() {
// Erstellen Sie das Spielfeld
    for (let i = 0; i < boardSize; i++) {
        let row = document.createElement('tr');
        for (let j = 0; j < boardSize; j++) {
            let cell = document.createElement('td');
            cell.textContent = ' ';
            cell.setAttribute('id', i + "" + j);
            cell.addEventListener("click", () => clickHandler(i, j))
            row.appendChild(cell);
        }
        gameBoard.appendChild(row);
    }
}

function clickHandler(i, j) {
    getGameState().then(gameState => {
        if (gameState === "WAITING_FOR_PLAYERS") {
            alert("Waiting for players")
        } else if (gameState === "WAITING_FOR_SHIPS") {
            fetch(svUrl + "/game/" + gameId + "/board/" + player_id + "/placeShip/" + i + "/" + j,
                {
                    method: "POST",
                })
                .then(response => response.json())
                .then(data => {
                    if (data) {
                        console.log("Ship placed: " + data)
                        updateBoard()
                    } else {
                        console.error("Received invalid data from server: ", data);
                    }
                })
                .catch(error => console.error(error));
        } else if (gameState === "WAITING_FOR_MOVE") {
            fetch(svUrl + "/game/" + gameId + "/player/" + player_id + "/attack/" + i + "/" + j,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                .then(response => response.json())
                .then(data => {

                    if (data.hit === true) {
                        console.log("Move made: " + data)
                        updateBoard()
                    } else {
                        console.error("Received invalid data from server: ", data);
                    }
                })
                .catch(error => console.error(error));
        } else {
            alert("Game is over")
        }
    })
}

async function getGameState() {
    let gameState = "UNKNOWN"
    try {
        const response = await fetch(svUrl + "/game/" + gameId + "/state", {method: "GET"});
        const data = await response.text();
        console.log("Game state: " + data)
        gameState = data;
    } catch (error) {
        console.error(error);
    }
    return gameState;
}

// Feld aktualisieren
function updateBoard() {
    fetch(svUrl + "/game/" + gameId + "/board/" + player_id,
        {
            method: "GET",
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                console.log("Board: " + data[0][0])
                for (let i = 0; i < boardSize; i++) {
                    for (let j = 0; j < boardSize; j++) {
                        gameBoard.rows[i].cells[j].textContent = data[i][j];
                    }
                }
            } else {
                console.error("Received invalid data from server: ", data);
            }
        })
        .catch(error => console.error(error));
}

function getPlayerId() {
    fetch(svUrl + "/game/" + gameId + "/player/" + playerName,
        {
            method: "GET",
        })
        .then(response => response.json())
        .then(data => {
            player_id = data.playerId;
            console.log("Player id: " + player_id)
            if (player_id === -1) {
                console.error("Received invalid player id from server: ", data);
            }
            waitForPlayer()
        })
        .catch(error => console.error(error));
}

function waitForPlayer() {

    if (player_id === 1) {
        console.log("Player 1: " + playerName)
        let intervallId;
        player1.innerHTML = playerName;
        player2.innerHTML = "Wait for player 2...";

        intervallId = setInterval(() => {
            fetch(svUrl + "/game/" + gameId + "/player/id/2",
                {
                    method: "GET",
                })
                .then(response => response.json())
                .then(data => {
                    if (data.player === true) { // Player found
                        player2.textContent = "Player 2: " + data.playerName;
                        clearInterval(intervallId);
                        updateBoard()
                    } else {
                        console.log("Player not found yet")
                    }
                })
                .catch(error => console.error(error));

        }, 1000);
    } else if (player_id === 2) {
        player2.textContent = "Player 1: " + playerName;
        updateBoard()
    }
}