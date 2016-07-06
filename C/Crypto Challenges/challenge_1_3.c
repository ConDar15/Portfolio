#include "helper_functions.h"

int main(int argc, char **argv)
{
	if(argc == 2)
	{
		uint8_t *ct;
		size_t n = decode_16(argv[1], &ct);
		double bestv = 0.0, tv;
		char *bests = "", *ts, c=0, bestc;
		while(++c)
		{
			ts = encode_char(xor_1char(ct,n,c), n);
			tv = score_text(ts, n);
			if (tv > bestv)
			{
				bests = ts;
				bestv = tv;
				bestc = c;
			}
			else
				free(ts);
		}
		printf("Best Guess:\n\tChar: %c\n\tPlain-text: %s\n\tScore: %f\n", bestc, bests, bestv);
	}
	else
		printf("You must provide a hex string\n");
}
