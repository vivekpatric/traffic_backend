package com.trafficnavigator.traffic_backend.traffic;

import org.springframework.stereotype.Component;

@Component
public class TrafficEvaluator {
        public String classify (double baseEta,double trafficEta){
            double ratio = trafficEta/baseEta;
            if(ratio<1.15) return "Green";
            if(ratio>1.35) return "Orange";
            return "Red";
        }
}
