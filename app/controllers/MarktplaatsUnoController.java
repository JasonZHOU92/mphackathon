package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.uno;

public class MarktplaatsUnoController extends Controller {
    public static Result update() {
        return ok(uno.render(120));
    }
}
