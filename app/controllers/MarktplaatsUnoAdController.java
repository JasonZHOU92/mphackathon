package controllers;

import play.Configuration;
import play.Play;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.uno;

import javax.inject.Inject;

public class MarktplaatsUnoAdController extends Controller {

    @Inject static WSClient ws;
    private static final String myAdsUrl = "https://api.marktplaats.nl/api3/ads/me.json";
    private static Configuration conf = Play.application().configuration();
    private static String apiVer = conf.getString("api_ver");
    private static String accessToken = conf.getString("access_token");
    private static String session = conf.getString("session");
    private static String screenWidth = conf.getString("screenWidth");
    private static String screenHeight = conf.getString("screenHeight");
    private static String appVersion = conf.getString("app_ver");
    private static String magicNumber = conf.getString("magic_number");

    public static Result update() {
        WSRequest request = ws.url(myAdsUrl);

        request.setQueryParameter("api_ver", apiVer);
        request.setQueryParameter("access_token", accessToken);
        request.setQueryParameter("session", session);
        request.setQueryParameter("screen_width", screenWidth);
        request.setQueryParameter("screen_height", screenHeight);
        request.setQueryParameter("app_ver", appVersion);
        request.setQueryParameter("magic_number", magicNumber);
        return ok(uno.render(20));
    }

    public static Result apiCall() {
        return ok(uno.render(20));
    }

}
