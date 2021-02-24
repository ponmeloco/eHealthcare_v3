package test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;


class Geocoder {

    private static double latlat;
    private static double lnglng;

    private static final String GEOCODING_RESOURCE = "https://geocode.search.hereapi.com/v1/geocode";
    private static final String API_KEY = "KsnUri8CIPkGUdbmUqTKAwNZuG9im4N3dJ3wmcMvxgg";

    public static String GeocodeSync(String query) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String requestUri = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + encodedQuery;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse<String> geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        return geocodingResponse.body();
    }

    public static LatLong decode(String location) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();


        JsonNode responseJsonNode = mapper.readTree(GeocodeSync(location));
        JsonNode items = responseJsonNode.get("items");


        for (JsonNode item : items) {
            JsonNode position = item.get("position");

            latlat = Double.parseDouble(position.get("lat").asText());
            lnglng = Double.parseDouble(position.get("lng").asText());
        }
        return new LatLong(latlat, lnglng);
    }

}

