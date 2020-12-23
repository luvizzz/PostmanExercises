package cases;

import domain.MultipleOperationsBody;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.Steps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.apache.http.HttpStatus.SC_OK;

public class Addition {

    Steps testSteps = new Steps();

    private static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of("2+2", 4.0),
            Arguments.of("2+2", 6.0),
            Arguments.of("800+89.999", 889.999)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testByGet(String expression, double expected) {
        Response response = testSteps.getFromMathJs(expression);
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasSingleValue(response, expected);
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testByPost(String expression, double expected) {
        MultipleOperationsBody expr = new MultipleOperationsBody(List.of(expression));
        Response response = testSteps.postInMathJs(expr);
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasMultipleValues(response, List.of(expected));
    }

    @Test
    void testMultipleOperations_expectSuccess() {
        MultipleOperationsBody expr = new MultipleOperationsBody(List.of(
                "3+3",
                "0+0",
                "800+12500",
                "-9000+1")
        );
        Response response = testSteps.postInMathJs(expr);
        testSteps.assertResponseCode(response, SC_OK);
        testSteps.assertResponseBodyHasMultipleValues(response, List.of(
                6.0,
                0.0,
                13300.0,
                -8999.0)
        );
    }
}
