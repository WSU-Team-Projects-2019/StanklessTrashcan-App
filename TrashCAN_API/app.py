from flask import Flask, request, g
from flask_restful import Resource, Api, reqparse
from gpiozero import Button, OutputDevice
from hx711 import HX711
import sqlite3

app = Flask(__name__)
api = Api(app)

#Set pins and other values
LID_SWITCH_PIN = 23
LID_BUTTON_PIN = 9
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
lid_button = OutputDevice(LID_BUTTON_PIN, active_high=False, initial_value=False)
light = OutputDevice(LIGHT_PIN, active_high=False, initial_value=False)
fan = OutputDevice(FAN_PIN, active_high=False, initial_value=False)
led = OutputDevice(LED_PIN, active_high=False, initial_value=False)

#Setup parser

#Setup database
DATABASE = '/path/to/database.db'

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
        if lid_switch.is_pressed:
            status = 'open'
        else:
            status = 'closed'
        return status
    def put(self):
        action = request.args.get('action')

        if action == 'off':
            lid_button.off()
        elif action == 'on':
            lid_button.on()
        elif action == 'toggle':
            lid_button.toggle()
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
        # TODO: Retrieve tare value from database
        tare = 0
        hx711.reset() #Maybe not necessary
        results = hx711.get_raw_data(NUM_MEASUREMENTS)
        return sum(results)/len(results) - tare

    def put(self):
        hx711.reset()  # Maybe not necessary
        #TODO: Store tare value in database
        tare = hx711.get_raw_data(NUM_MEASUREMENTS)
        return {'hello': 'world'}

class Weight (Resource):
    def get(self):
        return 'Not implemented'

    def post(self):
        return 'Not implemented'

    def delete(self):
        return 'Not implemented'

class Barcode (Resource):
    def get(self):
        return 'Not implemented'

    def post(self):
        return 'Not implemented'

    def delete(self):
        return 'Not implemented'

#Map resources
api.add_resource(Index, '/')
api.add_resource(Api, '/api')
api.add_resource(Lid, '/api/lid')
api.add_resource(Scale, '/api/scale')
api.add_resource(Light, '/api/light')
api.add_resource(Fan, '/api/fan')
api.add_resource(Barcode, '/api/barcode')
api.add_resource(Weight, '/api/weight')

if __name__ == '__main__':
    app.run()
