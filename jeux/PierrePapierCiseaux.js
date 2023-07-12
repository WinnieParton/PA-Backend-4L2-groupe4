const readline = require("readline");

function display(){
  return [
    {
      type: "BUTTON",
      content: {
        type: "img",
        src: "https://zupimages.net/up/23/28/53md.jpg",
      },
      value: "pierre",
    },
    {
      type: "BUTTON",
      content: {
        type: "img",
        src: "https://zupimages.net/up/23/28/t4ls.jpg",
      },
      value: "papier",
    },
     {
      type: "BUTTON",
      content: {
        type: "img",
        src: "https://zupimages.net/up/23/28/ihln.jpg",
      },
      value: "ciseaux",
    },
  ]
}
function getDisplay(message) {
  return JSON.stringify(
    Object.assign(
      {},
      {
        message: message,
        display: display()
      }
    )
  );
}

class Game {
  constructor() {
    this.player1Choice = "";
    this.player2Choice = "";
    this.scores = {
      joueur1: 0,
      joueur2: 0,
    };
  }

  determineWinner(player1Choice, player2Choice) {
    if (player1Choice === player2Choice) {
      return "Égalité";
    } else if (
      (player1Choice === "pierre" && player2Choice === "ciseaux") ||
      (player1Choice === "papier" && player2Choice === "pierre") ||
      (player1Choice === "ciseaux" && player2Choice === "papier")
    ) {
      return "joueur1";
    } else {
      return "joueur2";
    }
  }

  updateScores(winner) {
    if (winner === "joueur1") {
      this.scores.joueur1++;
    } else if (winner === "joueur2") {
      this.scores.joueur2++;
    }
  }

  play(player1Choice, player2Choice) {
    this.player1Choice = player1Choice;
    this.player2Choice = player2Choice;

    const winner = this.determineWinner(this.player1Choice, this.player2Choice);
    this.updateScores(winner);

    const result = {
      winner: winner,
      scores: this.scores,
    };
    return result;
  }
}

const game = new Game();
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

function getPlayerChoice(player) {
  return new Promise((resolve) => {
    console.log(getDisplay(`Joueur ${player}, choisissez pierre, papier ou ciseaux : `));
    rl.question("",
      (choice) => {
        const input = JSON.parse(choice);

        resolve(input.actions.value.trim().toLowerCase());
      }
    );
  });
}
async function playGame() {
  let playAgain = true;

  while (playAgain) {
    const player1Choice = await getPlayerChoice(1);
    const player2Choice = await getPlayerChoice(2);

    const result = game.play(player1Choice, player2Choice);

    playAgain = false;
    console.log(JSON.stringify( Object.assign({}, {
      display: display(),
      result,
      gam_over: true,
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
        JSON.stringify({ message: "Erreur lors de l'initialisation" })
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

