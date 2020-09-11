package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* Table containing nodes.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NodeTreeSnapshot {

    /**
    * Parent node index.
    */
    private java.util.List<Integer> parentIndex;

    /**
    * `Node`'s nodeType.
    */
    private java.util.List<Integer> nodeType;

    /**
    * `Node`'s nodeName.
    */
    private java.util.List<Integer> nodeName;

    /**
    * `Node`'s nodeValue.
    */
    private java.util.List<Integer> nodeValue;

    /**
    * `Node`'s id, corresponds to DOM.Node.backendNodeId.
    */
    private java.util.List<Integer> backendNodeId;

    /**
    * Attributes of an `Element` node. Flatten name, value pairs.
    */
    private java.util.List<java.util.List<Integer>> attributes;

    /**
    * Only set for textarea elements, contains the text value.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareStringData textValue;

    /**
    * Only set for input elements, contains the input's associated text value.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareStringData inputValue;

    /**
    * Only set for radio and checkbox input elements, indicates if the element has been checked
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareBooleanData inputChecked;

    /**
    * Only set for option elements, indicates if the element has been selected
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareBooleanData optionSelected;

    /**
    * The index of the document in the list of the snapshot documents.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareIntegerData contentDocumentIndex;

    /**
    * Type of a pseudo element node.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareStringData pseudoType;

    /**
    * Whether this DOM node responds to mouse clicks. This includes nodes that have had click event listeners attached via JavaScript as well as anchor tags that naturally navigate when clicked.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareBooleanData isClickable;

    /**
    * The selected url for nodes with a srcset attribute.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareStringData currentSourceURL;

    /**
    * The url of the script (if any) that generates this node.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareStringData originURL;



}