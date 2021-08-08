package example;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class App {

	public static void main(String[] args) {
		if (args.length > 0) {
			WindowsCredentialManager windowsCredentialManager = new WindowsCredentialManager();
			GenericWindowsCredentials genericWindowsCredentials = windowsCredentialManager.getByTargetName(args[0]);
			System.out.println(genericWindowsCredentials.toString());
		} else {
			displayAllGenericCredentialsDetails();
		}
	}

	private static void displayAllGenericCredentialsDetails() {
		IntByReference pCount = new IntByReference();
		PointerByReference pCredentials = new PointerByReference();

		boolean result = Advapi32_Credentials.INSTANCE.CredEnumerateW(null, 0, pCount, pCredentials);

		System.out.println("----------");
		System.out.println("showing all details of the generic credentials stored in Windows Credential Manager");
		System.out.println("number of credentials: " + pCount.getValue());
		System.out.println("----------");

		Pointer[] ps = pCredentials.getValue().getPointerArray(0, pCount.getValue());

		for (int i = 0; i < pCount.getValue(); i++) {

			Credential arrRef = new Credential(ps[i]);
			arrRef.read();
			if (CredentialType.valueOf(arrRef.Type) == CredentialType.CRED_TYPE_GENERIC) { // lists only
				System.out.println("credentials index:" + i);
				System.out.println("Flags: " + arrRef.Flags);
				System.out.println("Type: " + CredentialType.valueOf(arrRef.Type));
				System.out.println("TargetName/address: " + arrRef.TargetName.getWideString(0));
				System.out.println("LastWritten: " + arrRef.LastWritten.toDate());
				System.out.println("CredentialBlobSize: " + arrRef.CredentialBlobSize);
				if (arrRef.CredentialBlobSize > 0) {
					byte[] bytes = arrRef.CredentialBlob.getByteArray(0, arrRef.CredentialBlobSize);

					System.out.println(Arrays.toString(bytes));
					String s = new String(bytes, StandardCharsets.UTF_16LE);
					System.out.println("CredentialBlob(Password): " + s);
				}
				System.out.println("Persist: " + CredentialPersistType.valueOf(arrRef.Persist));
				System.out.println("AttributeCount: " + arrRef.AttributeCount);

				getUserName(arrRef);
				getTargetAlias(arrRef);

				System.out.println("----------");
			}
		}
	}

	private static String getUserName(Credential arrRef) {

		String result = null;
		try {
			if (arrRef.UserName != null) {
				result = arrRef.UserName.getWideString(0);
			}
		} catch (java.lang.Error e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			System.out.println("UserName: " + result);
		}
		return result;
	}

	private static String getTargetAlias(Credential arrRef) {

		String result = null;
		try {
			if (arrRef.TargetAlias != null) {
				result = arrRef.TargetAlias.getWideString(0);
			}
		} catch (java.lang.Error e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally {
			System.out.println("TargetAlias: " + result);
		}
		return result;
	}

}
