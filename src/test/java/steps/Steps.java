package steps;

import domain.MultipleOperationsBody;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import service.MathJsService;

import java.util.List;

public class Steps {

    MathJsService service = new MathJsService();

    public Response getFromMathJs(String expression) {
        return service.getMathJs(expression);
    }

    public Response postInMathJs(MultipleOperationsBody body) {
        return service.postMathJs(body);
    }

    public void assertResponseCode(Response response, int expectedCode) {
        int actualCode = response.getStatusCode();
        Assertions.assertEquals(expectedCode, actualCode, String.format("StatusCode received (%d) matched expected (%d)", actualCode, expectedCode));
    }

    public void assertResponseBodyHasSingleValue(Response response, double expectedValue) {
        double actualBody = response.htmlPath().getDouble("body");
        Assertions.assertEquals(expectedValue, actualBody, String.format("Body contains value (%f) which matched expected (%f)", actualBody, expectedValue));
    }

    public void assertResponseBodyHasMultipleValues(Response response, List<Double> expectedValues) {
        List<String> actualResponse = response.jsonPath().getList("result");
        for (int index = 0; index < expectedValues.size(); index++) {
            Double actualValue = Double.parseDouble(actualResponse.get(index));
            Double expectedValue = expectedValues.get(index);
            Assertions.assertEquals(expectedValue, actualValue, String.format("Body contains value (%f) which matched expected (%f)", actualValue, expectedValue));
        }
    }
}
