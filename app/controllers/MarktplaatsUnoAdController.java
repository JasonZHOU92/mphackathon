package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.Play;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.uno;

public class MarktplaatsUnoAdController extends Controller {


    private static Configuration conf = Play.application().configuration();
    private static String apiVer = conf.getString("api_ver");
    private static String session = conf.getString("session");
    private static String screenWidth = conf.getString("screenWidth");
    private static String screenHeight = conf.getString("screenHeight");
    private static String appVersion = conf.getString("app_ver");
    private static String magicNumber = conf.getString("magic_number");
//    private static String adId = conf.getString("ad_id");

    private static JsonNode requestJson(String url, String accessToken) {
        WSRequest request = WS.client().url(url);
        request.setQueryParameter("api_ver", apiVer);
        request.setQueryParameter("access_token", accessToken);
        request.setQueryParameter("session", session);
        request.setQueryParameter("screenWidth", screenWidth);
        request.setQueryParameter("screenHeight", screenHeight);
        request.setQueryParameter("app_ver", appVersion);
        request.setQueryParameter("magic_number", magicNumber);

        F.Promise<JsonNode> jsonPromise = request.get().map(response -> {
            return response.asJson();
        });
        return jsonPromise.get(1000);
    }

    public static Result viewCount(String accessToken) {
        final String myAdsUrl = "https://api.marktplaats.nl/api3/ads/me.json";
        JsonNode json = requestJson(myAdsUrl, accessToken);
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            JsonNode myAds = json.get("my_ads");
            if (myAds != null) {
                int viewCount = myAds.get(0).get("view_ad_count").asInt();
                return ok(uno.render(viewCount));
            } else {
                return badRequest("NAN");
            }
        }
    }

    public static Result favoriteCount(String accessToken) {
        return ok(uno.render(20));
    }

    public static Result bidCount(String accessToken) {
        final String myAdsUrl = "https://api.marktplaats.nl/api3/ads/me.json";
        JsonNode json = requestJson(myAdsUrl, accessToken);
        JsonNode myAds = json.get("my_ads");
        String adId = myAds.get(0).get("urn").asText();
        String adUrl = "https://api.marktplaats.nl/api3/ads/" + adId + ".json";

        JsonNode advertisementJson = requestJson(adUrl, accessToken);
        int bids = advertisementJson.get("bids").size();

        return ok(uno.render(bids));
    }

    public static Result highestBid(String accessToken) {
        final String myAdsUrl = "https://api.marktplaats.nl/api3/ads/me.json";
        JsonNode json = requestJson(myAdsUrl, accessToken);
        JsonNode myAds = json.get("my_ads");
        String adId = myAds.get(0).get("urn").asText();
        String adUrl = "https://api.marktplaats.nl/api3/ads/" + adId + ".json";

        JsonNode advertisementJson = requestJson(adUrl, accessToken);
        JsonNode highestBid = advertisementJson.get("bids").get(0);
        int cents = highestBid.get("value").asInt();
        int euros = cents / 100;

        return ok(views.html.highestBid.render(euros));
    }


    public static Result apiCall() {
        return ok(uno.render(20));
    }

}
