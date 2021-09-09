package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Junit3TestRunner extends Output {

    @Test
    void run() throws Exception {
        final Class<Junit3Test> clazz = Junit3Test.class;
        final Junit3Test junit3Test = clazz.getConstructor().newInstance();
        final Method[] methods = clazz.getDeclaredMethods();

        Arrays.stream(methods)
              .filter(method -> method.getName().startsWith("test"))
              .forEach(method -> {
                  try {
                      method.invoke(junit3Test);
                  } catch (IllegalAccessException | InvocationTargetException e) {
                      e.printStackTrace();
                  }
              });

        String output = captor.toString().trim();

        assertThat(output).contains("Running Test1");
        assertThat(output).contains("Running Test2");
        assertThat(output).doesNotContain("Running Test3");
    }
}
