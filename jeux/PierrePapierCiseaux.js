const readline = require("readline");

function display() {
    return [
        {
            "width": "300",
            "height": "100",
            "content": [
                {
                    "tag": "image",
                    "href": "https://zupimages.net/up/23/28/53md.jpg",
                    "height": "100",
                    "width": "100",
                    "x": "0",
                    "y": "0",
                },
                {
                    "tag": "image",
                    "href": "https://zupimages.net/up/23/28/t4ls.jpg",
                    "height": "100",
                    "width": "100",
                    "x": "100",
                    "y": "0",
                },
                {
                    "tag": "image",
                    "href": "https://zupimages.net/up/23/28/ihln.jpg",
                    "height": "100",
                    "width": "100",
                    "x": "200",
                    "y": "0",
                },
            ],
            "player": 1
        },
        {
            "width": "300",
            "height": "100",
            "content": [
                {
                    "tag": "image",
                    "href": "https://zupimages.net/up/23/28/53md.jpg",
                    "height": "100",
                    "width": "100",
                    "x": "0",
                    "y": "0",
                },
                {
                    "tag": "image",
                    "href": "https://zupimages.net/up/23/28/t4ls.jpg",
                    "height": "100",
                    "width": "100",
                    "x": "100",
                    "y": "0",
                },
                {
                    "tag": "image",
                    "href": "https://zupimages.net/up/23/28/ihln.jpg",
                    "height": "100",
                    "width": "100",
                    "x": "200",
                    "y": "0",
                },
            ],
            "player": 2
        },
    ]
}

function createEndSvg(source) {
    return {
        "tag": "image",
        "href": source,
        "height": "100",
        "width": "100",
        "x": "0",
        "y": 0,
    };
}

function displayover(player1Choice, player2Choice) {
    let player1data = {}
    let player2data = {}

    switch (player1Choice) {
        case "pierre":
            player1data = createEndSvg("https://zupimages.net/up/23/28/53md.jpg");
            break;
        case "papier":
            player1data = createEndSvg("https://zupimages.net/up/23/28/t4ls.jpg");
            break;
        case "ciseaux":
            player1data = createEndSvg("https://zupimages.net/up/23/28/ihln.jpg");
            break;
    }
    switch (player2Choice) {
        case "pierre":
            player2data = createEndSvg("https://zupimages.net/up/23/28/53md.jpg");
            break;
        case "papier":
            player2data = createEndSvg("https://zupimages.net/up/23/28/t4ls.jpg");
            break;
        case "ciseaux":
            player2data = createEndSvg("https://zupimages.net/up/23/28/ihln.jpg");
            break;
    }

    return [
        {
            "width": "100",
            "height": "100",
            "content": [player1data],
            "player": 1
        },
        {
            "width": "100",
            "height": "100",
            "content": [player2data],
            "player": 2
        },
    ]
}

function getActions(player) {
    return [
        {
            "type": "CLICK",
            "player": player,
            "zones": [
                {"x": 0, "y": 0, "width": 100, "height": 100},
                {"x": 100, "y": 0, "width": 100, "height": 100},
                {"x": 200, "y": 0, "width": 100, "height": 100}
            ]
        }
    ];
}

function getDisplay(game, player) {
    return JSON.stringify(
        Object.assign(
            {},
            {
                "displays": display(),
                "requested_actions": getActions(player),
                "game_state": {
                    "scores": game.scores,
                    "game_over": false
                }
            }
        )
    );
}

class Game {

    constructor() {
        this.scores = [0, 0];
    }

    resolvePlayerChoice(playerChoice) {
        switch (playerChoice.x) {
            case playerChoice.x >= 0 && playerChoice.x < 100:
                return "pierre";
            case playerChoice.x >= 100 && playerChoice.x < 200:
                return "papier";
            case playerChoice.x >= 200 && playerChoice.x <= 300:
                return "ciseaux";
        }
    }

    determineWinner(player1Choice, player2Choice) {
        if (this.resolvePlayerChoice(player1Choice) === this.resolvePlayerChoice(player2Choice)) {
            return "Égalité";
        } else if (
            (this.resolvePlayerChoice(player1Choice) === "pierre" && this.resolvePlayerChoice(player2Choice) === "ciseaux") ||
            (this.resolvePlayerChoice(player1Choice) === "papier" && this.resolvePlayerChoice(player2Choice) === "pierre") ||
            (this.resolvePlayerChoice(player1Choice) === "ciseaux" && this.resolvePlayerChoice(player2Choice) === "papier")
        ) {
            return "joueur1";
        } else {
            return "joueur2";
        }
    }

    updateScores(winner) {
        if (winner === "joueur1") {
            this.scores[0]++;
        } else if (winner === "joueur2") {
            this.scores[1]++;
        }
    }

    play(player1Choice, player2Choice) {
        this.updateScores(this.determineWinner(player1Choice, player2Choice));
        return this.scores;
    }
}

const game = new Game();
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

function getPlayerChoice(game, player) {
    return new Promise((resolve) => {
        console.log(getDisplay(game, player));
        rl.question("", (choice) => {
                const input = JSON.parse(choice);
                resolve(input.actions[0]);
            }
        );
    });
}

async function playGame() {
    let playAgain = true;

    while (playAgain) {
        const player1Choice = await getPlayerChoice(game, 1);
        const player2Choice = await getPlayerChoice(game, 2);

        const result = game.play(player1Choice, player2Choice);

        playAgain = false;
        console.log(JSON.stringify(Object.assign({}, {
            "displays": displayover(player1Choice, player2Choice),
            "requested_actions": null, // Must not be null
            "game_state": {
                "scores": result,
                "game_over": true
            },
        })));
        rl.close();
        return;
    }
}

rl.question("", (initialValue) => {
    try {
        let inputJson = JSON.parse(initialValue);
        if (inputJson.init) {
            playGame();
        } else {
            console.log(
                JSON.stringify({message: "Erreur lors de l'initialisation"})
            );
        }
    } catch (error) {
        console.log(
            JSON.stringify(
                Object.assign(
                    {},
                    {
                        error: "Invalid initial input JSON. Please try again.",
                    }
                )
            )
        );
        rl.close();
    }
});

