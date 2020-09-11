package jpuppeteer.cdp.cdp.entity.cast;

/**
* This is fired whenever the outstanding issue/error message changes. |issueMessage| is empty if there is no issue.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class IssueUpdatedEvent {

    /**
    */
    private String issueMessage;



}