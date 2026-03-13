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
		
		
//		1. 설계도 분석 (parseExpression(key))
//		parser는 먼저 전달받은 문자열 #orderCreateRequestDto.orderItemRequests.![productId]를 읽고 구조를 분석(Parsing)합니다. 이때는 실제 데이터가 무엇인지는 모릅니다.
//		#: "아, 이건 변수 이름이구나."
//		. : "그 객체 안에 있는 필드(혹은 getter)를 찾아야겠군."
//		![...]: "이건 리스트의 요소를 하나씩 꺼내서 특정 값만 모으라는 뜻이구나(Projection)."
//
//		2. 재료 찾기 (getValue(context, ...))
//		이제 분석된 설계도를 들고 context를 뒤집니다.
//		context 안에는 아까 for문을 돌면서 넣어둔 orderCreateRequestDto 객체가 들어있죠?
//		SpEL 엔진은 **리플렉션(Reflection)**이라는 기술을 사용해 실제 자바 객체의 메모리 주소로 접근합니다.
//		orderCreateRequestDto.getOrderItemRequests() 메서드를 호출해서 리스트 데이터를 가져옵니다.
//
//		3. 데이터 가공 및 결과 생성
//		질문자님이 쓰신 ![productId]는 SpEL의 아주 강력한 기능입니다. 내부적으로는 다음과 같이 동작합니다.
//		가져온 리스트를 한 바퀴 돕니다.
//		각 요소(OrderItemRequest)마다 getProductId()를 호출합니다.
//		그 결과값들(예: 101, 102)을 모아서 **새로운 ArrayList**를 만듭니다.
//
        Object value = parser.parseExpression(key).getValue(context, Object.class);
        System.out.println("value:::" + value);
        
        return value;
	}
}
