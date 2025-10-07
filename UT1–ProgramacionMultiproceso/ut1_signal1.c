/* El programa muestra cómo capturar una señal del sistema (en este caso SIGINT) 
   y cómo asignarle un manejador personalizado -> función que se ejecuta cuando llega la señal*/

#include<stdio.h>
#include<unistd.h>
#include<signal.h>


/*
  fun_signal es la función manejadora (handler) de la señal.
   - El parámetro signum recibe el número de la señal (en este caso, SIGINT = 2).
   - Cuando el usuario pulsa Ctrl+C, en lugar de que el programa termine inmediatamente (comportamiento por defecto), se ejecuta esta función
  */

void fun_signal(int signum){
  printf("\n Se ha pulsado Ctrl+C \n");
  signal(SIGINT,SIG_DFL);   // Se "re-arma" la señal con su acción por defecto
}


int main(){
  signal(SIGINT,fun_signal); // Esta línea registra la función manejadora para la señal SIGINT. A partir de aquí, si se pulsa Ctrl+C, se ejecutará fun_signal()
  for(int i=1;;i++){    //Infinite loop
    printf("%d : Me voy a dormir ...\n",i);
    sleep(1);  // Delay for 1 second
  }
  return 0;
}