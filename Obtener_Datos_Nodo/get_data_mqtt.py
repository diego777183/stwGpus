# author: dsantome

import paho.mqtt.client as mqtt #import the client1
import time

def on_message(client, userdata, message):
    print("message received " ,str(message.payload.decode("utf-8")))
    with open("/home/pi/Desktop/plugData.txt","w",encoding="utf-8") as file:
        file.write(str(message.payload.decode("utf-8")))

def connect_mqtt():
    broker_address="192.168.1.59"
    client = mqtt.Client("P1") 
    client.on_message=on_message 
    client.connect(broker_address) 
    client.loop_start() 
    client.subscribe("/stw/temp/tele/SENSOR")


connect_mqtt()
while True:
    time.sleep(1)