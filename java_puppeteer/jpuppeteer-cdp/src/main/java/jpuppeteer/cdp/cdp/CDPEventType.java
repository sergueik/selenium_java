package jpuppeteer.cdp.cdp;

public enum CDPEventType {

    /**
    * Event for when an animation has been cancelled.
    * @see jpuppeteer.cdp.cdp.entity.animation.AnimationCanceledEvent
    */
    ANIMATION_ANIMATIONCANCELED("Animation.animationCanceled", jpuppeteer.cdp.cdp.entity.animation.AnimationCanceledEvent.class),

    /**
    * Event for each animation that has been created.
    * @see jpuppeteer.cdp.cdp.entity.animation.AnimationCreatedEvent
    */
    ANIMATION_ANIMATIONCREATED("Animation.animationCreated", jpuppeteer.cdp.cdp.entity.animation.AnimationCreatedEvent.class),

    /**
    * Event for animation that has been started.
    * @see jpuppeteer.cdp.cdp.entity.animation.AnimationStartedEvent
    */
    ANIMATION_ANIMATIONSTARTED("Animation.animationStarted", jpuppeteer.cdp.cdp.entity.animation.AnimationStartedEvent.class),

    APPLICATIONCACHE_APPLICATIONCACHESTATUSUPDATED("ApplicationCache.applicationCacheStatusUpdated", jpuppeteer.cdp.cdp.entity.applicationcache.ApplicationCacheStatusUpdatedEvent.class),

    APPLICATIONCACHE_NETWORKSTATEUPDATED("ApplicationCache.networkStateUpdated", jpuppeteer.cdp.cdp.entity.applicationcache.NetworkStateUpdatedEvent.class),

    /**
    * Called when the recording state for the service has been updated.
    * @see jpuppeteer.cdp.cdp.entity.backgroundservice.RecordingStateChangedEvent
    */
    BACKGROUNDSERVICE_RECORDINGSTATECHANGED("BackgroundService.recordingStateChanged", jpuppeteer.cdp.cdp.entity.backgroundservice.RecordingStateChangedEvent.class),

    /**
    * Called with all existing backgroundServiceEvents when enabled, and all new events afterwards if enabled and recording.
    * @see jpuppeteer.cdp.cdp.entity.backgroundservice.BackgroundServiceEventReceivedEvent
    */
    BACKGROUNDSERVICE_BACKGROUNDSERVICEEVENTRECEIVED("BackgroundService.backgroundServiceEventReceived", jpuppeteer.cdp.cdp.entity.backgroundservice.BackgroundServiceEventReceivedEvent.class),

    /**
    * Fires whenever a web font is updated.  A non-empty font parameter indicates a successfully loaded web font
    * @see jpuppeteer.cdp.cdp.entity.css.FontsUpdatedEvent
    */
    CSS_FONTSUPDATED("CSS.fontsUpdated", jpuppeteer.cdp.cdp.entity.css.FontsUpdatedEvent.class),

    CSS_MEDIAQUERYRESULTCHANGED("CSS.mediaQueryResultChanged", null),

    /**
    * Fired whenever an active document stylesheet is added.
    * @see jpuppeteer.cdp.cdp.entity.css.StyleSheetAddedEvent
    */
    CSS_STYLESHEETADDED("CSS.styleSheetAdded", jpuppeteer.cdp.cdp.entity.css.StyleSheetAddedEvent.class),

    /**
    * Fired whenever a stylesheet is changed as a result of the client operation.
    * @see jpuppeteer.cdp.cdp.entity.css.StyleSheetChangedEvent
    */
    CSS_STYLESHEETCHANGED("CSS.styleSheetChanged", jpuppeteer.cdp.cdp.entity.css.StyleSheetChangedEvent.class),

    /**
    * Fired whenever an active document stylesheet is removed.
    * @see jpuppeteer.cdp.cdp.entity.css.StyleSheetRemovedEvent
    */
    CSS_STYLESHEETREMOVED("CSS.styleSheetRemoved", jpuppeteer.cdp.cdp.entity.css.StyleSheetRemovedEvent.class),

    /**
    * This is fired whenever the list of available sinks changes. A sink is a device or a software surface that you can cast to.
    * @see jpuppeteer.cdp.cdp.entity.cast.SinksUpdatedEvent
    */
    CAST_SINKSUPDATED("Cast.sinksUpdated", jpuppeteer.cdp.cdp.entity.cast.SinksUpdatedEvent.class),

    /**
    * This is fired whenever the outstanding issue/error message changes. |issueMessage| is empty if there is no issue.
    * @see jpuppeteer.cdp.cdp.entity.cast.IssueUpdatedEvent
    */
    CAST_ISSUEUPDATED("Cast.issueUpdated", jpuppeteer.cdp.cdp.entity.cast.IssueUpdatedEvent.class),

    /**
    * Fired when `Element`'s attribute is modified.
    * @see jpuppeteer.cdp.cdp.entity.dom.AttributeModifiedEvent
    */
    DOM_ATTRIBUTEMODIFIED("DOM.attributeModified", jpuppeteer.cdp.cdp.entity.dom.AttributeModifiedEvent.class),

    /**
    * Fired when `Element`'s attribute is removed.
    * @see jpuppeteer.cdp.cdp.entity.dom.AttributeRemovedEvent
    */
    DOM_ATTRIBUTEREMOVED("DOM.attributeRemoved", jpuppeteer.cdp.cdp.entity.dom.AttributeRemovedEvent.class),

    /**
    * Mirrors `DOMCharacterDataModified` event.
    * @see jpuppeteer.cdp.cdp.entity.dom.CharacterDataModifiedEvent
    */
    DOM_CHARACTERDATAMODIFIED("DOM.characterDataModified", jpuppeteer.cdp.cdp.entity.dom.CharacterDataModifiedEvent.class),

    /**
    * Fired when `Container`'s child node count has changed.
    * @see jpuppeteer.cdp.cdp.entity.dom.ChildNodeCountUpdatedEvent
    */
    DOM_CHILDNODECOUNTUPDATED("DOM.childNodeCountUpdated", jpuppeteer.cdp.cdp.entity.dom.ChildNodeCountUpdatedEvent.class),

    /**
    * Mirrors `DOMNodeInserted` event.
    * @see jpuppeteer.cdp.cdp.entity.dom.ChildNodeInsertedEvent
    */
    DOM_CHILDNODEINSERTED("DOM.childNodeInserted", jpuppeteer.cdp.cdp.entity.dom.ChildNodeInsertedEvent.class),

    /**
    * Mirrors `DOMNodeRemoved` event.
    * @see jpuppeteer.cdp.cdp.entity.dom.ChildNodeRemovedEvent
    */
    DOM_CHILDNODEREMOVED("DOM.childNodeRemoved", jpuppeteer.cdp.cdp.entity.dom.ChildNodeRemovedEvent.class),

    /**
    * Called when distrubution is changed.
    * @see jpuppeteer.cdp.cdp.entity.dom.DistributedNodesUpdatedEvent
    */
    DOM_DISTRIBUTEDNODESUPDATED("DOM.distributedNodesUpdated", jpuppeteer.cdp.cdp.entity.dom.DistributedNodesUpdatedEvent.class),

    DOM_DOCUMENTUPDATED("DOM.documentUpdated", null),

    /**
    * Fired when `Element`'s inline style is modified via a CSS property modification.
    * @see jpuppeteer.cdp.cdp.entity.dom.InlineStyleInvalidatedEvent
    */
    DOM_INLINESTYLEINVALIDATED("DOM.inlineStyleInvalidated", jpuppeteer.cdp.cdp.entity.dom.InlineStyleInvalidatedEvent.class),

    /**
    * Called when a pseudo element is added to an element.
    * @see jpuppeteer.cdp.cdp.entity.dom.PseudoElementAddedEvent
    */
    DOM_PSEUDOELEMENTADDED("DOM.pseudoElementAdded", jpuppeteer.cdp.cdp.entity.dom.PseudoElementAddedEvent.class),

    /**
    * Called when a pseudo element is removed from an element.
    * @see jpuppeteer.cdp.cdp.entity.dom.PseudoElementRemovedEvent
    */
    DOM_PSEUDOELEMENTREMOVED("DOM.pseudoElementRemoved", jpuppeteer.cdp.cdp.entity.dom.PseudoElementRemovedEvent.class),

    /**
    * Fired when backend wants to provide client with the missing DOM structure. This happens upon most of the calls requesting node ids.
    * @see jpuppeteer.cdp.cdp.entity.dom.SetChildNodesEvent
    */
    DOM_SETCHILDNODES("DOM.setChildNodes", jpuppeteer.cdp.cdp.entity.dom.SetChildNodesEvent.class),

    /**
    * Called when shadow root is popped from the element.
    * @see jpuppeteer.cdp.cdp.entity.dom.ShadowRootPoppedEvent
    */
    DOM_SHADOWROOTPOPPED("DOM.shadowRootPopped", jpuppeteer.cdp.cdp.entity.dom.ShadowRootPoppedEvent.class),

    /**
    * Called when shadow root is pushed into the element.
    * @see jpuppeteer.cdp.cdp.entity.dom.ShadowRootPushedEvent
    */
    DOM_SHADOWROOTPUSHED("DOM.shadowRootPushed", jpuppeteer.cdp.cdp.entity.dom.ShadowRootPushedEvent.class),

    DOMSTORAGE_DOMSTORAGEITEMADDED("DOMStorage.domStorageItemAdded", jpuppeteer.cdp.cdp.entity.domstorage.DomStorageItemAddedEvent.class),

    DOMSTORAGE_DOMSTORAGEITEMREMOVED("DOMStorage.domStorageItemRemoved", jpuppeteer.cdp.cdp.entity.domstorage.DomStorageItemRemovedEvent.class),

    DOMSTORAGE_DOMSTORAGEITEMUPDATED("DOMStorage.domStorageItemUpdated", jpuppeteer.cdp.cdp.entity.domstorage.DomStorageItemUpdatedEvent.class),

    DOMSTORAGE_DOMSTORAGEITEMSCLEARED("DOMStorage.domStorageItemsCleared", jpuppeteer.cdp.cdp.entity.domstorage.DomStorageItemsClearedEvent.class),

    DATABASE_ADDDATABASE("Database.addDatabase", jpuppeteer.cdp.cdp.entity.database.AddDatabaseEvent.class),

    EMULATION_VIRTUALTIMEBUDGETEXPIRED("Emulation.virtualTimeBudgetExpired", null),

    /**
    * Issued when the target starts or stops needing BeginFrames. Deprecated. Issue beginFrame unconditionally instead and use result from beginFrame to detect whether the frames were suppressed.
    * @see jpuppeteer.cdp.cdp.entity.headlessexperimental.NeedsBeginFramesChangedEvent
    */
    HEADLESSEXPERIMENTAL_NEEDSBEGINFRAMESCHANGED("HeadlessExperimental.needsBeginFramesChanged", jpuppeteer.cdp.cdp.entity.headlessexperimental.NeedsBeginFramesChangedEvent.class),

    /**
    * Fired when remote debugging connection is about to be terminated. Contains detach reason.
    * @see jpuppeteer.cdp.cdp.entity.inspector.DetachedEvent
    */
    INSPECTOR_DETACHED("Inspector.detached", jpuppeteer.cdp.cdp.entity.inspector.DetachedEvent.class),

    INSPECTOR_TARGETCRASHED("Inspector.targetCrashed", null),

    INSPECTOR_TARGETRELOADEDAFTERCRASH("Inspector.targetReloadedAfterCrash", null),

    LAYERTREE_LAYERPAINTED("LayerTree.layerPainted", jpuppeteer.cdp.cdp.entity.layertree.LayerPaintedEvent.class),

    LAYERTREE_LAYERTREEDIDCHANGE("LayerTree.layerTreeDidChange", jpuppeteer.cdp.cdp.entity.layertree.LayerTreeDidChangeEvent.class),

    /**
    * Issued when new message was logged.
    * @see jpuppeteer.cdp.cdp.entity.log.EntryAddedEvent
    */
    LOG_ENTRYADDED("Log.entryAdded", jpuppeteer.cdp.cdp.entity.log.EntryAddedEvent.class),

    /**
    * Fired when data chunk was received over the network.
    * @see jpuppeteer.cdp.cdp.entity.network.DataReceivedEvent
    */
    NETWORK_DATARECEIVED("Network.dataReceived", jpuppeteer.cdp.cdp.entity.network.DataReceivedEvent.class),

    /**
    * Fired when EventSource message is received.
    * @see jpuppeteer.cdp.cdp.entity.network.EventSourceMessageReceivedEvent
    */
    NETWORK_EVENTSOURCEMESSAGERECEIVED("Network.eventSourceMessageReceived", jpuppeteer.cdp.cdp.entity.network.EventSourceMessageReceivedEvent.class),

    /**
    * Fired when HTTP request has failed to load.
    * @see jpuppeteer.cdp.cdp.entity.network.LoadingFailedEvent
    */
    NETWORK_LOADINGFAILED("Network.loadingFailed", jpuppeteer.cdp.cdp.entity.network.LoadingFailedEvent.class),

    /**
    * Fired when HTTP request has finished loading.
    * @see jpuppeteer.cdp.cdp.entity.network.LoadingFinishedEvent
    */
    NETWORK_LOADINGFINISHED("Network.loadingFinished", jpuppeteer.cdp.cdp.entity.network.LoadingFinishedEvent.class),

    /**
    * Details of an intercepted HTTP request, which must be either allowed, blocked, modified or mocked. Deprecated, use Fetch.requestPaused instead.
    * @see jpuppeteer.cdp.cdp.entity.network.RequestInterceptedEvent
    */
    NETWORK_REQUESTINTERCEPTED("Network.requestIntercepted", jpuppeteer.cdp.cdp.entity.network.RequestInterceptedEvent.class),

    /**
    * Fired if request ended up loading from cache.
    * @see jpuppeteer.cdp.cdp.entity.network.RequestServedFromCacheEvent
    */
    NETWORK_REQUESTSERVEDFROMCACHE("Network.requestServedFromCache", jpuppeteer.cdp.cdp.entity.network.RequestServedFromCacheEvent.class),

    /**
    * Fired when page is about to send HTTP request.
    * @see jpuppeteer.cdp.cdp.entity.network.RequestWillBeSentEvent
    */
    NETWORK_REQUESTWILLBESENT("Network.requestWillBeSent", jpuppeteer.cdp.cdp.entity.network.RequestWillBeSentEvent.class),

    /**
    * Fired when resource loading priority is changed
    * @see jpuppeteer.cdp.cdp.entity.network.ResourceChangedPriorityEvent
    */
    NETWORK_RESOURCECHANGEDPRIORITY("Network.resourceChangedPriority", jpuppeteer.cdp.cdp.entity.network.ResourceChangedPriorityEvent.class),

    /**
    * Fired when a signed exchange was received over the network
    * @see jpuppeteer.cdp.cdp.entity.network.SignedExchangeReceivedEvent
    */
    NETWORK_SIGNEDEXCHANGERECEIVED("Network.signedExchangeReceived", jpuppeteer.cdp.cdp.entity.network.SignedExchangeReceivedEvent.class),

    /**
    * Fired when HTTP response is available.
    * @see jpuppeteer.cdp.cdp.entity.network.ResponseReceivedEvent
    */
    NETWORK_RESPONSERECEIVED("Network.responseReceived", jpuppeteer.cdp.cdp.entity.network.ResponseReceivedEvent.class),

    /**
    * Fired when WebSocket is closed.
    * @see jpuppeteer.cdp.cdp.entity.network.WebSocketClosedEvent
    */
    NETWORK_WEBSOCKETCLOSED("Network.webSocketClosed", jpuppeteer.cdp.cdp.entity.network.WebSocketClosedEvent.class),

    /**
    * Fired upon WebSocket creation.
    * @see jpuppeteer.cdp.cdp.entity.network.WebSocketCreatedEvent
    */
    NETWORK_WEBSOCKETCREATED("Network.webSocketCreated", jpuppeteer.cdp.cdp.entity.network.WebSocketCreatedEvent.class),

    /**
    * Fired when WebSocket message error occurs.
    * @see jpuppeteer.cdp.cdp.entity.network.WebSocketFrameErrorEvent
    */
    NETWORK_WEBSOCKETFRAMEERROR("Network.webSocketFrameError", jpuppeteer.cdp.cdp.entity.network.WebSocketFrameErrorEvent.class),

    /**
    * Fired when WebSocket message is received.
    * @see jpuppeteer.cdp.cdp.entity.network.WebSocketFrameReceivedEvent
    */
    NETWORK_WEBSOCKETFRAMERECEIVED("Network.webSocketFrameReceived", jpuppeteer.cdp.cdp.entity.network.WebSocketFrameReceivedEvent.class),

    /**
    * Fired when WebSocket message is sent.
    * @see jpuppeteer.cdp.cdp.entity.network.WebSocketFrameSentEvent
    */
    NETWORK_WEBSOCKETFRAMESENT("Network.webSocketFrameSent", jpuppeteer.cdp.cdp.entity.network.WebSocketFrameSentEvent.class),

    /**
    * Fired when WebSocket handshake response becomes available.
    * @see jpuppeteer.cdp.cdp.entity.network.WebSocketHandshakeResponseReceivedEvent
    */
    NETWORK_WEBSOCKETHANDSHAKERESPONSERECEIVED("Network.webSocketHandshakeResponseReceived", jpuppeteer.cdp.cdp.entity.network.WebSocketHandshakeResponseReceivedEvent.class),

    /**
    * Fired when WebSocket is about to initiate handshake.
    * @see jpuppeteer.cdp.cdp.entity.network.WebSocketWillSendHandshakeRequestEvent
    */
    NETWORK_WEBSOCKETWILLSENDHANDSHAKEREQUEST("Network.webSocketWillSendHandshakeRequest", jpuppeteer.cdp.cdp.entity.network.WebSocketWillSendHandshakeRequestEvent.class),

    /**
    * Fired when additional information about a requestWillBeSent event is available from the network stack. Not every requestWillBeSent event will have an additional requestWillBeSentExtraInfo fired for it, and there is no guarantee whether requestWillBeSent or requestWillBeSentExtraInfo will be fired first for the same request.
    * @see jpuppeteer.cdp.cdp.entity.network.RequestWillBeSentExtraInfoEvent
    */
    NETWORK_REQUESTWILLBESENTEXTRAINFO("Network.requestWillBeSentExtraInfo", jpuppeteer.cdp.cdp.entity.network.RequestWillBeSentExtraInfoEvent.class),

    /**
    * Fired when additional information about a responseReceived event is available from the network stack. Not every responseReceived event will have an additional responseReceivedExtraInfo for it, and responseReceivedExtraInfo may be fired before or after responseReceived.
    * @see jpuppeteer.cdp.cdp.entity.network.ResponseReceivedExtraInfoEvent
    */
    NETWORK_RESPONSERECEIVEDEXTRAINFO("Network.responseReceivedExtraInfo", jpuppeteer.cdp.cdp.entity.network.ResponseReceivedExtraInfoEvent.class),

    /**
    * Fired when the node should be inspected. This happens after call to `setInspectMode` or when user manually inspects an element.
    * @see jpuppeteer.cdp.cdp.entity.overlay.InspectNodeRequestedEvent
    */
    OVERLAY_INSPECTNODEREQUESTED("Overlay.inspectNodeRequested", jpuppeteer.cdp.cdp.entity.overlay.InspectNodeRequestedEvent.class),

    /**
    * Fired when the node should be highlighted. This happens after call to `setInspectMode`.
    * @see jpuppeteer.cdp.cdp.entity.overlay.NodeHighlightRequestedEvent
    */
    OVERLAY_NODEHIGHLIGHTREQUESTED("Overlay.nodeHighlightRequested", jpuppeteer.cdp.cdp.entity.overlay.NodeHighlightRequestedEvent.class),

    /**
    * Fired when user asks to capture screenshot of some area on the page.
    * @see jpuppeteer.cdp.cdp.entity.overlay.ScreenshotRequestedEvent
    */
    OVERLAY_SCREENSHOTREQUESTED("Overlay.screenshotRequested", jpuppeteer.cdp.cdp.entity.overlay.ScreenshotRequestedEvent.class),

    OVERLAY_INSPECTMODECANCELED("Overlay.inspectModeCanceled", null),

    PAGE_DOMCONTENTEVENTFIRED("Page.domContentEventFired", jpuppeteer.cdp.cdp.entity.page.DomContentEventFiredEvent.class),

    /**
    * Emitted only when `page.interceptFileChooser` is enabled.
    * @see jpuppeteer.cdp.cdp.entity.page.FileChooserOpenedEvent
    */
    PAGE_FILECHOOSEROPENED("Page.fileChooserOpened", jpuppeteer.cdp.cdp.entity.page.FileChooserOpenedEvent.class),

    /**
    * Fired when frame has been attached to its parent.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameAttachedEvent
    */
    PAGE_FRAMEATTACHED("Page.frameAttached", jpuppeteer.cdp.cdp.entity.page.FrameAttachedEvent.class),

    /**
    * Fired when frame no longer has a scheduled navigation.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameClearedScheduledNavigationEvent
    */
    PAGE_FRAMECLEAREDSCHEDULEDNAVIGATION("Page.frameClearedScheduledNavigation", jpuppeteer.cdp.cdp.entity.page.FrameClearedScheduledNavigationEvent.class),

    /**
    * Fired when frame has been detached from its parent.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameDetachedEvent
    */
    PAGE_FRAMEDETACHED("Page.frameDetached", jpuppeteer.cdp.cdp.entity.page.FrameDetachedEvent.class),

    /**
    * Fired once navigation of the frame has completed. Frame is now associated with the new loader.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameNavigatedEvent
    */
    PAGE_FRAMENAVIGATED("Page.frameNavigated", jpuppeteer.cdp.cdp.entity.page.FrameNavigatedEvent.class),

    PAGE_FRAMERESIZED("Page.frameResized", null),

    /**
    * Fired when a renderer-initiated navigation is requested. Navigation may still be cancelled after the event is issued.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameRequestedNavigationEvent
    */
    PAGE_FRAMEREQUESTEDNAVIGATION("Page.frameRequestedNavigation", jpuppeteer.cdp.cdp.entity.page.FrameRequestedNavigationEvent.class),

    /**
    * Fired when frame schedules a potential navigation.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameScheduledNavigationEvent
    */
    PAGE_FRAMESCHEDULEDNAVIGATION("Page.frameScheduledNavigation", jpuppeteer.cdp.cdp.entity.page.FrameScheduledNavigationEvent.class),

    /**
    * Fired when frame has started loading.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameStartedLoadingEvent
    */
    PAGE_FRAMESTARTEDLOADING("Page.frameStartedLoading", jpuppeteer.cdp.cdp.entity.page.FrameStartedLoadingEvent.class),

    /**
    * Fired when frame has stopped loading.
    * @see jpuppeteer.cdp.cdp.entity.page.FrameStoppedLoadingEvent
    */
    PAGE_FRAMESTOPPEDLOADING("Page.frameStoppedLoading", jpuppeteer.cdp.cdp.entity.page.FrameStoppedLoadingEvent.class),

    /**
    * Fired when page is about to start a download.
    * @see jpuppeteer.cdp.cdp.entity.page.DownloadWillBeginEvent
    */
    PAGE_DOWNLOADWILLBEGIN("Page.downloadWillBegin", jpuppeteer.cdp.cdp.entity.page.DownloadWillBeginEvent.class),

    PAGE_INTERSTITIALHIDDEN("Page.interstitialHidden", null),

    PAGE_INTERSTITIALSHOWN("Page.interstitialShown", null),

    /**
    * Fired when a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload) has been closed.
    * @see jpuppeteer.cdp.cdp.entity.page.JavascriptDialogClosedEvent
    */
    PAGE_JAVASCRIPTDIALOGCLOSED("Page.javascriptDialogClosed", jpuppeteer.cdp.cdp.entity.page.JavascriptDialogClosedEvent.class),

    /**
    * Fired when a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload) is about to open.
    * @see jpuppeteer.cdp.cdp.entity.page.JavascriptDialogOpeningEvent
    */
    PAGE_JAVASCRIPTDIALOGOPENING("Page.javascriptDialogOpening", jpuppeteer.cdp.cdp.entity.page.JavascriptDialogOpeningEvent.class),

    /**
    * Fired for top level page lifecycle events such as navigation, load, paint, etc.
    * @see jpuppeteer.cdp.cdp.entity.page.LifecycleEvent
    */
    PAGE_LIFECYCLEEVENT("Page.lifecycleEvent", jpuppeteer.cdp.cdp.entity.page.LifecycleEvent.class),

    PAGE_LOADEVENTFIRED("Page.loadEventFired", jpuppeteer.cdp.cdp.entity.page.LoadEventFiredEvent.class),

    /**
    * Fired when same-document navigation happens, e.g. due to history API usage or anchor navigation.
    * @see jpuppeteer.cdp.cdp.entity.page.NavigatedWithinDocumentEvent
    */
    PAGE_NAVIGATEDWITHINDOCUMENT("Page.navigatedWithinDocument", jpuppeteer.cdp.cdp.entity.page.NavigatedWithinDocumentEvent.class),

    /**
    * Compressed image data requested by the `startScreencast`.
    * @see jpuppeteer.cdp.cdp.entity.page.ScreencastFrameEvent
    */
    PAGE_SCREENCASTFRAME("Page.screencastFrame", jpuppeteer.cdp.cdp.entity.page.ScreencastFrameEvent.class),

    /**
    * Fired when the page with currently enabled screencast was shown or hidden `.
    * @see jpuppeteer.cdp.cdp.entity.page.ScreencastVisibilityChangedEvent
    */
    PAGE_SCREENCASTVISIBILITYCHANGED("Page.screencastVisibilityChanged", jpuppeteer.cdp.cdp.entity.page.ScreencastVisibilityChangedEvent.class),

    /**
    * Fired when a new window is going to be opened, via window.open(), link click, form submission, etc.
    * @see jpuppeteer.cdp.cdp.entity.page.WindowOpenEvent
    */
    PAGE_WINDOWOPEN("Page.windowOpen", jpuppeteer.cdp.cdp.entity.page.WindowOpenEvent.class),

    /**
    * Issued for every compilation cache generated. Is only available if Page.setGenerateCompilationCache is enabled.
    * @see jpuppeteer.cdp.cdp.entity.page.CompilationCacheProducedEvent
    */
    PAGE_COMPILATIONCACHEPRODUCED("Page.compilationCacheProduced", jpuppeteer.cdp.cdp.entity.page.CompilationCacheProducedEvent.class),

    /**
    * Current values of the metrics.
    * @see jpuppeteer.cdp.cdp.entity.performance.MetricsEvent
    */
    PERFORMANCE_METRICS("Performance.metrics", jpuppeteer.cdp.cdp.entity.performance.MetricsEvent.class),

    /**
    * There is a certificate error. If overriding certificate errors is enabled, then it should be handled with the `handleCertificateError` command. Note: this event does not fire if the certificate error has been allowed internally. Only one client per target should override certificate errors at the same time.
    * @see jpuppeteer.cdp.cdp.entity.security.CertificateErrorEvent
    */
    SECURITY_CERTIFICATEERROR("Security.certificateError", jpuppeteer.cdp.cdp.entity.security.CertificateErrorEvent.class),

    /**
    * The security state of the page changed.
    * @see jpuppeteer.cdp.cdp.entity.security.VisibleSecurityStateChangedEvent
    */
    SECURITY_VISIBLESECURITYSTATECHANGED("Security.visibleSecurityStateChanged", jpuppeteer.cdp.cdp.entity.security.VisibleSecurityStateChangedEvent.class),

    /**
    * The security state of the page changed.
    * @see jpuppeteer.cdp.cdp.entity.security.SecurityStateChangedEvent
    */
    SECURITY_SECURITYSTATECHANGED("Security.securityStateChanged", jpuppeteer.cdp.cdp.entity.security.SecurityStateChangedEvent.class),

    SERVICEWORKER_WORKERERRORREPORTED("ServiceWorker.workerErrorReported", jpuppeteer.cdp.cdp.entity.serviceworker.WorkerErrorReportedEvent.class),

    SERVICEWORKER_WORKERREGISTRATIONUPDATED("ServiceWorker.workerRegistrationUpdated", jpuppeteer.cdp.cdp.entity.serviceworker.WorkerRegistrationUpdatedEvent.class),

    SERVICEWORKER_WORKERVERSIONUPDATED("ServiceWorker.workerVersionUpdated", jpuppeteer.cdp.cdp.entity.serviceworker.WorkerVersionUpdatedEvent.class),

    /**
    * A cache's contents have been modified.
    * @see jpuppeteer.cdp.cdp.entity.storage.CacheStorageContentUpdatedEvent
    */
    STORAGE_CACHESTORAGECONTENTUPDATED("Storage.cacheStorageContentUpdated", jpuppeteer.cdp.cdp.entity.storage.CacheStorageContentUpdatedEvent.class),

    /**
    * A cache has been added/deleted.
    * @see jpuppeteer.cdp.cdp.entity.storage.CacheStorageListUpdatedEvent
    */
    STORAGE_CACHESTORAGELISTUPDATED("Storage.cacheStorageListUpdated", jpuppeteer.cdp.cdp.entity.storage.CacheStorageListUpdatedEvent.class),

    /**
    * The origin's IndexedDB object store has been modified.
    * @see jpuppeteer.cdp.cdp.entity.storage.IndexedDBContentUpdatedEvent
    */
    STORAGE_INDEXEDDBCONTENTUPDATED("Storage.indexedDBContentUpdated", jpuppeteer.cdp.cdp.entity.storage.IndexedDBContentUpdatedEvent.class),

    /**
    * The origin's IndexedDB database list has been modified.
    * @see jpuppeteer.cdp.cdp.entity.storage.IndexedDBListUpdatedEvent
    */
    STORAGE_INDEXEDDBLISTUPDATED("Storage.indexedDBListUpdated", jpuppeteer.cdp.cdp.entity.storage.IndexedDBListUpdatedEvent.class),

    /**
    * Issued when attached to target because of auto-attach or `attachToTarget` command.
    * @see jpuppeteer.cdp.cdp.entity.target.AttachedToTargetEvent
    */
    TARGET_ATTACHEDTOTARGET("Target.attachedToTarget", jpuppeteer.cdp.cdp.entity.target.AttachedToTargetEvent.class),

    /**
    * Issued when detached from target for any reason (including `detachFromTarget` command). Can be issued multiple times per target if multiple sessions have been attached to it.
    * @see jpuppeteer.cdp.cdp.entity.target.DetachedFromTargetEvent
    */
    TARGET_DETACHEDFROMTARGET("Target.detachedFromTarget", jpuppeteer.cdp.cdp.entity.target.DetachedFromTargetEvent.class),

    /**
    * Notifies about a new protocol message received from the session (as reported in `attachedToTarget` event).
    * @see jpuppeteer.cdp.cdp.entity.target.ReceivedMessageFromTargetEvent
    */
    TARGET_RECEIVEDMESSAGEFROMTARGET("Target.receivedMessageFromTarget", jpuppeteer.cdp.cdp.entity.target.ReceivedMessageFromTargetEvent.class),

    /**
    * Issued when a possible inspection target is created.
    * @see jpuppeteer.cdp.cdp.entity.target.TargetCreatedEvent
    */
    TARGET_TARGETCREATED("Target.targetCreated", jpuppeteer.cdp.cdp.entity.target.TargetCreatedEvent.class),

    /**
    * Issued when a target is destroyed.
    * @see jpuppeteer.cdp.cdp.entity.target.TargetDestroyedEvent
    */
    TARGET_TARGETDESTROYED("Target.targetDestroyed", jpuppeteer.cdp.cdp.entity.target.TargetDestroyedEvent.class),

    /**
    * Issued when a target has crashed.
    * @see jpuppeteer.cdp.cdp.entity.target.TargetCrashedEvent
    */
    TARGET_TARGETCRASHED("Target.targetCrashed", jpuppeteer.cdp.cdp.entity.target.TargetCrashedEvent.class),

    /**
    * Issued when some information about a target has changed. This only happens between `targetCreated` and `targetDestroyed`.
    * @see jpuppeteer.cdp.cdp.entity.target.TargetInfoChangedEvent
    */
    TARGET_TARGETINFOCHANGED("Target.targetInfoChanged", jpuppeteer.cdp.cdp.entity.target.TargetInfoChangedEvent.class),

    /**
    * Informs that port was successfully bound and got a specified connection id.
    * @see jpuppeteer.cdp.cdp.entity.tethering.AcceptedEvent
    */
    TETHERING_ACCEPTED("Tethering.accepted", jpuppeteer.cdp.cdp.entity.tethering.AcceptedEvent.class),

    TRACING_BUFFERUSAGE("Tracing.bufferUsage", jpuppeteer.cdp.cdp.entity.tracing.BufferUsageEvent.class),

    /**
    * Contains an bucket of collected trace events. When tracing is stopped collected events will be send as a sequence of dataCollected events followed by tracingComplete event.
    * @see jpuppeteer.cdp.cdp.entity.tracing.DataCollectedEvent
    */
    TRACING_DATACOLLECTED("Tracing.dataCollected", jpuppeteer.cdp.cdp.entity.tracing.DataCollectedEvent.class),

    /**
    * Signals that tracing is stopped and there is no trace buffers pending flush, all data were delivered via dataCollected events.
    * @see jpuppeteer.cdp.cdp.entity.tracing.TracingCompleteEvent
    */
    TRACING_TRACINGCOMPLETE("Tracing.tracingComplete", jpuppeteer.cdp.cdp.entity.tracing.TracingCompleteEvent.class),

    /**
    * Issued when the domain is enabled and the request URL matches the specified filter. The request is paused until the client responds with one of continueRequest, failRequest or fulfillRequest. The stage of the request can be determined by presence of responseErrorReason and responseStatusCode -- the request is at the response stage if either of these fields is present and in the request stage otherwise.
    * @see jpuppeteer.cdp.cdp.entity.fetch.RequestPausedEvent
    */
    FETCH_REQUESTPAUSED("Fetch.requestPaused", jpuppeteer.cdp.cdp.entity.fetch.RequestPausedEvent.class),

    /**
    * Issued when the domain is enabled with handleAuthRequests set to true. The request is paused until client responds with continueWithAuth.
    * @see jpuppeteer.cdp.cdp.entity.fetch.AuthRequiredEvent
    */
    FETCH_AUTHREQUIRED("Fetch.authRequired", jpuppeteer.cdp.cdp.entity.fetch.AuthRequiredEvent.class),

    /**
    * Notifies that a new BaseAudioContext has been created.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.ContextCreatedEvent
    */
    WEBAUDIO_CONTEXTCREATED("WebAudio.contextCreated", jpuppeteer.cdp.cdp.entity.webaudio.ContextCreatedEvent.class),

    /**
    * Notifies that an existing BaseAudioContext will be destroyed.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.ContextWillBeDestroyedEvent
    */
    WEBAUDIO_CONTEXTWILLBEDESTROYED("WebAudio.contextWillBeDestroyed", jpuppeteer.cdp.cdp.entity.webaudio.ContextWillBeDestroyedEvent.class),

    /**
    * Notifies that existing BaseAudioContext has changed some properties (id stays the same)..
    * @see jpuppeteer.cdp.cdp.entity.webaudio.ContextChangedEvent
    */
    WEBAUDIO_CONTEXTCHANGED("WebAudio.contextChanged", jpuppeteer.cdp.cdp.entity.webaudio.ContextChangedEvent.class),

    /**
    * Notifies that the construction of an AudioListener has finished.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.AudioListenerCreatedEvent
    */
    WEBAUDIO_AUDIOLISTENERCREATED("WebAudio.audioListenerCreated", jpuppeteer.cdp.cdp.entity.webaudio.AudioListenerCreatedEvent.class),

    /**
    * Notifies that a new AudioListener has been created.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.AudioListenerWillBeDestroyedEvent
    */
    WEBAUDIO_AUDIOLISTENERWILLBEDESTROYED("WebAudio.audioListenerWillBeDestroyed", jpuppeteer.cdp.cdp.entity.webaudio.AudioListenerWillBeDestroyedEvent.class),

    /**
    * Notifies that a new AudioNode has been created.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.AudioNodeCreatedEvent
    */
    WEBAUDIO_AUDIONODECREATED("WebAudio.audioNodeCreated", jpuppeteer.cdp.cdp.entity.webaudio.AudioNodeCreatedEvent.class),

    /**
    * Notifies that an existing AudioNode has been destroyed.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.AudioNodeWillBeDestroyedEvent
    */
    WEBAUDIO_AUDIONODEWILLBEDESTROYED("WebAudio.audioNodeWillBeDestroyed", jpuppeteer.cdp.cdp.entity.webaudio.AudioNodeWillBeDestroyedEvent.class),

    /**
    * Notifies that a new AudioParam has been created.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.AudioParamCreatedEvent
    */
    WEBAUDIO_AUDIOPARAMCREATED("WebAudio.audioParamCreated", jpuppeteer.cdp.cdp.entity.webaudio.AudioParamCreatedEvent.class),

    /**
    * Notifies that an existing AudioParam has been destroyed.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.AudioParamWillBeDestroyedEvent
    */
    WEBAUDIO_AUDIOPARAMWILLBEDESTROYED("WebAudio.audioParamWillBeDestroyed", jpuppeteer.cdp.cdp.entity.webaudio.AudioParamWillBeDestroyedEvent.class),

    /**
    * Notifies that two AudioNodes are connected.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.NodesConnectedEvent
    */
    WEBAUDIO_NODESCONNECTED("WebAudio.nodesConnected", jpuppeteer.cdp.cdp.entity.webaudio.NodesConnectedEvent.class),

    /**
    * Notifies that AudioNodes are disconnected. The destination can be null, and it means all the outgoing connections from the source are disconnected.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.NodesDisconnectedEvent
    */
    WEBAUDIO_NODESDISCONNECTED("WebAudio.nodesDisconnected", jpuppeteer.cdp.cdp.entity.webaudio.NodesDisconnectedEvent.class),

    /**
    * Notifies that an AudioNode is connected to an AudioParam.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.NodeParamConnectedEvent
    */
    WEBAUDIO_NODEPARAMCONNECTED("WebAudio.nodeParamConnected", jpuppeteer.cdp.cdp.entity.webaudio.NodeParamConnectedEvent.class),

    /**
    * Notifies that an AudioNode is disconnected to an AudioParam.
    * @see jpuppeteer.cdp.cdp.entity.webaudio.NodeParamDisconnectedEvent
    */
    WEBAUDIO_NODEPARAMDISCONNECTED("WebAudio.nodeParamDisconnected", jpuppeteer.cdp.cdp.entity.webaudio.NodeParamDisconnectedEvent.class),

    /**
    * This can be called multiple times, and can be used to set / override / remove player properties. A null propValue indicates removal.
    * @see jpuppeteer.cdp.cdp.entity.media.PlayerPropertiesChangedEvent
    */
    MEDIA_PLAYERPROPERTIESCHANGED("Media.playerPropertiesChanged", jpuppeteer.cdp.cdp.entity.media.PlayerPropertiesChangedEvent.class),

    /**
    * Send events as a list, allowing them to be batched on the browser for less congestion. If batched, events must ALWAYS be in chronological order.
    * @see jpuppeteer.cdp.cdp.entity.media.PlayerEventsAddedEvent
    */
    MEDIA_PLAYEREVENTSADDED("Media.playerEventsAdded", jpuppeteer.cdp.cdp.entity.media.PlayerEventsAddedEvent.class),

    /**
    * Called whenever a player is created, or when a new agent joins and recieves a list of active players. If an agent is restored, it will recieve the full list of player ids and all events again.
    * @see jpuppeteer.cdp.cdp.entity.media.PlayersCreatedEvent
    */
    MEDIA_PLAYERSCREATED("Media.playersCreated", jpuppeteer.cdp.cdp.entity.media.PlayersCreatedEvent.class),

    /**
    * Issued when new console message is added.
    * @see jpuppeteer.cdp.cdp.entity.console.MessageAddedEvent
    */
    CONSOLE_MESSAGEADDED("Console.messageAdded", jpuppeteer.cdp.cdp.entity.console.MessageAddedEvent.class),

    /**
    * Fired when breakpoint is resolved to an actual script and location.
    * @see jpuppeteer.cdp.cdp.entity.debugger.BreakpointResolvedEvent
    */
    DEBUGGER_BREAKPOINTRESOLVED("Debugger.breakpointResolved", jpuppeteer.cdp.cdp.entity.debugger.BreakpointResolvedEvent.class),

    /**
    * Fired when the virtual machine stopped on breakpoint or exception or any other stop criteria.
    * @see jpuppeteer.cdp.cdp.entity.debugger.PausedEvent
    */
    DEBUGGER_PAUSED("Debugger.paused", jpuppeteer.cdp.cdp.entity.debugger.PausedEvent.class),

    DEBUGGER_RESUMED("Debugger.resumed", null),

    /**
    * Fired when virtual machine fails to parse the script.
    * @see jpuppeteer.cdp.cdp.entity.debugger.ScriptFailedToParseEvent
    */
    DEBUGGER_SCRIPTFAILEDTOPARSE("Debugger.scriptFailedToParse", jpuppeteer.cdp.cdp.entity.debugger.ScriptFailedToParseEvent.class),

    /**
    * Fired when virtual machine parses script. This event is also fired for all known and uncollected scripts upon enabling debugger.
    * @see jpuppeteer.cdp.cdp.entity.debugger.ScriptParsedEvent
    */
    DEBUGGER_SCRIPTPARSED("Debugger.scriptParsed", jpuppeteer.cdp.cdp.entity.debugger.ScriptParsedEvent.class),

    HEAPPROFILER_ADDHEAPSNAPSHOTCHUNK("HeapProfiler.addHeapSnapshotChunk", jpuppeteer.cdp.cdp.entity.heapprofiler.AddHeapSnapshotChunkEvent.class),

    /**
    * If heap objects tracking has been started then backend may send update for one or more fragments
    * @see jpuppeteer.cdp.cdp.entity.heapprofiler.HeapStatsUpdateEvent
    */
    HEAPPROFILER_HEAPSTATSUPDATE("HeapProfiler.heapStatsUpdate", jpuppeteer.cdp.cdp.entity.heapprofiler.HeapStatsUpdateEvent.class),

    /**
    * If heap objects tracking has been started then backend regularly sends a current value for last seen object id and corresponding timestamp. If the were changes in the heap since last event then one or more heapStatsUpdate events will be sent before a new lastSeenObjectId event.
    * @see jpuppeteer.cdp.cdp.entity.heapprofiler.LastSeenObjectIdEvent
    */
    HEAPPROFILER_LASTSEENOBJECTID("HeapProfiler.lastSeenObjectId", jpuppeteer.cdp.cdp.entity.heapprofiler.LastSeenObjectIdEvent.class),

    HEAPPROFILER_REPORTHEAPSNAPSHOTPROGRESS("HeapProfiler.reportHeapSnapshotProgress", jpuppeteer.cdp.cdp.entity.heapprofiler.ReportHeapSnapshotProgressEvent.class),

    HEAPPROFILER_RESETPROFILES("HeapProfiler.resetProfiles", null),

    PROFILER_CONSOLEPROFILEFINISHED("Profiler.consoleProfileFinished", jpuppeteer.cdp.cdp.entity.profiler.ConsoleProfileFinishedEvent.class),

    /**
    * Sent when new profile recording is started using console.profile() call.
    * @see jpuppeteer.cdp.cdp.entity.profiler.ConsoleProfileStartedEvent
    */
    PROFILER_CONSOLEPROFILESTARTED("Profiler.consoleProfileStarted", jpuppeteer.cdp.cdp.entity.profiler.ConsoleProfileStartedEvent.class),

    /**
    * Notification is issued every time when binding is called.
    * @see jpuppeteer.cdp.cdp.entity.runtime.BindingCalledEvent
    */
    RUNTIME_BINDINGCALLED("Runtime.bindingCalled", jpuppeteer.cdp.cdp.entity.runtime.BindingCalledEvent.class),

    /**
    * Issued when console API was called.
    * @see jpuppeteer.cdp.cdp.entity.runtime.ConsoleAPICalledEvent
    */
    RUNTIME_CONSOLEAPICALLED("Runtime.consoleAPICalled", jpuppeteer.cdp.cdp.entity.runtime.ConsoleAPICalledEvent.class),

    /**
    * Issued when unhandled exception was revoked.
    * @see jpuppeteer.cdp.cdp.entity.runtime.ExceptionRevokedEvent
    */
    RUNTIME_EXCEPTIONREVOKED("Runtime.exceptionRevoked", jpuppeteer.cdp.cdp.entity.runtime.ExceptionRevokedEvent.class),

    /**
    * Issued when exception was thrown and unhandled.
    * @see jpuppeteer.cdp.cdp.entity.runtime.ExceptionThrownEvent
    */
    RUNTIME_EXCEPTIONTHROWN("Runtime.exceptionThrown", jpuppeteer.cdp.cdp.entity.runtime.ExceptionThrownEvent.class),

    /**
    * Issued when new execution context is created.
    * @see jpuppeteer.cdp.cdp.entity.runtime.ExecutionContextCreatedEvent
    */
    RUNTIME_EXECUTIONCONTEXTCREATED("Runtime.executionContextCreated", jpuppeteer.cdp.cdp.entity.runtime.ExecutionContextCreatedEvent.class),

    /**
    * Issued when execution context is destroyed.
    * @see jpuppeteer.cdp.cdp.entity.runtime.ExecutionContextDestroyedEvent
    */
    RUNTIME_EXECUTIONCONTEXTDESTROYED("Runtime.executionContextDestroyed", jpuppeteer.cdp.cdp.entity.runtime.ExecutionContextDestroyedEvent.class),

    RUNTIME_EXECUTIONCONTEXTSCLEARED("Runtime.executionContextsCleared", null),

    /**
    * Issued when object should be inspected (for example, as a result of inspect() command line API call).
    * @see jpuppeteer.cdp.cdp.entity.runtime.InspectRequestedEvent
    */
    RUNTIME_INSPECTREQUESTED("Runtime.inspectRequested", jpuppeteer.cdp.cdp.entity.runtime.InspectRequestedEvent.class),

    ;

    private String name;

    private Class clazz;

    CDPEventType(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class getClazz() {
        return clazz;
    }

    public <T> T getObject(Object object) {
        if (clazz == null) {
            return null;
        } else if (!clazz.equals(object.getClass())) {
            throw new RuntimeException("can not cast " + object.getClass() + " to " + clazz);
        } else {
            return (T) object;
        }
    }

    public static CDPEventType findByName(String name) {
        for(CDPEventType val : values()) {
            if (val.name.equals(name)) return val;
        }
        return null;
    }
}