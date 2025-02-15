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
package com.googlecode.concurrenttrees.radix.node.util;

import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.NodeList;

import java.util.*;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Static utility methods useful when implementing {@link com.googlecode.concurrenttrees.radix.node.Node}s.
 *
 * @author Niall Gallagher
 */
public class NodeUtil {

    /**
     * Private constructor, not used.
     */
    NodeUtil() {
    }

    /**
     * Returns the index of the node in the given {@link AtomicReferenceArray} whose edge starts with the given
     * first character.
     * <p/>
     * This method expects that some constraints are enforced on the {@link AtomicReferenceArray}:
     * <ul>
     *     <li>
     *         The array must already be in ascending sorted order of the first character of the edge for each node
     *     </li>
     *     <li>
     *         No entries in the array can be null
     *     </li>
     *     <li>
     *         Any existing node in the array cannot be swapped concurrently for another unless the edge associated
     *         with the other node also starts with the same first character
     *     </li>
     * </ul>
     * If these constraints are enforced as expected, then this method will have deterministic behaviour even in the
     * face of concurrent modification.
     *
     * @param childNodes An {@link AtomicReferenceArray} of {@link com.googlecode.concurrenttrees.radix.node.Node} objects, which is used in accordance with
     * the constraints documented in this method
     *
     * @param edgeFirstCharacter The first character of the edge for which the associated node is required
     * @return The index of the node representing the indicated edge, or a value < 0 if no such node exists in the
     * array
     */
    public static int binarySearchForEdge(AtomicReferenceArray<Node> childNodes, char edgeFirstCharacter) {
        // inspired by Collections#indexedBinarySearch()
        int low = 0;
        int high = childNodes.length() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Node midVal = childNodes.get(mid);
            int cmp = midVal.getIncomingEdgeFirstCharacter() - edgeFirstCharacter;

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    /**
     * Checks if any nodes in the given list represent edges having the same first character.
     *
     * @param nodes The list of nodes to validate
     * @return {@code true} if the supplied edges are free of duplicates.
     */
    public static boolean hasNoDuplicateEdges(NodeList nodes) {
        // Sanity check that no two nodes specify an edge with the same first character...
        Set<Character> uniqueChars = new HashSet<Character>(nodes.size());
        for (int index = 0; index < nodes.size(); index++) {
            uniqueChars.add(nodes.get(index).getIncomingEdgeFirstCharacter());
        }
        return nodes.size() == uniqueChars.size();
    }
}
