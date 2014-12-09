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

char* concat(char *s1, char *s2)
{
    char *result = malloc(strlen(s1)+strlen(s2)+1);//+1 for the zero-terminator
    //in real code you would check for errors in malloc here
    strcpy(result, s1);
    strcat(result, s2);
    return result;
}

int main(int argc, char **argv) {
  int port, sockfd, sent, received;
  char *hostname;
  char buffer[1024];
  char *document;
  char *message;
  char message2[]="GET ";
  message = message2;
  
  port = strtol(argv[2], 0, 0);		
  hostname = argv[1];
  document= argv[3];

  if (argc < 4) {
    fprintf(stderr, "Incorrect number of arguments\n");
    exit(1);
  }	 
  sockfd = tcp_connect(hostname, port);
  message = concat(message, document);
  strcat (message, " HTTP/1.0\n");
  strcat (message, "Host: ");
  strcat (message, hostname);
  strcat (message, "\n\n");

  //sockfd = tcp_connect(hostname, port);
  printf("Message Sent: \n%s", message);
  
  printf("Sending Message...\n");
  sent = send(sockfd, message, strlen(message),0 );
  if(sent != strlen(message) )
    printf("ERROR: message not sent completely\n");
  printf("Message Sent: \n%s\n", message);


  received = recv(sockfd, buffer, 1024,0);
  printf("Receiving Message...\n");
  if(received<0){
    printf("ERROR: message not received\n");
    exit(1);
    }
  printf("Message received: \n%s\n", buffer);

  return 0;
}
