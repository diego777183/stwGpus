# Author: Alberto Perez

import sys, os, subprocess, requests
from flask import Flask
import json

def parse_node_data(data):
    gpu_data = {}
    gpu_data["isOnline"] = 1
    gpu_data["name"] = data["name"]
    gpu_data["efficiency"] = data["efficiency"]  
    gpu_data["hashrate"] = data["hashrate"]  
    gpu_data["powerGPU"] = data["power"]
    gpu_data["temperature"] = data["temperature"]
    gpu_data["solved_count"] = data["shares"]["solved_count"]  
    gpu_data["rejected_count"] = data["shares"]["rejected_count"]  
    return gpu_data



def get_temperature():
    return subprocess.check_output('cat /sys/bus/i2c/devices/1-0076/iio\:device0/in_temp_input', shell=True).decode('utf-8').replace('\n', '')

def get_plug_data():
    with open("/home/pi/Desktop/plugData.txt","r",encoding="utf-8") as file:
        data = json.loads(file.read())  
    return data["ENERGY"]["Power"] 
    



app = Flask(__name__)


@app.route('/get', methods=['GET'])
def get_data():
    data = {}
    #Node

    try:
        response = requests.get('http://192.168.1.65:4067/summary', timeout=20)
        print(response)
        data["gpus"] = parse_node_data(response.json()["gpus"][0])
    except Exception:
        data["gpus"] = {}
        data["gpus"]["isOnline"] = 0
    #Thermometer
    data["thermometer"] = get_temperature()
    #Plug
    data["power"] = get_plug_data()

    return data




if __name__ == '__main__':
    
    if os.geteuid() != 0:
        print('Debes tener privilegios root para este script.')
        sys.exit(1)
    app.run(host="0.0.0.0", debug=True,port=4000)

    