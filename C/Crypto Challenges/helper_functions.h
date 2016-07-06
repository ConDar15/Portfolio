#ifndef HELPER_FUNCTIONS
	#define HELPER_FUNTIONS
	#include "standard_includes.h"
	uint8_t decode_16_char(const char);
	size_t decode_16(char*, uint8_t**);
	char *encode_64(const uint8_t*, const size_t);
	uint8_t *xor(const uint8_t*, const uint8_t*, const size_t);
	uint8_t *xor_1char(const uint8_t*, const size_t, const char);
	char *encode_16(uint8_t*, size_t);
	char *encode_char(uint8_t*, size_t);
	double score_text(const char*, const size_t);
	size_t decode_char(char*, uint8_t**);
	uint8_t *xor_key(const uint8_t*, const uint8_t*, 
					 const size_t, const size_t);
	uint8_t byte_weight(uint8_t);
	unsigned int hamming_distance (const uint8_t*, const uint8_t*,
								   const size_t);
	uint8_t decode_64_char(const char);
	size_t decode_64(char*, uint8_t**);
#endif 
