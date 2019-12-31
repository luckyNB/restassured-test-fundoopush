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

import java.io.File;

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

    @Test
    public void givenTokenAndUserDetails_WhenCorrect_ShouldChangePassword() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "5e0985c14d22670032530f13");
        jsonObject.put("email", "laxmanbhosale7374@gmail.com");
        jsonObject.put("password", "123456");
        jsonObject.put("new_password", "abcd1234");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(new Header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDk4NWMxNGQyMjY3MDAzMjUzMGYxMyJ9LCJpYXQiOjE1Nzc2ODI0OTMsImV4cCI6MTU3Nzc2ODg5M30.jh2CJZdfLQ47GLDzJ5HOCplljRcb9hmJ3V6-51US8Kk"))
                .body(jsonObject.toJSONString())
                .post("https://fundoopush-backend-dev.bridgelabz.com/account/change-password");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        System.out.println(status + "    " + message);
        int code = response.getStatusCode();
        Assert.assertEquals(200, code);
    }


    @Test
    public void givenToken_Image_TitleAndDescriptionForPost_RedirectionLinks_isPublished_isArchieved_youTubeFlag_youtubelink_WhenCorrect_ShouldReturnTrueAndSuccessMessage() throws ParseException {
        File testUploadFile = new File("/home/user/Pictures/Screenshot from 2019-06-14 10-37-20.png");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDk4NWMxNGQyMjY3MDAzMjUzMGYxMyJ9LCJpYXQiOjE1Nzc3NjUyNDEsImV4cCI6MTU3Nzg1MTY0MX0.Fv5utpMpzuowrqZT9i4TQzNjEPFyY5JvLSHZLKAZmGU")
                .multiPart("image", testUploadFile)
                .formParam("title", "ganeshji")
                .formParam("description", "goddemo ganesh")
                .formParam("redirect_link", "https://www.google.com")
                .formParam("is_published", false)
                .formParam("archive", false)
                .formParam("youtube_flag", false)
                .formParam("youtube_url", "https://www.youtube.com/watch?v=yDdBOspPp_c")
                .formParam("video_link", "https://www.youtube.com/watch?v=yDdBOspPp_c")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse((body.print()));
        int statusCode = response.getStatusCode();
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertEquals(201, statusCode); // response.then().
        Assert.assertTrue(status);
        Assert.assertEquals("Redirect added Successfully", message);
    }

    @Test
    public void givenToken_WhenValidatedSuccessfully_ShouldReturnRedirectsList() throws ParseException {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDk4NWMxNGQyMjY3MDAzMjUzMGYxMyJ9LCJpYXQiOjE1Nzc3NjUyNDEsImV4cCI6MTU3Nzg1MTY0MX0.Fv5utpMpzuowrqZT9i4TQzNjEPFyY5JvLSHZLKAZmGU")
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("All Redirects retrieved Successfully", message);
    }

    @Test
    public void givenToken_Id_Image_OnUpdate_ShouldUpdateTheRedirectLink() throws ParseException {
        File testUploadFile = new File("/home/user/Pictures/Screenshot from 2019-06-14 10-37-20.png");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDk4NWMxNGQyMjY3MDAzMjUzMGYxMyJ9LCJpYXQiOjE1Nzc3NjUyNDEsImV4cCI6MTU3Nzg1MTY0MX0.Fv5utpMpzuowrqZT9i4TQzNjEPFyY5JvLSHZLKAZmGU")
                .formParam("_id", "5e0acf3f4d22670032531002")
                .multiPart("image", testUploadFile)
                .formParam("title", "Laxmans File")
                .formParam("description", "Update new file to existing file")
                .put("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        JSONObject object = (JSONObject) new JSONParser().parse(response.getBody().print());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("Redirect updated Successfully", message);
        Assert.assertTrue(status);
    }

    @Test
    public void givenPostId_WhenPostExists_ShouldDeletePost() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("_id", "5e0ad3034d2267003253100c");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDk4NWMxNGQyMjY3MDAzMjUzMGYxMyJ9LCJpYXQiOjE1Nzc3NjUyNDEsImV4cCI6MTU3Nzg1MTY0MX0.Fv5utpMpzuowrqZT9i4TQzNjEPFyY5JvLSHZLKAZmGU")
                .body(jsonObject.toJSONString())
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects/delete");
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(response.getBody().print());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertEquals("Redirect deleted Successfully", message);
        Assert.assertTrue(status);
    }

    @Test
    public void getAllRedirectsBridgelabzWebsitesPosts() throws ParseException {
        Response response = RestAssured.given().get("https://fundoopush-backend-dev.bridgelabz.com/bl-redirects");
        int statusCode = response.getStatusCode();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.getBody().print());
        boolean status = (boolean) jsonObject.get("status");
        String message = (String) jsonObject.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals("All Redirects retrieved Successfully", message);
        Assert.assertEquals(200, statusCode);
    }
}
