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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.adarwin.rule.Rule;

public class RuleBuilder implements RuleProducer {
    private final RuleClassBindings ruleClassBindings;
    private final Map variables = new HashMap();
	private final String ruleExpression;
	private final Logger logger;
	
    public RuleBuilder(RuleClassBindings ruleClassBindings, String ruleExpression, Logger logger)
		throws ADarwinException {

        this.ruleClassBindings = ruleClassBindings;
		this.logger = logger;
		this.ruleExpression = ruleExpression.replaceAll("\\s++", " ").replaceAll(",\\s++", ",");

		if (!Util.balanced(ruleExpression, '(', ')')) {
			throw new ADarwinException("Unbalanced expression: \"" + ruleExpression + "\"");
		}
    }

	public void produce(RuleConsumer ruleConsumer) throws ADarwinException {
    	String[] subExpression = parse(ruleExpression);
    	
    	for (int rLoop = 0; rLoop < subExpression.length; rLoop++) {
    		Rule rule = buildRule(subExpression[rLoop].trim());

			logger.reset(Runner.CLASSES_VIOLATED + rule.toString(ruleClassBindings));

			ruleConsumer.consume(rule, logger);
    	}
    }

    private Rule buildRule(String expression) throws ADarwinException {
		String variable = getVariableValue(expression);
		String name = getName(expression);

		if (ruleClassBindings.getClass(name) == null) {
			throw new ADarwinException("No such rule: \"" + name + "\"");
		}

		if (variables.get(name) != null) {
			return (Rule) variables.get(name);
		}

        Rule rule = buildRule(name, getParameters(expression));

        if (variable != null && variable.length() > 0) {
        	variables.put(variable, rule);
        	return Rule.NULL;
        }

        return rule;
    }

	private String getVariableValue(String expression) {
		if (expression.indexOf('=') != -1) {
			return expression.substring(0, expression.indexOf('='));
		}

		return "";
	}

	private String getName(final String expression) {
		int startOfName = 0;
		
		if (expression.indexOf('=') != -1) {
			startOfName = expression.indexOf('=') + 1;
		}
		
		int endOfName = expression.length();
		
        if (expression.indexOf('(') != -1) {
			endOfName = expression.indexOf('(');
        }

		return expression.substring(startOfName, endOfName).trim();        
    }

    private String[] getParameters(final String expression) {
        String[] parameters = new String[0];

        int firstParathesesPos = expression.indexOf('(');
        if (firstParathesesPos != -1) {
            int lastParathesisPos = expression.lastIndexOf(')');

            if (lastParathesisPos - firstParathesesPos > 1) {
                String innerBit = expression.substring(firstParathesesPos + 1, lastParathesisPos);

                int depth = 0;
                int startPos = 0;
                List parameterList = new LinkedList();

                for (int cLoop = 0; cLoop < innerBit.length(); ++cLoop) {
                    switch (innerBit.charAt(cLoop)) {
                        case '(' : depth++; break;
                        case ')' : depth--; break;
                        case ',' :
                            if (depth == 0) {
                                parameterList.add(innerBit.substring(startPos, cLoop));
                                startPos = cLoop+1;
                            }
                            
                            break;
                    }
                }

                parameterList.add(innerBit.substring(startPos));

                parameters = (String[]) parameterList.toArray(new String[0]);
            }
        }

        return parameters;
    }

    private Rule buildRule(String name, String[] arguments) throws ADarwinException {
		Constructor constructor = getConstructor(name, arguments);

		if (hasCorrectSimpleForm(constructor, arguments.length)) {
			return constructRule(constructor, getSimpleParameters(arguments, constructor));
		}
		else {
			return constructRule(constructor, getAggreateParameters(arguments, constructor));
		}
    }

    private Object[] getAggreateParameters(final String[] arguments, final Constructor constructor) throws ADarwinException {
		Object[] constructorParameters;
		
		if (constructor.getParameterTypes()[0].equals(Rule[].class)) {
			Rule[] constructorParameter = new Rule[arguments.length];

			for (int cLoop = 0; cLoop < arguments.length; cLoop++) {
				constructorParameter[cLoop] = buildRule(arguments[cLoop]);
			}
			
			constructorParameters = new Object[] {constructorParameter};
		}
		else {
			constructorParameters = new Object[] {arguments};
		}
		return constructorParameters;
	}

	private Object[] getSimpleParameters(final String[] arguments, Constructor constructor) throws ADarwinException {
		Object[] constructorParameters = new Object[arguments.length];

		for (int cLoop = 0; cLoop < constructorParameters.length; cLoop++) {
			if (Rule.class.isAssignableFrom(constructor.getParameterTypes()[cLoop])) {
				constructorParameters[cLoop] = buildRule(arguments[cLoop]);
			}
			else {
				constructorParameters[cLoop] = arguments[cLoop];
			}
		}
		return constructorParameters;
	}

	private Rule constructRule(Constructor constructor, Object[] constructorParameters)
		throws ADarwinException {

		try {
			return (Rule) constructor.newInstance(constructorParameters);
		} catch (Exception e) {
			throw new ADarwinException(e);
		}
	}

	private Constructor getConstructor(String ruleName, String[] arguments)
		throws ADarwinException {

		Constructor[] constructors = ruleClassBindings.getClass(ruleName).getConstructors();

		for (int cLoop = 0; cLoop < constructors.length; ++cLoop) {
			Constructor constructor = constructors[cLoop];

			if (hasCorrectSignature(ruleName, constructor, arguments.length)) {
				return constructor;
			}
		}

		throw new ADarwinException(
			"No constructor for: " + ruleName + '(' + Util.concat(arguments) + ')');
    }

	private boolean hasCorrectSignature(String name, Constructor constructor, int length) {
		return hasCorrectSimpleForm(constructor, length) ||
			hasAggregateForm(constructor);
	}

	private boolean hasAggregateForm(Constructor constructor) {
		Class[] parameterTypes = constructor.getParameterTypes();
		return parameterTypes.length == 1 &&
			(Rule[].class.equals(parameterTypes[0]) ||
			 String[].class.equals(parameterTypes[0]));
	}

	private boolean hasCorrectSimpleForm(Constructor constructor, int length) {
		return constructor.getParameterTypes().length == length &&
				parametersHaveCorrectTypes(constructor.getParameterTypes());
	}

	private boolean parametersHaveCorrectTypes(Class[] parameterTypes) {
		for (int pLoop = 0; pLoop < parameterTypes.length; pLoop++) {
			Class parameterType = parameterTypes[pLoop];

			if (!parameterType.equals(String.class) &&
				!Rule.class.isAssignableFrom(parameterType)) {
				return false;
			}
		}

		return true;
	}

	public static String[] parse(String expression) {
		StringBuffer buffer = new StringBuffer(expression);

		int depth = 0;
		for (int bLoop = 0; bLoop < buffer.length(); ++bLoop) {
			switch (buffer.charAt(bLoop)) {
				case '(' : 
					depth++;
					break;
				case ')':
					depth--;
					break;
				case ',':
					if (depth == 0) {
						buffer.setCharAt(bLoop, ';');
					}
					break;
			}
		}

		return simpleParse(buffer.toString(), ";");
	}

	private static String[] simpleParse(String expression, String separator) {
		List things = new LinkedList();
		
		StringTokenizer stringTokenizer = new StringTokenizer(expression, separator);
		
		while (stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			things.add(token.trim());
		}
		
		return (String[]) things.toArray(new String[0]);
	}
}
