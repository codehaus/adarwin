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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.adarwin.rule.Rule;

public class RuleBuilder implements RuleProducer {
    private final RuleClassBindings ruleClassBindings;
    private final Map variables = new HashMap();

    public RuleBuilder(RuleClassBindings ruleClassBindings) {
        this.ruleClassBindings = ruleClassBindings;
    }

    public void produce(String expression, RuleConsumer ruleBuilderListener)
		throws ADarwinException {

    	String[] subExpression = parse(expression);

    	for (int rLoop = 0; rLoop < subExpression.length; rLoop++) {
    		buildRule(subExpression[rLoop], ruleBuilderListener);
    	}
    }

	public Rule[] buildRules(String expression) throws ADarwinException {
		String[] subExpression = parse(expression);

		List rules = new ArrayList(subExpression.length);

		for (int rLoop = 0; rLoop < subExpression.length; rLoop++) {
			rules.add(buildRule(subExpression[rLoop]));
		}

		return (Rule[]) rules.toArray(new Rule[0]);
	}

    public Rule buildRule(String expression) throws ADarwinException {
		checkBalancedParathesis(expression);
        try {
        	String variable = getVariableValue(expression);
			String name = getName(expression);
			
			if (variables.get(name) != null) {
				return getVariable(name);
			}
			
            Rule rule = buildRule(name, getParameters(expression));
            if (variable != null && variable.length() > 0) {
            	variables.put(variable, rule);
            	return Rule.NULL;
            }
            return rule;
        } catch (ADarwinException e) {
            throw e;
        } catch (Exception e) {
            throw new ADarwinException(e);
        }
    }

    public Rule buildRule(String expression, RuleConsumer ruleBuilderListener)
		throws ADarwinException {

		checkBalancedParathesis(expression);
        try {
        	String variable = getVariableValue(expression);
			String name = getName(expression);
			
			if (variables.get(name) != null) {
				Rule variableValue = getVariable(name);
				ruleBuilderListener.consume(variableValue, ruleClassBindings);
				return variableValue;
			}
			
            Rule rule = buildRule(name, getParameters(expression));
            if (variable != null && variable.length() > 0) {
            	variables.put(variable, rule);
            	ruleBuilderListener.consume(Rule.NULL, ruleClassBindings);
            	return Rule.NULL;
            }
            ruleBuilderListener.consume(rule, ruleClassBindings);
            return rule;
        } catch (ADarwinException e) {
            throw e;
        } catch (Exception e) {
            throw new ADarwinException(e);
        }
    }

    public Rule getVariable(String name) {
		return (Rule) variables.get(name);
	}

	private String getVariableValue(String expression) {
		expression = expression.replaceAll("\\s++", "");
		
		if (expression.indexOf('=') != -1) {
			return expression.substring(0, expression.indexOf('='));
		}
		return "";

	}
	private void checkBalancedParathesis(String expression) throws ADarwinException {
		if (countNumberOf(expression, "(") != countNumberOf(expression, ")")) {
			throw new ADarwinException("Unbalanced expression: \"" + expression + "\"");
		}
	}

	private int countNumberOf(String searchIn, String searchFor) {
		int count = 0;
		int pos = pos = searchIn.indexOf(searchFor, 0);
		while (pos != -1) {
			count++;
			pos += searchFor.length();
			pos = searchIn.indexOf(searchFor, pos);
		}

		return count;
	}

	private String getName(String expression) {
		expression = expression.replaceAll("\\s++", "");
		
		int startOfName = 0;
		
		if (expression.indexOf('=') != -1) {
			startOfName = expression.indexOf('=') + 1;
		}
		
		int endOfName = expression.length();
		
        if (expression.indexOf('(') != -1) {
			endOfName = expression.indexOf('(');
        }

		return expression.substring(startOfName, endOfName);        
    }

    private String[] getParameters(String expression) {
		expression = expression.replaceAll(",\\s++", ",");
    	
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

    private Rule buildRule(String name, String[] arguments) throws ADarwinException,
    	IllegalAccessException, InstantiationException, InvocationTargetException {

        Class ruleClass = ruleClassBindings.getClass(name);

		if (ruleClass == null) {
			throw new ADarwinException("No such rule: " + name);
		}

		Constructor ruleConstructor = getConstructor(name, ruleClass, arguments);
		if (!hasAggregateForm(ruleConstructor)) {
			Object[] constructorParameters = new Object[arguments.length];

			for (int cLoop = 0; cLoop < constructorParameters.length; cLoop++) {
				if (Rule.class.isAssignableFrom(ruleConstructor.getParameterTypes()[cLoop])) {
					constructorParameters[cLoop] = buildRule(arguments[cLoop]);
				}
				else {
					constructorParameters[cLoop] = arguments[cLoop];
				}
			}

			return (Rule) ruleConstructor.newInstance(constructorParameters);
		}
		else if (ruleConstructor.getParameterTypes()[0].equals(Rule[].class)) {
			Rule[] constructorParameter = new Rule[arguments.length];

			for (int cLoop = 0; cLoop < arguments.length; cLoop++) {
				constructorParameter[cLoop] = buildRule(arguments[cLoop]);
			}
			
			return (Rule) ruleConstructor.newInstance(new Object[] {constructorParameter});
		}
		else {
			return (Rule) ruleConstructor.newInstance(new Object[] {arguments});
		}
    }

    private Constructor getConstructor(String name, Class ruleClass, String[] arguments)
		throws ADarwinException {

		Constructor[] constructors = ruleClass.getConstructors();

		for (int cLoop = 0; cLoop < constructors.length; ++cLoop) {
			Constructor constructor = constructors[cLoop];

			if (hasCorrectSignature(name, constructor, arguments.length)) {
				return constructor;
			}
		}

		throw new ADarwinException("No constructor for: " + name + '(' + concat(arguments) + ')');
    }

	private String concat(Object[] arguments) {
		StringBuffer buffer = new StringBuffer();

			for (int aLoop = 0; aLoop < arguments.length; aLoop++) {
				if (aLoop != 0) {
					buffer.append(", ");
				}
				buffer.append(arguments[aLoop]);
			}

		return buffer.toString();
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
