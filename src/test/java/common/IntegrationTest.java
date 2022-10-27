package common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kitchenpos.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@ExtendWith(DataBaseCleaner.class)
@Inherited
public @interface IntegrationTest {
}
