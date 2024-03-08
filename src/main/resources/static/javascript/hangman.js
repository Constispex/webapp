class Hangman {
    tries = document.getElementById("tries")
    word = document.getElementById("guessedWord")
    guessedLetters = document.getElementById("guessedLetters")

}

errorField = document.getElementById("errorField")
msgField = document.getElementById("messageField")
hangman = new Hangman()

document.getElementById("guessLetterButton").addEventListener("click", guessLetter)


function addMsg(msg) {
    msgField.innerHTML += msg + "<br>"
}


function startHangman() {
    try {
        fetch("/hangman/startGame", {
            method: "GET",
        }).then(response => response.json())
            .then(data => {
                console.log(data)
                addMsg(data.message)
                drawHangman(data.tries)
                updateHangman(data)
            })
    } catch (error) {
        errorField.innerHTML = "Error: " + error
        console.log("Error: " + error)
    }
}

function updateHangman(data) {
    hangman.guessedLetters.innerHTML = data.guessedLetters
    hangman.tries.innerHTML = data.tries
    hangman.word.innerHTML = data.guessedWord
    drawHangman(data.tries)
}

function guessLetter() {
    try {
        let letter = document.getElementById("guessLetter").value
        fetch("/hangman/guess/" + letter, {
            method: "POST",
        }).then(response => response.json())
            .then(data => {
                console.log(data)
                addMsg(data.message)
                updateHangman(data)
            })
    } catch (error) {
        errorField.innerHTML = "Error: " + error
        console.log("Error: " + error)
    }
    document.getElementById("guessLetter").value = ""
}


let canvas = document.getElementById('hangmanCanvas');
let ctx = canvas.getContext('2d');

function drawHangman(tries) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw the gallows
    ctx.beginPath();
    ctx.moveTo(100, 350);
    ctx.lineTo(200, 350);
    ctx.moveTo(150, 350);
    ctx.lineTo(150, 100);
    ctx.lineTo(250, 100);
    ctx.lineTo(250, 150);
    ctx.stroke();

    if (tries > 0) {
        // Draw the head
        ctx.beginPath();
        ctx.arc(250, 175, 25, 0, Math.PI * 2);
        ctx.stroke();
    }

    if (tries > 1) {
        // Draw the body
        ctx.beginPath();
        ctx.moveTo(250, 200);
        ctx.lineTo(250, 275);
        ctx.stroke();
    }

    if (tries > 2) {
        // Draw the left arm
        ctx.beginPath();
        ctx.moveTo(250, 225);
        ctx.lineTo(225, 200);
        ctx.stroke();
    }

    if (tries > 3) {
        // Draw the right arm
        ctx.beginPath();
        ctx.moveTo(250, 225);
        ctx.lineTo(275, 200);
        ctx.stroke();
    }

    if (tries > 4) {
        // Draw the left leg
        ctx.beginPath();
        ctx.moveTo(250, 275);
        ctx.lineTo(225, 300);
        ctx.stroke();
    }

    if (tries > 5) {
        // Draw the right leg
        ctx.beginPath();
        ctx.moveTo(250, 275);
        ctx.lineTo(275, 300);
        ctx.stroke();
    }
}