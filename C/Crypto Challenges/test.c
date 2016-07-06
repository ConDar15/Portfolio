#include "helper_functions.h"
int main(int argc, char **argv)
{
	uint8_t *x;
	printf("%s\n", argv[1]);
	size_t n = decode_16(argv[1], &x);
	char *y = encode_64(x, n);
	printf("%s\n", y);
	n = decode_64(y, &x);
	y = encode_16(x, n);
	printf("%s\n", y);
}
