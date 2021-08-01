package example;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinBase.FILETIME;

public class Credential extends Structure {
	/* typedef struct _CREDENTIAL {
		  DWORD                 Flags;
		  DWORD                 Type;
		  LPTSTR                TargetName;
		  LPTSTR                Comment;
		  FILETIME              LastWritten;
		  DWORD                 CredentialBlobSize;
		  LPBYTE                CredentialBlob;
		  DWORD                 Persist;
		  DWORD                 AttributeCount;
		  PCREDENTIAL_ATTRIBUTE Attributes;
		  LPTSTR                TargetAlias;
		  LPTSTR                UserName;
		} CREDENTIAL, *PCREDENTIAL; */
	public static class ByReference extends Credential implements Structure.ByReference {}
	
	public int                 		Flags;
	public int                 		Type;
	public Pointer               	TargetName;
	public Pointer               	Comment;
	public FILETIME           		LastWritten = new FILETIME();
	public int                 		CredentialBlobSize;
	public Pointer                	CredentialBlob;
	public int	                 	Persist;
	public int                 		AttributeCount;    	
	public Pointer			 		Attributes;
	public Pointer                	TargetAlias;
	public Pointer                	UserName;

	@SuppressWarnings("unchecked")
	@Override
	protected List getFieldOrder() {
		return Arrays.asList("Flags", "Type", "TargetName", "Comment", "LastWritten", "CredentialBlobSize", 
				"CredentialBlob", "Persist", "AttributeCount", "Attributes", "TargetAlias", "UserName");
	}

	public Credential(Pointer p) {
		super(p);
	}

	public Credential() {
	}

}
