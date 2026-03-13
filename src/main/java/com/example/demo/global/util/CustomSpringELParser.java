package com.example.demo.global.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

// SpEL(Spring Expression Language)
// Expression Language(표현 언어)
public class CustomSpringELParser {
	public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
		
		// 1. 추적용 Map 선언
//		Map<String, Object> contextDump = new HashMap<>();
		
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		
		
		for (int i = 0; i < parameterNames.length; i++) {
			System.out.println("파라미터 이름: " + parameterNames[i] + ", 값: " + args[i]);
            context.setVariable(parameterNames[i], args[i]);
            
//            contextDump.put(parameterNames[i], args[i]); // 추적용
        }
		
//		System.out.println("contextDumps:::" + contextDump);
		
        Object value = parser.parseExpression(key).getValue(context, Object.class);
        System.out.println("value:::" + value);
        
        return value;
	}
}
