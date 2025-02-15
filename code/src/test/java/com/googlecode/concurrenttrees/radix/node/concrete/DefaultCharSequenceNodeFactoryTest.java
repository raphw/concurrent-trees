/*
 * Copyright 2012-2013 Niall Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.concurrenttrees.radix.node.concrete;

import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.SimpleNodeList;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static junit.framework.Assert.assertTrue;

/**
 * @author Niall Gallagher
 */
public class DefaultCharSequenceNodeFactoryTest {

    private boolean assertions;

    @Before
    public void setUp() {
        assert assertions = true;
    }

    @Test(expected = AssertionError.class)
    public void testCreateNode_NullEdge() throws Exception {
        assertTrue(assertions);
        //noinspection NullableProblems
        new DefaultCharSequenceNodeFactory().createNode(null, 1, SimpleNodeList.EMPTY, false);
    }

    @Test(expected = AssertionError.class)
    public void testCreateNode_EmptyEdgeNonRoot() throws Exception {
        assertTrue(assertions);
        //noinspection NullableProblems
        new DefaultCharSequenceNodeFactory().createNode("", 1, SimpleNodeList.EMPTY, false);
    }

    @Test(expected = AssertionError.class)
    public void testCreateNode_NullEdges() throws Exception {
        assertTrue(assertions);
        //noinspection NullableProblems
        new DefaultCharSequenceNodeFactory().createNode("FOO", 1, null, false);
    }
}
