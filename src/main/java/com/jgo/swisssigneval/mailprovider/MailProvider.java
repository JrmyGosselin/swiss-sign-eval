package com.jgo.swisssigneval.mailprovider;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.awaitility.Awaitility;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MailProvider {
    private static final String API_URL = "https://api.mail.tm";

    private static final String CREATE_ACCOUNT = "/accounts";
    private static final String MESSAGES = "/messages";
    private static final String TOKEN = "/token";

    private String email;
    private String jwtToken;

    public String getThrowAwayEmail() {
        return email;
    }

    public String getCode() {
        Awaitility.await().with().timeout(Duration.ofMinutes(2)).until(() -> {
            HttpResponse<JsonNode> response = Unirest.get(API_URL + MESSAGES)
                    .header("Authorization", "Bearer " + jwtToken)
                    .asJson();
            JSONObject messages = new JSONObject(response.getBody().toString());

            int messageCount = messages.getInt("hydra:totalItems");

            if(messageCount == 1) {
                return true;
            } else {
                Thread.sleep(2000);
                return false;
            }
        });

        HttpResponse<JsonNode> finalResponse = Unirest.get(API_URL + MESSAGES)
            .header("Authorization", "Bearer " + jwtToken)
            .asJson();

        JSONObject messages = new JSONObject(finalResponse.getBody().toString());

        String messageId = messages.getJSONArray("hydra:member").getJSONObject(0).getString("id");

        HttpResponse<JsonNode> messageDetailsResponse = Unirest.get(API_URL + MESSAGES + "/" + messageId)
                .header("Authorization", "Bearer " + jwtToken)
                .asJson();

        String rawHtmlMessage = new JSONObject(messageDetailsResponse.getBody().toString())
                .getJSONArray("html")
                .getString(0);

        // Not very clean, I know
        String code = rawHtmlMessage.substring(rawHtmlMessage.indexOf("<h3"),
                rawHtmlMessage.indexOf("</h3>"));
        code = code.substring(code.indexOf(">") + 1);

        return code;
    }

    public static MailProvider initNewAccount() {
        MailProvider mailProvider = new MailProvider();

        // Create account
        JSONObject accountRequestBody = new JSONObject();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        mailProvider.email = "mdupont" + timestamp + "@candassociates.com";
        accountRequestBody = accountRequestBody.put("address", mailProvider.email);
        accountRequestBody = accountRequestBody.put("password", "placeholder");

        HttpResponse<JsonNode> createResponse = Unirest.post(API_URL + CREATE_ACCOUNT)
                .header("Content-Type", "application/json")
                .body(accountRequestBody.toString())
                .asJson();

        if(!createResponse.isSuccess()) {
            throw new RuntimeException(
                    "Account creation failed. Maybe the 3rd party API decided to shut the service down :(");
        }

        // Get corresponding JWT token
        HttpResponse<JsonNode> tokenResponse = Unirest.post(API_URL + TOKEN)
                .header("Content-Type", "application/json")
                .body(accountRequestBody.toString())
                .asJson();

        mailProvider.jwtToken = tokenResponse.getBody().getObject().getString("token");

        return mailProvider;
    }


}
