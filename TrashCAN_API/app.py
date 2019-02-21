import sqlite3
import json
import uuid
import datetime
from flask import Flask, request, g
from flask_restful import Resource, Api, reqparse
from gpiozero import Button, OutputDevice
from hx711 import HX711

app = Flask(__name__)
api = Api(app)

#Set pins and other values
LID_SWITCH_PIN = 23
LID_OPEN_PIN = 9
LID_CLOSE_PIN = 10
LIGHT_PIN = 24
FAN_PIN = 25
LED_PIN = 8
SCALE_DATA_PIN = 5
SCALE_CLOCK_PIN = 6
SCALE_GAIN = 64
NUM_MEASUREMENTS = 5

# Setup scale amp
hx711 = HX711(
    dout_pin=SCALE_DATA_PIN,
    pd_sck_pin=SCALE_CLOCK_PIN,
    channel='A',
    gain=SCALE_GAIN
)

#Create objects for physical objects
lid_switch = Button(LID_SWITCH_PIN)
lid_open_button = OutputDevice(LID_OPEN_PIN, active_high=False, initial_value=False)
lid_close_button = OutputDevice(LID_CLOSE_PIN, active_high=False, initial_value=False)
light = OutputDevice(LIGHT_PIN, active_high=False, initial_value=False)
fan = OutputDevice(FAN_PIN, active_high=False, initial_value=False)
led = OutputDevice(LED_PIN, active_high=False, initial_value=False)

#Setup parser

#Setup database
DATABASE = '/srv/trashcan/venv/database.db'

def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

class Index (Resource):
    def get(self):
        content = "<h1>This is an index page</h1>"
        return content

class Api(Resource):
    def get(self):
        content = "<h1>This is an API page</h1>"
        return content

class Lid(Resource):
    def get(self):
        if lid_switch.value:
            status = 'open'
        else:
            status = 'closed'
        return status
    def put(self):
        action = request.args.get('action')

        if action == 'off':
            lid_close_button.on()
        elif action == 'on':
            lid_open_button.on()
        elif action == 'toggle':
            if lid_switch.value:
                lid_close_button.on()
            else:
                lid_close_button.on()
        else:
            return 400
        return 'Success'

class Light(Resource):
    def get(self):
        if light.value:
            status = 'on'
        else:
            status = 'off'
        return status

    def put(self):
        action = request.args.get('action')

        if action == 'off':
            light.off()
        elif action == 'on':
            light.on()
        elif action == 'toggle':
            light.toggle()
        else:
            return 400
        return 'Success'

class Fan(Resource):
    def get(self):
        if fan.value:
            status = 'on'
        else:
            status = 'off'
        return status

    def put(self):
        action = request.args.get('action')

        if action == 'off':
            fan.off()
        elif action == 'on':
            fan.on()
        elif action == 'toggle':
            fan.toggle()
        else:
            return 400
        return 'Success'


class LightED(Resource):
    def get(self):
            if led.value:
                status = 'on'
            else:
                status = 'off'
            return status

    def put(self):
        action = request.args.get('action')

        if action == 'off':
            led.off()
        elif action == 'on':
            led.on()
        elif action == 'toggle':
            led.toggle()
        else:
            return 400
        return 'Success'

class Scale (Resource):
    def get(self):
        conn = get_db()
        conn.cursor().execute("SELECT [Value] FROM [System_Options] WHERE [Option_Name] = 'tare'")
        tare = conn.cursor().fetchone()
        hx711.reset() #Maybe not necessary
        results = hx711.get_raw_data(NUM_MEASUREMENTS)
        return sum(results)/len(results) - tare

    def put(self):
        conn = get_db()
        hx711.reset()  # Maybe not necessary
        tare = hx711.get_raw_data(NUM_MEASUREMENTS)
        conn.cursor().execute("UPDATE [System_Options] SET [Value] = ? WHERE [Option_Name] = 'tare'", (tare,))
        conn.commit()
        return 'Success'

class WeightList (Resource):
    def get(self):
        conn = get_db()
        conn.cursor().execute("SELECT * FROM[Weight]")
        results = conn.cursor().fetchall()
        return json.dumps(results)

    def delete(self):
        return 501

class BarcodeList (Resource):
    def get(self):
        conn = get_db()
        conn.cursor().execute("SELECT * FROM[Barcode]")
        results = conn.cursor().fetchall()
        return json.dumps(results)

    def delete(self):
        return 501

class Barcode (Resource):
    def post(self,barcode):
        conn = get_db()
        barcode_id = uuid.uuid1()
        time = datetime.datetime.now()
        conn.cursor().execute("INSERT INTO ([barcode_id],[timestamp],[barcode]) VALUES(?, ?, ?)",(barcode_id,time,barcode,))
        conn.commit()
        return barcode_id

    def delete(self, barcode_id):
        conn = get_db()
        result = conn.cursor().execute("DELETE FROM[Barcode] WHERE barcode_id = ?",(barcode_id,))
        conn.commit()
        return result


class Weight (Resource):
    def post(self):
        conn = get_db()
        weight_id = uuid.uuid1()
        time = datetime.datetime.now()
        conn.cursor().execute("INSERT INTO ([weight_id],[timestamp],[weight]) VALUES(?, ?, ?)",(weight_id,time,weight,))
        conn.commit()
        return weight_id

    def delete(self, weight_id):
        conn = get_db()
        result = conn.cursor().execute("DELETE FROM[Weight] WHERE weight_id = ?",(weight_id,))
        conn.commit()
        return result

#Map resource
api.add_resource(Index, '/')
api.add_resource(Api, '/api')
api.add_resource(Lid, '/api/lid')
api.add_resource(Scale, '/api/scale')
api.add_resource(Light, '/api/light')
api.add_resource(Fan, '/api/fan')
api.add_resource(BarcodeList, '/api/barcode')
api.add_resource(WeightList, '/api/weight')
api.add_resource(Barcode, '/api/barcode/<barcode_id>')
api.add_resource(Weight, '/api/weight/<weight_id>')

if __name__ == '__main__':
    app.run()
