#include <sys/stat.h>
#include <stdio.h>

int main(void) {
    const char *fifo = "canal_num";
	int error;
	
    error=mkfifo(fifo, 0666);
	
	if (error==-1)
	{
       printf("El canal ya existe \n");
    } else {
       printf("Canal creado correctamente \n");
    }
    return 0;
}
