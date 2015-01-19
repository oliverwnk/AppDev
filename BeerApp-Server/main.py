# all the imports
import sqlite3
import json
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
	i = 0
	for row in cur.fetchall():
               	row = list(row)
		if row[0] == None:
			row[0]=" "
		if row[1] == None:
			row[1]=" "
		if row[2] == None:
			row[2] =" "
		if row[3] == None:
			row[3]=" "
		if row[4] == None:
                        row[4]=" "
                if row[5] == None:
                        row[5]=" "
                if row[6] == None:
                        row[6] =" "
		if row[7] == None:
                        row[7]=" "
                if row[8] == None:
                        row[8]=" "
                if row[9] == None:
                        row[9] =" "
		if row[10] == None:
                        row[10]=" "
                if row[11] == None:
                        row[11]=" "
                if row[12] == None:
                        row[12] =" "
		row = tuple(row)  
		entries.append(dict(id=row[0],user_id=row[1], categorie=row[2].encode('utf-8'), productName=row[3].encode('utf-8'), text=row[4].encode('utf-8'), price=row[5], quantity=row[6], contactDetails=row[7], latitude=row[8], longtitude=row[9], Timezone=row[10].encode('utf-8'), beginTime=row[11].encode('utf-8'), endTime=row[12].encode('utf-8'),retry=row[13].encode('utf-8') ))
	return render_template('show_entries.html',entries = entries)
@app.route('/My')
def show_my_entries():
	db = get_db()
	user = request.args.get('user')
	
	cur = db.execute('select * from entries where user_id = ? ',[user])
	#print cur.fetchall()
	#entries = cur.fetchall()
	entries = []
	for row in cur.fetchall():
		entries.append(dict(user_id=row[0], categorie=row[1], productName=row[2], text=row[3], price=row[4], quantity=row[5], contactDetails=row[6].encode('utf-8'), latitude=row[7], longtitude=row[8], Timezone=row[9], beginTime=row[10], endTime=row[11],retry=row[12]) )
	return render_template('show_entries.html',entries = entries)

@app.route('/add',methods=['GET'])
def show_add():
	user = request.args.get('user')
	categorie = request.args.get('categorie')
	productName = request.args.get('productName')
	
	text = request.args.get('text')
	price = request.args.get('price')
	quanity = request.args.get('quantity')
	contactDetails = request.args.get('contactdetails')
	latitude = request.args.get('latitude')
	longtitude = request.args.get('longtitude')
	Timezone = request.args.get('Timezone')
	beginTime = request.args.get('beginTime')
	endTime = request.args.get('endTime')
	retry = request.args.get('retry')
	activ = request.args.get('activ')
	ret = g.db.execute('insert into entries (user_id,categorie,productName,name,price,quantity,contactDetails,latitude,longtitude,Timezone,beginTime,endTime,retry,activ) values (?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?)',[user, categorie, productName, text, price,  quanity, contactDetails, latitude, longtitude, Timezone, beginTime, endTime, retry, activ])
	g.db.commit();
	return "ok"
@app.route('/getID',methods=['GET'])
def getId():
	cur = g.db.execute('select * from users')
	i = len(cur.fetchall())
	g.db.execute('insert into users (user_id) values (?)' ,[""])
	g.db.commit()
	return ""+str(i)
if __name__ == '__main__':
    app.run(host="192.168.1.148",port=5000)
