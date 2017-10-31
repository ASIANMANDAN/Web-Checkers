package com.webcheckers.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Player;
import org.junit.Test;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The unit test suite for the {@code home.ftl} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class HomeViewTest {

    private static final String SIGNOUT_LINK_TAG = "<a href=\"/signout\">Sign out [player]</a>";
    private static final String SIGNIN_LINK_TAG = "<a href=\"/signin\">Sign-in</a>";
    private static final String PLAY_BUTTON = "<button type=\"submit\">Play!</button>";
    private static final String SIGNOUT_BUTTON = "<button>Sign Out</button>";
    private static final String SIGNIN_BUTTON = "<button>Sign In</button>";

    private final TemplateEngine engine = new FreeMarkerEngine();

    /**
     * Test the home view does not display certain info when player is signed out.
     */
    @Test
    public void test_not_SignedIn() {
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);

        vm.put("title", "Welcome!");
        vm.put(GetHomeRoute.PLAYERS_ONLINE_ATTR, "0");
        vm.put(GetHomeRoute.CURR_PLAYER, null);
        vm.put(GetHomeRoute.PLAYERS_LIST_ATTR, null);
        vm.put(GetHomeRoute.MESSAGE_ATTR, null);

        final String viewHtml = engine.render(modelAndView);

        assertTrue("Sign In button exists", viewHtml.contains(SIGNIN_BUTTON));
        assertTrue("Sign In link exists", viewHtml.contains(SIGNIN_LINK_TAG));
        assertFalse("Play button is generated", viewHtml.contains(PLAY_BUTTON));
        assertFalse("Sign Out button is generated", viewHtml.contains(SIGNOUT_BUTTON));
        assertFalse("Sign Out link is generated", viewHtml.contains(SIGNOUT_LINK_TAG));
    }

    /**
     * Test the home view displays certain info when player is signed out.
     */
    @Test
    public void test_SignedIn() {
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);

        //Mock a Player object to pass in for current player
        final Player player = mock(Player.class);
        when(player.getUsername()).thenReturn("player");

        final HashSet<String> playersOnline = new HashSet<>();
        playersOnline.add("OtherPlayer");

        vm.put("title", "Welcome!");
        vm.put(GetHomeRoute.PLAYERS_ONLINE_ATTR, "0");
        vm.put(GetHomeRoute.CURR_PLAYER, player);
        vm.put(GetHomeRoute.PLAYERS_LIST_ATTR, playersOnline);
        vm.put(GetHomeRoute.MESSAGE_ATTR, null);

        final String viewHtml = engine.render(modelAndView);

        assertFalse("Sign In button is generated", viewHtml.contains(SIGNIN_BUTTON));
        assertFalse("Sign In link is generated", viewHtml.contains(SIGNIN_LINK_TAG));
        assertTrue("Play button exists", viewHtml.contains(PLAY_BUTTON));
        assertTrue("Sign Out button exists", viewHtml.contains(SIGNOUT_BUTTON));
        assertTrue("Sign Out link exists", viewHtml.contains(SIGNOUT_LINK_TAG));
    }
}
