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
package com.googlecode.concurrenttrees.radix.node.concrete.chararray;

import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.SimpleNodeList;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Niall Gallagher
 */
public class CharArrayNodeNonLeafVoidValueTest {
    
    @Test
    public void testUpdateOutgoingEdge() throws Exception {
        Node node = new CharArrayNodeNonLeafVoidValue("FOO", new SimpleNodeList(new CharArrayNodeDefault("BAR1", 1, SimpleNodeList.EMPTY)));
        node.updateOutgoingEdge(new CharArrayNodeDefault("BAR2", null, SimpleNodeList.EMPTY));
    }

    @Test(expected = IllegalStateException.class)
    public void testUpdateOutgoingEdge_NonExistentEdge() throws Exception {
        Node node = new CharArrayNodeNonLeafVoidValue("FOO", new SimpleNodeList(new CharArrayNodeDefault("BAR", 1, SimpleNodeList.EMPTY)));
        node.updateOutgoingEdge(new CharArrayNodeDefault("CAR", null, SimpleNodeList.EMPTY));
    }

    @Test
    public void testToString() throws Exception {
        Node node = new CharArrayNodeNonLeafVoidValue("FOO", SimpleNodeList.EMPTY);
        Assert.assertEquals("Node{edge=FOO, value=-, edges=[]}", node.toString());
    }

    @Test
    public void testGetOutgoingEdge() throws Exception {
        Node node = new CharArrayNodeNonLeafVoidValue("FOO", new SimpleNodeList(new CharArrayNodeDefault("BAR1", 1, SimpleNodeList.EMPTY)));
        Assert.assertNotNull(node.getOutgoingEdge('B'));
        Assert.assertEquals(1, node.getOutgoingEdge('B').getValue());
    }

    @Test
    public void testGetOutgoingEdge_NonExistent() throws Exception {
        Node node = new CharArrayNodeNonLeafVoidValue("FOO", new SimpleNodeList(new CharArrayNodeDefault("BAR1", 1, SimpleNodeList.EMPTY)));
        Assert.assertNull(node.getOutgoingEdge('C'));
    }
}
