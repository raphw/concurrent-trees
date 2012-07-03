package com.googlecode.concurrenttrees.examples.usage;

import com.googlecode.concurrenttrees.common.CharSequenceUtil;
import com.googlecode.concurrenttrees.radix.node.concrete.NaiveCharSequenceNodeFactory;
import com.googlecode.concurrenttrees.solver.LCSubstringSolver;

/**
 * @author Niall Gallagher
 */
public class LCSubstringSolverUsage {

    static final String document1 =
            "albert einstein, was a german theoretical physicist who developed the theory of general relativity";

    static final String document2 =
            "near the beginning of his career, albert einstein thought that newtonian mechanics was no longer " +
            "enough to reconcile the laws of classical mechanics with the laws of the electromagnetic field";

    static final String document3 =
            "in late summer 1895, at the age of sixteen, albert einstein sat the entrance examinations for " +
            "the swiss federal polytechnic in zurich";

    public static void main(String[] args) {
        LCSubstringSolver solver = new LCSubstringSolver(new NaiveCharSequenceNodeFactory());

        solver.add(document1);
        solver.add(document2);
        solver.add(document3);

        String longestCommonSubstring = CharSequenceUtil.toString(solver.getLongestCommonSubstring());
        System.out.println(longestCommonSubstring);
    }
}