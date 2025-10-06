#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main() {
    const char *fifo = "canal_num";
    int numero;

    // Crear FIFO si no existe
    mkfifo(fifo, 0666);

    printf("Introduce un número entero: ");
    scanf("%d", &numero);

    // Abrir FIFO para escritura (bloquea hasta que el lector abra)
    int fd = open(fifo, O_WRONLY);
    if (fd == -1) {
        printf("escritor: error al abrir FIFO");
        exit(EXIT_FAILURE);
    }

    // Escribir el número en la FIFO
    if (write(fd, &numero, sizeof(numero)) == -1) {
        printf("escritor: error al escribir");
    } else {
        printf(" Número %d enviado al lector.\n", numero);
    }

    close(fd);
    return 0;
}
