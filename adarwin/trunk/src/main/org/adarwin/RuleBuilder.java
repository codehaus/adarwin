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
import java.util.Properties;
import java.util.StringTokenizer;

import org.adarwin.rule.NameRule;
import org.adarwin.rule.Rule;

public class RuleBuilder implements RuleProducer {
    private final Map variables = new HashMap();
	private final String ruleExpression;
	private final Logger logger;
	public static final String CLASSES_VIOLATED = "classes violated: ";
	private Properties ruleMapping;

    public RuleBuilder(String ruleExpression, Logger logger, Properties ruleMapping) {
		this.logger = logger;
		this.ruleMapping = ruleMapping;
		this.ruleExpression = ruleExpression.
			replaceAll("\\s++", " ").
			replaceAll(",\\s++", ",").
			replaceAll("\\s++=\\s++", "=");

		if (!Util.balanced(ruleExpression, '(', ')')) {
			throw new ADarwinException("Unbalanced expression: \"" + ruleExpression + "\"");
		}
    }

	public void produce(RuleConsumer ruleConsumer) {
    	String[] subExpressions = parse(ruleExpression);

    	for (int rLoop = 0; rLoop < subExpressions.length; rLoop++) {
    		String subExpression = subExpressions[rLoop].trim();
			Rule rule = buildRule(subExpression);
			if (rule instanceof NameRule) {
				logger.reset(CLASSES_VIOLATED + ((NameRule) rule).getName());
			}
			else {
				logger.reset(CLASSES_VIOLATED + subExpression);
			}

			ruleConsumer.consume(rule, logger);
    	}
    }

    private Rule buildRule(String expression) {
		String ruleName = getRuleName(expression);

		if (expression.indexOf('=') == -1) {
        	return getRuleOrVariable(expression);
		}
        else if (getClass(ruleName) == null) {
    		throw new ADarwinException("No such rule: " + ruleName);
    	}

		variables.put(getVariableName(expression), buildRule(ruleName, expression));

    	return Rule.NULL;
    }

	private Rule getRuleOrVariable(String expression) {
		String ruleName = getRuleName(expression);
		
		if (getClass(ruleName) == null &&
			variables.get(ruleName) == null) {
			throw new ADarwinException("No such rule, or variable: \"" + ruleName + "\"");
		}
		else if (getClass(ruleName) != null) {
			return buildRule(ruleName, expression);
		}
		else {
			return (Rule) variables.get(ruleName);
		}
	}

	private String getVariableName(String expression) {
		if (expression.indexOf('=') != -1) {
			return expression.substring(0, expression.indexOf('='));
		}

		return "";
	}

	private String getRuleName(final String expression) {
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

    private Rule buildRule(String ruleName, String expression) {
    	String[] arguments = getParameters(expression);

		Constructor[] constructors = getClass(ruleName).getConstructors();

		for (int cLoop = 0; cLoop < constructors.length; ++cLoop) {
			Constructor constructor = constructors[cLoop];

			if (hasSimpleForm(constructor, arguments.length)) {
				return createSimpleRule(arguments, constructor);
			}
			else if (hasAggregateForm(constructor)) {
				return createAggregateRule(arguments, constructor);
			}
		}

		throw new ADarwinException("No constructor for: " + expression);
    }

    private Class getClass(String ruleName) {
    	String className = ruleMapping.getProperty(ruleName);
		
		if (className == null) {
			return null;
		}

    	try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ADarwinException("Unable to find class: " + className, e);
		}
	}

	private Rule createSimpleRule(String[] arguments, Constructor constructor) {
		Object[] constructorParameters = new Object[arguments.length];

		for (int cLoop = 0; cLoop < constructorParameters.length; cLoop++) {
			if (Rule.class.isAssignableFrom(constructor.getParameterTypes()[cLoop])) {
				constructorParameters[cLoop] = getRuleOrVariable(arguments[cLoop]);
			}
			else {
				constructorParameters[cLoop] = arguments[cLoop];
			}
		}

		return createRule(constructor, constructorParameters);
	}

	private Rule createAggregateRule(String[] arguments, Constructor constructor) {
		if (constructor.getParameterTypes()[0].equals(Rule[].class)) {
			Rule[] constructorParameter = new Rule[arguments.length];

			for (int cLoop = 0; cLoop < arguments.length; cLoop++) {
				String expression = arguments[cLoop];
				constructorParameter[cLoop] = getRuleOrVariable(expression);
			}

			return createRule(constructor, new Object[] {constructorParameter});
		}
		else {
			return createRule(constructor, new Object[] {arguments});
		}
	}

	private Rule createRule(Constructor constructor, Object[] parameters) {
		try {
			return (Rule) constructor.newInstance(parameters);
		} catch (Exception e) {
			throw new ADarwinException(e);
		}
	}

	private boolean hasAggregateForm(Constructor constructor) {
		Class[] parameterTypes = constructor.getParameterTypes();
		return parameterTypes.length == 1 &&
			(Rule[].class.equals(parameterTypes[0]) ||
			 String[].class.equals(parameterTypes[0]));
	}

	private boolean hasSimpleForm(Constructor constructor, int length) {
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
