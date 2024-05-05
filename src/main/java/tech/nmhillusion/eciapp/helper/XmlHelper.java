package tech.nmhillusion.eciapp.helper;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.nmhillusion.n2mix.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public abstract class XmlHelper {
    public static List<Node> getNodeFromList(String nodeName, NodeList nodeList) {
        final int nodeListLength = nodeList.getLength();
        final List<Node> nodeResultList = new ArrayList<>();

        for (int nodeIdx = 0; nodeIdx < nodeListLength; ++nodeIdx) {
            final Node node = nodeList.item(nodeIdx);
            if (node.getNodeName().equals(nodeName)) {
                nodeResultList.add(node);
            }
        }
        return nodeResultList;
    }

    public static String joiningNodeList(List<Node> nodeList, String delimiter, boolean isTrimValue) {
        Stream<String> nodeContentStream = nodeList.stream()
                .map(Node::getTextContent);

        if (isTrimValue) {
            nodeContentStream = nodeContentStream
                    .map(StringUtil::trimWithNull)
                    .map(s -> s.replace("\n", " ")
                            .replace("\t", " ")
                    )
                    .map(s -> {
                        while (s.contains("  ")) {
                            s = s.replace("  ", " ");
                        }
                        return s;
                    });
        }

        return nodeContentStream
                .collect(Collectors.joining(delimiter))
                ;
    }
}
