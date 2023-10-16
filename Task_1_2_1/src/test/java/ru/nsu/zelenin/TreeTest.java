package ru.nsu.zelenin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Testing Tree class and its methods.
 */
public class TreeTest {

    @Test
    public void emptyEquals() {
        Tree<String> a = new Tree<>("A");
        Tree<String> b = new Tree<>("B");

        a.remove();
        b.remove();
        assertEquals(a, b);
    }

    @Test
    public void equalsTest() {
        Tree<Integer> one = new Tree<>(1);
        var a = one.addChild(2);
        a.addChild(3);
        one.addChild(4);
        Tree<Integer> two = new Tree<>(1);
        two.addChild(4);
        var a1 = two.addChild(2);
        a1.addChild(3);
        assertEquals(one, two);
    }

    @Test
    public void catchAnExceptionWhileAddingDfs() {
        Tree<String> myTree = new Tree<>("i think it's strange");
        myTree.addChild("when people say");
        myTree.addChild("you're the next big thing");
        var a = myTree.addChild("you'll never fade");
        a.addChild("the slightest touch, forced to fold");
        a.addChild("sleight of the hand, modern gold");

        assertThrows(ConcurrentModificationException.class, () -> {
            for (Tree<String> tree : myTree) {
                myTree.addChild("oops");
            }
        });
    }

    @Test
    public void catchAnExceptionWhileRemovingDfs() {
        Tree<String> myTree = new Tree<>("tonight i'll have a look");
        var a = myTree.addChild("and try to find my face again");
        a.addChild("buried beneath this house");
        var c = myTree.addChild("my spirit screams and dies again");
        var d = c.addChild("out back, a monster wears a clock of Persian leather");
        d.addChild("behind the tv screen, i've fallen to my knees");
        c.addChild("i said, you've got me where you want me again");
        Tree<String> subtree = new Tree<>("and i can't turn away");
        subtree.addChild("i'm hanging by a thread");
        subtree.addChild("and i'm feeling like i'll fall");
        subtree.addChild("i'm stuck here in between the shadows of my yesterday");
        d.addChild("i want to get away, i need to get away");
        d.addChild(subtree);

        assertThrows(ConcurrentModificationException.class, () -> {
            for (Tree<String> tree : myTree) {
                a.remove();
            }
        });
    }

    @Test
    public void dfsOrderCheck() {
        Tree<Integer> mine = new Tree<>(1);
        var a = mine.addChild(2);
        a.addChild(3);
        a.addChild(4);
        Tree<Integer> subtree = new Tree<>(5);
        subtree.addChild(6);
        var b = subtree.addChild(7);
        b.addChild(8);
        mine.addChild(subtree);
        int[] checkArray = new int[8];
        int i = 0;

        for (Tree<Integer> tree : mine) {
            checkArray[i++] = tree.getValue();
        }

        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8}, checkArray);
    }

    @Test
    public void bfsOrderCheck() {
        Tree<Integer> mine = new Tree<>(1);
        var a = mine.addChild(2);
        a.addChild(3);
        a.addChild(4);
        Tree<Integer> subtree = new Tree<>(5);
        subtree.addChild(6);
        var b = subtree.addChild(7);
        b.addChild(8);
        mine.addChild(subtree);
        int[] checkArray = new int[8];
        int i = 0;

        for (Iterator<Tree<Integer>> tree = mine.iteratorBfs(); tree.hasNext();) {
            checkArray[i++] = tree.next().getValue();
        }

        assertArrayEquals(new int[] {1, 2, 5, 3, 4, 6, 7, 8}, checkArray);
    }

    @Test
    public void treesEquals() {
        Tree<String> myTree = new Tree<>("time shakes");
        myTree.addChild("found you in the water");
        myTree.addChild("at first you were my father");
        myTree.addChild("and now i love you like a brother");
        Tree<String> myTreeClone = new Tree<>("time shakes");
        myTreeClone.addChild("found you in the water");
        var b = myTreeClone.addChild("at first you were my father");
        myTreeClone.addChild("and now i love you like a brother");
        var c = b.addChild("earthquakes shake the dust behind you");
        Tree<String> subtree = new Tree<>("this world at times will blind you");
        subtree.addChild("still i know i'll see you there");
        c.addChild(subtree);
        c.remove();

        assertEquals(myTree, myTreeClone);
    }

    @Test
    public void emptyTreeIterationDfs() {
        Tree<String> mytree = new Tree<>("when everyone");
        mytree.addChild("you thought you knew");
        var a = mytree.addChild("deserts your fight");
        a.addChild("i'll go with you");
        a.addChild("you're facing down");
        mytree.addChild("a dark hall");
        mytree.addChild("i'll grab my light");
        a.addChild("and go with you...");

        List<String> res = new ArrayList<>();

        mytree.remove();

        for (Tree<String> tree : mytree) {
            res.add(tree.getValue());
        }

        assertEquals(new ArrayList<>(), res);
    }

    @Test
    public void emptyTreeIterationBfs() {
        Tree<String> mytree = new Tree<>("we were at the table by the window of view");
        mytree.addChild("casting shadows, the sun was pushing through");
        var a = mytree.addChild("spoke a lot of words, i don't know if i spoke the truth");
        a.addChild("got so much to lose");
        a.addChild("got so much to prove");
        a.addChild("God, don't let me lose my mind");
        var b = mytree.addChild("trouble on my left, trouble on my right");
        b.addChild("i've been facing troubles almost all my life");
        b.addChild("my sweet love, won't you pull me through?");
        b.addChild("everywhere i look, i catch a glimpse of you");
        b.addChild("i said it was love and i did it for life, didn't do it for you");
        mytree.addChild("ooh, ooh-ooh");
        mytree.addChild("ooh-ooh");

        List<String> res = new ArrayList<>();

        mytree.remove();

        for (Iterator<Tree<String>> tree = mytree.iteratorBfs(); tree.hasNext();) {
            res.add(tree.next().getValue());
        }

        assertEquals(new ArrayList<>(), res);

    }

    @Test
    public void catchAnExceptionWhileRemovingBfs() {
        Tree<String> myTree = new Tree<>("i've been trying real hard");
        var a = myTree.addChild("to, to realise");
        a.addChild("but some thing take a long, long");
        var c = myTree.addChild("long, long time");
        var d = c.addChild("long time");
        d.addChild("hold the phone, hit repeat");
        d.addChild("got me foaming at the knees");
        c.addChild("saw the flame, tasted sin");
        c.addChild("you burn me once again");
        a.addChild("cut the cord, she's a creep");
        a.addChild("aberdeen!");
        myTree.addChild("way back, way back, way back!");

        assertThrows(ConcurrentModificationException.class, () -> {
            for (Iterator<Tree<String>> tree = myTree.iteratorBfs(); tree.hasNext();) {
                a.remove();
            }
        });
    }

    @Test
    public void catchAnExceptionWhileAddingBfs() {
        Tree<String> myTree = new Tree<>("shake me down");
        var a = myTree.addChild("not a lot of people left around");
        a.addChild("who knows now");
        var c = myTree.addChild("softly laying on the ground");
        var d = c.addChild("ooooh");
        d.addChild("not a lot of people left around");
        myTree.addChild("oooh oooh");

        assertThrows(ConcurrentModificationException.class, () -> {
            for (Iterator<Tree<String>> tree = myTree.iteratorBfs(); tree.hasNext();) {
                myTree.addChild("even on a cloudy day i'll keep my eyes fixed on the ground");
            }
        });
    }



}
