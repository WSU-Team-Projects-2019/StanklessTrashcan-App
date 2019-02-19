from flask import Flask, request
from flask_restful import Resource, Api, reqparse
from gpiozero import Button, OutputDevice
from hx711 import HX711

app = Flask(__name__)
api = Api(app)

#Set pins and other values
LID_PIN = 23
LIGHT_PIN = 24
FAN_PIN = 25
LED_PIN = 8
WEIGHT_DATA_PIN = 5
WEIGHT_CLOCK_PIN = 6
WEIGHT_GAIN = 64
NUM_MEASUREMENTS = 5
TARE = 0

#Create objects for physical objects
lid = Button(LID_PIN)
light = OutputDevice(LIGHT_PIN, active_high=False, initial_value=False)
fan = OutputDevice(FAN_PIN, active_high=False, initial_value=False)
led = OutputDevice(LED_PIN, active_high=False, initial_value=False)

#Setup parser


class Lid(Resource):
    def get(self):
        if lid.is_pressed:
            status = 'open'
        else:
            status = 'closed'
        return status

class Light(Resource):
    def get(self):
        if light.is_active:
            status = 'on'
        else:
            status = 'off'
        return status

    def put(self):
        action = request.args.get('action')

        if action == 'off':
            light.off()
        if action == 'on':
            light.on()
        if action == 'toggle':
            light.toggle()
        else:
            return
        return {'hello': 'world'}


class Fan(Resource):
    def get(self):
        if fan.is_active:
            status = 'on'
        else:
            status = 'off'
        return status

    def put(self):
        return {'hello': 'world'}


class LightED(Resource):
    def get(self):
        def get(self):
            if light.is_active:
                status = 'on'
            else:
                status = 'off'
            return status

    def put(self):
        return {'hello': 'world'}

class Weight(Resource):
    # Setup scale amp
    hx711 = HX711(
        dout_pin=WEIGHT_DATA_PIN,
        pd_sck_pin=WEIGHT_CLOCK_PIN,
        channel='A',
        gain=WEIGHT_GAIN
    )

    def get(self):
        hx711.reset() #Maybe not necessary
        results = hx711.get_raw_data(NUM_MEASUREMENTS)
        return sum(results)/len(results) - TARE

    def put(self):
        hx711.reset()  # Maybe not necessary
        TARE = hx711.get_raw_data(NUM_MEASUREMENTS)
        return {'hello': 'world'}

api.add_resource(HelloWorld, '/')

if __name__ == '__main__':
    app.run()
