from Mathematical_Utilities import gcd
from itertools import combinations

class Stopwatch:
    """A class to record a time difference like a stopwatch"""
    #Implemented as I could not find a simple timer module in the python docs
    
    from datetime import datetime, timedelta
    
    def __init__(self,Start = False):
        self.Stopped = True
        self.Time = [None, self.timedelta()]
        if Start:
            self.start()
    
    def start(self):
        if self.Stopped == True:
            self.Time[0] = self.datetime.now()
            self.Time[1] = self.timedelta()
            self.Stopped = False

    def stop(self):
        if self.Stopped == False:
            self.Time[0] = self.datetime.now() - self.Time[0]
            self.Stopped = True

    def restart(self):
        if self.Stopped == False:
            self.Stopped = True
            self.start()

    def resume(self):
        if self.Stopped == True and self.Time != None:
            self.Time[1] += self.Time[0]
            self.Time[0]  = self.datetime.now()
            self.Stopped  = False

    def get_Time(self):
        if self.Time[0] == None:
            return(0,0,0)
        else:
            if self.Stopped == True:
                t = sum(self.Time,self.timedelta())
            else:
                t = (self.datetime.now() - self.Time[0]) + self.Time[1]
            return (t.days,t.seconds,t.microseconds)

    def display(self):
        print("{0} days, {1}.{2:06d}s".format(*self.get_Time()))

class Fraction:
    """A class to model fractions as 2 integers"""

    #Classes used to customise behaviour
    #Initialises the object when created
    def __init__(self,N,D = 1):
        self.set(N,D)
        
    #Called when any string representation of the fraction is needed
    def __repr__(self):
        if self.numerator == 0:
            return "0"
        if self.denominator == 1:
            return str(self.numerator)
        return("{0}/{1}".format(self.numerator, self.denominator))

    #Classes called for conversion to integer and floats
    def __int__(self):
        return self.numerator//self.denominator
    
    def __float__(self):
        return float(self.numerator/self.denominator)

    #Classes called to utilise the standard boolean logic operators    
    def __lt__(self,x):
        if isinstance(x,Fraction):
            return self.numerator/self.denominator < x.numerator/x.denominator
        else:
            return self.numerator/self.denominator < x

    def __le__(self,x):
        if isinstance(x,Fraction):
            return self.numerator/self.denominator <= x.numerator/x.denominator
        else:
            return self.numerator/self.denominator <= x

    def __eq__(self,x):
        if isinstance(x,Fraction):
            return self.numerator/self.denominator == x.numerator/x.denominator
        else:
            return self.numerator/self.denominator == x

    def __ne__(self,x):
        if isinstance(x,Fraction):
            return self.numerator/self.denominator != x.numerator/x.denominator
        else:
            return self.numerator/self.denominator != x

    def __gt__(self,x):
        if isinstance(x,Fraction):
            return self.numerator/self.denominator > x.numerator/x.denominator
        else:
            return self.numerator/self.denominator > x

    def __ge__(self,x):
        if isinstance(x,Fraction):
            return self.numerator/self.denominator >= x.numerator/x.denominator
        else:
            return self.numerator/self.denominator >= x

    #Classes called to utilise the standard arithmetic operators
    def __add__(self,x):

        if isinstance(x, Fraction):
            A = self.numerator * x.denominator + x.numerator * self.denominator
            B = self.denominator * x.denominator
            return Fraction(A, B)            
            
        elif isinstance(x, int):
            A = self.numerator + x * self.denominator
            B = self.denominator
            return Fraction(A, B)

        elif isinstance(x, float):
            return self + self.approximate(x)

    def __sub__(self,x):

        if isinstance(x, int) or isinstance(x, float):
            return self + (-x)

        elif isinstance(x, Fraction):
            return self + Fraction(-x.numerator, x.denominator)
    
    def __mul__(self,x):

        if isinstance(x, Fraction):
            A = self.numerator * x.numerator
            B = self.denominator * x.denominator
            return Fraction(A, B)

        elif isinstance(x, int):
            A = self.numerator * x
            B = self.denominator
            return Fraction(A, B)

        elif isinstance(x, float):
            return self * self.approximate(x)

    def __truediv__(self,x):
        
        if isinstance(x, Fraction):
            return self * Fraction(x.denominator,x.numerator)

        elif isinstance(x, int):
            return self * Fraction(1,x)

        elif isinstance(x, float):
            return self * Fraction(1/x)


    def __mod__(self,x):
            
        if self.numerator == 0:
            return Fraction(self.numerator, sefl.denominator)
        
        y = abs(self.numerator)//self.denominator//x
        A = abs(self.numerator) - x * y * self.denominator

        if self.numerator < 0:
            A = x * self.denominator - A
            
        B = self.denominator
        return Fraction(A, B)

    def __pow__(self,x):
        return Fraction(self.numerator**x, self.denominator**x)

    def __radd__(self,x):
        return self + x

    def __rsub__(self,x):
        return -(self - x)

    def __rmul__(self,x):
        return self * x

    def __rtruediv__(self,x):
        return Fraction(x) / self

    def __rpow__(self,x):
        return x**(self.numerator/self.denominator)

    #Class called for negation
    def __neg__(self):
        return Fraction(-self.numerator, self.denominator)

    #Class called for the abs() function
    def __abs__(self):
        return Fraction(abs(self.numerator), self.denominator)
   
    #Sets the values of the fraction
    def set(self,N,D = 1):
        """N can be an integer, Fraction or float, D must be an non - zero integer if given"""
       
        if D == 0:
            print("The denominator cannot be 0, denominator set to 1")
            D = 1
        
        if isinstance(N, Fraction):
            self.numerator   = N.numerator
            self.denominator = N.denominator

        elif isinstance(N, float):
            self.set(self.approximate(N))
           
        else:
            try:
                self.numerator   = int(N)
                self.denominator = int(D)
            except:
                print("Parameters could not be converted to integers, values of fraction set to 1/1")
                self.numerator = self.denominator = 1
                
        #Calls the simplify method to reduce fraction to optimal form
        self.simplify()

    #Approximates the first 6 decimal places of a floating point number
    def approximate(self,x):
        x *= 1000000
        x = int(x)
        f = Fraction(x,1000000)
        return f
    
    def simplify(self):
        """Puts the fraction into the simplest form possible"""

        #Utilises the gcd of the num and denom to reduce the fraction
        factor = gcd(self.numerator,self.denominator)
        self.numerator   //= factor
        self.denominator //= factor

class Bisection_Method:
    """Iterator for the bisection method"""
    def __init__(self,function,x0,x1,steps = 10,target = 0):
        self.function = function
        self.x0 = x0
        self.x1 = x1
        self.steps = steps
        self.target = target

    def set_Target(self,target):
        self.target = target

    def set_Lower(self,x0):
        self.x0 = x0

    def set_Upper(self,x1):
        self.x1 = x1

    def set_Steps(self,steps):
        self.steps = steps
        
    def __iter__(self):
        self.index = self.steps
        self.A = self.x0
        self.B = self.x1
        return self

    def __next__(self):
        if self.index == 0:
            raise StopIteration
        
        self.index -= 1
        x = (self.A + self.B)/2
        T = (self.steps - self.index, self.A, x, self.B, self.function(x))

        if self.function(x) - self.target < 0:
            self.A = x
        else:
            self.B = x
        return T

class BitString:
    def __init__(self, string):
        if not isinstance(string, str):
            raise TypeError("string argument must be of type str")
        if not self.__test(string):
            raise ValueError("The string must represent a binary bit stream")
        self.string = string

    def __len__(self):
        return len(self.string)
    
    def __test(self, string):
        v = set(['0','1'])
        for c in string:
            if c not in v:
                return False
        return True
    
    def __invert__(self):
            t = ''
            for c in self.string:
                t += c == '1'and '0' or '1'
            return BitString(t)

    def __eq__(self, obj):
        if isinstance(obj, BitString):
            return self.string == obj.string
        if isinstance(obj, str):
            return self.string == obj
        return False

    def __req__(self, obj):
        return self == obj

    def __and__(self, obj):
        if isinstance(obj, str):
            if not self.__test(obj):
                return False
            obj = BitString(obj)
        if isinstance(obj, BitString):
            if len(obj) != len(self):
                return False
            t = ''
            for p in zip(self.string, obj.string):
                t += '0' in p and '0' or '1'
            return BitString(t)

    def __rand__(self, obj):
        return self & obj

    def __or__(self, obj):
        if isinstance(obj, str):
            if not self.__test(obj):
                return False
            obj = BitString(obj)
        if isinstance(obj, BitString):
            if len(obj) != len(self):
                return False
            t = ''
            for p in zip(self.string, obj.string):
                t += '1' in p and '1' or '0'
            return BitString(t)

    def __ror__(self, obj):
        return self | obj

    def __iter__(self):
        for c in self.string:
            yield c

    def __getitem__(self, i):
        return self.string[i]

    def __str__(self):
        return self.string
    
    def __repr__(self):
        return 'BitString : ' + self.string

    def __hash__(self):
        return hash('BIT'+self.string+'STRING')
