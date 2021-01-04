import json
import requests
from cmd import Cmd
from datetime import date
import base64



#username = "***REMOVED***"
username = "***REMOVED***"
password = "***REMOVED***"
userid = None
BASE_URL = '***REMOVED***'
#BASE_URL='http://0.0.0.0:8888'
headers = {'Content-Type': 'application/json'}
session = None
token = None
roles = set()
logedin = False
userid = None


class LoginDto(object):
	def __init__(self, username, password):
		self.username = username
		self.password = password

	def toJson(self):
		return json.dumps(self, default=lambda o: o.__dict__)


class LendingDto(object):
	def __init__(self, startDate, mediumCopyId, userId, renewalCount):
		self.startDate = startDate
		self.mediumCopyId = mediumCopyId
		self.userId = userId
		self.renewalCount = renewalCount
	
	def toJson(self):
		return json.dumps(self, default=lambda o: o.__dict__)

		
def login(username, password, url):
	glbals = globals()
	
	loginDto = LoginDto(username=glbals['username'], password=glbals['password'])
	payload = loginDto.toJson()
	with requests.Session() as s:
		try:
			response = s.post(glbals['BASE_URL']+"/login", headers=glbals['headers'], data=payload)
			response.raise_for_status()
			jresp = response.json()
			glbals['session'] = s
			glbals['token'] = jresp["access_token"]
			glbals['userid'] = getUserID(jresp["access_token"])
			response.raise_for_status()
			jresp = response.json()
			glbals["logedin"] = True
			for role in jresp["roles"]:
				glbals['roles'].add(role)
		except Exception as err:
			print(err)
			
def serachMedium(medium):
	glbals = globals()
	try:
		r = requests.get(glbals['BASE_URL']+"/search/"+medium)
		print(json.dumps(json.loads(r.text), indent = 1))

	except Exception as err:
		print(err)
		
def lendAMedium(mediumtype, mediumId):
	glbals = globals()
	#copyID = getCopyIdOfMedium(mediumId, mediumtype)

	print("copy id = "+mediumId)
	
	hed = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + glbals['token']}
	today = date.today().strftime("%Y-%m-%d")
	lendingDto = LendingDto(startDate=today, mediumCopyId=mediumId, userId=glbals['userid'], renewalCount=0)
	payload = lendingDto.toJson()
	try:
		response = glbals['session'].post(glbals['BASE_URL']+"/lending", headers=hed, data=payload)
		response.raise_for_status()
		jresp = response.json()
		print(json.dumps(jresp, indent = 1))

	except Exception as err:
		print(err)

def getUserID(token):
	splitToken = token.split(".")
	payload = splitToken[1]
	
	# Base64 Decoding
	payload += '=' * (-len(payload) % 4)
	decodedBytes = base64.b64decode(payload)
	decodedStr = str(decodedBytes, "utf-8")
	jsonPayload = json.loads(decodedStr)
	return jsonPayload["uuid"]

def getCopyIdOfMedium(mediumtype, mediumID):
	glbals = globals()
	hed = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + glbals['token']}
	response = glbals['session'].get(glbals['BASE_URL']+"/detail/"+mediumtype+"/"+mediumID, headers=hed)
	jsonResp = response.json()
	availableID = None
	#print(jsonResp)
	try:
		for copy in jsonResp["copies"]:
			if copy["available"] == True:
				availableID = copy["id"]
				print("There is an available Copy")
				break
		if availableID == None:
			print("Sory there is no Copy available.")
			return None
		else:
			print("Okey lets lend the Copy with the ID="+availableID)
			return availableID
	except:
		print("There are no Copies available")
	
def printStatus():
	glbals = globals()
	print("Current configuration: ")
	print("Host: "+ glbals['BASE_URL'])
	print("Username: "+glbals['username'])
	print("Password: "+ glbals['password'])
	if glbals["logedin"]:
		print("UserID: "+ glbals['userid'])
		print("Assigned Roles: ")
		for role in glbals['roles']:
			print(" -" + role)


class MyPromt(Cmd):
	glbals = globals()
	intro = 'Welcome to the LibraryManagement shell.   Type help or ? to list commands.\nDefault login is set to '+ glbals['BASE_URL']+ ' with Username: "' + glbals['username'] + '" and Password: "' + glbals['password'] + '"'
	prompt = 'LibraryManagement> '

	def do_exit(self, inp):
		'''exit the application.'''
		print("Bye Bye. See you next Time")
		return True

	def do_status(self, inp):
		'''Print the current configured Login Data'''
		printStatus()


	def do_setLogin(self, inp):
		'''Set the Login Credentials with: "setlogin ***REMOVED*** username password'''
		global host
		global username
		global password

		args = inp.split(" ")

		if len(args) != 3:
			print("Your input was not correct.")

		else:
			host = args[0]
			username = args[1]
			password = args[2]
			printStatus()


	def do_tryLogin(self, inp):
		'''Try to login with the given Credentials.'''
		login(username, password, BASE_URL)
		printStatus()

	def do_search(self, inp):
		'''Search for Media type. Example: search book'''
		serachMedium(inp)
		
	def do_lend(self, inp):
		'''Lend a Medium. Example: lend book 4f3737cb-cfba-4ce4-8fb9-6eed6dcec318 \nId have to be a valid mediumID you can get one with search book.'''
		#lendAMedium(inp)
		glbals = globals()
		args = inp.split(" ")
		if len(args) != 2:
			print("Your input was not correct.")
		else:
			id = getCopyIdOfMedium(args[0], args[1])
			if id != None:
				lendAMedium(args[0], id)


if __name__ == '__main__':
    MyPromt().cmdloop()


#login. vernleihe, suche,
