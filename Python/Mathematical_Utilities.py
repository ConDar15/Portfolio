from math import *
from itertools import count
import sys

def is_Prime(n):
    """Returns true or false if n is prime or not"""
    if n <= 1:
        return False
    else:
        for a in range(2,int(n**0.5) + 1):
            if n % a == 0:
                return False
    return True

def divisor_Pairs_I(n):
    """Generator that returns all divisor pairs of n"""
    for i in range(1,int(n**0.5) + 1):
        if n%i == 0:
            yield (i,n//i)

def divisor_Pairs(n):
    """Returns a list of all divisor pairs of n"""
    return list(divisor_Pairs_I(n))

factorial = lambda n: None if n < 0 else product(range(2,n+1))

def prime_Decomp(n):
    """Returns the prime decomposition of n as a 2D list of factors and multiplicity"""
    decomp = [[],[]]
    factors = []
    powers  = []

    if is_Prime(n):
        decomp = ((n,),(1,))

    else:   
        for p in get_Primes_I(int(n/2)):
            if n % p == 0:
                c = 1
                while n % p**(c+1) == 0:
                    c += 1
                factors.append(p)
                powers.append(c)
                n /= p**c
            if n == 1:
                break
                
        decomp = (tuple(factors),tuple(powers))
    return decomp

def get_Primes_I(N):
    """Gets an iterator of all primes up to N"""
    #Utilises the Sieve of Eratosthenes to find the primes
    A = [True]*(N+1)
    for n in range(2,N+1):
        if A[n]:
            yield n
            for x in range(n*n,N+1,n):
                A[x] = False
            
def get_Primes(N):
    """Get a list containing all primes up to N"""
    #Returns a list of the iterator above
    return list(get_Primes_I(N))

def is_Pandigital(X,zero = False,n = None):
    """Tests whether X is n pandigital"""
    if zero == True:
        z = 0
    else:
        z = 1
    if n == None:
        n = len(X)
    else:
        if z == 0:
            n += 1
    return set(str(X)) == set([str(i+z) for i in range(n)])

def transpose(A):
    """Transposes the 2D matrix given"""
    return [list(i) for i in zip(*A)]

def determinant(A):
    """Returns the determinant of the square matrix given"""
    if len(A) == 1:
        return A[0][0]
    else:
        return sum([A[0][i]
                    *determinant
                    ([A[j+1][:i] + A[j+1][i+1:]for j in range(len(A) - 1)])
                    *(-1)**i
                    for i in range(len(A))])

def invert(A):
    """Performs mathematical inversion of the 2D square matrix given"""
    Det_A = determinant(A)
    if Det_A != 0:
        C = [[determinant([A[j][:i] + A[j][i+1:]
                             for j in range(len(A)) if j != r])
              *(-1)**(i+r) for i in range(len(A))] for r in range(len(A))]
        return [[c/Det_A for c in row] for row in transpose(C)]
    else:
        return None

def mat_Mult(A,B):
    """Performs A*B (which != B*A in all cases) and returns None if they can't be multiplied"""
    if len(A[0]) == len(B):
        return[[sum([A[i][k]*B[k][j] for k in range(len(B))])
                for j in range(len(B[0]))]
               for i in range(len(A))]
    else:
        return None

#Tests for figurate numbers, i.e. Triangular, Pentagonal, etc and the general Figurate case
is_triangular = lambda T: (-1+(1+8*T)**0.5)/2 % 1 == 0
is_pentagonal = lambda P: (1+(1+24*P)**0.5)/6 % 1 == 0
is_hexagonal  = lambda H: (1+(1+8*H) **0.5)/4 % 1 == 0
is_Figurate = lambda F,n: ((n-4)+((4-n)**2 + (n-2)*8*F)**0.5)/(2*(n-2)) % 1 == 0

is_Palindrome = lambda n: str(n) == str(n)[::-1]

gcd = lambda a,b: a < b and gcd(b,a) or (a%b != 0 and gcd(b,a%b) or b)

def gcd_many(a,b,*args):
    """Finds the gcd of 2 or more numbers"""
    x = gcd(a,b)
    for i in args:
        x = gcd(x,i)
    return x

def product(iterable,start = 1):
    """Multiplies all values in an iterable, faster than recursion for some cases"""
    result = start
    for i in iterable:
        result*=i
    return result

def euler_Tot_Func(n):
    """Calculates the Euler Totient Function for a given value"""
    if is_Prime(n):
        return(n-1)
    P = []
    t = n
    for p in get_Primes_I(n):
        if t%p == 0:
            P += [p]
            while t%p == 0:
                t/=p
        if t == 1:
            break
    return (n*product((p-1 for p in P)))/product(P)

def partition_Function(n):
    """Calculates the number of distinct partitions that n objects can form"""
    #This dictionary is maintained across multiple runs, it is used for efficiency purposes
    D = partition_Function.Dictionary
    if n in D:
        return D[n]
    if n < 0:
        return 0
    if n == 0 or n == 1:
        return 1
    L = []
    for k in count(1):
        x = k*(3*k-1)//2
        y = x + k
        s = (-1)**(k-1)
        if x > n and y > n:
            break
        try:
            L += [s*partition_Function(n-x), s*partition_Function(n-y)]
        except RuntimeError:
            sys.setrecursionlimit(sys.getrecursionlimit() + 1000)
            L += [s*partition_Function(n-x), s*partition_Function(n-y)]
    D[n] = sum(L)
    return(sum(L))
#Initialisation of the dictionary for the above function
partition_Function.Dictionary = {}

def root_digits(n):
    """Outputs the digits of a square root one by one accurately as an iterator"""
    #Algorithm details can be found online, just trust me that this works
    
    #This generates the next digit in the root
    #Utilises nonlocal variables to avoid excess passing of variables
    def next_digit(num = 0):
        nonlocal c, p, x, y, Digits
        c = 100*(c-y) + num
        if p == 0:
            x = int(c**0.5)
        else:
            x = c//(20*p)
        while (x)*(20*p + x) > c:
            x -= 1
        y = x*(20*p+x)
        p = p*10 + x
        return Digits[x]
    
    #The four lines below convert n into left and right hand side strings of even length
    N = str(n)
    A,*B = N.split(".")
    A = "0"*(len(A)%2) + A
    B = B[0] + "0"*(len(B[0])%2) if B != [] else ""
    #The four lines below convert n into left and right hand side (of the decimal point)
    #    strings of even length
    L = map(int,map(lambda i: A[i:i+2],range(0,len(A),2)))
    R = map(int,map(lambda i: B[i:i+2],range(0,len(B),2)))
    y = p = c = x = 0
    #Note on variables:
    #   p = the number formed by the digits already found
    #   c = the current value used by the algorithm to find x
    #   x = the next digit to be found
    #   y = the value of (x*20+p)*x (needed for algorithm)
    
    #Dictionary used to prevent having to call the str function repeatedly
    Digits = {int(c):c for c in "0123456789"}
    
    for l in L:
        yield next_digit(l)

    if N.find(".") != -1 or c-y != 0:
        yield "."
    
    for r in R:
        yield next_digit(r)

    while c - y != 0:
        yield next_digit()

def get_root_digits(x,n=10):
	"""Returns the first n root digits of x as a string"""
	R = []
	c = 0
	n += 1
	
	#Goes through the digits of root(x) that the algorithm spits out
	for d in root_digits(x):
		R.append(d)
		#Break condition, for when the number of digits is reached
		#Loops for n+1 times to include the decimal point
		if c >= n:
			break
		c+=1

	#Returns the string of the digits
	return "".join(R)

def fibonacci_sequence(N = -1):
    """Fibonacci sequence iterator. Returns first N terms if N is given"""
    a = 0
    b = 1
    c = N
    while c != 0:
        yield b
        a,b = b,a+b
        c -= 1

def get_fibonacci_sequence(N):
    """Return a list of the first N fibonacci numbers"""
    return list(fibonacci_sequence(max(N,0)))

def get_poly(co_eff):
    """Returns a function that returns the value of a polynomial given by the co_eff parameter at a given x value"""
    return lambda x : sum([co_eff[i] * x **i for i in range(len(co_eff))])

#Returns a list which is the difference between each successive term in the input
diff = lambda a : [a[i+1] - a[i] for i in range(len(a) - 1)]

def fit(v, ret_func = True):
    """For a sequence of values v it gives the smallest powered polynomial (p) s.t. p(i+1) = v[i]"""
    #Only terminates if such a finite plynomial exists
    N = len(v)
    c = [0 for i in range(N)]
    P = get_poly(c)
    x = [v[n] - P(n+1) for n in range(N)]
    while not len(set(x)) == 1:
        t = sub_fit(diff(x), N - 1)
        c = [c[0]] + [c[i] + t[i-1]//i for i in range(1,N)]
        P = get_poly(c)
        x = [v[n] - P(n+1) for n in range(N)]
    c[0] = x[0]
    return (ret_func and [get_poly(c)] or [c])[0]

def sub_fit(v, d):
    """Used to recursively solve the fit function"""
    c = [0 for n in range(d)]
    if len(set(v)) == 1:
        c[0] = v[0]
    else:
        t = sub_fit(diff(v), d - 1)
        c = [c[0]] + [c[i] + t[i-1]//i for i in range(1,d)]
    return c

def get_graph():
    """Gets a undirected graph from the user as input of number of vertices and edges.
Each edge can appears at most once in the output"""
    V = set(range(1, max(1,int(input("Please input the number of verticies on the graph (minimum 1): ")) + 1)))
    E = set()
    while True:
        try:
            x = int(input("First vertex of edge: "))
            y = int(input("Second vertex of egdge: "))
            if not {x,y}<V:
                print("Error: Invalid edge, one or more of the verticies do not exist, please try again.")
                continue
            E |= {frozenset([x,y])}
            print()
        except:
            break
    return (V,E)

def is_cover(C,G):
    V,E = G
    empty = set()
    for e in E:
        if e&C==empty:
            return False
    return True
