class Board:
    def __init__(self):
        self.board = [
        [" ", " ", " "],
        [" ", " ", " "],
        [" ", " ", " "]
        ]
        self.turn = "X"
        self.available_actions = [[i,j] for i in range(3) for j in range(3)]
        self.terminal = False

    def move(self, x, y):
        #x = int(input("Col number: ")) - 1
        #y = int(input("Row number: ")) - 1
        if not [x, y] in self.available_actions:
            print("Already occupied, please choose another spot!")
            return
        self.board[y][x] = self.turn
        self.turn = "X" if self.turn == "O" else "O"
        self.available_actions.remove([x, y])

    def check(self):
        states = [
        self.board[0][0] == self.board[1][0] == self.board[2][0] and not self.board[0][0] == " ",
        self.board[0][0] == self.board[0][1] == self.board[0][2] and not self.board[0][0] == " ",
        self.board[0][1] == self.board[1][1] == self.board[2][1] and not self.board[0][1] == " ",
        self.board[1][0] == self.board[1][1] == self.board[1][2] and not self.board[1][0] == " ",
        self.board[0][2] == self.board[1][2] == self.board[2][2] and not self.board[0][2] == " ",
        self.board[2][0] == self.board[2][1] == self.board[2][2] and not self.board[2][0] == " ",
        self.board[0][0] == self.board[1][1] == self.board[2][2] and not self.board[0][0] == " ",
        self.board[2][0] == self.board[1][1] == self.board[0][2] and not self.board[2][0] == " ",
        ]
        if states[0] or states[1] or states[2] or states[3] or states[4] or states[5] or states[6] or states[7]:
            return self.turn
        if (not " " in self.board[0]) and (not " " in self.board[1]) and (not " " in self.board[2]):
            return "tie"

    def restart(self):
        self.board = [
        [" ", " ", " "],
        [" ", " ", " "],
        [" ", " ", " "]
        ]
        self.terminal = False
        self.available_actions = [[i,j] for i in range(3) for j in range(3)]

    def show(self):
        print("+-----+")
        print("|{0} {1} {2}|\n|{3} {4} {5}|\n|{6} {7} {8}|".format(*self.board[0], *self.board[1], *self.board[2]))
        print("+-----+")

if __name__ == "__main__":
    from random import choice, random
    qtable = dict()
    prestate = None
    print("Welcome to TicTacToe! \n")
    game = Board()
    while True:
        if game.turn == "X":
            action = None
            if hash(str(game.board)) in qtable:
                if random() < 1/(1+qtable[hash(str(game.board))]["n"]**2):
                    action = tuple(choice(game.available_actions))
                else:
                    for i in qtable[hash(str(game.board))]:
                        if i == "n":
                            continue
                        if action == None:
                            action = i
                        if qtable[hash(str(game.board))][i] > qtable[hash(str(game.board))][action]:
                            action = i
                qtable[hash(str(game.board))]["n"] += 1
            else:
                state = {tuple(game.available_actions[i]):0 for i in range(len(game.available_actions))}
                state["n"] = 0
                qtable[hash(str(game.board))] = state
                action = tuple(choice(game.available_actions))
            prestate = hash(str(game.board))
            preaction = action
            n = qtable[hash(str(game.board))]["n"]
            game.move(*action)
            reward = 0
            if game.check() == "X":
                reward = 1.0
            elif game.check() == "O":
                reward = -1.0
            if hash(str(game.board)) in qtable:
                for i in qtable[hash(str(game.board))]:
                    if i == "n":
                        continue
                    if qtable[hash(str(game.board))][i] > qtable[hash(str(game.board))][action]:
                        action = i
                    qtable[prestate][action] += (1/(1+n**2)) * (qtable[hash(str(game.board))][action] + reward - qtable[prestate][preaction])
            qtable[prestate][action] += (1/(1+n**2)) * (reward - qtable[prestate][preaction])
        else:
            action = choice(game.available_actions)
            game.move(*action)
        game.show()
        if not game.check() == None:
            if game.check() == "tie":
                print("It's a TIE!")
            else:
                print("X" if game.check() == "O" else "O", " is the Winner!")
                input("Press any key to restart!")
            game.restart()
    input("Press any key to exit!")

