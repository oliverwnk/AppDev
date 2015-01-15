# all the imports
import sqlite3
from contextlib import closing
from flask import Flask, request, session, g, redirect, url_for, \
     abort, render_template, flash

# configuration
DATABASE = '/tmp/flaskr.db'
DEBUG = True
SECRET_KEY = 'development key'
USERNAME = 'admin'
PASSWORD = 'default'

# create our little application :)
app = Flask(__name__)
app.config.from_object(__name__)
app.config.from_envvar('FLASKR_SETTINGS', silent=True)
def connect_db():
    return sqlite3.connect(app.config['DATABASE'])
def init_db():
	"""Initializes the database."""
	db = get_db()
	with app.open_resource('shema.sql', mode='r') as f:
		db.cursor().executescript(f.read())
		db.commit()
@app.before_request
def before_request():
    g.db = connect_db()

@app.teardown_request
def teardown_request(exception):
    db = getattr(g, 'db', None)
    if db is not None:
        db.close()
print('Initialized the database.')
def get_db():
	"""Opens a new database connection if there is none yet for the
	current application context.
	"""
	if not hasattr(g, 'sqlite_db'):
		g.sqlite_db = connect_db()
		return g.sqlite_db
@app.route('/')
def show_entries():
	db = get_db()
	cur = db.execute('select * from entries')
	#print cur.fetchall()
	#entries = cur.fetchall()
	entries = []
	for row in cur.fetchall():
		print "n"
		entries.append(dict(user_id=row[0], categorie=row[1], productName=row[2], text=row[3], price=row[4], quantity=row[5], contactDetails=row[6], latitude=row[7], longtitude=row[8], Timezone=row[9], beginTime=row[10], endTime=row[11],retry=row[12]) )
	print entries;
	return render_template('show_entries.html',entries = entries)
@app.route('/')
def show_entries():
	db = get_db()
	cur = db.execute('select * from entries')
	#print cur.fetchall()
	#entries = cur.fetchall()
	entries = []
	for row in cur.fetchall():
		print "n"
		entries.append(dict(user_id=row[0], categorie=row[1], productName=row[2], text=row[3], price=row[4], quantity=row[5], contactDetails=row[6], latitude=row[7], longtitude=row[8], Timezone=row[9], beginTime=row[10], endTime=row[11],retry=row[12]) )
	print entries;
	return render_template('show_entries.html',entries = entries)
@app.route('/My')
def show_entries():
	db = get_db()
	user = request.args.get('user')
	print user;
	cur = db.execute('select * from entries where user_id = ? ',[user])
	#print cur.fetchall()
	#entries = cur.fetchall()
	entries = []
	for row in cur.fetchall():
		print "n"
		entries.append(dict(user_id=row[0], categorie=row[1], productName=row[2], text=row[3], price=row[4], quantity=row[5], contactDetails=row[6], latitude=row[7], longtitude=row[8], Timezone=row[9], beginTime=row[10], endTime=row[11],retry=row[12]) )
	print entries;
	return render_template('show_entries.html',entries = entries)

@app.route('/add',methods=['GET'])
def show_add():
	user = request.args.get('user')
	print user;
	categorie = request.args.get('categorie')
	print categorie
	productName = request.args.get('productName')
	print productName
	text = request.args.get('text')
	print text
	price = request.args.get('price')
	print price
	quanity = request.args.get('quantity')
	print quanity
	contactDetails = request.args.get('contactDetails')
	print contactDetails
	latitude = request.args.get('latitude')
	print latitude
	longtitude = request.args.get('longtitude')
	print longtitude
	Timezone = request.args.get('Timezone')
	print Timezone
	beginTime = request.args.get('beginTime')
	print beginTime
	endTime = request.args.get('endTime')
	print endTime
	retry = request.args.get('retry')
	print retry
	activ = request.args.get('activ')
	print activ
	ret = g.db.execute('insert into entries (user_id,categorie,productName,name,price,quantity,contactDetails,latitude,longtitude,Timezone,beginTime,endTime,retry,activ) values (?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?)',[user, categorie, productName, text, price,  quanity, contactDetails, latitude, longtitude, Timezone, beginTime, endTime, retry, activ])
	g.db.commit();
	return "ok"
@app.route('/getID',methods=['GET'])
def getId():
	g.db.execute('select count(*) from users')
	g.db.execute('insert into users ("")')
	g.db.commit()
	return render_template('index.html')
if __name__ == '__main__':
    app.run()
