package tech.nmhillusion.eciapp.service_impl.un_sanction.parser;

import org.w3c.dom.Node;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public abstract class XmlNodeParser<T> {
    public abstract T parse(Node xmlNode, String delimiter) throws Exception;
}
