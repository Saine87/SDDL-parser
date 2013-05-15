/**
 * 
 */
package sddl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sddl.Sddl.IllegalFormatSddl;
import sddl.Sddl.InvalidTokenInSddl;
import sddl.Sddl.NullACLsInSddl;
import sddl.Sddl.UnSupportedTagInSddl;

/**
 * @author BQ
 *
 */
public class SddlFormatTest {	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//set up a mock SidsResolver
		SidsResolverFactory.setInstance(new SidsResolver() {
			public String[] resolveSids(String[] sids) {
				Map<String, String> SidMap = new HashMap<String, String>();

				SidMap.put("S-1-0-0", "Test SID0");
				SidMap.put("bf967aba-0de6-11d0-a285-00aa003049e2", "Test SID1");
				SidMap.put("bf967a9c-0de6-11d0-a285-00aa003049e2", "Test SID2");
				SidMap.put("6da8a4ff-0e52-11d0-a286-00aa003049e2", "Test SID3");
				SidMap.put("bf967aa8-0de6-11d0-a285-00aa003049e2", "Test SID4");
				SidMap.put("S-1-394713083-3668336197-2626859016-1331753871-2927391653-2060153963", "Internal Provisioning");
				SidMap.put("S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404", "Token Operators"); 
				SidMap.put("S-1-394713083-3668336197-512", "Administrators"); 
				SidMap.put("afeef501-e8c1-b047-a2c5-53b655e01d43", "mitoken-Blob"); 
				SidMap.put("19ca1f19-c9d5-b443-ae17-a3243e5b3013", "mitoken-MobileSoftToken"); 
				SidMap.put("26d97369-6070-11d1-a9c6-0000f80367c1", "Object-Category"); 
				SidMap.put("bf9679e7-0de6-11d0-a285-00aa003049e2", "Object-Guid"); 
				SidMap.put("19199728-ae90-2d4b-92d5-dc85f01a7552", "mitoken-YubiKeyToken"); 
				SidMap.put("303c951e-b30e-f24e-91b7-bac01357f134", "mitoken-User-GUID"); 
				SidMap.put("5b4f89dc-2927-6044-a1ee-9dd606390f56", "mitoken-LastUsedCounter64"); 
				SidMap.put("7b9f9664-417c-4a4d-ac00-4a634b626720", "mitoken-InstallationResult"); 
				SidMap.put("S-1-394713083-3668336197-3384110351-1285990756-2248301485-250344422", 
						"S-1-394713083-3668336197-3384110351-1285990756-2248301485-250344422"); 
				SidMap.put("e66a7ab1-4e78-ad47-b568-8e9b063d7ae9", "mitoken-ActivatedMobileSoftToken"); 
				SidMap.put("334cc1a6-a171-5d4a-9f35-105bf9c93c56", "mitoken-DeviceHeaders"); 
				SidMap.put("97138b55-9868-455f-b457-f630f909225b", "MiToken-Operator-Writable-Properties"); 
				SidMap.put("ca946999-11ff-104f-a1c4-ceaead46ade7", "mitoken-DriftSteps"); 
				SidMap.put("88d24edd-bd8a-1c48-a1b3-f8b567a7c4b3", "mitoken-SerialNumber");
				
				String[] descrs = new String[sids.length];

				int i = 0;
				for (String sid: sids) {
					descrs[i++] = SidMap.get(sid);
				}

				return descrs;
		}});
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		SidsResolverFactory.setInstance(null);
	}

	/**
	 * Test method for {@link sddl.SddlFormat#SddlFormat(sddl.Sddl)}.
	 * @throws IllegalFormatSddl 
	 * @throws InvalidTokenInSddl 
	 * @throws UnSupportedTagInSddl 
	 * @throws NullACLsInSddl 
	 */
	@Test
	public void testSddlFormat() throws NullACLsInSddl, UnSupportedTagInSddl, InvalidTokenInSddl, IllegalFormatSddl {
		// This is actually an acceptance test.
		
		// set up test fixture
		String[] sddlstrs= {
				"O:AOG:DAD:(A;;RPWPCCDCLCSWRCWDWOGA;;;S-1-0-0)",
				
				"O:DAG:DAD:(A;;RPWPCCDCLCRCWOWDSDSW;;;SY)"
				+ "(A;;RPWPCCDCLCRCWOWDSDSW;;;DA)"
				+ "(OA;;CCDC;bf967aba-0de6-11d0-a285-00aa003049e2;;AO)"
				+ "(OA;;CCDC;bf967a9c-0de6-11d0-a285-00aa003049e2;;AO)"
				+ "(OA;;CCDC;6da8a4ff-0e52-11d0-a286-00aa003049e2;;AO)"
				+ "(OA;;CCDC;bf967aa8-0de6-11d0-a285-00aa003049e2;;PO)"
				+ "(A;;RPLCRC;;;AU)S:(AU;SAFA;WDWOSDWPCCDCSW;;;WD)",
				
				"O:S-1-394713083-3668336197-512G:S-1-394713083-3668336197-512D:AI(A;CI;CCRPWP;;;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIIOID;WP;afeef501-e8c1-b047-a2c5-53b655e01d43;9b4a3e75-a0e9-1d46-88f4-c3e5d6515722;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIIOID;WP;ee1abb36-8ca3-e740-96b6-04f636b4773b;19199728-ae90-2d4b-92d5-dc85f01a7552;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIIOID;WP;bc457a93-a4fd-144a-a8ec-14e258514dd7;19199728-ae90-2d4b-92d5-dc85f01a7552;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIID;RP;bf967a0e-0de6-11d0-a285-00aa003049e2;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(OA;CIID;RP;bf967a0e-0de6-11d0-a285-00aa003049e2;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(OA;CIID;RP;26d4321f-ee9c-c34e-804a-05931791a3bc;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(OA;CIID;RP;26d4321f-ee9c-c34e-804a-05931791a3bc;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(OA;CIID;RP;97138b55-9868-455f-b457-f630f909225b;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(OA;CIID;RP;26d97369-6070-11d1-a9c6-0000f80367c1;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(OA;CIID;RP;26d97369-6070-11d1-a9c6-0000f80367c1;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(OA;CIID;RP;12032779-9e81-435a-b2f3-121ab2467055;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(OA;CIID;RP;12032779-9e81-435a-b2f3-121ab2467055;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(OA;CIID;RP;bf9679e5-0de6-11d0-a285-00aa003049e2;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(OA;CIID;RP;bf9679e5-0de6-11d0-a285-00aa003049e2;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(OA;CIID;RP;bf9679e7-0de6-11d0-a285-00aa003049e2;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(OA;CIID;RP;bf9679e7-0de6-11d0-a285-00aa003049e2;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(OA;CIID;WP;2c5eec62-32c3-f447-8818-ae2e49f958e3;;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIID;WP;ca946999-11ff-104f-a1c4-ceaead46ade7;;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIID;WP;5b4f89dc-2927-6044-a1ee-9dd606390f56;;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIID;WP;ffeae6de-2639-de47-bbb3-23041b731b0e;;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(OA;CIID;RPWP;97138b55-9868-455f-b457-f630f909225b;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(A;CIID;LC;;;S-1-394713083-3668336197-2374565103-1090902329-4160518571-2958893976)(A;CIID;LC;;;S-1-394713083-3668336197-3307769337-1289553793-260056251-1545652222)(A;CIID;LCRP;;;S-1-394713083-3668336197-4062723690-1075087636-347875498-3398394404)(A;CIID;LCRPRC;;;S-1-394713083-3668336197-2626859016-1331753871-2927391653-2060153963)(A;CIID;CCDCLCSWRPWPDTLOCRSDRCWDWO;;;S-1-394713083-3668336197-512)S:AI(AU;CIIDSAFA;0x10d016b;;;WD)",
		};
		
		int i;
		for (i = 0; i < sddlstrs.length; i++) {
			Sddl sddl = new Sddl(sddlstrs[i]);
			SddlFormat sddlFormat = new SddlFormat(sddl);
			// SDDL raw string
			System.out.println("SDDL[" + i + "] " + sddlFormat);
			// Parser result
			System.out.println("SDDL[" + i + "] " + sddlFormat.toParserResult());
			// names are resolved if possible
			System.out.println("SDDL[" + i + "] " + sddlFormat.toDescription());
			// verbose descriptions for names if any
			System.out.println("SDDL[" + i + "] " + sddlFormat.toVerboseDescription());
		}

		System.out.printf("There are total %d error(s) in the string(s). See the above for details.\n", Sddl.errs);

		assertEquals("Some exception(s) happened. Check the output for details. Errors ", 0, Sddl.errs);
		assertEquals("Some exception(s) happened. Loop counts ", sddlstrs.length, i);
	}

	public static void main(String args[]) {
	      org.junit.runner.JUnitCore.main("sddl.SddlFormatTest");
	}

}
