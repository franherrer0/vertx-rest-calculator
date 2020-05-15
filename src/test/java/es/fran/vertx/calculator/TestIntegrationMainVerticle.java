package es.fran.vertx.calculator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.net.ServerSocket;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import es.fran.vertx.calculator.verticle.MainVerticle;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(VertxExtension.class)
public class TestIntegrationMainVerticle {

	int port;

	@BeforeAll
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) throws IOException {

		ServerSocket socket = new ServerSocket(0);
		port = socket.getLocalPort();
		System.out.println("Port:" + port);
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		socket.close();

		DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("HTTP_PORT_TEST", port));

		vertx.deployVerticle(new MainVerticle(), options, testContext.succeeding(id -> testContext.completeNow()));
		// vertx.deployVerticle(new MainVerticle(), testContext.completing()));
	}

	@Test
	public void testAdd200() {
		get("/v1/calc/add/1/5").then().statusCode(200);
	}

	@Test
	public void testAddResultOK() {
		get("/v1/calc/add/1/2").then().statusCode(200).assertThat().body("result", Matchers.equalTo(3f), "opDesc",
				equalTo("1.0 + 2.0"));
	}

	@Test
	public void testSubtract200() {
		get("/v1/calc/subtract/1/5").then().statusCode(200);
	}

	@Test
	public void testSusbractResultOK() {
		get("/v1/calc/subtract/1/2").then().statusCode(200).assertThat().body("result", Matchers.equalTo(-1.0f),
				"opDesc", equalTo("1.0 - 2.0"));
	}

	private static String payload = "{\n" + "  \"op1\": 5.2,\n" + "  \"op2\": 3.0,\n" + "  \"operator\": \"*\"\n" + "}";

	@Test
	public void testMultiplyResultOK() {
		given().contentType(ContentType.JSON).body(payload).post("/v1/calc").then().statusCode(200).assertThat()
				.body("result", Matchers.equalTo(15.6f), "opDesc", equalTo("5.2 * 3.0"));
	}

	@AfterAll
	void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
		testContext.completeNow();
	}
}
