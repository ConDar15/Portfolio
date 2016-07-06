#include "helper_functions.h"

int main(int argc, char **argv)
{
	if(argc == 3)
	{
		uint8_t *a, *b;
		int n = decode_16(argv[1], &a), m = decode_16(argv[2], &b);
		if(n == m)
			printf("%s\n", encode_16(xor(a, b, n), n));
		else
			printf("The lengths of the strings do not match\n");
	}
	else
		printf("Incorrect number of arguments\n");
}
