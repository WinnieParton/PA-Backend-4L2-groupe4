const readline = require("readline");

// Render the display
const displayOutput = {
    display: [
        {
            type: "INPUT",
            style: {
                width: "100px",
                height: "20px",
                border: "1px solid blue",
            },
        },
        {
            type: "BUTTON",
            style: {
                width: "100px",
                height: "20px",
                background: "red",
            },
            content: "Deviner",
        },
    ],
};
class Game {
    constructor() {
        this.randomNumber = Math.floor(Math.random() * 100) + 1;
        this.attempts = 0;
        this.found = false;
    }

    checkNumber(number) {
        if (number == this.randomNumber) {
            this.found = true;
            console.log(
                JSON.stringify(
                    Object.assign({}, displayOutput, {
                        message: `Félicitations, vous avez trouvé le nombre en ${this.attempts} tentative(s)!`,
                        gam_over: true,
                    })
                )
            );
        } else if (number > this.randomNumber) {
            console.log(
                JSON.stringify(
                    Object.assign({}, displayOutput, {
                        message: "Le nombre est plus petit"+this.randomNumber+" number"+number,
                        gam_over: false,
                    })
                )
            );
        } else {
            console.log(
                JSON.stringify(
                    Object.assign({}, displayOutput, {
                        message: "Le nombre est plus grand"+this.randomNumber+" number"+number,
                        gam_over: false,
                    })
                )
            );
        }
        return;
    }

    resetGame() {
        this.attempts = 0;
        this.found = false;
        this.randomNumber = Math.floor(Math.random() * 100) + 1;
    }
}

const game = new Game();
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

function runGame(input) {
    let inputJson = JSON.parse(input);
    // Check if the "init" property exists
    if (inputJson.init) {
        console.log(JSON.stringify(displayOutput));
        rl.question("", (userInput) => {
            try {
                runGame(userInput);
            } catch (error) {
                console.log(
                    JSON.stringify(
                        Object.assign({}, displayOutput, {
                            error: "Invalid user input JSON. Please try again.",
                        })
                    )
                );
                runGame({});
            }
        });
    } else {
        const number = inputJson.actions.value;
        if (isNaN(number)) {
            console.log(
                JSON.stringify(
                    Object.assign({}, displayOutput, {
                        error: "Entrée invalide. Veuillez saisir un numéro valide.",
                    })
                )
            );
            return;
        }

        game.attempts++;
        const result = game.checkNumber(number);

        if (!game.found) {
            rl.question("", (userInput) => {
                try {
                    runGame(userInput);
                } catch (error) {
                    console.log(
                        JSON.stringify(
                            Object.assign({}, displayOutput, {
                                error: "Invalid user input JSON. Please try again.",
                            })
                        )
                    );
                    runGame({});
                }
            });
        } else {
            return;
        }
    }
}

rl.question("", (initialInput) => {
    try {
        runGame(initialInput);
    } catch (error) {
        console.log(
            JSON.stringify(
                Object.assign({}, displayOutput, {
                    error: "Invalid initial input JSON. Please try again." + error,
                })
            )
        );
        rl.close();
    }
});
