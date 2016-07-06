#include "helper_functions.h"

uint8_t decode_16_char(const char c)
{
	switch(c)
	{
		case '0':
			return 0; 
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
		case 'A':
			return 10;
		case 'b':
		case 'B':
			return 11;
		case 'c':
		case 'C':
			return 12;
		case 'd':
		case 'D':
			return 13;
		case 'e':
		case 'E':
			return 14;
		case 'f':
		case 'F':
			return 15;
		default:
			//The character passed must be a valid hex character
			printf("Character: %s\n", &c);
			assert(0); 
	}
}

size_t decode_16(char *in, uint8_t **out)
{
	//Asserts that the null pointer is not passed
	assert(in); 
	size_t n = strlen(in);
	//Asserts that a complete number of bytes is given
	//	as two hex digits are one byte.
	assert(!(n%2)); 
	uint8_t *res = malloc((n/2)*sizeof(*out)), *p = res;
	char *q = in;
	for(; q && *q; q+=2)
		*p++ = decode_16_char(*q) << 4 | decode_16_char(*(q+1));
	*out = res;
	//Returns the number of bytes converted
	return n/2;
}

char *encode_64(const uint8_t *restrict in, const size_t l)
{
	//Asserts that the null pointer is not passed
	assert(in);
	static const char *digit_64 = 
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	//Each 3 bytes produces 4 Base64 digits, thus l/3
	int i, n = l/3;
	char *restrict out = malloc((4*n + (l%3 ? 5 : 1))*sizeof(*out));
	for(i = 0; i < n; i++)
	{
		//All byte places are relative to a given starting byte

		//Converts the first 6 bits of the first byte
		out[4*i] = 
			digit_64[in[3*i] >> 2];
		//Converts the last 2 bits of the first byte and
		//	the first 4 bits of the second byte
		out[4*i + 1] = 
			digit_64[(in[3*i] & 3) << 4 | in[3*i+1] >> 4];
		//Converts the last 4 bits of the second byte and
		//	the first 2 bits of the third byte
		out[4*i + 2] =
			digit_64[(in[3*i+1] & 15) << 2 | in[3*i+2] >> 6];
		//Converts the last 6 bits of the thrid byte
		out[4*i + 3] =
			digit_64[in[3*i+2] & 63];
	}
	//Considers the cases for the remainder number of bytes when
	//	divided by 3
	switch(l%3)
	{
		//In the case that we have a multiple of 3 bytes
		case 0:
			out[4*n] = '\0';
			break;
		//In the case that there is a spare byte
		case 1:
			//Convert the last 2 Base64 digits as best as possible
			out[4*n] =
				digit_64[in[3*n] >> 2];
			out[4*n + 1] =
				digit_64[(in[3*n] & 3) << 4];
			//Add two padding digits
			out[4*n + 2] = '=';
			out[4*n + 3] = '=';
			out[4*n + 4] = '\0';
			break;
		//In the case that there are 2 spare bytes
		case 2:
			//Convert the last 3 Base64 digits as best as possible
			out[4*n] =
				digit_64[in[3*n] >> 2];
			out[4*n + 1] =
				digit_64[(in[3*n] & 3) << 4 | in[3*n+1] >> 4];
			out[4*n + 2] =
				digit_64[(in[3*i + 1] & 15) << 4];
			//Add a padding digit
			out[4*n + 3] = '=';
			out[4*n + 4] = '\0';
			break;
		default:
			assert(0); //HOW DID YOU GET HERE?!?!?!?!?!
	}
	return out;
}

uint8_t *xor(const uint8_t *restrict a, 
			 const uint8_t *restrict b, 
			 const size_t l)
{
	uint8_t *c = malloc(l*sizeof(*c));
	int i;
	for(i = 0; i < l; i++)
	{
		*(c+i) = *(a+i) ^ *(b+i);
	}
	return c;
}

uint8_t *xor_1char(const uint8_t *a, const size_t l, const char b)
{
	int i;
	uint8_t *c = malloc(l*sizeof(*c));
	for(i = 0; i < l; i++)
		c[i] = (uint8_t) b ^ a[i];
	return c;
}

char *encode_16(uint8_t *in, size_t l)
{
	//Asserts that the null pointer is not passed
	assert(in);
	char *res = malloc((2*l + 1)*sizeof(*res)), *p = res;
	static const char *digit_16 = "0123456789abcdef";
	while(l--)
	{
		*p++ = digit_16[*in >> 4];
		*p++ = digit_16[*in++ & 15];
	}
	*p = '\0';
	return res;
}

char *encode_char(uint8_t *in, size_t l)
{
	//Asserts that the null pointer is not passed
	assert(in);
	char *res = malloc((l + 1)*sizeof(*res)), *p = res;
	while(l--)
		*p++ = (char) *in++;
	*p = '\0';
	return res;
}

double score_text(const char *restrict text, const size_t n)
{
	//Counts characters as they are encountered
	unsigned int *count = calloc(26, sizeof(*count));
	//These frequencies represent the frequency of which letters
	//	appear in english text, FREQ[0] is the frequency of 'A'
	//	and from there on
	static const double FREQ[] = {0.0812, 0.0149, 0.0271, 0.0432,
								  0.1202, 0.0230, 0.0203, 0.0592,
								  0.0731, 0.0010, 0.0069, 0.0398, 
								  0.0261, 0.0695, 0.0768, 0.0182, 
								  0.0011, 0.0602, 0.0628, 0.0910,
								  0.0288, 0.0111, 0.0209, 0.0017, 
								  0.0211, 0.0007};
	char t;
	unsigned int i, spaces = 0, punct = 0, l = 0;
	double space_err, punct_err, letter_err = 0.0;
	//Cycles through and counts characters
	//As this is to be used in conjunction with xors and other
	//	cryptography techniques there may be null characters
	//  that are not at the end of the string, thus we need to
	//	use a known length of the string.
	for(i = 0; i < n; i++)
	{
		t = text[i];
		if(t == ' ')
			spaces++;
		else if(t >= 'a' && t <= 'z')
		{
			l++;
			count[t-'a'] ++; 
		}
		else if(t >= 'A' && t <= 'Z')
		{
			l++;
			count[t-'A']++;
		}
		else
			punct++;
	}
	//If no english letters were found then stop
	if(!l)
		return -100.0;
	//The absolute distance from the expected proportion of text that
	//	is spaces or punctuation.
	//About 15% of english text is spaces
	space_err = fabs((double)spaces/n - 0.15);
	//About 2% of english text is punctuation
	punct_err = fabs((double)punct/n - 0.02);
	//Calculates a similar error for the english letters in the text
	for(i = 0; i < 26; i++)
		letter_err += FREQ[i] * fabs((double)count[i]/l - FREQ[i]);
	//Returns the probability that the text is english language text
	return fmax(1.0 - (space_err + punct_err + letter_err), 0.0);
}

size_t decode_char(char *in, uint8_t **out)
{
	//Asserts that the in pointer is not null
	assert(in);
	//The simple case casts the pointer type as uint8_t and char have
	//	the same number of bits
	if(CHAR_BIT == 8)
	{
		*out = (uint8_t*)in;
		return strlen(in);
	}
	else
	{
		//Asserts that the number of bits in a char is divisible by 8
		assert(CHAR_BIT % 8 == 0);
		int i, n = CHAR_BIT/8;
		uint8_t *res = malloc(n*strlen(in)*sizeof(*res)), *q = res;
		char *p = in;
		//Does some bitshifting to break up chars into 8 bit sections
		for(;*p;p++)
			for(i = 0; i < n; i++)
				*q++ = (uint8_t)(*p >> (CHAR_BIT - 8*i) & 255);
		*out = res;
		return n*strlen(in);
	}
}

uint8_t *xor_key(const uint8_t *restrict a, 
				 const uint8_t *restrict b, 
				 const size_t al, const size_t bl)
{
	int i, j = -1;
	uint8_t *c = malloc(al*sizeof(*c)), *cp = c;
	for(i = 0; i < al; i++)
	{
		if(++j >= bl)
			j = 0;
		c[i] = a[i] ^ b[j];
	}
	return c;
}

uint8_t byte_weight(uint8_t a)
{
	a = (0x55 & a) + (0x55 & (a >> 1));
	a = (0x33 & a) + (0x33 & (a >> 2));
	a = (0x0f & a) + (0x0f & (a >> 4));
	return a;
}

unsigned int hamming_distance(const uint8_t *restrict a, 
					 		  const uint8_t *restrict b, 
					 		  const size_t l)
{
	unsigned int i, sum = 0;
	uint8_t *c = xor(a, b, l);
	for(i = 0; i < l; i++)
	{
		sum += byte_weight(c[i]);
	}
	return sum;
}

uint8_t decode_64_char(const char c)
{
	switch(c)
	{
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
			return c - 'A';
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			return c - 'a' + 26;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return c - '0' + 52;
		case '+':
			return 62;
		case '/':
			return 63;
		case '=':
			return 0x40;
		default:
			//The character must be a valid base 64 digit
			printf("Character: %c\n", c);
			assert(0);
	}
}

size_t decode_64(char *in, uint8_t **out)
{
	int n = strlen(in), i;
	size_t l;
	char flag = 0;
	assert(n % 4 == 0);
	l = (n / 4) * 3;
	*out = malloc(l*sizeof(**out));
	uint8_t a, b, c, d, *p = *out;
	for(i = 0; i < n;)
	{
		a = decode_64_char(in[i++]);
		b = decode_64_char(in[i++]);
		c = decode_64_char(in[i++]);
		d = decode_64_char(in[i++]);
		if(c == 0x40)
		{
			c = 0;
			d = 0;
			flag = 1;
			l -= 2;
		}
		else if(d == 0x40)
		{
			d = 0;
			flag = 2;
			l -= 1;
		}
		*p++ = a << 2 | (b >> 4) & 0x03;
		if(flag < 1)
			*p++ = (b & 0x0F) << 4 | (c >> 2) & 0x0F;
		else
			break;
		if(flag < 2)
			*p++ = (c & 0x03) << 6 | d;
		else
			break;
	}
	return l;
}
