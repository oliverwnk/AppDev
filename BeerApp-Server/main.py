# all the imports
import sqlite3
import json
import math
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
	categorie = request.args.get('categorie')
	productName = ""
	productName = request.args.get('productName')
	latitude = request.args.get('latitude')
	
	longtitude = request.args.get('longtitude')
	rad = request.args.get('radius')
	#assert half_side_in_miles > 0
	latitude_in_degrees = (latitude)
	longitude_in_degrees = (longtitude)
	#assert latitude_in_degrees >= -180.0 and latitude_in_degrees  <= 180.0
	#assert longitude_in_degrees >= -180.0 and longitude_in_degrees <= 180.0
	if(rad == None):
		rad=0.0
	half_side_in_km = float(rad)/2#half_side_in_miles * 1.609344
	lat = math.radians(float(latitude_in_degrees))
	lon = math.radians(float(longitude_in_degrees))
	radius  = 6371
	# Radius of the parallel at given latitude
	parallel_radius = radius*math.cos(lat)

	lat_min = lat - half_side_in_km/radius
	lat_max = lat + half_side_in_km/radius
	lon_min = lon - half_side_in_km/parallel_radius
	lon_max = lon + half_side_in_km/parallel_radius
	rad2deg = math.degrees

	
	lat_min = rad2deg(lat_min)
	lon_min = rad2deg(lon_min)
	lat_max = rad2deg(lat_max)
	lon_max = rad2deg(lon_max)
	db = get_db()
	if(categorie == None):
		cur = db.execute('select * from entries where longtitude > ? and longtitude < ? and latitude > ? and latitude < ? and activ = "true"',[lon_min,lon_max,lat_min,lat_max])
	else:
		if(productName == None):
			cur = db.execute('select * from entries where categorie = ? and longtitude > ? and longtitude < ? and latitude > ? and latitude < ? and activ = "true"', [categorie,lon_min,lon_max,lat_min,lat_max])
		else:
			cur = db.execute('select * from entries where categorie = ? and longtitude > ? and longtitude < ? and latitude > ? and latitude < ? and productName = ? and activ = "true"', [categorie,lon_min,lon_max,lat_min,lat_max,productName])
	#print cur.fetchall()
	#entries = cur.fetchall()
	entries = []
	i = 0
	for row in cur.fetchall():
		print row;
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
		if row[14] == None:
			row[14] = "false";
		row = tuple(row)  
		entries.append(dict(id=row[0],user_id=row[1], categorie=row[2].encode('utf-8'), productName=row[3].encode('utf-8'), text=row[4].encode('utf-8'), price=row[5], quantity=row[6], contactDetails=row[7].encode('utf-8'), longtitude=row[8], latitude=row[9], Timezone=row[10].encode('utf-8'), beginTime=row[11].encode('utf-8'), endTime=row[12].encode('utf-8'),retry=row[13].encode('utf-8'),active=row[14].encode('utf-8') ))
	return render_template('show_entries.html',entries = entries)
@app.route('/getentrybyid')
def show_id_entry():
	user_id = request.args.get('user_id')
	db = get_db()
	cur = db.execute('select * from entries where id = ? and activ = "true"', [user_id]) 
        entries = []
        i = 0
        for row in cur.fetchall():
                print row;
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
                if row[14] == None:
                        row[14] = "false";
                row = tuple(row)
                entries.append(dict(id=row[0],user_id=row[1], categorie=row[2].encode('utf-8'), productName=row[3].encode('utf-8'), text=row[4].encode('utf-8'), price=row[5], quantity=row[6], contactDetails=row[7].encode('utf-8'), longtitude=row[8], latitude=row[9], Timezone=row[10].encode('utf-8'), beginTime=row[11].encode('utf-8'), endTime=row[12].encode('utf-8'),retry=row[13].encode('utf-8'),active=row[14].encode('utf-8') ))
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
		row = list(row)
		if row[14] == None:
			row[14] = "false";
		row = tuple(row)
		entries.append(dict(id=row[0],user_id=row[1], categorie=row[2].encode('utf-8'), productName=row[3].encode('utf-8'), text=row[4].encode('utf-8'), price=row[5], quantity=row[6], contactDetails=row[7].encode('utf-8'), latitude=row[8], longtitude=row[9], Timezone=row[10], beginTime=row[11].encode('utf-8'), endTime=row[12].encode('utf-8'),retry=row[13].encode('utf-8'),active=row[14].encode('utf-8')) )
	return render_template('show_entries.html',entries = entries)
@app.route('/activate',methods=['GET'])
def activate():
	id = request.args.get('id');
	print id;
	ret = g.db.execute("update entries set activ = 'true' where id = ?",[id]);
	g.db.commit();
	return"ok"
@app.route('/deactivate',methods=['GET'])
def deactivate():
        id = request.args.get('id');
	print id;
        ret = g.db.execute("update entries set activ = 'false' where id = ?",[id]);
        g.db.commit();
        return"ok"

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
	id = request.args.get('id')
	cur = g.db.execute('select * from entries')
	i = len(cur.fetchall())
	if id >= i :
		ret = g.db.execute('update entries set user_id = ? ,categorie = ?,productName = ?,name = ?,price = ?,quantity = ?,contactDetails = ?,latitude = ?,longtitude = ?,Timezone = ?,beginTime = ?,endTime = ?,retry = ?,activ = ? where id = ?',[user, categorie, productName, text, price,  quanity, contactDetails, latitude, longtitude, Timezone, beginTime, endTime, retry, activ,id])	
	else:
		ret = g.db.execute('insert into entries (user_id,categorie,productName,name,price,quantity,contactDetails,latitude,longtitude,Timezone,beginTime,endTime,retry,activ) values (?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?)',[user, categorie, productName, text, price,  quanity, contactDetails, latitude, longtitude, Timezone, beginTime, endTime, retry, activ])
		g.db.commit();
	return "ok"
@app.route('/getID',methods=['GET'])
def getId():
	cur = g.db.execute('select * from users')
	i = len(cur.fetchall())
	g.db.execute('insert into users (user_id) values (?)' ,[""])
	g.db.commit()
	i+=1
	return ""+str(i)
if __name__ == '__main__':
    app.run(host="192.168.1.148",port=5000)
