package at.fhv.jee.example.ejb;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
import static org.junit.Assert.assertThat;

public class HelloStatelessBeanTest {

    @Test
    public void helloStatelessBeanGreetsCallerOnSayHello() {

        // Arrange
        HelloStatelessBean helloStatelessBean = new HelloStatelessBean();

        // Act
        String answer = helloStatelessBean.sayHello();

        // Assert
        assertThat(
            "Caller should be greeted!",
            answer,
            either(containsString("Hello")).or(containsString("hello"))
        );
    }
}
