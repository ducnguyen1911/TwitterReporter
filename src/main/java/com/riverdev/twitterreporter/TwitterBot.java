/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.riverdev.twitterreporter;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

/**
 * A Camel Router
 */
public class TwitterBot extends RouteBuilder {
	
//	private static final double GRIDSIZE = 10.0;

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main.main(args);
    }

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {
//    	StringBuilder sb = new StringBuilder();
		
//		boolean first = true;
//		for (double swLat = 25.0; swLat <= 49.0; swLat = swLat + 10.0) {
//			for (double swLon = -125.0; swLon <= -67.0; swLon = swLon + 10.0) {
//				double neLat = swLat + GRIDSIZE;
//				double neLon = swLon + GRIDSIZE;
//				if (first) {
//					sb.append(swLon + "," + swLat + ";" + neLon + "," + neLat);
//					first = false;
//				} else {
//					sb.append(";" + swLon + "," + swLat + ";" + neLon + "," + neLat);
//				}
//			}
//		}
    	
        try {
        	URL url = getClass().getResource("/oauth.properties");
            Properties p = new Properties();
        	InputStream inStream = url.openStream();
            p.load(inStream);
    		
        	from("twitter://streaming/filter?type=polling"
    				+ "&consumerKey=" + p.getProperty("consumer.key")
    				+ "&consumerSecret=" + p.getProperty("consumer.secret")
    				+ "&accessToken=" + p.getProperty("access.token")
    				+ "&accessTokenSecret=" + p.getProperty("access.token.secret")
    				+ "&locations=-125,25;-67,49")
    				.to("bean:tweetProcessor?method=process");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}