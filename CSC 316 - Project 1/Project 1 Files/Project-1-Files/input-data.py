#! /usr/bin/env python

# input-data.py - produces output consisting of a selected number of random
# words, with a selected number of them being distinct.
#
# @author Matt Stallmann, 2010/06/28

import sys
import random

def usage():
    print "Usage python input-data.py NUM_WORDS NUM_DISTINCT"
    print " where NUM_WORDS is the total number of words (one per line)"
    print " and NUM_DISTINCT is the number of distinct words"
    print "Both numbers are expressed as powers of 2"

# returns either 'a' or 'b' with equal probability
def random_letter():
    if random.random() < 0.5:
        return 'a'
    return 'b'

# returns a word (consisting of a's and b's) of the given length
def random_word( length ):
    word = ''
    for i in range( 0, length ):
        word = word + random_letter()
    return word

def main():
    if len( sys.argv ) != 3:
        usage()
        sys.exit()

    # convert number of words based on the fact that the command line gives
    # it as a power of two
    num_words = 1 << int( sys.argv[1] )
    # there are 2^n words consisting of n a's and b's
    word_length = int( sys.argv[2] )

    #
    for i in range( 0, num_words ):
        # add the 'x' so that it's possible to ask for only 1 distinct word,
        # i.e., a word length of 0
        print "x" + random_word( word_length )

main()
