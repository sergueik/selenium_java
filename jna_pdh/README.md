### Info
this directory contains
a fragment of the `PDHTest.java` [test class](https://github.com/java-native-access/jna/blob/master/contrib/platform/test/com/sun/jna/platform/win32/PdhTest.java) from
[jna](https://github.com/java-native-access/jna) project focusing on collecting the `System\Processor Queue Length` performance metric

The code was modeled after the native app implementation found in [host-sflow](https://github.com/sflow/host-sflow):
```c
uint64_t readFormattedCounter(char* path)
{
    PDH_HQUERY Query = NULL;
    PDH_HCOUNTER Counter;
	DWORD dwType;
	PDH_FMT_COUNTERVALUE Value;
	LONGLONG ret = 0;

    if(PdhOpenQuery(NULL, 0, &Query) == ERROR_SUCCESS) {
		if(PdhAddCounter(Query, path, 0, &Counter) == ERROR_SUCCESS && 
           PdhCollectQueryData(Query) == ERROR_SUCCESS &&
		   PdhGetFormattedCounterValue(Counter, PDH_FMT_LARGE, &dwType, &Value) == ERROR_SUCCESS) { 
			ret = Value.largeValue;
		}
		if (Query) PdhCloseQuery(Query);
    }
	return (uint64_t)ret;
}


```
the test copied from existing JNA PDH tests:
```java
    
  	@Test
  	public void testProcessorQueueLength() {
  		PDH_COUNTER_PATH_ELEMENTS elems = new PDH_COUNTER_PATH_ELEMENTS();

  		elems.szObjectName = "System";
  		elems.szInstanceName = null;
  		elems.szCounterName = "Processor Queue Length";
  		String counterName = makeCounterPath(pdh, elems);

  		HANDLEByReference ref = new HANDLEByReference();
  		assertErrorSuccess("PdhOpenQuery", pdh.PdhOpenQuery(null, null, ref), true);

  		HANDLE hQuery = ref.getValue();
  		try {
  			ref.setValue(null);
  			assertErrorSuccess("PdhAddEnglishCounter",
  					pdh.PdhAddEnglishCounter(hQuery, counterName, null, ref), true);

  			HANDLE hCounter = ref.getValue();
  			try {
  				assertErrorSuccess("PdhCollectQueryData",
  						pdh.PdhCollectQueryData(hQuery), true);

  				DWORDByReference lpdwType = new DWORDByReference();
  				PDH_RAW_COUNTER rawCounter = new PDH_RAW_COUNTER();
  				assertErrorSuccess("PdhGetRawCounterValue",
  						pdh.PdhGetRawCounterValue(hCounter, lpdwType, rawCounter), true);
  				assertEquals("Counter data status", WinError.ERROR_SUCCESS,
  						rawCounter.CStatus);
  				showRawCounterData(System.out, counterName, rawCounter);
  			} finally {
  				assertErrorSuccess("PdhRemoveCounter", pdh.PdhRemoveCounter(hCounter),
  						true);
  			}
  		} finally {
  			assertErrorSuccess("PdhCloseQuery", pdh.PdhCloseQuery(hQuery), true);
  		}
  	}

```
### Usage

The easiest way to see a non-zero value of the Processor Queue is to run the test in the underpowered VM.

```sh
mvn test
```
```text
[INFO] Running com.sun.jna.platform.win32.PdhTest
```
```text
\System\Processor Queue Length Thu Mar 03 08:50:27 PST 2022 1st=14 2nd=0 multi=1
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
