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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class RuleClassBindings {
    private Map ruleToClass;
    private Map classToRule;

    public RuleClassBindings() {
        ruleToClass = new HashMap();
        classToRule = new HashMap();
    }

    public RuleClassBindings(String propertiesFileName) throws IOException, ClassNotFoundException {
        this();

        Properties properties = new Properties();
        properties.load(new FileInputStream(propertiesFileName));
        for (Iterator iterator = properties.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry mapping = (Map.Entry) iterator.next();
            addMapping((String) mapping.getKey(), Class.forName((String) mapping.getValue()));
        }
    }

    public RuleClassBindings(String[] names, Class[] classes) {
        this();

        for (int mLoop = 0; mLoop < names.length; ++mLoop) {
            addMapping(names[mLoop], classes[mLoop]);
        }
    }

    public RuleClassBindings(String name, Class classes) {
        this(new String[] {name}, new Class[] {classes});
    }

    public void addMapping(String rule, Class clazz) {
        ruleToClass.put(rule, clazz);
        classToRule.put(clazz, rule);
    }

    public Class getClass(String rule) {
        return (Class) ruleToClass.get(rule);
    }

    public String getRule(Class clazz) {
        return (String) classToRule.get(clazz);
    }
}
