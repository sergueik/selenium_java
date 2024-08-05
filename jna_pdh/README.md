### Info

this directory contains
a derivative of the `PDHTest.java` [test class](https://github.com/java-native-access/jna/blob/master/contrib/platform/test/com/sun/jna/platform/win32/PdhTest.java)
and its dependencies 
  * `AbstractPlatformTestSupport` [class](https://github.com/java-native-access/jna/blob/master/contrib/platform/test/com/sun/jna/platform/AbstractPlatformTestSupport.java)
  * `AbstractWin32TestSupport` [class](https://github.com/java-native-access/jna/blob/master/contrib/platform/test/com/sun/jna/platform/win32/AbstractWin32TestSupport.java)

 from
[jna](https://github.com/java-native-access/jna) contribution project.

Note, that since the code above is part of the test, it is not included in the `jna-platform.jar` released to [Maven Central](https://mvnrepository.com/artifact/net.java.dev.jna/jna-platform) (the `Pdh.java` is )

The test is
focusing on collecting the Windows specific __Performance Counters__ metrics,
for example the `System\Processor Queue Length` or `Processor\0\% Processor Time` where the notation is:


token | description | comment
--- | --- | ---
`Processor` | category |
`% Processor Time`| counter |
`0`  | Instance  | blank for *single instance* counters

The code was modeled after the native C app implementation found in [host-sflow](https://github.com/sflow/host-sflow):
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
### See Also
   * [Performance Counters](https://learn.microsoft.com/en-us/windows/win32/perfctrs/performance-counters-portal)
     + [About Performance Counters](https://learn.microsoft.com/en-us/windows/win32/perfctrs/about-performance-counters)
     + [Using Performance Counters](https://learn.microsoft.com/en-us/windows/win32/perfctrs/using-performance-counters)
     + [Performance Counters Reference](https://learn.microsoft.com/en-us/windows/win32/perfctrs/performance-counters-reference)
     + [Performance Counter functions](https://learn.microsoft.com/en-us/windows/win32/perfctrs/performance-counters-functions)
     + [performance Counters XML Schema](https://learn.microsoft.com/en-us/windows/win32/perfctrs/performance-counters-schema)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
