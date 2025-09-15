#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>

void main() {
  pid_t pid;
  
 
  pid = fork();
  printf("Hola, ¿soy el padre o el hijo?\n");
  sleep(20);
  printf("Mi pid es %d", getpid());
  exit(0);

}

