/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class RuleClassBindings {
    private final Map ruleToClass = new HashMap();
    private final Map classToRule = new HashMap();

    public RuleClassBindings(String propertiesFileName, IFileAccessor fileAccessor)
		throws ADarwinException {

        Properties properties = loadProperties(propertiesFileName, fileAccessor);
        for (Iterator iterator = properties.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry mapping = (Map.Entry) iterator.next();
            addMapping((String) mapping.getKey(), (String) mapping.getValue());
        }
    }

	public RuleClassBindings(String[] names, Class[] classes) {
        for (int mLoop = 0; mLoop < names.length; ++mLoop) {
            addMapping(names[mLoop], classes[mLoop]);
        }
    }

    public RuleClassBindings(String name, Class clazz) {
    	addMapping(name, clazz);
    }

    public Class getClass(String rule) {
        return (Class) ruleToClass.get(rule);
    }

    public String getRule(Class clazz) {
        return (String) classToRule.get(clazz);
    }
    
    private void addMapping(String rule, String clazz) throws ADarwinException {
    	try {
			addMapping(rule, Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			throw new ADarwinException("Unable to find class: " + clazz, e);
		}
    }

	private void addMapping(String rule, Class clazz) {
		ruleToClass.put(rule, clazz);
		classToRule.put(clazz, rule);
	}

    private Properties loadProperties(String propertiesFileName, IFileAccessor fileAccessor) throws ADarwinException {
        try {
			Properties properties = new Properties();
			properties.load(fileAccessor.openFile(propertiesFileName));
			return properties;
		} catch (IOException e) {
			throw new ADarwinException("Unable to loading bindings: " + propertiesFileName, e);
		}
	}
}
