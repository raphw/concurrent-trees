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

import com.googlecode.concurrenttrees.common.CharSequences;
import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.NodeFactory;
import com.googlecode.concurrenttrees.radix.node.NodeList;
import com.googlecode.concurrenttrees.radix.node.concrete.charsequence.*;
import com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue;
import com.googlecode.concurrenttrees.radix.node.util.NodeUtil;

/**
 * A {@link NodeFactory} which creates various implementations of {@link Node} objects all of which store incoming
 * edge characters as a {@link CharSequence} (a <i>view</i> onto the original key) rather than copying the edge into a
 * character array.
 * <p/>
 * Returns an optimal node implementation depending on arguments supplied, which will be one of:
 * <ul>
 *     <li>{@link CharSequenceNodeDefault} - contains all possible fields</li>
 *     <li>{@link CharSequenceNodeNonLeafNullValue} - does not store a value, returns {@code null} for value</li>
 *     <li>{@link CharSequenceNodeNonLeafVoidValue} - does not store a value, returns {@link VoidValue} for value</li>
 *     <li>{@link CharSequenceNodeLeafVoidValue} - does not store child edges or a value, returns {@link VoidValue} for value</li>
 *     <li>{@link CharSequenceNodeLeafWithValue} - does not store child edges, but does store a value</li>
 * </ul>
 * <p/>
 * When the application supplies {@link VoidValue} for a value, this factory will omit actually storing that value
 * in the tree and will return one of the Void-optimized nodes above which can reduce memory usage.
 *
 * @author Niall Gallagher
 */
public class DefaultCharSequenceNodeFactory implements NodeFactory {

    private static final long serialVersionUID = 1L;

    @Override
    public Node createNode(CharSequence edgeCharacters, Object value, NodeList childNodes, boolean isRoot) {
        assert edgeCharacters != null : "The edgeCharacters argument was null";
        assert isRoot || edgeCharacters.length() > 0 : "Invalid edge characters for non-root node: " + CharSequences.toString(edgeCharacters);
        assert childNodes != null : "The edgeCharacters argument was null";
        assert NodeUtil.hasNoDuplicateEdges(childNodes) : "Duplicate edge detected in list of nodes supplied: " + childNodes;

        if (childNodes.isEmpty()) {
            // Leaf node...
            if (value instanceof VoidValue) {
                return new CharSequenceNodeLeafVoidValue(edgeCharacters);
            }
            else if (value != null) {
                return new CharSequenceNodeLeafWithValue(edgeCharacters, value);
            }
            else {
                return new CharSequenceNodeLeafNullValue(edgeCharacters);
            }
        }
        else {
            // Non-leaf node...
            if (value instanceof VoidValue) {
                return new CharSequenceNodeNonLeafVoidValue(edgeCharacters, childNodes);
            }
            else if (value == null) {
                return new CharSequenceNodeNonLeafNullValue(edgeCharacters, childNodes);
            }
            else {
                return new CharSequenceNodeDefault(edgeCharacters, value, childNodes);
            }
        }
    }

}
