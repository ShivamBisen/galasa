/*
 * Copyright contributors to the Galasa project
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package dev.galasa.framework.api.secrets.internal;

import java.time.Instant;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;

import com.google.gson.JsonObject;

import dev.galasa.framework.api.common.BaseServletTest;
import dev.galasa.framework.api.common.resources.GalasaResourceValidator;

public class SecretsServletTest extends BaseServletTest {

    protected static final Map<String, String> REQUEST_HEADERS = Map.of("Authorization", "Bearer " + BaseServletTest.DUMMY_JWT);
    protected static final String BASE64_ENCODING = "base64";

    protected JsonObject createSecretJson(String value, String encoding) {
        JsonObject secretJson = new JsonObject();
        if (value != null) {
            secretJson.addProperty("value", value);
        }

        if (encoding != null) {
            secretJson.addProperty("encoding", encoding);
        }

        return secretJson;
    }

    protected JsonObject createSecretJson(String value) {
        return createSecretJson(value, null);
    }

    protected JsonObject generateUsernamePasswordSecretJson(
        String secretName,
        String username,
        String password,
        String encoding,
        String description,
        String lastUpdatedUser,
        Instant lastUpdatedTime
    ) {
        JsonObject secretJson = new JsonObject();
        secretJson.addProperty("apiVersion", GalasaResourceValidator.DEFAULT_API_VERSION);

        String type = "UsernamePassword";
        secretJson.add("metadata", generateExpectedMetadata(secretName, type, encoding, description, lastUpdatedUser, lastUpdatedTime));
        secretJson.add("data", generateExpectedUsernamePasswordData(username, password, encoding));

        secretJson.addProperty("kind", "GalasaSecret");

        return secretJson;
    }

    protected JsonObject generateUsernameSecretJson(
        String secretName,
        String username,
        String encoding,
        String description,
        String lastUpdatedUser,
        Instant lastUpdatedTime
    ) {
        JsonObject secretJson = new JsonObject();
        secretJson.addProperty("apiVersion", GalasaResourceValidator.DEFAULT_API_VERSION);

        String type = "Username";
        secretJson.add("metadata", generateExpectedMetadata(secretName, type, encoding, description, lastUpdatedUser, lastUpdatedTime));
        secretJson.add("data", generateExpectedUsernameData(username, encoding));

        secretJson.addProperty("kind", "GalasaSecret");

        return secretJson;
    }

    protected JsonObject generateUsernameTokenSecretJson(
        String secretName,
        String username,
        String token,
        String encoding,
        String description,
        String lastUpdatedUser,
        Instant lastUpdatedTime
    ) {
        JsonObject secretJson = new JsonObject();
        secretJson.addProperty("apiVersion", GalasaResourceValidator.DEFAULT_API_VERSION);

        String type = "UsernameToken";
        secretJson.add("metadata", generateExpectedMetadata(secretName, type, encoding, description, lastUpdatedUser, lastUpdatedTime));
        secretJson.add("data", generateExpectedUsernameTokenData(username, token, encoding));

        secretJson.addProperty("kind", "GalasaSecret");

        return secretJson;
    }

    protected JsonObject generateTokenSecretJson(
        String secretName,
        String token,
        String encoding,
        String description,
        String lastUpdatedUser,
        Instant lastUpdatedTime
    ) {
        JsonObject secretJson = new JsonObject();
        secretJson.addProperty("apiVersion", GalasaResourceValidator.DEFAULT_API_VERSION);

        String type = "Token";
        secretJson.add("metadata", generateExpectedMetadata(secretName, type, encoding, description, lastUpdatedUser, lastUpdatedTime));
        secretJson.add("data", generateExpectedTokenData(token, encoding));

        secretJson.addProperty("kind", "GalasaSecret");

        return secretJson;
    }

    private JsonObject generateExpectedMetadata(
        String secretName,
        String type,
        String encoding,
        String description,
        String lastUpdatedUser,
        Instant lastUpdatedTime
    ) {
        JsonObject metadata = new JsonObject();
        metadata.addProperty("name", secretName);
        if (lastUpdatedTime != null) {
            metadata.addProperty("lastUpdatedTime", lastUpdatedTime.toString());
        }

        if (lastUpdatedUser != null) {
            metadata.addProperty("lastUpdatedBy", lastUpdatedUser);
        }

        if (encoding != null) {
            metadata.addProperty("encoding", encoding);
        }

        if (description != null) {
            metadata.addProperty("description", description);
        }

        metadata.addProperty("type", type);

        return metadata;
    }

    private JsonObject generateExpectedUsernameData(String username, String encoding) {
        JsonObject data = new JsonObject();

        if (encoding != null && encoding.equals("base64")) {
            Encoder encoder = Base64.getEncoder();
            username = encoder.encodeToString(username.getBytes());
        }

        data.addProperty("username", username);

        return data;
    }

    private JsonObject generateExpectedTokenData(String token, String encoding) {
        JsonObject data = new JsonObject();

        if (encoding != null && encoding.equals("base64")) {
            Encoder encoder = Base64.getEncoder();
            token = encoder.encodeToString(token.getBytes());
        }

        data.addProperty("token", token);

        return data;
    }

    private JsonObject generateExpectedUsernameTokenData(String username, String token, String encoding) {
        JsonObject data = new JsonObject();

        if (encoding != null && encoding.equals("base64")) {
            Encoder encoder = Base64.getEncoder();
            username = encoder.encodeToString(username.getBytes());
            token = encoder.encodeToString(token.getBytes());
        }

        data.addProperty("username", username);
        data.addProperty("token", token);

        return data;
    }

    private JsonObject generateExpectedUsernamePasswordData(String username, String password, String encoding) {
        JsonObject data = new JsonObject();

        if (encoding != null && encoding.equals("base64")) {
            Encoder encoder = Base64.getEncoder();
            username = encoder.encodeToString(username.getBytes());
            password = encoder.encodeToString(password.getBytes());
        }

        data.addProperty("username", username);
        data.addProperty("password", password);

        return data;
    }
}
