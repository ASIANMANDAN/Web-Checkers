package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * The UI controller to GET the sign-in page.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetSigninRoute implements Route{

    //FTL file which is responsible for rendering the page
    private static final String VIEW_NAME = "signin.ftl";

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Sign-in page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Player Sign-in");
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
