package controllers;

import play.Configuration;
import play.Play;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.uno;

import javax.inject.Inject;

public class MarktplaatsUnoAdController extends Controller {

    @Inject static WSClient ws;
    private static final String myAdsUrl = "https://api.marktplaats.nl/api3/ads/me.json";
    private static final String accessToken = "e9d8b435-0041-4199-8e0c-5cdd80cc9f7b";

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
//        WSRequest request = ws.url(myAdsUrl);
//        request.setQueryParameter("", )
//        return ok(uno.render(20));
        return ok(uno.render(20));
    }

    public static Result apiCall() {
        return ok(uno.render(20));
    }

}
