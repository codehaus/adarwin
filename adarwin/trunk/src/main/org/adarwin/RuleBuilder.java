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

import org.adarwin.rule.Rule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class RuleBuilder {
    private Grammar grammar;

    public RuleBuilder(Grammar grammar) {
        this.grammar = grammar;
    }

    public Rule buildRule(String expression) throws BuilderException {
		checkBalancedParathesis(expression);
        try {
            return buildRule(getName(expression), getParameters(expression));
        } catch (BuilderException e) {
            throw e;
        } catch (Exception e) {
            throw new BuilderException(e);
        }
    }

	private void checkBalancedParathesis(String expression) throws BuilderException {
		if (countNumberOf(expression, "(") != countNumberOf(expression, ")")) {
			throw new BuilderException("Unbalanced expression: \"" + expression + "\"");
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
        if (expression.indexOf('(') != -1) {
            return expression.substring(0, expression.indexOf('('));
        }
        else {
            return expression;
        }
    }

    private String[] getParameters(String expression) {
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
                        case ' ' :
                            if (depth == 0) {
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

    private Rule buildRule(String name, String[] arguments) throws BuilderException, IllegalAccessException,
        InstantiationException, InvocationTargetException {

        Class ruleClass = getRuleClass(name);

		if (ruleClass == null) {
			throw new BuilderException("No such rule: " + name);
		}

		Constructor ruleConstructor = getConstructor(name, ruleClass, arguments);

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

    private Constructor getConstructor(String name, Class ruleClass, String[] arguments) throws BuilderException {
		Constructor[] constructors = ruleClass.getConstructors();

		for (int cLoop = 0; cLoop < constructors.length; ++cLoop) {
			Constructor constructor = constructors[cLoop];

			if (hasCorrectSignature(name, constructor, arguments.length)) {
				return constructor;
			}
		}

		throw new BuilderException("No constructor for: " + name + '(' + concat(arguments) + ')');
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

	private Class getRuleClass(String name) {
        return grammar.getClass(name);
    }
}
