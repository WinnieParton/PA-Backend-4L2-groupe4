const readline = require("readline");

// Render the display
const displayOutput = {
  display: [
    {
      type: "INPUT",
      style: "width:100px; height: 20px; border: 1px solid blue;",
    },
    {
      type: "BUTTON",
      style: "width:100px; height: 20px; background: red;",
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
    if (number === this.randomNumber) {
      this.found = true;
      return Object.assign({}, displayOutput, {
        message: `Félicitations, vous avez trouvé le nombre en ${this.attempts} tentative(s)!`,
        gam_over: true,
      });
    } else if (number > this.randomNumber) {
      return Object.assign({}, displayOutput, {
        message: "Le nombre est plus petit",
        gam_over: false,
      });
    } else {
      return Object.assign({}, displayOutput, {
        message: "Le nombre est plus grand",
        gam_over: false,
      });
    }
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
  input = JSON.parse(input);

  const number = input.actions.value;
  if (isNaN(number)) {
    return Object.assign({}, displayOutput, {
      error: "Entrée invalide. Veuillez saisir un numéro valide.",
    });
    return;
  }

  game.attempts++;
  const result = game.checkNumber(number);
  console.log(JSON.stringify(result));

  if (!game.found) {
    rl.question("", (userInput) => {
      try {
        runGame(userInput);
      } catch (error) {
        console.log(
          Object.assign({}, displayOutput, {
            error: "Invalid user input JSON. Please try again.",
          })
        );
        runGame({});
      }
    });
  } else {
    console.log(
      Object.assign({}, displayOutput, {
        message: "La partie est terminée.",
        gam_over: true,
      })
    );
  }
}

rl.question("", (initialInput) => {
  try {
    runGame(initialInput);
  } catch (error) {
    console.log(
      Object.assign({}, displayOutput, {
        error: "Invalid initial input JSON. Please try again.",
      })
    );
    rl.close();
  }
});

console.log(displayOutput);
