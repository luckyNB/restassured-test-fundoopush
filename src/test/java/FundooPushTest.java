import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.IsEqual.equalTo;

public class FundooPushTest {
    public static String tokenValue = null;

    @Before
    public void setUp() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "laxmanbhosale7374@gmail.com");
        jsonObject.put("password", "123456");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        String body = response.getBody().asString();
        JSONObject object = (JSONObject) new JSONParser().parse(body);
        String token = (String) object.get("token");
        tokenValue = token;
        RestAssured.baseURI = "https://fundoopush-backend-dev.bridgelabz.com";
    }

    @Test
    public void givenEmailAndPassword_WhenCorrect_ShouldReturnSuccessCode() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "laxmanbhosale7374@gmail.com");
        jsonObject.put("password", "123456");
        Response response = RestAssured.given()
                .body(jsonObject.toJSONString())
                .when()
                .contentType(ContentType.JSON)
                .post("/login");
        response.then().assertThat().statusCode(200);
        response.then().body("status", equalTo(true));
        response.then().body("message", equalTo("Logged in Successfully"));

    }

    @Test
    public void givenEmailIdAndPassword_OnRegister_ShouldReturnSuccessCode() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "laxmanbhosale007@gmail.com");
        jsonObject.put("password", "123456");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        response.prettyPrint();
        response.then().body("status", equalTo(true));
        response.then().body("message", equalTo("Registered Successfully"));
        response.then().statusCode(201);
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
        JSONObject object = (JSONObject) new JSONParser().parse(body.asString());
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
        JSONObject object = (JSONObject) new JSONParser().parse(body.asString());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertEquals(401, code);
        Assert.assertFalse(status);
        Assert.assertEquals("Wrong password", message);
    }

    @Test
    public void givenToken_WhenLogoutFromSystem_ShouldReturnTrueAndSuccesMessage() throws ParseException {
        Header header = new Header("token", tokenValue);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(header)
                .post("https://fundoopush-backend-dev.bridgelabz.com/logout");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.asString());
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
                .header(new Header("token", tokenValue))
                .body(jsonObject.toJSONString())
                .post("https://fundoopush-backend-dev.bridgelabz.com/account/change-password");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.asString());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        int code = response.getStatusCode();
        Assert.assertEquals(200, code);
    }


    @Test
    public void givenToken_Image_TitleAndDescriptionForPost_RedirectionLinks_isPublished_isArchieved_youTubeFlag_youtubelink_WhenCorrect_ShouldReturnTrueAndSuccessMessage() throws ParseException {
        File testUploadFile = new File("/home/user/Pictures/Screenshot from 2019-06-14 10-37-20.png");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token", tokenValue)
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
        JSONObject object = (JSONObject) new JSONParser().parse((body.asString()));
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
                .header("token", tokenValue)
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.asString());
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
                .header("token", tokenValue)
                .formParam("_id", "5e0acf3f4d22670032531002")
                .multiPart("image", testUploadFile)
                .formParam("title", "Laxmans File")
                .formParam("description", "Update new file to existing file")
                .put("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        JSONObject object = (JSONObject) new JSONParser().parse(response.getBody().asString());
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
                .header("token", tokenValue)
                .body(jsonObject.toJSONString())
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects/delete");
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(response.getBody().asString());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertEquals("Redirect deleted Successfully", message);
        Assert.assertTrue(status);
    }

    @Test
    public void getAllRedirectsBridgelabzWebsitesPosts() throws ParseException {
        Response response = RestAssured.given().get("https://fundoopush-backend-dev.bridgelabz.com/bl-redirects");
        int statusCode = response.getStatusCode();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.getBody().asString());
        boolean status = (boolean) jsonObject.get("status");
        String message = (String) jsonObject.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals("All Redirects retrieved Successfully", message);
        Assert.assertEquals(200, statusCode);
    }
//Editing the hashtag as per as hashtag _id and adding new hashtag as per as redirect_id.

    @Test
    public void givenTokenAndHashtagBody_OnEdit_ShouldEditHshtag() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirect_id", "5e0ad95f4d2267003253101d");
        jsonObject.put("hashtag", "#Bridgelabz #Automation #Laxman");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .header("token", tokenValue)
                .post("https://fundoopush-backend-dev.bridgelabz.com/hashtag/edit");
        JSONObject object = (JSONObject) new JSONParser().parse(response.getBody().asString());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        int statusCode = response.getStatusCode();
        Assert.assertTrue(status);
        Assert.assertEquals("Hashtag edit done Successfully", message);
        Assert.assertEquals(200, statusCode);
    }

    @Test
    public void givenTokenAndHashtagName_WhenCorrect_ShouldRedirectHashTag() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("hashtagname", "#Laxman #Automationtest #fundoopush")
                .header("token", tokenValue)
                .get("https://fundoopush-backend-dev.bridgelabz.com/redirects/hashtag/{hashtagname}");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(response.getBody().asString());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        int statusCode = response.getStatusCode();

        Assert.assertEquals("Hashtag sent Successfully", message);
        Assert.assertEquals(200, statusCode);
        Assert.assertTrue(status);
    }

    @Test
    public void givenTokenAndURLBody_WhenCorrect_ShouldCreateWebScrappingURL() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", "https://www.deccanchronicle.com/technology/in-other-news/270319/companies-that-are-changing-the-way-education-is-being-delivered-to-st.html");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("token", tokenValue)
                .body(jsonObject.toJSONString())
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/web-scraping");
        ResponseBody body = response.getBody();
        int statusCode = response.getStatusCode();
        JSONObject object = (JSONObject) new JSONParser().parse(response.getBody().asString());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("Successfully scrapped data", message);
    }

    @Test
    public void givenTokenAndHashTagBody_WhenCorrect_ShouldReturnSuccessMessage() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hashtag", "#Laxman #Automationtest #fundoopush");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("token", tokenValue)
                .body(jsonObject.toJSONString())
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/search/hashtag");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.asString());
        boolean status = (boolean) object.get("status");
        String message = (String) object.get("message");
        int statusCode = response.getStatusCode();
        Assert.assertEquals("Successfully searched data", message);
        Assert.assertEquals(200, statusCode);
        Assert.assertTrue(status);
    }

    @Test
    public void givenTokenAndJobData_WhenCorrect_ShouldPostJob() throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirect_id", "5e09e5a64d22670032530fe7");
        jsonObject.put("years_of_experience", "23");
        jsonObject.put("salary", 3.66);
        jsonObject.put("location", "13Pune");
        jsonObject.put("company_profile", "13Automation");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .body(jsonObject.toJSONString())
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs");
        int status = response.getStatusCode();
        String string = response.asString();
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
        ResponseBody body = response.getBody();
    }

    @Test
    public void givenJobIdAndHashtagName_WhenCorrect_ShouldAddHashtagForJob() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("job_id", "5e0d88aa3b17ce008e85dc26");
        jsonObject.put("hashtag", "#Bridgelabz #Nanded #Pune");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .body(jsonObject.toJSONString())
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs/hashtag/add");
        int status = response.getStatusCode();
        String string = response.asString();
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenJobIdAndHashtagId_WhenCorrect_ShouldRemoveTheHashtagFromExistingJob() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("job_id", "5e0d96353b17ce008e85dc65");
        jsonObject.put("hashtag_id", "5d41270b0d205f00a7687cc0");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", tokenValue)
                .body(jsonObject.toJSONString())
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs/hashtag/remove");
        int status = response.getStatusCode();
        String string = response.asString();
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }
}
