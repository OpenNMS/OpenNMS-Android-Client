package org.opennms.gsoc.nodes;

import org.opennms.gsoc.model.Node;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NodesServerCommunication {

    List<Node> getNodes(String url)
            throws InterruptedException, ExecutionException, IOException;

}
