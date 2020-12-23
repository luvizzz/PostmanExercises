package cases;

import domain.MultipleOperationsBody;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import steps.Steps;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;

public class Multiplication {

    Steps testSteps = new Steps();

    @Test
    void test2_2_expect4() {
        Response response = testSteps.getFromMathJs("2*2");
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasSingleValue(response, 4.0);
    }

    @Test
    void test8_2_expectFailure() {
        Response response = testSteps.getFromMathJs("8*2");
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasSingleValue(response, -6.0);
    }

    @Test
    void test800_89999_expect71999_2() {
        Response response = testSteps.getFromMathJs("800*89.999");
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasSingleValue(response, 71999.2);
    }

    @Test
    void test3_3_expect9() {
        MultipleOperationsBody expr = new MultipleOperationsBody(List.of("3*3"));
        Response response = testSteps.postInMathJs(expr);
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasMultipleValues(response, List.of(9.0));
    }

    @Test
    void testMultipleOperations_expectSuccess() {
        MultipleOperationsBody expr = new MultipleOperationsBody(List.of(
                "3*3",
                "0*0",
                "800*12500",
                "-9000*1",
                "1000*10")
        );
        Response response = testSteps.postInMathJs(expr);
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasMultipleValues(response, List.of(
                9.0,
                0.0,
                1e+7,
                -9000.0,
                10000.0)
        );
    }
}
