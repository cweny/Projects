/*
    Carlos Wen
    Tic Tac Toe Game
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
            thisScore = minimax(board, OTURN);
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

    if(turn == OTURN) bestScore = 999;
    else if(turn == XTURN) bestScore = -999;

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
            for(i=0; i<9; i++)
            {
                if(board[i] == BLANK && turn == OTURN)
                {
                    board[i] = O;
                    thisScore = minimax(board,XTURN);
                    board[i] = BLANK;
                }
                else if(board[i] == BLANK && turn == XTURN)
                {
                    board[i] = X;
                    thisScore = minimax(board,OTURN);
                    board[i] = BLANK;
                }

                if(bestScore > thisScore && turn == OTURN && board[i] == BLANK)
                {
                    bestScore = thisScore;
                }
                else if(bestScore < thisScore && turn == XTURN && board[i] == BLANK)
                {
                    bestScore = thisScore;
                }
            }
            if (bestScore<0) bestScore++;
            else if (bestScore>0) bestScore--;

        return bestScore;
        }
    }
    return 0;
}
