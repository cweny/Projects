 #include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <memory.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <ctype.h>
#include <time.h>

#include "tcp.h"
int parse(char *string){
	    int num1 = strtol(string,0,10);
	    int i=0;
	    int test=num1;
	    while(test/10!=0){
	      test=test/10;
	      i++;
	    }
	    char operator = string[i+1];
	    int num2 = strtol(string+i+2,0,10);
	    int result;
	    printf("operator:%c, num1:%d, num2:%d\n", operator, num1,num2);
	    switch(operator){
	    case '+':
	      result = num1+num2;
	      break;
	    case '-':
	      result = num1-num2;
	      break;
	    case '*':
	      result = num1*num2;
	      break; 
	    case '/':
	      result = num1/num2;
	      break;
	    default:
		break;
		}
  return result;
}
int main(int argc, char **argv) {
  //struct sockaddr *sockaddress = malloc(sizeof(struct sockaddr));
    struct sockaddr_in *server_address = malloc(sizeof(struct sockaddr));
    struct sockaddr_in *client_address = malloc(sizeof(struct sockaddr));
    int sockfd, new_sockfd, receive, sent;
    socklen_t clilen;
    char buffer[256];
    long port;
    sockfd = socket(AF_INET,SOCK_STREAM,0);

    if (sockfd < 0) {
      printf("\nERROR opening socket");
      exit(1);
    }
    if (argc < 2) {
      port = 2000;
    }	
    else
      port = strtol(argv[1], 0, 0);	
    server_address->sin_family = AF_INET;
    server_address->sin_port = htons(port);
    //server_address->sin_addr.s_addr = htonl(INADDR_ANY);
    bzero( &(server_address->sin_zero),8 );

    if( bind(sockfd ,(struct sockaddr *)server_address, sizeof(struct sockaddr))< 0 ){
      printf("\nERROR binding socket");
      exit(1);
    }
    listen(sockfd,5);
    clilen = sizeof(client_address);
    while((new_sockfd = accept(sockfd, (struct sockaddr *)client_address, &clilen))>=0){
      if (new_sockfd < 0) {
        perror("\nERROR on accept");
        exit(1);
      }
      bzero(buffer,256);
      receive = read( new_sockfd,buffer,255 );
      if (receive < 0) {
        perror("\nERROR reading from socket");
        exit(1);
      }
      printf("\nHere is the message:\n%s",buffer);
      /*
	if(buffer[0]=="G" && buffer[1]=="E" && buffer[2]=="T" ) {
	printf("Right message\n");
	}*/
      int i=0;


      while(!(buffer[i]=='G' && buffer[i+1]=='E' && buffer[i+2]=='T' ) && i<strlen(buffer)){
	i++;
      }
      if(i>=strlen(buffer)){
	time_t mytime;
	mytime = time(NULL);
	char error[]="HTTP/1.0 400 Bad Request\n";
	strcat (error, ctime(&mytime));
        strcat (error, "Server: Carlos's fake web server\n");
	strcat (error, "Content-Type: text/html\n");
	sent = send(new_sockfd, error, strlen(error),0 );
	if(sent != strlen(error) )
	  printf("ERROR: message not sent completely\n");
        else
	  printf("Message Sent: \n%s\n", error);
      }
      else{ 
	  const char* from = buffer+i+4;
	  char *to = (char*) malloc(11);
	  strncpy(to, from, 11);
	  printf("Messaget: \n%s\n", to);

	  const char* from2 = buffer+i+4;
	  char *to2 = (char*) malloc(16);
	  strncpy(to2, from2, 16);
	  printf("Messaget: \n%s\n", to2);

	  if(strcmp(to, "/index.html")==0){
	    time_t mytime;
	    mytime = time(NULL);
	    char message[]="HTTP/1.0 200 OK\n";
	    strcat (message, ctime(&mytime));
	    strcat (message, "Server: Carlos's fake web server\n");
	    strcat (message, "Content-Type: text/html\n");
	    strcat (message, "\n<!DOCTYPE html>\n<html>\n<body bgcolor=\"white\">\n<p>This is a project for CPSC 261 at UBC.</p>\n</body>\n</html>\n");
	    sent = send(new_sockfd, message, strlen(message),0 );
	    if(sent != strlen(message) )
	      printf("ERROR: message not sent completely\n");
	    else
	      printf("Message Sent: \n%s\n", message);
	  }
	  else if(strcmp(to2, "/calculate.html?")==0){
	    //printf("char:%c\n", *(buffer+i+20));
	    //buffer = buffer+i+20;
	   
	    time_t mytime;
	    mytime = time(NULL);
	    char message[]="HTTP/1.0 200 OK\n";
	    strcat (message, ctime(&mytime));
	    strcat (message, "Server: Carlos's fake web server\n");
	    strcat (message, "Content-Type: text/html\n");
	    strcat (message, "\n<!DOCTYPE html>\n<html>\n<body bgcolor=\"white\">\n<p>");
	    /*char buffer[256];
	    snprintf(buffer, 10, "%d", num1);
	    strcat (message,buffer);
	    switch(operator){
	    case '+':
	      strcat (message, "+");
	      break;
	    case '-':
	      strcat (message, "-");
	      break;
	    case '*':
	      strcat (message, "*");
	      break;
	    case '/':
	      strcat (message, "/");
	      break;
	    default:
		break;
	    }
	    snprintf(buffer, 10, "%d", num2);
	    strcat (message,buffer);
	    strcat (message, "=");
	    snprintf(buffer, 10, "%d", result);
	    strcat (message,buffer);*/
	    int k=i+20;
	    while(buffer[k]!= ' '){
	      k++;
	    }
	    k=k-(i+20);
	    const char* from3 = buffer+i+20;
	    char *to3=malloc(256);
	    strncpy(to3, from3, k);
	    to3[k]='\0';
	    strcat (message, to3);
	    //printf("char:%c\n", *(buffer+i+20));
	    strcat (message, "=");
	    char buf[20];
	    snprintf(buf, 10, "%d", parse(to3));
	    strcat (message, buf);

	    strcat (message, "</p>\n</body>\n</html>\n");
	    sent = send(new_sockfd, message, strlen(message),0 );
	    if(sent != strlen(message) )
	      printf("ERROR: message not sent completely\n");
	    else
	      printf("Message Sent: \n%s\n", message);
	  }
	  else{
	    time_t mytime;
	    mytime = time(NULL);
	    char error[]="HTTP/1.0 400 Bad Request\n";
	    strcat (error, ctime(&mytime));
	    strcat (error, "Server: Carlos's fake web server\n");
	    strcat (error, "Content-Type: text/html\n"); 
	    strcat (error, "\n<!DOCTYPE html>\n<html>\n<body bgcolor=\"white\">\n<p>Error! File not found.</p>\n</body>\n</html>\n");
	    sent = send(new_sockfd, error, strlen(error),0 );
	    if(sent != strlen(error) )
	      printf("ERROR: message not sent completely\n");
	    else
	      printf("Message Sent: \n%s\n", error);
	  }
      }
    }
    shutdown(sockfd, SHUT_WR);
    shutdown(new_sockfd, SHUT_WR);
  return 0; 
}
