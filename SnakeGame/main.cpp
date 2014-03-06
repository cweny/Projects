#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdio.h>
#include <windows.h>


#define MAX_SNAKE_SIZE 100
#define SQUARE_SIZE 0.4
#define BOARD_SIZE 12

/*
    Carlos Wen
    snake game in opengl
    Runs on windows computers
*/

static int speed=100;
static int rot_x=0, rot_y=0;
static GLfloat trans_x=0, trans_y=0, trans_z=0;

static bool lose = false;
static bool fruit_eaten = false;
static int snake_size = 1, snake_tail = 0;
static int snake[MAX_SNAKE_SIZE][2]={{1,1}};
//the data structure for the snake behaves like a queue, but it is easy to iterate through(so that the snake is rendered)
//the data structure is basically an array
//an index of the tail of the snake is kept. This index changes as the snake moves
//the head of the snake would be tail+size. So both the head and tail changes everytime
//so the snake is in different parts of the array at different moments. 
//made it this way to attempt to make it fast
enum ways {UP,DOWN,LEFT,RIGHT};
static ways direction[MAX_DIRECTIONS] = {UP};
static int direction_size=1;
static int current_direction=0;

static char board [BOARD_SIZE][BOARD_SIZE];
static int fruit[2] = {6,1};
//function to update the window and render new images
void move_snake(void)
{
    int x = snake[(snake_tail+snake_size-1)%MAX_SNAKE_SIZE][0];
    int y = snake[(snake_tail+snake_size-1)%MAX_SNAKE_SIZE][1];
    if(lose)
    {
        printf("\nyou lost");
        snake_tail=0;
        snake[0][0]=1;
        snake[0][1]=1;
        snake_size=1;
        for (int i = 0; i<BOARD_SIZE; i++ )
        {
            for(int j = 0; j <BOARD_SIZE ;j++){
                board[i][j] = ' ';
            }
        }
        lose = false;
    }
    if(fruit_eaten)
    {
        do{
                fruit[0] = rand()% BOARD_SIZE;
                fruit[1] = rand()% BOARD_SIZE;

        }while(board[fruit[0]][fruit[1]] != ' ');
        fruit_eaten = false;
    }
    switch(direction[current_direction]){
        case UP:
            if(y == BOARD_SIZE-1)
            {
                if(board[x][0]=='o')
                {
                    lose = true;
                }
                else if( x==fruit[0] && fruit[1]==0)
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=0;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;

                    board[x][0]='o';
                    snake_size++;
                    fruit_eaten = true;
                }
                else
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=0;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;
                    board[x][0]='o';
                    board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                    snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
                }
            }
            else if (board[x][y+1] == 'o'){lose = true;}
            else if (x==fruit[0] && fruit[1]==y+1)
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y+1;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;

                board[x][y+1]='o';
                snake_size++;
                fruit_eaten = true;
            }
            else 
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y+1;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;
                board[x][y+1]='o';
                board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
            }
            break;
        case DOWN:
            if(y == 0)
            {
                if(board[x][BOARD_SIZE-1]=='o')
                {
                    lose = true;
                }
                else if( x==fruit[0] && fruit[1]==BOARD_SIZE-1)
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=BOARD_SIZE-1;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;

                    board[x][BOARD_SIZE-1]='o';
                    snake_size++;
                    fruit_eaten = true;
                }
                else
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=BOARD_SIZE-1;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;
                    board[x][BOARD_SIZE-1]='o';
                    board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                    snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
                }
            }
            else if (board[x][y-1] == 'o'){lose = true;}
            else if (x==fruit[0] && fruit[1]==y-1)
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y-1;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;

                board[x][y-1]='o';
                snake_size++;
                fruit_eaten = true;
            }
            else //(board[x][y+1] == ' ')
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y-1;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x;
                board[x][y-1]='o';
                board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
            }
            break;
        case LEFT:
            if(x == 0)
            {
                if(board[BOARD_SIZE-1][y]=='o')
                {
                    lose = true;
                }
                else if( BOARD_SIZE-1==fruit[0] && fruit[1]==y)
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=BOARD_SIZE-1;

                    board[BOARD_SIZE-1][y]='o';
                    snake_size++;
                    fruit_eaten = true;
                }
                else
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=BOARD_SIZE-1;
                    board[BOARD_SIZE-1][y]='o';
                    board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                    snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
                }
            }
            else if (board[x-1][y] == 'o'){lose = true;}
            else if (x-1==fruit[0] && fruit[1]==y)
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x-1;

                board[x-1][y]='o';
                snake_size++;
                fruit_eaten = true;
            }
            else //(board[x][y+1] == ' ')
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x-1;
                board[x-1][y]='o';
                board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
            }
            break;
        case RIGHT:
            if(x == BOARD_SIZE-1)
            {
                if(board[0][y]=='o')
                {
                    lose = true;
                }
                else if( 0==fruit[0] && fruit[1]==y)
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=0;

                    board[0][y]='o';
                    snake_size++;
                    fruit_eaten = true;
                }
                else
                {
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                    snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=0;
                    board[0][y]='o';
                    board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                    snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
                }
            }
            else if (board[x+1][y] == 'o'){lose = true;}
            else if (x+1==fruit[0] && fruit[1]==y)
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x+1;

                board[x+1][y]='o';
                snake_size++;
                fruit_eaten = true;
            }
            else //(board[x][y+1] == ' ')
            {
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][1]=y;
                snake[(snake_tail+snake_size)%MAX_SNAKE_SIZE][0]=x+1;
                board[x+1][y]='o';
                board[ snake[snake_tail][0] ][ snake[snake_tail][1] ]=' ';
                snake_tail=(snake_tail+1)%MAX_SNAKE_SIZE;
            }
            break;
        }
    if(direction_size>1)
    {
        direction_size--;
        current_direction=(current_direction+1)%MAX_DIRECTIONS;
    }
    Sleep(speed);
    glutPostRedisplay();
}
//function to draw board
void draw_board_squares(void)
{
    glBegin(GL_LINES);
    for(int i=0; i<=BOARD_SIZE; i++)
    {
        glVertex2f(i*SQUARE_SIZE, 0);
        glVertex2f(i*SQUARE_SIZE, BOARD_SIZE*SQUARE_SIZE);
    }
        for(int j=0; j<=BOARD_SIZE; j++)
    {
        glVertex2f(0, j*SQUARE_SIZE);
        glVertex2f(BOARD_SIZE*SQUARE_SIZE, j*SQUARE_SIZE);
    }
    glEnd();
}
//function used to draw the snake
void draw_square(GLfloat x, GLfloat y)
{
    glBegin(GL_LINE_STRIP);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE,0);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,0);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,0);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE,0);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE,SQUARE_SIZE);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE,0);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE,0);
    glVertex3f(x*SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,0);
    glVertex3f(x*SQUARE_SIZE+SQUARE_SIZE,y*SQUARE_SIZE+SQUARE_SIZE,0);
    glEnd();


}
void init(void)
{
    glClearColor (0.0, 0.0, 0.0, 0.0);
    glMatrixMode(GL_MODELVIEW);
}
void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT);
    glPushMatrix();

    glRotatef(rot_x,1,0,0);
    glRotatef(rot_y,0,1,0);
    glTranslatef(trans_x, trans_y, trans_z);
    glColor3f(0.0f,0.0f,1.0f);
    draw_board_squares();
    glColor3f(0.0f,1.0f,0.0f);
    draw_square(fruit[0],fruit[1]);

    glColor3f(1.0f,0.5f,0.0f);
    for(int i=0; i<snake_size; i++)
    {
        //if(snake_tail+i < MAX_SNAKE_SIZE)
        draw_square(snake[(snake_tail+i)%(MAX_SNAKE_SIZE)][0],snake[(snake_tail+i)%(MAX_SNAKE_SIZE)][1]);
    }
    glPopMatrix();
    glutSwapBuffers();
}

void reshape (int w, int h)
{
    glViewport (0, 0, (GLsizei) w, (GLsizei) h);
    glMatrixMode (GL_PROJECTION);
    glLoadIdentity ();
    glFrustum(-0.05,1.0,-0.05,1.0,1.0,20);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef (0.0, 0.0, -5.0);
}

void keyboard (unsigned char key, int x, int y)
{
   switch (key) {
      case 's':
         if(direction[current_direction]!=UP)
         {
            direction[(current_direction+1)%MAX_DIRECTIONS] = DOWN;
            direction_size++;
            //move_snake();
         }
         break;
      case 'a':
         if(direction[current_direction]!=RIGHT)
         {
            direction[(current_direction+1)%MAX_DIRECTIONS] = LEFT;
            direction_size++;
            //move_snake();
         }
         break;
      case 'd':
         if(direction[current_direction]!=LEFT)
         {
            direction[(current_direction+1)%MAX_DIRECTIONS] = RIGHT;
            direction_size++;
            //move_snake();
         }
         break;
      case 'w':
         if(direction[current_direction]!=DOWN)
         {
            direction[(current_direction+1)%MAX_DIRECTIONS] = UP;
            direction_size++;
            //move_snake();
         }
         break;
      case 't':
         trans_y+=0.1;
         break;
      case 'f':
         trans_x-=0.1;
         break;
      case 'g':
         trans_y-=0.1;
         break;
      case 'h':
         trans_x+=0.1;
         break;
      case 'i':
         rot_x+=1;
         break;
      case 'j':
         rot_y-=1;
         break;
      case 'k':
         rot_x-=1;
         break;
      case 'l':
         rot_y+=1;
         break;
      case 'n':
         trans_z+=1;
         break;
      case 'm':
         trans_z-=1;
         break;
      case 'z':
         speed+=10;
         break;
      case 'x':
         if(speed>0)
         speed-=10;
         break;
      default:
         break;
   }
}

int main(int argc, char** argv)
{
    system("title Snake Game");
    printf("Use 'w', 'a', 's', 'd' keys to move the snake\n"
           "Use 't', 'f', 'g', 'h' keys to translate through the board\n"
           "Use 'n', 'm' keys to zoom\n"
           "Use 'i', 'j', 'k', 'l' keys to rotate the board\n"
           "Use 'z', 'x' keys for speed");
    for (int i = 0; i<BOARD_SIZE; i++ )
    {
        for(int j = 0; j <BOARD_SIZE ;j++){
            board[i][j] = ' ';
        }
    }
    board[1][1]='o';
    glutInit(&argc, argv);
    glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize (500, 500);
    glutInitWindowPosition (100, 100);
    glutCreateWindow ("Snake Game");
    init ();
    glutIdleFunc(move_snake);
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboard);
    glutMainLoop();
   return 0;
}







