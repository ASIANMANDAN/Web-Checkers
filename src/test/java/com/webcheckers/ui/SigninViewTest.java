package com.webcheckers.ui;

import org.junit.Test;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * The unit test suite for the {@code signin.ftl} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class SigninViewTest {

    private static final String HOME_LINK_TAG = "<a href=\"/\">My Home</a>";
    private static final String SIGN_IN_LINK_TAG = "<a href=\"/signin\">Sign-in</a>";
    private static final String SIGN_IN_BUTTON = "<button type=\"submit\">Sign In</button>";

    private final TemplateEngine engine = new FreeMarkerEngine();

    /**
     * Test the sign in view rendered when either the link or button are clicked
     */
    @Test
    public void test_new_signin(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSigninRoute.VIEW_NAME);

        vm.put("title", "Player Sign-in");

        final String viewHtml = engine.render(modelAndView);

        assertTrue("My Home link exists.", viewHtml.contains(HOME_LINK_TAG));
        assertTrue("Sign-in link exists.", viewHtml.contains(SIGN_IN_LINK_TAG));
        assertTrue("Sign In button exists.", viewHtml.contains(SIGN_IN_BUTTON));
    }

    /**
     * Test the sign in page displays the correct error message when the username is blank.
     */
    @Test
    public void test_blank_username(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSigninRoute.VIEW_NAME);

        vm.put("title", "Player Sign-in");
        vm.put(PostSigninRoute.MESSAGE_ATTR, "Your username cannot be blank.");

        final String viewHtml = engine.render(modelAndView);

        assertTrue("My Home link exists.", viewHtml.contains(HOME_LINK_TAG));
        assertTrue("Sign-in link exists.", viewHtml.contains(SIGN_IN_LINK_TAG));
        assertTrue("Sign In button exists.", viewHtml.contains(SIGN_IN_BUTTON));
        assertTrue("Error message displays.", viewHtml.contains("Your username cannot be blank."));
    }

    /**
     * Test the sign in page displays the correct error message when the username is taken.
     */
    @Test
    public void test_taken_username(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSigninRoute.VIEW_NAME);

        vm.put("title", "Player Sign-in");
        vm.put(PostSigninRoute.MESSAGE_ATTR, "The username you selected is already in use.");

        final String viewHtml = engine.render(modelAndView);

        assertTrue("My Home link exists.", viewHtml.contains(HOME_LINK_TAG));
        assertTrue("Sign-in link exists.", viewHtml.contains(SIGN_IN_LINK_TAG));
        assertTrue("Sign In button exists.", viewHtml.contains(SIGN_IN_BUTTON));
        assertTrue("Error message displays.", viewHtml.contains("The username you selected is already in " +
                "use."));
    }

    /**
     * Test the sign in page displays the correct error message when the username has double quotes.
     */
    @Test
    public void test_double_quotes(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSigninRoute.VIEW_NAME);

        vm.put("title", "Player Sign-in");
        vm.put(PostSigninRoute.MESSAGE_ATTR, "The username you selected was invalid as it contains double quotation " +
                "marks.");

        final String viewHtml = engine.render(modelAndView);

        assertTrue("My Home link exists.", viewHtml.contains(HOME_LINK_TAG));
        assertTrue("Sign-in link exists.", viewHtml.contains(SIGN_IN_LINK_TAG));
        assertTrue("Sign In button exists.", viewHtml.contains(SIGN_IN_BUTTON));
        assertTrue("Error message displays.", viewHtml.contains("The username you selected was invalid as " +
                "it contains double quotation marks"));
    }
}
