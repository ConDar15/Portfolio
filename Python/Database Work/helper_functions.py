from random import randint

def roll(roll_string):
	total = 0
	if '+' in roll_string:
		roll_string, bonus = roll_string.split('+')
		total = int(bonus)
	n, x = map(int, roll_string.split("d"))
	for i in range(n):
		total += randint(1,x)
	return total

def get_name(type):
	while True:
		try:
			name = input("Please %s input name: " % type)
			if name:
				return name
			else:
				raise Exception
		except KeyboardInterrupt:
			print("Aborted while setting name")
			raise KeyboardInterrupt
		except:
			print("Error while inputting name, try again")

def get_level(type):
	while True:
		try:
			roll = int(input("Please input %s level roll: " % type))
			bonus = int(input("Please input %s level bonus: " % type))
			return (roll, bonus)
		except KeyboardInterrupt:
			print("Aborted while setting level")
			raise KeyboardInterrupt
		except:
			print("Error while inputting level, try again")

def get_effect(type):
	while True:
		try:
			effect = input("Please input %s effect: " % type)
			if effect:
				return effect
			else:
				raise Exception
		except KeyboardInterrupt:
			print("Aborted while setting effect")
			raise KeyboardInterrupt
		except:
			print("Error while inputting level, try again")

def get_type():
	while True:
		try:
			c_type = input("Is the cypher anoetic? <y/n>: ")
			if c_type.upper() == "Y":
				return "Anoetic"
			elif c_type.upper() == "N":	
				return "Occultic"
			else:
				raise Exception
		except KeyboardInterrupt:
			print("Aborted while setting type")
			raise KeyboardInterrupt
		except:
			print("Error while inputting type, try again")

def get_forms(type):
	forms = []
	while True:
		try:
			form = input("Please input %s form: " % type)
			if form == "":
				return tuple(forms)
			forms.append((type, form))
		except KeyboardInterrupt:
			print("Aborted while setting %s forms" % type)
			raise KeyboardInterrupt
		except:
			print("Error while inputting form, try again")

def get_table_name(type):
	while True:
		try:
			table = input("Does the %s have an associated table? <y/n>: " % type)
			if table.upper() == "Y":
				table = input("Please input the %s's table name: ")
			elif table.upper() == "N":
				table = None
			else:
				raise Exception
			return table
		except KeyboardInterrupt:
			print("Aborted while setting table name")
			raise KeyboardInterrupt
		except:
			print("Error while inputting table name, try again")

def get_table(name):
	upper = 0
	table = []
	while True:
		while upper <= 100:
			try:
				lower = upper + 1
				n_upper = int(input("Input the next upper bound (between %03d and 100): " % (lower)))
				if n_upper < lower:
					raise ValueError
				if n_upper > 100:
					upper = 100
				upper = n_upper
				effect = input("Please input the effect details: ")
				table.append((name, effect, lower, upper))
			except KeyboardInterrupt:
				pass			
