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

int main(int argc, char **argv) {
  int port,i;	
  char *hostname;
  int sockfd, sent, received;
  char buffer[256];
  char *message="Mon Mar 17 08:37:14 2014";
  char *message2=malloc(sizeof(buffer));


  for(i=0; i<256;i++){
    buffer[i]=0;
  }
  if (argc < 3) {
    fprintf(stderr, "Incorrect number of arguments\n");
    exit(1);
  }	
  port = strtol(argv[1], 0, 0);	
  hostname = argv[2];
  
  time_t mytime;
  mytime = time(NULL);
  message = ctime(&mytime);

  sockfd = tcp_connect(hostname, port);

  printf("Sending Message...\n");

  sent = send(sockfd, message, strlen(message),0 );

  if(sent != strlen(message) )
    printf("ERROR: message not sent completely\n");
  
  printf("Message sent: %s", message);


  received = recv(sockfd, buffer, 255,0);

  printf("Receiving Message...\n");

  if(received<0){
    printf("ERROR: message not received\n");
    exit(1);
  }


  printf("Message received: %s\n", buffer);


  int k = atoi(buffer);
  k--;

  sprintf(message2, "%d", k);
  strcat (message2," My name is Carlos Wen\n");
  printf("Sending New Message...\n");

  sent = send(sockfd, message2, strlen(message2),0 );

  if(sent != strlen(message2) )
    printf("ERROR: message not sent completely\n");
  
  printf("Message sent: %s", message2);

  printf("Receiving New Message...\n");
  printf("Message received: ");
  
  for(i=0; i<256;i++){
    buffer[i]=0;
  }
  do{
    printf("%s", buffer);
    received = recv(sockfd, buffer, 255,0);

    if(received<0){
      printf("ERROR: message not received\n");
      exit(1);
    }

  }while(received!=0);

  printf("\n");

  shutdown(sockfd, SHUT_WR);
  return 0;
}
