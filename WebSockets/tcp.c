#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <memory.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <ctype.h>
#include <sys/types.h>
#include <sys/socket.h>

#include "tcp.h"



char *format_sockaddr(struct sockaddr *saddr, char *buffer) {
    struct sockaddr_in *sin = (struct sockaddr_in *)saddr;
    int ip, port;
    ip = ntohl(sin->sin_addr.s_addr);
    port = ntohs(sin->sin_port);
    sprintf(buffer, "<%u.%u.%u.%u, port %d>", 
	    (ip>>24) & 0xFF, (ip>>16) & 0xFF, 
	    (ip>>8) & 0XFF, ip & 0XFF , port);
    return buffer;
}
    
/*
 * Open a UDP socket for receiving messages.
 *
 * This is only left here for you to look at
 */
int udp_open(int local_port)
{
    int fd; 
    struct   sockaddr_in sin;
    char buffer[32];
  
    fd = socket(AF_INET, SOCK_DGRAM, 0);
    if (fd < 0) 
	die0("Error creating UDP socket");
  
    /* Bind the local socket to listen at the local_port. */
    printf ("Binding locally to port %d\n", local_port);
    memset((char *)&sin, 0, sizeof(sin));
    sin.sin_family = AF_INET;
    sin.sin_port = htons(local_port);
    if (bind(fd, (struct sockaddr *)&sin, sizeof(sin)) < 0) 
	die0("Bind failed");
    
    printf("UDP socket at %s configured\n",
	   format_sockaddr((struct sockaddr *)&sin, buffer));
    return (fd);
}

/*
 * Convert a DNS name or numeric IP address into an integer value
 * (in network byte order).  This is more general-purpose than
 * inet_addr() which maps dotted pair notation to uint. 
 */
struct sockaddr *get_sockaddr(const char *s, int port, struct sockaddr *saddr) {
    struct sockaddr_in *sin = (struct sockaddr_in *)saddr;
    int ip;
    if (isdigit(*s)) {
	ip = (unsigned int)inet_addr(s);
    } else {
	struct hostent *hp = gethostbyname(s);
	if (hp == 0)
	    die1("Can't translate %s to an address", s);
	ip = *((unsigned int **)hp->h_addr_list)[0];
    }
    memset((char *)sin, 0, sizeof(*sin));
    sin->sin_family = AF_INET;
    sin->sin_port = htons(port);
    sin->sin_addr.s_addr = ip;
    return saddr;
}

void die0(const char *msg) {
    fprintf(stderr, "%s\n", msg);
    exit(1);
}

void die1(const char *format, const char *msg) {
    fprintf(stderr, format, msg);
    fprintf(stderr, "\n");
    exit(1);
}

void diee(const char *msg) {
  perror(msg);
  exit(1);
}

/*
 * Connect to the given port on the given host, returning the connected
 * socket.  If there are any errors, print an error message and exit.
 */
int tcp_connect(char *hostname, int port) {
  //die0("tcp_connect: Not implemented");
    struct sockaddr *sockaddress = malloc(sizeof(struct sockaddr));
    struct sockaddr_in *my_address = malloc(sizeof(struct sockaddr));
    int sockfd;

    sockfd = socket(AF_INET,SOCK_STREAM,0);

    if (sockfd < 0) {
      printf("\nERROR opening socket");
      exit(1);
    }

    my_address->sin_family = AF_INET;
    my_address->sin_port = htons(0);
    my_address->sin_addr.s_addr = htonl(INADDR_ANY);
    bzero( &(my_address->sin_zero),8 );

    if( bind(sockfd ,(struct sockaddr *)my_address, sizeof(struct sockaddr))< 0 ){
      printf("\nERROR binding socket");
      exit(1);
    }

    get_sockaddr(hostname, port, sockaddress);

    if( connect(sockfd, sockaddress, sizeof(struct sockaddr)) < 0 ){
      printf("\nERROR connecting socket");
      exit(1);
    }

    return sockfd;
}
