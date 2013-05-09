package crg.turtle.ast;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import clojure.lang.IPersistentMap;
import beaver.Symbol;

public class PropertyListNode extends Symbol implements AstNode {
  private final Object node;
  private final List<PredicateObjects> properties;

  public PropertyListNode(Object node, List<PredicateObjects> properties) {
    this.node = node;
    this.properties = properties;
  }

  public List<PredicateObjects> getProperties() { return properties; }

  public Object getNode(IPersistentMap prefixes) {
    return node;
  }

  public List<Triple> getTriples(IPersistentMap prefixes, List<Triple> triples) {
    for (PredicateObjects pos: properties) {
      Object p = pos.getPredicate();
      for (Object o: pos.getObjects()) {
        if (o instanceof AstNode) ((AstNode)o).addAsObject(prefixes, triples, node, p);
        else triples.add(new Triple(node, p, o));
      }
    }
    return triples;
  }

  public List<Triple> addAsObject(IPersistentMap prefixes, List<Triple> triples, Object s, Object p) {
    triples.add(new Triple(s, p, node));
    return getTriples(prefixes, triples);
  }

}
