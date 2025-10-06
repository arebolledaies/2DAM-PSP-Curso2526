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

    printf("Esperando número del escritor...\n");

    // Abrir FIFO para lectura (bloquea hasta que el escritor escriba)
    int fd = open(fifo, O_RDONLY);
    if (fd == -1) {
        printf("lector: error al abrir FIFO");
        exit(EXIT_FAILURE);
    }

    // Leer el número enviado
    if (read(fd, &numero, sizeof(numero)) == -1) {
        printf("lector: error al leer");
        close(fd);
        exit(EXIT_FAILURE);
    }

    printf("Número recibido: %d\n", numero);
    if (numero % 2 == 0)
        printf("%d es un numero PAR.\n",numero);
    else
        printf("%d es un numero IMPAR.\n",numero);

    close(fd);
    return 0;
}
