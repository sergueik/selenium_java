package jpuppeteer.cdp.cdp.entity.browser;

/**
* Definition of PermissionDescriptor defined in the Permissions API: https://w3c.github.io/permissions/#dictdef-permissiondescriptor.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PermissionDescriptor {

    /**
    * Name of permission. See https://cs.chromium.org/chromium/src/third_party/blink/renderer/modules/permissions/permission_descriptor.idl for valid permission names.
    */
    private String name;

    /**
    * For "midi" permission, may also specify sysex control.
    */
    private Boolean sysex;

    /**
    * For "push" permission, may specify userVisibleOnly. Note that userVisibleOnly = true is the only currently supported type.
    */
    private Boolean userVisibleOnly;

    /**
    * For "wake-lock" permission, must specify type as either "screen" or "system".
    */
    private String type;

    /**
    * For "clipboard" permission, may specify allowWithoutSanitization.
    */
    private Boolean allowWithoutSanitization;



}