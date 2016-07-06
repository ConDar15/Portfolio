#include "helper_functions.h"
int main(int argc, char **argv)
{
	if(argc == 3)
	{
		uint8_t *text, *key, *result;
		size_t n, m;
		n = decode_char(argv[1], &text);
		m = decode_char(argv[2], &key);
		result = xor_key(text, key, n, m);
		printf("%s\n", encode_16(result, n));
	}
	else
		printf("Precisely one text and one key must be provided\n");
}
