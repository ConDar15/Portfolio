#include "helper_functions.h"

int main(int argc, char** argv)
{
	if(argc >= 2)
	{
		int i;
		size_t n;
		uint8_t *p;
		for(i = 1; i < argc; i++)
		{
			printf("\nInput: %s\n", argv[i]);
			n = decode_16(argv[i], &p);
			printf("Base 64: %s\n", encode_64(p,n));
		}
	}
	else
		printf("Must provide at least one argument");
}
	
