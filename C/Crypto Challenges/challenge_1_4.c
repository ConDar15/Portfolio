#include "helper_functions.h"
int main(int argc, char **argv)
{
	if(argc > 1)
	{
		int i;
		char line[60], *bests, *opts = 0, bestc, optc = 0, c, *s;
		uint8_t *val;
		size_t n;
		double optv = 0, bestv, v;
		for(i = 1; i < argc; i++)
		{
			FILE *fp = fopen(argv[i], "r");
			while(fscanf(fp, "%s", line) != EOF)
			{
				n = decode_16(line, &val);
				c = 0;
				bestv = 0.0;
				bestc = 0;
				bests = 0;
				while(++c)
				{
					s = encode_char(xor_1char(val, n, c),n);
					v = score_text(s, n);
					//if(line[0] == '3' && line[1] == 'f' &&
					   //line[2] == '1' && line[3] == 'b')
						//printf("%c :: %s :: %f\n", c, s, v);
					if(v > bestv)
					{
						bestv = v;
						bestc = c;
						//free(bests);
						bests = s;
					}
					//else
					//	free(s);
				}
				//printf("%s : %s\n", line, bests);
				if(bestv > optv)
				{
					optv = bestv;
					optc = bestc;
				//	free(opts);
					opts = bests;
				}
				//else
				//	free(bests);
			}
			fclose(fp);
			printf("File '%s' best guess:\n\tPlain-Text:"
				   "%s\n\tKey:%c\n\tScore:%f\n", 
				   argv[i], opts, optc, optv);
		}
	}
}
