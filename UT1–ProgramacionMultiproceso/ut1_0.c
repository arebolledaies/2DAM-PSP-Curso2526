#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
void main()
{
  for (int i=10;i<=100;i=i+5)
  {
   printf("Me voy a dormir %d segundos \n",i);
   sleep(i);
   }
   printf("\nFin de programa....\n");
   exit(0);
}
