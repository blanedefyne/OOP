package ru.nsu.zelenin;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void catchAnExceptionWhileAddingDFS() {
        Tree<String> myTree = new Tree<>("i think it's strange");
        myTree.addChild("when people say");
        myTree.addChild("you're the next big thing");
        var a = myTree.addChild("you'll never fade");
        a.addChild("the slightest touch, forced to fold");
        a.addChild("sleight of the hand, modern gold");

        assertThrows(ConcurrentModificationException.class,() -> {
            for (Tree<String> tree : myTree) {
                myTree.addChild("oops");
            }
        });
    }

    @Test
    public void catchAnExceptionWhileRemovingDFS() {
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

        assertThrows(ConcurrentModificationException.class,() -> {
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

        for (Iterator<Tree<Integer>> tree = mine.iteratorBFS(); tree.hasNext();) {
            checkArray[i++] = tree.next().getValue();
        }

        assertArrayEquals(new int[] {1, 2, 5, 3, 4, 6, 7, 8}, checkArray);
    }

    @Test
    public void treesEquals() {
        Tree<String> myTree = new Tree<>("T");
        myTree.addChild("o");
        var a = myTree.addChild("b");
        myTree.addChild("i");
        Tree<String> subtree = new Tree<>("Doesn't");
        subtree.addChild("seem");
        var b = subtree.addChild("to");
        b.addChild("matter");
        var c = subtree.addChild("what");
        c.addChild("i do");
        c.addChild("i'm always number 2");
        a.addChild(subtree);
        Tree<String> myTreeClone = new Tree<>("T");
        myTreeClone.addChild("o");
        myTreeClone.addChild("i");
        a.remove();

        assertEquals(myTree, myTreeClone);
    }

    @Test
    public void emptyTreeIteration() {
        Tree<String> mytree = new Tree<>("each");
        mytree.addChild("breath");
        var a = mytree.addChild("i left");
        a.addChild("behind");
        List<String> res = new ArrayList<>();

        mytree.remove();

        for (Tree<String> tree : mytree) {
            res.add(tree.getValue());
        }

        assertEquals(new ArrayList<>(), res);
    }

    @Test
    public void catchAnExceptionWhileRemovingBFS() {
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

        assertThrows(ConcurrentModificationException.class,() -> {
            for (Iterator<Tree<String>> tree = myTree.iteratorBFS(); tree.hasNext();) {
                a.remove();
            }
        });
    }

    @Test
    public void catchAnExceptionWhileAddingBFS() {
        Tree<String> myTree = new Tree<>("shake me down");
        var a = myTree.addChild("not a lot of people left around");
        a.addChild("who knows now");
        var c = myTree.addChild("softly laying on the ground");
        var d = c.addChild("ooooh");
        d.addChild("not a lot of people left around");
        myTree.addChild("oooh oooh");

        assertThrows(ConcurrentModificationException.class,() -> {
            for (Iterator<Tree<String>> tree = myTree.iteratorBFS(); tree.hasNext();) {
                myTree.addChild("even on a cloudy day i'll keep my eyes fixed on the ground");
            }
        });
    }

}
