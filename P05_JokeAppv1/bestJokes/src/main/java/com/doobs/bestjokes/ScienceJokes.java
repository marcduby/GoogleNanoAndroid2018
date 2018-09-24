package com.doobs.bestjokes;

import java.util.ArrayList;
import java.util.List;

/**
 * Joke generating class
 *
 */
public class ScienceJokes {

    public static void main(String[] args) {
        // create the joke class
        ScienceJokes scienceJokes = new ScienceJokes();

        // print the joke
        System.out.println(scienceJokes.getAtomJoke());
    }

    /**
     * get a science joke
     *
     * @return
     */
    public String getAtomJoke() {
        // local variables
        List<String> stringList = new ArrayList<String>();
        StringBuffer stringBuffer = new StringBuffer();

        // build joke
        stringList.add("Two atoms are walking down the street, when one trips and falls");
        stringList.add("The other atom helps him get up and asks: 'Are you OK?'");
        stringList.add("The second atom responds: 'I think I lost an electron'");
        stringList.add("The first atom asks: 'Are you sure?'");
        stringList.add("The second atom replies: 'Yes, I am positive.'");

        // add in CRs
        for (String string : stringList) {
            stringBuffer.append(string);
            stringBuffer.append("\n");
        }

        // return
        return stringBuffer.toString();
    }

}
