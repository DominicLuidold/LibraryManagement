package at.fhv.teamg.librarymanagement.webfrontend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloStatelessBeanTest {

    @Test
    public void helloStatelessBeanGreetsCallerOnSayHello() {

        // Arrange
        HelloStatelessBean helloStatelessBean = new HelloStatelessBean();

        // Act
        String answer = helloStatelessBean.sayHello();

        // Assert
        assertTrue(answer.contains("Hello") || answer.contains("hello"), "Caller should be greeted!");
    }
}
