#! /usr/bin/env python

# skewed-input.py - produces output consisting of a selected number of random
# words consisting of a's and b's, with length and proportion of a's
# controllable, but random.
#
# @author Matt Stallmann, 2010/08/25

import sys
import random

def usage():
    print "Usage ./skewed-input.py NUM_WORDS LF PA"
    print " where NUM_WORDS is the total number of words (one per line)"
    print "  - this is expressed as a power of 2"
    print " LF is a factor governing the lengths of words (0 <= WL < 1)"
    print "  the expected length of a word is (roughly) 2 / (1 - WL)"
    print "  larger LF means longer words"
    print " PA is the probability that a given letter is an 'a' (0 <= PA <= 1)"

# returns either 'a' or 'b' with equal probability
def random_letter( prob_a ):
    if random.random() < prob_a:
        return 'a'
    return 'b'

# returns a word (consisting of a's and b's) of the given length
def random_word( length_factor, prob_a ):
    word = ''
    # deal with special case (0 letters => 1 letter)
    if random.random() > length_factor:
        return word + random_letter( prob_a )
    while True:
        word += random_letter( prob_a )
        if random.random() > length_factor:
            return word

def main():
    if len( sys.argv ) != 4:
        usage()
        sys.exit()

    # convert number of words based on the fact that the command line gives
    # it as a power of two
    power = int(sys.argv[1])
    if power > 31:
        print "number of words is too large; recall that the argument is an exponent!"
        sys.exit()
    num_words = 1 << power;
    length_factor = float( sys.argv[2] )
    prob_a = float( sys.argv[3] )

    #
    for i in range( 0, num_words ):
        # add the 'x' so that it's possible to ask for only 1 distinct word,
        # i.e., a word length of 0
        print random_word( length_factor, prob_a )

main()
