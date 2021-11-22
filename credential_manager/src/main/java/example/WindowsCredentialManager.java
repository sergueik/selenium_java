package example;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class WindowsCredentialManager {

	public List<GenericWindowsCredentials> genericCredentials; 	
	
	public WindowsCredentialManager() {
		genericCredentials = new ArrayList<GenericWindowsCredentials>();
		enumerateGenericCredentials();
	}
	
	public GenericWindowsCredentials getByTargetName(String targetName) {
		
		for (GenericWindowsCredentials gwc : genericCredentials) {
			if (gwc.getAddress().equals(targetName)) {
				return gwc;
			}			
		}
		return null;
	}
	
	private void enumerateGenericCredentials() {
		IntByReference pCount = new IntByReference();
		PointerByReference pCredentials = new PointerByReference();	
		
		boolean result = Advapi32_Credentials.INSTANCE.CredEnumerateW(null, 0, pCount, pCredentials);
		
		Pointer[] ps = pCredentials.getValue().getPointerArray(0,  pCount.getValue());
		
		for (int i=0; i<pCount.getValue(); i++) {
		
			Credential arrRef = new Credential(ps[i]);
			arrRef.read();
			if (CredentialType.valueOf(arrRef.Type) == CredentialType.CRED_TYPE_GENERIC) { //only generic credentials
			
				GenericWindowsCredentials gwc = new GenericWindowsCredentials();
				gwc.setAddress(arrRef.TargetName.getWideString(0)); //address
				gwc.setUsername(getUserName(arrRef)); //username
				
			if (arrRef.CredentialBlobSize > 0) {
				byte[] bytes = arrRef.CredentialBlob.getByteArray(0, arrRef.CredentialBlobSize);
				
				gwc.setPassword(new String(bytes, StandardCharsets.UTF_16LE)); //password
			}		
					
			genericCredentials.add(gwc);
		
			}
		}
	}

	private String getUserName(Credential arrRef) {
		
		String result = null;
		try {				
			if (arrRef.UserName != null) {
				result = arrRef.UserName.getWideString(0); 
			}
		} catch (java.lang.Error e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			//System.out.println("UserName: " + result);
		}
		return result;
	}

}
