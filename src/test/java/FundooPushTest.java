import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class FundooPushTest {

    @Test
    public void givenEmailAndPassword_WhenCorrect_ShouldReturnSuccessCode() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "laxmanbhosale7374@gmail.com");
        jsonObject.put("password", "123456");

        Response response = RestAssured.given()
                .body(jsonObject.toJSONString())
                .when()
                .contentType(ContentType.JSON)
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        int code = response.statusCode();
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        System.out.println("Status:" + status);
        System.out.println("status code:" + code);
        System.out.println();
        Assert.assertEquals(200, code);
        Assert.assertTrue(status);
        Assert.assertEquals("Logged in Successfully", message);
    }

    @Test
    public void givenEmailIdAndPassword_OnRegister_ShouldReturnSuccessCode() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "laxmanbhosale360@gmail.com");
        jsonObject.put("password", "123456");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        int code = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertEquals(201, code);
        Assert.assertTrue(status);
        Assert.assertEquals("Registered Successfully", message);
    }

    @Test
    public void givenValidEmailIdAndInvalidPassword_ShouldReturnFalseStatusAndWrongStatusCode() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "laxmanbhosale7374@gmail.com");
        jsonObject.put("password", "12345");
        Response response = RestAssured.given()
                .body(jsonObject.toJSONString())
                .when()
                .contentType(ContentType.JSON)
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        int code = response.statusCode();
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        System.out.println("Status:" + status);
        System.out.println("status code:" + code);
        System.out.println();
        Assert.assertEquals(401, code);
        Assert.assertFalse(status);
        Assert.assertEquals("Wrong password", message);
    }

    @Test
    public void givenInvalidEmailAndCorrectPassword_onLogin_ShouldReturnFalseAndWrongPasswordMessage() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "laxmanbhosale7132@gmail.com");
        jsonObject.put("password", "12345");
        Response response = RestAssured.given()
                .body(jsonObject.toJSONString())
                .when()
                .contentType(ContentType.JSON)
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        int code = response.statusCode();
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        System.out.println("Status:" + status);
        System.out.println("status code:" + code);
        System.out.println();
        Assert.assertEquals(401, code);
        Assert.assertFalse(status);
        Assert.assertEquals("Wrong password", message);
    }

    @Test
    public void givenToken_WhenLogoutFromSystem_ShouldReturnTrueAndSuccesMessage() throws ParseException {
        Header header = new Header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDk4NWMxNGQyMjY3MDAzMjUzMGYxMyJ9LCJpYXQiOjE1Nzc2ODI0OTMsImV4cCI6MTU3Nzc2ODg5M30.jh2CJZdfLQ47GLDzJ5HOCplljRcb9hmJ3V6-51US8Kk");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(header)
                .post("https://fundoopush-backend-dev.bridgelabz.com/logout");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int code = response.getStatusCode();
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals(200, code);
        Assert.assertEquals("Logged out successfully from the system", message);


    }
}
