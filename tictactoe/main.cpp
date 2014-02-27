/*
    Carlos Wen
    Tic Tac Toe Game
    Uses a recursive minimax algorithm to calculcate the best move. 
    The algorithm tries to predict all the possible moves that can follow after
    10 is given to the best move. -10 is given to the worst
    Every time it tries to predict, it assumes the player picks the best move for him and the worst move for the computer
    So the play by the player will be negative. While the play by the computer will be positive
    The higher the number the closer the move(for the computer to win) is.
    The more negative, the closer the move(for the cmputer to lose) is.
    board is represented in an array
*/

#include <iostream>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

enum play{X, O, BLANK};
enum playerTurn{XTURN, OTURN};
enum gameState {XWON, OWON, DRAW, ONGOING};

void playGame(play[]);
gameState check(play[]);
void printBoard(play[]);
void getPlay(play[],playerTurn);
int compMove(play[]);
int minimax(play[],playerTurn);


int main(void)
{
    system("title Tic Tac Toe");
    play board[9] = {BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK};
    playGame(board);
    return 0;
}

void playGame(play board[])
{
    int gamesPlayed = 0;
    int bestMove;
    playerTurn turn;

    while(true)
    {
        for(int a=0; a<9; a++) board[a] = BLANK;

        if(gamesPlayed%2 == 0) turn = XTURN;
        else turn = OTURN;
        system("cls");
        printBoard(board);

        while(check(board) == ONGOING)
        {
            if (turn == OTURN)
                getPlay(board, turn);
            else
            {
                bestMove = compMove(board);
                board[bestMove] = X;
            }

            system("cls");
            printBoard(board);


            switch (check(board))
            {
        case XWON:
            cout << "\n\t\t\tPLAYER X WON\n";
            system("PAUSE");
            break;
        case OWON:
            cout << "\n\t\t\tPLAYER O WON\n";
            system("PAUSE");
            break;
        case DRAW:
            cout << "\n\t\t\tIT'S A DRAW\n";
            system("PAUSE");
            break;
        case ONGOING:
            break;
            }

            if (turn == XTURN)turn = OTURN;
            else turn = XTURN;
        }
        gamesPlayed++;
    }
}

gameState check(play board[])
{
    /*0 1 2
      3 4 5
      6 7 8*/
    int combination[8][3] = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    for(int k=0; k<8; k++)
    {
        if (board[combination[k][0]] == X && board[combination[k][1]] == X && board[combination[k][2]] == X )
            return XWON;
        else if (board[combination[k][0]] == O && board[combination[k][1]] == O && board[combination[k][2]] == O )
            return OWON;
    }

    for (int n=0; n<9; n++)
        if (board[n] == BLANK) return ONGOING;

    return DRAW;
}

void printBoard(play board[])
{
    char squares[9];
    for (int n=0; n<9; n++)
    {
        if ( board[n] == BLANK) squares[n] = 49+n;
        else if (board[n] == X) squares[n] = 'X';
        else if (board[n] == O) squares[n] = 'O';
    }

    cout << "\n\n\n\n\n\t\t\t\t" << squares[0] << " | " << squares[1] << " | " << squares[2];
    cout << "\n\t\t\t\t---------";
    cout << "\n\t\t\t\t" << squares[3] << " | " << squares[4] << " | " << squares[5];
    cout << "\n\t\t\t\t---------";
    cout << "\n\t\t\t\t" << squares[6] << " | " << squares[7] << " | " << squares[8] << "\n\n";
}

void getPlay(play board[], playerTurn turn)
{
    if (turn == XTURN)
    {
        int play;
        do{
                cout <<"\n\t PLAYER X TURN.TYPE YOUR PLAY";
                cin >> play;
        }while(play<1 || play>9 || board[play-1] != BLANK);
        board[play-1] = X;
    }
    else if (turn == OTURN)
    {
        int play;
        do{
                cout <<"\n\t PLAYER O TURN.TYPE YOUR PLAY";
                cin >> play;
        }while(play<1 || play>9 || board[play-1]!= BLANK);
        board[play-1] = O;
    }
}

int compMove(play board[])
{
    int bestMove;
    int bestScore = -999;
    int thisScore;

    for(int k=0; k<9 ; k++)
    {
        if(board[k] == BLANK)
        {
            board[k] = X;
            thisScore = minimax(board, OTURN);//calculate the value of each move with minimax algorithm
            board[k] = BLANK;
            if (thisScore > bestScore)
            {
                bestMove = k;
                bestScore = thisScore;
            }
        }
    }
    return bestMove;
}

int minimax(play board[],playerTurn turn)
{
    int bestScore;
    int thisScore;
    int i;
    // depending on whose turn it is, the best move would be the best for the player or for the computer
    if(turn == OTURN) bestScore = 999;
    else if(turn == XTURN) bestScore = -999;
    // to calculate the value of every move, the easiest way is if that move leads to a win or loss
    // most of the times this doesnt happen, the game is still ongoing
    // so to calculate the value of the move, more claculation of future moves have to be done
    switch(check(board))
    {
    case DRAW:
        return 0;
    case XWON:
        return 10;
    case OWON:
        return -10;
    case ONGOING:
        {
            for(i=0; i<9; i++)//check all possible future moves. So it has to go through every square in the board
            {
                if(board[i] == BLANK && turn == OTURN)//checks whose turn would the move trying to be predicted is
                {
                    board[i] = O;
                    thisScore = minimax(board,XTURN);// every minimax call tries to figure out the value of that move
                    board[i] = BLANK;                // the move that is tried to be figured can be either a move from the player or from the computer
                }
                else if(board[i] == BLANK && turn == XTURN)
                {
                    board[i] = X;
                    thisScore = minimax(board,OTURN);
                    board[i] = BLANK;
                }
                //depending on whose turn it is, it will try to get the highest or lowest value
                //if it is the player's turn, then the most negative number is looked for
                //if it is the computer's turn, then the most positive number is looked for
                if(bestScore > thisScore && turn == OTURN && board[i] == BLANK)
                {
                    bestScore = thisScore;
                }
                else if(bestScore < thisScore && turn == XTURN && board[i] == BLANK)
                {
                    bestScore = thisScore;
                }
            }
            
            // every time minimax is called, that predicted move is farther from the present move
            // so its value is less and less
            // for the player's turn, a less value means a more positive(less advantage for the player)
            // for the computer's turn, a less value means a more negative(less advantage for the computer)
            if (bestScore<0) bestScore++;
            else if (bestScore>0) bestScore--;
            
            //return the "value" of the move
        return bestScore;
        }
    }
    return 0;
}
