/**
 *  Analog Alarm Panel Bell Sensor
 *
 *  Copyright 2016 Michael Landi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Analog Alarm Panel Bell Sensor", namespace: "landi", author: "Michael Landi") {
		capability "Alarm"
		capability "Refresh"
        capability "Polling"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("status", "device.status") {
        	state("OK", label: "OK", action: "alarm.off", icon:"st.Home.home4", backgroundColor:"#79b821", nextState: "Alarm")
            state("Alarm", label: "Alarm", action: "alarm.both", icon:"st.Home.home4", backgroundColor:"#79b821", nextState: "Alarm")
        }
        
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat") {
			state("default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh")
		}
        
        main "status"
		details(["status", "refresh"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	
    switch (description) {
    	case "on":
        	sendEvent(name: "status", value: "Alarm", displayed: "true", description: "Refresh: Alarm Detected")
        	break;
        case "off":
        	sendEvent(name: "status", value: "OK", displayed: "true", description: "Refresh: Alarm Off")
        	break;
    }

}

// handle commands
def off() {
	zigbee.smartShield(text: "off").format()
}

def strobe() {
	zigbee.smartShield(text: "strobe").format()
}

def siren() {
	zigbee.smartShield(text: "siren").format()
}

def both() {
	zigbee.smartShield(text: "both").format()
}

def poll() {
	refresh();
}

def refresh() {
	zigbee.smartShield(text: "refresh").format()
}