My slightly profane and crass notes for Advent of Code Problems.

Nothing I put here should be taken as a critizism of the contest itself,
which is amazingly well done and awesome.

# 2021 

## Day 1 - Sonar Sweep

### Part 1

Can you compare some numbers?  

### Part 2

How about a rolling sequence of numbers?

## Day 7 - The Treachery of Whales

Align some crabs to explode some holes.

Basic loops and some adding.

## Day 8 - Seven Segment Search

How much can you over complicate and inductive logic?  A lot?  I bet it is a lot.

## Day 9 - Lava Tubes

You have a field of numbers.  Find low points.

### Part 1 

Find the low points.

### Part 2

Find the area of basins that feed into the low points.  The problem statement eleminates the hard bits for you.

## Day 10 - Syntax Scoring

XKCD - https://xkcd.com/297/

### Part 1

Can you use a stack as you parse?

### Part 2

Can you break your above code and record different numbers?
(also, remember that int sucks, and long rules)

## Day 11 - Dumbo Octopus

Conways game of life with some modifications.  Normally you do conways
with two maps, before and after.  And you base after only on before to avoid
tripping yourself up.  With this one, the beasts can chain flash so you can't do that.

So the easy way is to have some special values for things that have flashed and can't
flash anymore. I used -2.

### Part 1

Run some cycles.

### Part 2

Run some cycles until they all flash.

Easy as eating pancakes.

## Day 12 - Passage Pathing

Basic recusive path counting with some funny rules.
Used memoization, but didn't actually need it.

## Day 13 - Transparent Origami

Some simple folding points around in a 2d map.
Thank fuck there were no rotations.
I always fuck up rotations.

## Day 14 - Extended Polymerization

This was a fun one.  It comes down to a memoized recursive count.


# 2020

## Day 16 - Ticket Translation

Can you manage to use some range rules to determine what things maps to what things.
Then repeat that as you reduce the number of outstanding things.

## Day 17 - Conway Cubes

### Part 1
Can you do Conway's game of life in 3d?  Easy, just don't screw up the neighbors.

### Part 2
What about 4D?  Ha ha ha.  Don't worry about visualizing it.  Also, debugging is impossible.
Don't write bugs.


## Day 18 - Operation Order

Can write an expression processor for some bullshit math rules.  Also, use longs.

## Day 19 - Monster Messages

Recursive decent parser.  Or something.



## Day 20 - Jurassic Jigsaw

In this problem you have a bunch of 2d map tiles that you need to stitch together
where they match up.

### Part 1

Can you rotate and flip?  Can you do a recursive search?  Not super hard but lots of details.

### Part 2

Now do some more silly modifications and look for given pattern.

## Day 21 - Allergen Assessment

Use logic to make conclusions about mappings of items to alergens.

### Part 1 

Do some basic logic.

### Part 2

Find the full mapping.  If you did part 1 in a reasonable way, this is easy.


## Day 22 - Crab Combat

Crab war cards.

### Part 1

Can you follow basic rules. 

### Part 2

Can you manage some oddly phrased recusion rules.

## Day 23 - Crab Cups

Those crabs want to play some weird cup game.

### Part 1

Can you manipulate a linked list of things with some sort of cursor and add and remove
things without screwing up?

### Part 2

Times 10 million because fuck your cpu and your shit ArrayList implementation



## Day 24 - Lobby Layout

Some hex grid for a hotel lobby.

### Part 1 

Can you walk a hex grid without tripping on own face?

### Part 2

Now play conways game of life on it.

## Day 25 - Combo Breaker

Some weird public key RFID thing.  Basically do pow-mod a bunch
or don't.  Whatever.

### Part 1

Can you write a for loop?  You think it is complicated, but it isn't.

### Part 2

Did you get all the stars and can you press a button?
It took me almost three minutes to press the button.


# 2018

## Day 19 - Go With The Flow 

This is a run this assembly code problems.

### Part 1
I don't even know.  Part 1 is easy, you run the program and it exits.

### Part 2

This thing runs forever.  It is counting to some number of millions over and over again.
You can skip some, but some of them cause magic to happen.  I still don't understand it.
It has something to do with the factors of some of the inputs and multipling the factors.

Don't ask me.


## Day 21 - Chronal Conversion

Wherein you run a small bit of assembly like code to figure out what inputs cause the program to terminate
and what inputs cause it to run forever (or just a long time).

I spent a long time being stupid about this one.  Then I realized, it doesn't read the input!
At all!  Well, except one compare and if it is equal, then it exits.

So you just run it and when it does the compare operation that does read the input, write down the value it compared.

### Part 1

Return the first value that is compared to the "input"

### Part 2

Keep running the problem, saving each value compared as it runs.  The last new value is the winner and you are done
when you get a repeat.


## Day 24 - Immune System Simulator 20XX

Basically, two problems.  A parsing problem and a simulation problem.
Fairly straight forward.

### Part 1

Wheeeeeee

### Part 2

Wrinkle here is there can be ties where neighter army can damage the other.  Also, do it a lot.

# 2017

## Day 1 - Inverse Captcha

hello string

## Day 2 - Corruption Checksum

hello lists with a little bit of mod

## Day 3 - Spiral Memory

Fun with spirals.  The trick here is to keep track of the direction you are moving
as you write it out.

## Day 4 - High-Entropy Passphrases

List -> set 
list.size == set.size (part 1)
list -> set of sorted strings (part 2)

## Day 5 - A Maze of Twisty Trampolines, All Alike

Problem involving jumping around.  Just requires careful reading of the statement
and tracking of things.


