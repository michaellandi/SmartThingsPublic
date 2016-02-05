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
	definition (name: "Analog Alarm Panel Bell", namespace: "landi", author: "Michael Landi") {
		capability "Switch"
        capability "Refresh"
        capability "Sensor"
	}

	simulator {
        status "on":  "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
        status "off": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"
    
        // reply messages
        reply "raw 0x0 { 00 00 0a 0a 6f 6e }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
        reply "raw 0x0 { 00 00 0a 0a 6f 66 66 }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"
	}

	tiles {
		standardTile("status", "device.status") {
        	state("Off", label: "Off", action: "switch.off", icon:"st.Home.home4", backgroundColor:"#79b821", nextState: "On")
            state("On", label: "On", action: "switch.on", icon:"st.Home.home4", backgroundColor:"red", nextState: "Off")
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
        	sendEvent(name: "status", value: "on", displayed: "true", description: "Refresh: On")
        	break;
        case "off":
        	sendEvent(name: "status", value: "off", displayed: "true", description: "Refresh: Off")
        	break;
    }
}

// handle commands
def off() {
	log.debug "Sent to device: refresh"
	zigbee.smartShield(text: "off").format()
}

def on() {
	log.debug "Sent to device: refresh"
	zigbee.smartShield(text: "on").format()
}

def refresh() {
	log.debug "Sent to device: refresh"
	zigbee.smartShield(text: "refresh").format()
}