package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.Play;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.uno;

public class MarktplaatsUnoAdController extends Controller {

    private static final String myAdsUrl = "https://api.marktplaats.nl/api3/ads/me.json";

    public static Result update() {
        Configuration conf = Play.application().configuration();
        String apiVer = conf.getString("api_ver");
        String accessToken = conf.getString("access_token");
        String session = conf.getString("session");
        String screenWidth = conf.getString("screenWidth");
        String screenHeight = conf.getString("screenHeight");
        String appVersion = conf.getString("app_ver");
        String magicNumber = conf.getString("magic_number");
        String adId = conf.getString("ad_id");




        WSRequest request = WS.client().url(myAdsUrl);
        request.setQueryParameter("api_ver", apiVer);
        request.setQueryParameter("access_token", accessToken);
        request.setQueryParameter("session", session);
        request.setQueryParameter("screenWidth", screenWidth);
        request.setQueryParameter("screenHeight", screenHeight);
        request.setQueryParameter("app_ver", appVersion);
        request.setQueryParameter("magic_number", magicNumber);
        F.Promise<WSResponse> responseRequest = request.get();
        F.Promise<JsonNode> jsonPromise = request.get().map(response -> {
            return response.asJson();
        });
        JsonNode json = jsonPromise.get(1000);
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            JsonNode myAds = json.get("my_ads");
            int viewCount = myAds.get(0).get("view_ad_count").asInt();
            return ok(uno.render(viewCount));
        }
    }

    public static Result apiCall() {
        return ok(uno.render(20));
    }

}
