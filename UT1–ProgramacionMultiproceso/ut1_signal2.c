#include <stdio.h>
#include <unistd.h>
#include <signal.h>

/*
  signal_funcion es la función manejadora (handler) de la señal.
   - El parámetro signum recibe el número de la señal (en este caso, SIGUSR1).
 */
void signal_funcion(int signum){
  printf("He recibido la orden de dormirme 10 segundos ... \n");
  sleep(10);
}

int main(){
  pid_t pid;

  signal(SIGUSR1,signal_funcion); // Registro de la señal SIGUSR1

  printf("Soy un proceso con pid %d\n",getpid());
  pid=getpid();      //Se recupera el pid
  kill(pid,SIGUSR1);        // Se envía la señal SIGUSR1 a sí mismo
  printf("Terminación del proceso\n");
  return 0;
}