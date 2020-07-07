package jpuppeteer.cdp.cdp.domain;

/**
*/
public class DOM {

    private jpuppeteer.cdp.CDPSession session;

    public DOM(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Collects class names for the node with given id and all of it's child nodes.
    */
    public jpuppeteer.cdp.cdp.entity.dom.CollectClassNamesFromSubtreeResponse collectClassNamesFromSubtree(jpuppeteer.cdp.cdp.entity.dom.CollectClassNamesFromSubtreeRequest request, int timeout) throws Exception {
        return session.send("DOM.collectClassNamesFromSubtree", request, jpuppeteer.cdp.cdp.entity.dom.CollectClassNamesFromSubtreeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.CollectClassNamesFromSubtreeResponse> asyncCollectClassNamesFromSubtree(jpuppeteer.cdp.cdp.entity.dom.CollectClassNamesFromSubtreeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.collectClassNamesFromSubtree", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.CollectClassNamesFromSubtreeResponse>(future, jpuppeteer.cdp.cdp.entity.dom.CollectClassNamesFromSubtreeResponse.class);
    }

    /**
    * Creates a deep copy of the specified node and places it into the target container before the given anchor.
    */
    public jpuppeteer.cdp.cdp.entity.dom.CopyToResponse copyTo(jpuppeteer.cdp.cdp.entity.dom.CopyToRequest request, int timeout) throws Exception {
        return session.send("DOM.copyTo", request, jpuppeteer.cdp.cdp.entity.dom.CopyToResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.CopyToResponse> asyncCopyTo(jpuppeteer.cdp.cdp.entity.dom.CopyToRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.copyTo", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.CopyToResponse>(future, jpuppeteer.cdp.cdp.entity.dom.CopyToResponse.class);
    }

    /**
    * Describes node given its id, does not require domain to be enabled. Does not start tracking any objects, can be used for automation.
    */
    public jpuppeteer.cdp.cdp.entity.dom.DescribeNodeResponse describeNode(jpuppeteer.cdp.cdp.entity.dom.DescribeNodeRequest request, int timeout) throws Exception {
        return session.send("DOM.describeNode", request, jpuppeteer.cdp.cdp.entity.dom.DescribeNodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.DescribeNodeResponse> asyncDescribeNode(jpuppeteer.cdp.cdp.entity.dom.DescribeNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.describeNode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.DescribeNodeResponse>(future, jpuppeteer.cdp.cdp.entity.dom.DescribeNodeResponse.class);
    }

    /**
    * Disables DOM agent for the given page.
    */
    public void disable(int timeout) throws Exception {
        session.send("DOM.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Discards search results from the session with the given id. `getSearchResults` should no longer be called for that search.
    */
    public void discardSearchResults(jpuppeteer.cdp.cdp.entity.dom.DiscardSearchResultsRequest request, int timeout) throws Exception {
        session.send("DOM.discardSearchResults", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDiscardSearchResults(jpuppeteer.cdp.cdp.entity.dom.DiscardSearchResultsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.discardSearchResults", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables DOM agent for the given page.
    */
    public void enable(int timeout) throws Exception {
        session.send("DOM.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Focuses the given element.
    */
    public void focus(jpuppeteer.cdp.cdp.entity.dom.FocusRequest request, int timeout) throws Exception {
        session.send("DOM.focus", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncFocus(jpuppeteer.cdp.cdp.entity.dom.FocusRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.focus", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns attributes for the specified node.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetAttributesResponse getAttributes(jpuppeteer.cdp.cdp.entity.dom.GetAttributesRequest request, int timeout) throws Exception {
        return session.send("DOM.getAttributes", request, jpuppeteer.cdp.cdp.entity.dom.GetAttributesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetAttributesResponse> asyncGetAttributes(jpuppeteer.cdp.cdp.entity.dom.GetAttributesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getAttributes", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetAttributesResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetAttributesResponse.class);
    }

    /**
    * Returns boxes for the given node.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetBoxModelResponse getBoxModel(jpuppeteer.cdp.cdp.entity.dom.GetBoxModelRequest request, int timeout) throws Exception {
        return session.send("DOM.getBoxModel", request, jpuppeteer.cdp.cdp.entity.dom.GetBoxModelResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetBoxModelResponse> asyncGetBoxModel(jpuppeteer.cdp.cdp.entity.dom.GetBoxModelRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getBoxModel", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetBoxModelResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetBoxModelResponse.class);
    }

    /**
    * Returns quads that describe node position on the page. This method might return multiple quads for inline nodes.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetContentQuadsResponse getContentQuads(jpuppeteer.cdp.cdp.entity.dom.GetContentQuadsRequest request, int timeout) throws Exception {
        return session.send("DOM.getContentQuads", request, jpuppeteer.cdp.cdp.entity.dom.GetContentQuadsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetContentQuadsResponse> asyncGetContentQuads(jpuppeteer.cdp.cdp.entity.dom.GetContentQuadsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getContentQuads", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetContentQuadsResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetContentQuadsResponse.class);
    }

    /**
    * Returns the root DOM node (and optionally the subtree) to the caller.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetDocumentResponse getDocument(jpuppeteer.cdp.cdp.entity.dom.GetDocumentRequest request, int timeout) throws Exception {
        return session.send("DOM.getDocument", request, jpuppeteer.cdp.cdp.entity.dom.GetDocumentResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetDocumentResponse> asyncGetDocument(jpuppeteer.cdp.cdp.entity.dom.GetDocumentRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getDocument", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetDocumentResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetDocumentResponse.class);
    }

    /**
    * Returns the root DOM node (and optionally the subtree) to the caller.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetFlattenedDocumentResponse getFlattenedDocument(jpuppeteer.cdp.cdp.entity.dom.GetFlattenedDocumentRequest request, int timeout) throws Exception {
        return session.send("DOM.getFlattenedDocument", request, jpuppeteer.cdp.cdp.entity.dom.GetFlattenedDocumentResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetFlattenedDocumentResponse> asyncGetFlattenedDocument(jpuppeteer.cdp.cdp.entity.dom.GetFlattenedDocumentRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getFlattenedDocument", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetFlattenedDocumentResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetFlattenedDocumentResponse.class);
    }

    /**
    * Returns node id at given location. Depending on whether DOM domain is enabled, nodeId is either returned or not.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetNodeForLocationResponse getNodeForLocation(jpuppeteer.cdp.cdp.entity.dom.GetNodeForLocationRequest request, int timeout) throws Exception {
        return session.send("DOM.getNodeForLocation", request, jpuppeteer.cdp.cdp.entity.dom.GetNodeForLocationResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetNodeForLocationResponse> asyncGetNodeForLocation(jpuppeteer.cdp.cdp.entity.dom.GetNodeForLocationRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getNodeForLocation", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetNodeForLocationResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetNodeForLocationResponse.class);
    }

    /**
    * Returns node's HTML markup.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetOuterHTMLResponse getOuterHTML(jpuppeteer.cdp.cdp.entity.dom.GetOuterHTMLRequest request, int timeout) throws Exception {
        return session.send("DOM.getOuterHTML", request, jpuppeteer.cdp.cdp.entity.dom.GetOuterHTMLResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetOuterHTMLResponse> asyncGetOuterHTML(jpuppeteer.cdp.cdp.entity.dom.GetOuterHTMLRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getOuterHTML", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetOuterHTMLResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetOuterHTMLResponse.class);
    }

    /**
    * Returns the id of the nearest ancestor that is a relayout boundary.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetRelayoutBoundaryResponse getRelayoutBoundary(jpuppeteer.cdp.cdp.entity.dom.GetRelayoutBoundaryRequest request, int timeout) throws Exception {
        return session.send("DOM.getRelayoutBoundary", request, jpuppeteer.cdp.cdp.entity.dom.GetRelayoutBoundaryResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetRelayoutBoundaryResponse> asyncGetRelayoutBoundary(jpuppeteer.cdp.cdp.entity.dom.GetRelayoutBoundaryRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getRelayoutBoundary", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetRelayoutBoundaryResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetRelayoutBoundaryResponse.class);
    }

    /**
    * Returns search results from given `fromIndex` to given `toIndex` from the search with the given identifier.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetSearchResultsResponse getSearchResults(jpuppeteer.cdp.cdp.entity.dom.GetSearchResultsRequest request, int timeout) throws Exception {
        return session.send("DOM.getSearchResults", request, jpuppeteer.cdp.cdp.entity.dom.GetSearchResultsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetSearchResultsResponse> asyncGetSearchResults(jpuppeteer.cdp.cdp.entity.dom.GetSearchResultsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getSearchResults", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetSearchResultsResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetSearchResultsResponse.class);
    }

    /**
    * Hides any highlight.
    */
    public void hideHighlight(int timeout) throws Exception {
        session.send("DOM.hideHighlight", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHideHighlight() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.hideHighlight");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Highlights DOM node.
    */
    public void highlightNode(int timeout) throws Exception {
        session.send("DOM.highlightNode", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHighlightNode() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.highlightNode");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Highlights given rectangle.
    */
    public void highlightRect(int timeout) throws Exception {
        session.send("DOM.highlightRect", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHighlightRect() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.highlightRect");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Marks last undoable state.
    */
    public void markUndoableState(int timeout) throws Exception {
        session.send("DOM.markUndoableState", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncMarkUndoableState() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.markUndoableState");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Moves node into the new container, places it before the given anchor.
    */
    public jpuppeteer.cdp.cdp.entity.dom.MoveToResponse moveTo(jpuppeteer.cdp.cdp.entity.dom.MoveToRequest request, int timeout) throws Exception {
        return session.send("DOM.moveTo", request, jpuppeteer.cdp.cdp.entity.dom.MoveToResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.MoveToResponse> asyncMoveTo(jpuppeteer.cdp.cdp.entity.dom.MoveToRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.moveTo", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.MoveToResponse>(future, jpuppeteer.cdp.cdp.entity.dom.MoveToResponse.class);
    }

    /**
    * Searches for a given string in the DOM tree. Use `getSearchResults` to access search results or `cancelSearch` to end this search session.
    */
    public jpuppeteer.cdp.cdp.entity.dom.PerformSearchResponse performSearch(jpuppeteer.cdp.cdp.entity.dom.PerformSearchRequest request, int timeout) throws Exception {
        return session.send("DOM.performSearch", request, jpuppeteer.cdp.cdp.entity.dom.PerformSearchResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.PerformSearchResponse> asyncPerformSearch(jpuppeteer.cdp.cdp.entity.dom.PerformSearchRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.performSearch", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.PerformSearchResponse>(future, jpuppeteer.cdp.cdp.entity.dom.PerformSearchResponse.class);
    }

    /**
    * Requests that the node is sent to the caller given its path. // FIXME, use XPath
    */
    public jpuppeteer.cdp.cdp.entity.dom.PushNodeByPathToFrontendResponse pushNodeByPathToFrontend(jpuppeteer.cdp.cdp.entity.dom.PushNodeByPathToFrontendRequest request, int timeout) throws Exception {
        return session.send("DOM.pushNodeByPathToFrontend", request, jpuppeteer.cdp.cdp.entity.dom.PushNodeByPathToFrontendResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.PushNodeByPathToFrontendResponse> asyncPushNodeByPathToFrontend(jpuppeteer.cdp.cdp.entity.dom.PushNodeByPathToFrontendRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.pushNodeByPathToFrontend", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.PushNodeByPathToFrontendResponse>(future, jpuppeteer.cdp.cdp.entity.dom.PushNodeByPathToFrontendResponse.class);
    }

    /**
    * Requests that a batch of nodes is sent to the caller given their backend node ids.
    */
    public jpuppeteer.cdp.cdp.entity.dom.PushNodesByBackendIdsToFrontendResponse pushNodesByBackendIdsToFrontend(jpuppeteer.cdp.cdp.entity.dom.PushNodesByBackendIdsToFrontendRequest request, int timeout) throws Exception {
        return session.send("DOM.pushNodesByBackendIdsToFrontend", request, jpuppeteer.cdp.cdp.entity.dom.PushNodesByBackendIdsToFrontendResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.PushNodesByBackendIdsToFrontendResponse> asyncPushNodesByBackendIdsToFrontend(jpuppeteer.cdp.cdp.entity.dom.PushNodesByBackendIdsToFrontendRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.pushNodesByBackendIdsToFrontend", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.PushNodesByBackendIdsToFrontendResponse>(future, jpuppeteer.cdp.cdp.entity.dom.PushNodesByBackendIdsToFrontendResponse.class);
    }

    /**
    * Executes `querySelector` on a given node.
    */
    public jpuppeteer.cdp.cdp.entity.dom.QuerySelectorResponse querySelector(jpuppeteer.cdp.cdp.entity.dom.QuerySelectorRequest request, int timeout) throws Exception {
        return session.send("DOM.querySelector", request, jpuppeteer.cdp.cdp.entity.dom.QuerySelectorResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.QuerySelectorResponse> asyncQuerySelector(jpuppeteer.cdp.cdp.entity.dom.QuerySelectorRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.querySelector", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.QuerySelectorResponse>(future, jpuppeteer.cdp.cdp.entity.dom.QuerySelectorResponse.class);
    }

    /**
    * Executes `querySelectorAll` on a given node.
    */
    public jpuppeteer.cdp.cdp.entity.dom.QuerySelectorAllResponse querySelectorAll(jpuppeteer.cdp.cdp.entity.dom.QuerySelectorAllRequest request, int timeout) throws Exception {
        return session.send("DOM.querySelectorAll", request, jpuppeteer.cdp.cdp.entity.dom.QuerySelectorAllResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.QuerySelectorAllResponse> asyncQuerySelectorAll(jpuppeteer.cdp.cdp.entity.dom.QuerySelectorAllRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.querySelectorAll", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.QuerySelectorAllResponse>(future, jpuppeteer.cdp.cdp.entity.dom.QuerySelectorAllResponse.class);
    }

    /**
    * Re-does the last undone action.
    */
    public void redo(int timeout) throws Exception {
        session.send("DOM.redo", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRedo() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.redo");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Removes attribute with given name from an element with given id.
    */
    public void removeAttribute(jpuppeteer.cdp.cdp.entity.dom.RemoveAttributeRequest request, int timeout) throws Exception {
        session.send("DOM.removeAttribute", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveAttribute(jpuppeteer.cdp.cdp.entity.dom.RemoveAttributeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.removeAttribute", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Removes node with given id.
    */
    public void removeNode(jpuppeteer.cdp.cdp.entity.dom.RemoveNodeRequest request, int timeout) throws Exception {
        session.send("DOM.removeNode", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveNode(jpuppeteer.cdp.cdp.entity.dom.RemoveNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.removeNode", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that children of the node with given id are returned to the caller in form of `setChildNodes` events where not only immediate children are retrieved, but all children down to the specified depth.
    */
    public void requestChildNodes(jpuppeteer.cdp.cdp.entity.dom.RequestChildNodesRequest request, int timeout) throws Exception {
        session.send("DOM.requestChildNodes", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRequestChildNodes(jpuppeteer.cdp.cdp.entity.dom.RequestChildNodesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.requestChildNodes", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that the node is sent to the caller given the JavaScript node object reference. All nodes that form the path from the node to the root are also sent to the client as a series of `setChildNodes` notifications.
    */
    public jpuppeteer.cdp.cdp.entity.dom.RequestNodeResponse requestNode(jpuppeteer.cdp.cdp.entity.dom.RequestNodeRequest request, int timeout) throws Exception {
        return session.send("DOM.requestNode", request, jpuppeteer.cdp.cdp.entity.dom.RequestNodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.RequestNodeResponse> asyncRequestNode(jpuppeteer.cdp.cdp.entity.dom.RequestNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.requestNode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.RequestNodeResponse>(future, jpuppeteer.cdp.cdp.entity.dom.RequestNodeResponse.class);
    }

    /**
    * Resolves the JavaScript node object for a given NodeId or BackendNodeId.
    */
    public jpuppeteer.cdp.cdp.entity.dom.ResolveNodeResponse resolveNode(jpuppeteer.cdp.cdp.entity.dom.ResolveNodeRequest request, int timeout) throws Exception {
        return session.send("DOM.resolveNode", request, jpuppeteer.cdp.cdp.entity.dom.ResolveNodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.ResolveNodeResponse> asyncResolveNode(jpuppeteer.cdp.cdp.entity.dom.ResolveNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.resolveNode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.ResolveNodeResponse>(future, jpuppeteer.cdp.cdp.entity.dom.ResolveNodeResponse.class);
    }

    /**
    * Sets attribute for an element with given id.
    */
    public void setAttributeValue(jpuppeteer.cdp.cdp.entity.dom.SetAttributeValueRequest request, int timeout) throws Exception {
        session.send("DOM.setAttributeValue", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetAttributeValue(jpuppeteer.cdp.cdp.entity.dom.SetAttributeValueRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setAttributeValue", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets attributes on element with given id. This method is useful when user edits some existing attribute value and types in several attribute name/value pairs.
    */
    public void setAttributesAsText(jpuppeteer.cdp.cdp.entity.dom.SetAttributesAsTextRequest request, int timeout) throws Exception {
        session.send("DOM.setAttributesAsText", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetAttributesAsText(jpuppeteer.cdp.cdp.entity.dom.SetAttributesAsTextRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setAttributesAsText", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets files for the given file input element.
    */
    public void setFileInputFiles(jpuppeteer.cdp.cdp.entity.dom.SetFileInputFilesRequest request, int timeout) throws Exception {
        session.send("DOM.setFileInputFiles", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetFileInputFiles(jpuppeteer.cdp.cdp.entity.dom.SetFileInputFilesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setFileInputFiles", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets if stack traces should be captured for Nodes. See `Node.getNodeStackTraces`. Default is disabled.
    */
    public void setNodeStackTracesEnabled(jpuppeteer.cdp.cdp.entity.dom.SetNodeStackTracesEnabledRequest request, int timeout) throws Exception {
        session.send("DOM.setNodeStackTracesEnabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetNodeStackTracesEnabled(jpuppeteer.cdp.cdp.entity.dom.SetNodeStackTracesEnabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setNodeStackTracesEnabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Gets stack traces associated with a Node. As of now, only provides stack trace for Node creation.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetNodeStackTracesResponse getNodeStackTraces(jpuppeteer.cdp.cdp.entity.dom.GetNodeStackTracesRequest request, int timeout) throws Exception {
        return session.send("DOM.getNodeStackTraces", request, jpuppeteer.cdp.cdp.entity.dom.GetNodeStackTracesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetNodeStackTracesResponse> asyncGetNodeStackTraces(jpuppeteer.cdp.cdp.entity.dom.GetNodeStackTracesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getNodeStackTraces", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetNodeStackTracesResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetNodeStackTracesResponse.class);
    }

    /**
    * Returns file information for the given File wrapper.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetFileInfoResponse getFileInfo(jpuppeteer.cdp.cdp.entity.dom.GetFileInfoRequest request, int timeout) throws Exception {
        return session.send("DOM.getFileInfo", request, jpuppeteer.cdp.cdp.entity.dom.GetFileInfoResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetFileInfoResponse> asyncGetFileInfo(jpuppeteer.cdp.cdp.entity.dom.GetFileInfoRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getFileInfo", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetFileInfoResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetFileInfoResponse.class);
    }

    /**
    * Enables console to refer to the node with given id via $x (see Command Line API for more details $x functions).
    */
    public void setInspectedNode(jpuppeteer.cdp.cdp.entity.dom.SetInspectedNodeRequest request, int timeout) throws Exception {
        session.send("DOM.setInspectedNode", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetInspectedNode(jpuppeteer.cdp.cdp.entity.dom.SetInspectedNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setInspectedNode", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets node name for a node with given id.
    */
    public jpuppeteer.cdp.cdp.entity.dom.SetNodeNameResponse setNodeName(jpuppeteer.cdp.cdp.entity.dom.SetNodeNameRequest request, int timeout) throws Exception {
        return session.send("DOM.setNodeName", request, jpuppeteer.cdp.cdp.entity.dom.SetNodeNameResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.SetNodeNameResponse> asyncSetNodeName(jpuppeteer.cdp.cdp.entity.dom.SetNodeNameRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setNodeName", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.SetNodeNameResponse>(future, jpuppeteer.cdp.cdp.entity.dom.SetNodeNameResponse.class);
    }

    /**
    * Sets node value for a node with given id.
    */
    public void setNodeValue(jpuppeteer.cdp.cdp.entity.dom.SetNodeValueRequest request, int timeout) throws Exception {
        session.send("DOM.setNodeValue", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetNodeValue(jpuppeteer.cdp.cdp.entity.dom.SetNodeValueRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setNodeValue", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets node HTML markup, returns new node id.
    */
    public void setOuterHTML(jpuppeteer.cdp.cdp.entity.dom.SetOuterHTMLRequest request, int timeout) throws Exception {
        session.send("DOM.setOuterHTML", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetOuterHTML(jpuppeteer.cdp.cdp.entity.dom.SetOuterHTMLRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.setOuterHTML", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Undoes the last performed action.
    */
    public void undo(int timeout) throws Exception {
        session.send("DOM.undo", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncUndo() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.undo");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns iframe node that owns iframe with the given domain.
    */
    public jpuppeteer.cdp.cdp.entity.dom.GetFrameOwnerResponse getFrameOwner(jpuppeteer.cdp.cdp.entity.dom.GetFrameOwnerRequest request, int timeout) throws Exception {
        return session.send("DOM.getFrameOwner", request, jpuppeteer.cdp.cdp.entity.dom.GetFrameOwnerResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.dom.GetFrameOwnerResponse> asyncGetFrameOwner(jpuppeteer.cdp.cdp.entity.dom.GetFrameOwnerRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOM.getFrameOwner", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.dom.GetFrameOwnerResponse>(future, jpuppeteer.cdp.cdp.entity.dom.GetFrameOwnerResponse.class);
    }
}