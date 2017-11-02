package com.webcheckers.model.board;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The unit test suite for the {@link Piece} component.
 *
 * @author Dan Wang
 */
public class PieceTest {

    private static final Piece.Color red = Piece.Color.RED;
    private static final Piece.Color white = Piece.Color.WHITE;
    private static final Piece.Type single = Piece.Type.SINGLE;
    private static final Piece.Type king = Piece.Type.KING;

    private static Piece CuT1;
    private static Piece CuT2;

    @Before
    public void testSetup(){
        CuT1 = new Piece(red, single);
        CuT2 = new Piece(white, king);
    }

    /**
     * Test the {@link Piece#getColor()} method.
     */
    @Test
    public void test_get_color(){
        assertEquals(Piece.Color.RED, CuT1.getColor());
        assertEquals(Piece.Color.WHITE, CuT2.getColor());
    }

    /**
     * Test the {@link Piece#getType()} method.
     */
    @Test
    public void test_get_type(){
        assertEquals(Piece.Type.SINGLE, CuT1.getType());
        assertEquals(Piece.Type.KING, CuT2.getType());
    }

    /**
     * Test the {@link Piece#equals(Object)} method.
     */
    @Test
    public void test_equals(){
        //Create all different combination of piece attributes
        Piece CuT3 = new Piece(red, king);
        Piece CuT4 = new Piece(white, single);
        Piece CuT5 = new Piece(red, single);
        Piece CuT6 = new Piece(white, king);
        //Test CuT1 will not be equal to pieces that are white or kings.
        assertFalse(CuT1.equals(CuT2));
        assertFalse(CuT1.equals(CuT3));
        assertFalse(CuT1.equals(CuT4));
        //Test Cut2 will not be equal to pieces that are red or singles.
        assertFalse(CuT2.equals(CuT3));
        assertFalse(CuT2.equals(CuT4));
        //Test Cut1 will equal other red single pieces.
        assertTrue(CuT1.equals(CuT5));
        //Test Cut@ will equal other white king pieces.
        assertTrue(CuT2.equals(CuT6));
    }

}
