#include <stdio.h>
#include "TimeUtility.hpp"

int main(void) {
   printf("Agora devem ser %d horas, %d minutos e %d segundos, espero eu.\n", 
      TimeUtility::getCurrentHours(), 
      TimeUtility::getCurrentMinutes(),
      TimeUtility::getCurrentSeconds());

   printf("Dia do ano: %d\n", TimeUtility::getCurrentDayOfYear());
   
   printf("Ano: %d\n", TimeUtility::getCurrentYear());

   return 0;
}
