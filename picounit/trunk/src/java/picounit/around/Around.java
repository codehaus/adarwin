package picounit.around;

import java.lang.reflect.Method;


public interface Around {
	void before(Object object, Method method);

	void after(Object object, Method method);
}
