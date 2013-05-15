package sddl;

/**
 * @author BQ
 *
 */
import java.util.*;

interface Descr {
	String value();
	String descr();
}

//
// SDDLconstants From https://singularity.svn.codeplex.com/svn/base/Windows/Inc/Sddl.h
// We use class here instead of interface because interface does not allow static initializer which we use.
public class SddlConstants {
	//
	// SDDL Version information
	//
	static final int SDDL_REVISION_1 = 1;
	static final int SDDL_REVISION = SDDL_REVISION_1;

	//
	// SDDL Component tags
	//
	static final char SDDL_OWNER			= 'O';		// Owner tag 
	static final char SDDL_GROUP			= 'G';		// Group tag 
	static final char SDDL_DACL				= 'D';		// DACL tag 
	static final char SDDL_SACL				= 'S';		// SACL tag

	//
	// SDDL Security descriptor controls
	//
	static final int SDDL_FLAG_SIZE		= 2;
	
	enum SddlFlag implements Descr {
		SDDL_PROTECTED					("P", "DACL or SACL Protected"), 
		SDDL_AUTO_INHERIT_REQ			("AR", "Auto inherit request"), 
		SDDL_AUTO_INHERITED				("AI", "DACL/SACL are auto inherited");
		
		private SddlFlag(String value, String descr) { this.value = value; this.descr = descr; }
		private final String value;
		private final String descr;
		public String value() { return this.value; }
		public String descr() { return this.descr; }
	}
	
	//A map of SDDL flags to their names and descriptions

	static final Map<String, SddlFlag> sddlFlagMap = new HashMap<String, SddlFlag>();
	static {
		for (SddlFlag f: SddlFlag.values()) sddlFlagMap.put(f.value(), f);
	}

	//
	// SDDL Ace types
	//
	static final int SDDL_ACE_TYPE_SIZE		= 2;

	enum AceType implements Descr {
		ACCESS_ALLOWED						("A", "Access allowed"), 
		SDDL_ACCESS_DENIED					("D", "Access denied"), 
		SDDL_OBJECT_ACCESS_ALLOWED			("OA", "Object access allowed"), 
		SDDL_OBJECT_ACCESS_DENIED			("OD", "Object access denied"), 
		SDDL_AUDIT							("AU", "Audit"), 
		SDDL_ALARM							("AL", "Alarm"), 
		SDDL_OBJECT_AUDIT					("OU", "Object audit"), 
		SDDL_OBJECT_ALARM					("OL", "Object alarm");
		
		private AceType(String value, String descr) { this.value = value; this.descr = descr; }
		private final String value;
		private final String descr;
		public String value() { return this.value; }
		public String descr() { return this.descr; }
	}
	
	//A map of ACE types to their names and descriptions

	static final Map<String, AceType> aceTypeMap = new HashMap<String, AceType>();
	static {
		for (AceType type: AceType.values()) aceTypeMap.put(type.value(), type);
	}
	
	//
	// SDDL Ace flags
	//
	static final int SDDL_ACE_FLAG_SIZE		= 2;
	
	enum AceFlag implements Descr {
		SDDL_CONTAINER_INHERIT			("CI", "Container inherit"), 
		SDDL_OBJECT_INHERIT				("OI", "Object inherit"), 
		SDDL_NO_PROPAGATE				("NP", "Inherit no propagate"), 
		SDDL_INHERIT_ONLY				("IO", "Inherit only"), 
		SDDL_INHERITED					("ID", "Inherited"), 
		SDDL_AUDIT_SUCCESS				("SA", "Audit success"), 
		SDDL_AUDIT_FAILURE				("FA", "Audit failure");
		
		private AceFlag(String value, String descr) { this.value = value; this.descr = descr; }
		private final String value;
		private final String descr;
		public String value() { return this.value; }
		public String descr() { return this.descr; }
	}
	
	//A map of ACE flags to their names and descriptions

	static final Map<String, AceFlag> aceFlagMap = new HashMap<String, AceFlag>();
	static {
		for (AceFlag flag: AceFlag.values()) aceFlagMap.put(flag.value(), flag);
	}
	
	//
	// SDDL Rights
	//
	static final int SDDL_RIGHT_SIZE		= 2;
	
	enum AceRight implements Descr {
		SDDL_READ_PROPERTY		("RP"), 
		SDDL_WRITE_PROPERTY		("WP"), 
		SDDL_CREATE_CHILD		("CC"), 
		SDDL_DELETE_CHILD		("DC"), 
		SDDL_LIST_CHILDREN		("LC"), 
		SDDL_SELF_WRITE			("SW"), 
		SDDL_LIST_OBJECT		("LO"), 
		SDDL_DELETE_TREE		("DT"), 
		SDDL_CONTROL_ACCESS		("CR"), 
		SDDL_READ_CONTROL		("RC"), 
		SDDL_WRITE_DAC			("WD"), 
		SDDL_WRITE_OWNER		("WO"), 
		SDDL_STANDARD_DELET		("SD"), 
		SDDL_GENERIC_ALL		("GA"), 
		SDDL_GENERIC_READ		("GR"), 
		SDDL_GENERIC_WRITE		("GW"), 
		SDDL_GENERIC_EXECUTE	("GX"), 
		SDDL_FILE_ALL			("FA"), 
		SDDL_FILE_READ			("FR"), 
		SDDL_FILE_WRITE			("FW"), 
		SDDL_FILE_EXECUTE		("FX"), 
		SDDL_KEY_ALL			("KA"), 
		SDDL_KEY_READ			("KR"), 
		SDDL_KEY_WRITE			("KW"), 
		SDDL_KEY_EXECUTE		("KX");
		
		private AceRight(String value) { this.value = value; }
		private final String value;
		public String value() { return this.value; }
		public String descr() { return this.toString(); }
	}
	
	//A map of ACE rights to their names

	static final Map<String, AceRight> aceRightMap = new HashMap<String, AceRight>();
	static {
		for (AceRight right: AceRight.values()) aceRightMap.put(right.value(), right);
	}

	//
	// SDDL User alias max size
	//	      - currently, upto two supported eg. "DA"
	//	      - modify this if more WCHARs need to be there in future e.g. "DAX"
	//

	static final int SDDL_ALIAS_SIZE                     = 2;

	//
	// SDDL User aliases
	//
	enum SidAlias implements Descr {
		SDDL_DOMAIN_ADMINISTRATORS				("DA", "Domain admins"),
		SDDL_DOMAIN_GUESTS						("DG", "Domain guests"), 
		SDDL_DOMAIN_USERS						("DU", "Domain users"), 
		SDDL_ENTERPRISE_DOMAIN_CONTROLLERS		("ED", "Enterprise domain controllers"), 
		SDDL_DOMAIN_DOMAIN_CONTROLLERS			("DD", "Domain domain controllers"), 
		SDDL_DOMAIN_COMPUTERS					("DC", "Domain computers"), 
		SDDL_BUILTIN_ADMINISTRATORS				("BA", "Builtin (local) administrators"), 
		SDDL_BUILTIN_GUESTS						("BG", "Builtin (local) guests"), 
		SDDL_BUILTIN_USERS						("BU", "Builtin (local) users"), 
		SDDL_LOCAL_ADMIN						("LA", "Local administrator account"), 
		SDDL_LOCAL_GUEST						("LG", "Local group account"), 
		SDDL_ACCOUNT_OPERATORS					("AO", "Account operators"), 
		SDDL_BACKUP_OPERATORS					("BO", "Backup operators"), 
		SDDL_PRINTER_OPERATORS					("PO", "Printer operators"), 
		SDDL_SERVER_OPERATORS					("SO", "Server operators"), 
		SDDL_AUTHENTICATED_USERS				("AU", "Authenticated users"), 
		SDDL_PERSONAL_SELF						("PS", "Personal self"), 
		SDDL_CREATOR_OWNER						("CO", "Creator owner"), 
		SDDL_CREATOR_GROUP						("CG", "Creator group"), 
		SDDL_LOCAL_SYSTEM						("SY", "Local system"), 
		SDDL_POWER_USERS						("PU", "Power users"), 
		SDDL_EVERYONE							("WD", "Everyone ( World )"), 
		SDDL_REPLICATOR							("RE", "Replicator"), 
		SDDL_INTERACTIVE						("IU", "Interactive logon user"), 
		SDDL_NETWORK							("NU", "Nework logon user"), 
		SDDL_SERVICE							("SU", "Service logon user"), 
		SDDL_RESTRICTED_CODE					("RC", "Restricted code"), 
		SDDL_ANONYMOUS							("AN", "Anonymous Logon"), 
		SDDL_SCHEMA_ADMINISTRATORS				("SA", "Schema Administrators"), 
		SDDL_CERT_SERV_ADMINISTRATORS			("CA", "Certificate Server Administrators"), 
		SDDL_RAS_SERVERS						("RS", "RAS servers group"), 
		SDDL_ENTERPRISE_ADMINS					("EA", "Enterprise administrators"), 
		SDDL_GROUP_POLICY_ADMINS				("PA", "Group Policy administrators"), 
		SDDL_ALIAS_PREW2KCOMPACC				("RU", "alias to allow previous windows 2000"), 
		SDDL_LOCAL_SERVICE						("LS", "Local service account (for services)"), 
		SDDL_NETWORK_SERVICE					("NS", "Network service account (for services)"), 
		SDDL_REMOTE_DESKTOP						("RD", "Remote desktop users (for terminal server)"), 
		SDDL_NETWORK_CONFIGURATION_OPS			("NO", "Network configuration operators ( to manage configuration of networking features )"), 
		SDDL_PERFMON_USERS						("MU", "Performance Monitor Users"), 
		SDDL_PERFLOG_USERS						("LU", "Performance Log Users");
		
		private SidAlias(String value, String descr) { this.value = value; this.descr = descr; }
		private final String value;
		private final String descr;
		public String value() { return this.value; }
		public String descr() { return this.descr; }
	}
	
	//A map of sid aliases to their names and descriptions

	static final Map<String, SidAlias> sidAliasMap = new HashMap<String, SidAlias>();
	static {
		for (SidAlias sid: SidAlias.values()) sidAliasMap.put(sid.value(), sid);
	}

	//
	// SDDL Seperators - character version
	//
	static final char SDDL_SEPERATORC                      = ';';
	static final char SDDL_DELIMINATORC                    = ':';
	static final char SDDL_ACE_BEGINC                      = '(';
	static final char SDDL_ACE_ENDC                        = ')';
	static final char SDDL_DASHC                           = '-';

	//
	// SDDL Seperators - string version
	//
	static final String SDDL_SEPERATOR                      = ";";
	static final String SDDL_DELIMINATOR                    = ":";
	static final String SDDL_ACE_BEGIN                      = "(";
	static final String SDDL_ACE_END                        = ")";
	static final String SDDL_DASH	                        = "-";
	
	//
	// Other
	//
	static final char SDDL_NULL                      		= '\0';
	static final String SDDL_EMPTY                    		= "";
	static final String SDDL_SPACES                      	= "[ \t\n\b\f\r]";
	static final String NL                    				= "\n";
	
	//
	// SECURITY_DESCRIPTOR_CONTROL
	// http://msdn.microsoft.com/en-us/library/aa379566%28VS.85%29.aspx
	//
	enum SdCtr {
		SE_DACL_AUTO_INHERIT_REQ,
		SE_DACL_AUTO_INHERITED,
		SE_DACL_DEFAULTED,
		SE_DACL_PRESENT,
		SE_DACL_PROTECTED,
		SE_GROUP_DEFAULTED,
		SE_OWNER_DEFAULTED,
		SE_RM_CONTROL_VALID,
		SE_SACL_AUTO_INHERIT_REQ,
		SE_SACL_AUTO_INHERITED,
		SE_SACL_DEFAULTED,
		SE_SACL_PRESENT,
		SE_SACL_PROTECTED,
		SE_SELF_RELATIVE,
	}
	
	// well-known SIDs
	enum WellKnownSid {
		/* Universal well-known SIDs.
		 * see below.
			Null SID	S-1-0-0
			World	S-1-1-0
			Local	S-1-2-0
			Creator Owner ID	S-1-3-0
			Creator Group ID	S-1-3-1
		*/
		
		//RID values are used with universal well-known SIDs.
		//The Identifier authority column shows the prefix of the identifier authority with which you can 
		//combine the RID to create a universal well-known SID.
		
		SECURITY_NULL_SID						("S-1-0-0"), 
		SECURITY_WORLD_SID						("S-1-1-0"), 
		SECURITY_LOCAL_SID						("S-1-2-0"), 
		SECURITY_LOCAL_LOGON_SID				("S-1-2-1"), 
		SECURITY_CREATOR_OWNER_SID				("S-1-3-0"), 
		SECURITY_CREATOR_GROUP_SID				("S-1-3-1"), 
		
		//The SECURITY_NT_AUTHORITY (S-1-5) predefined identifier authority produces SIDs that are not 
		//universal but are meaningful only on Windows installations. You can use the following SID values 
		//with SECURITY_NT_AUTHORITY to create well-known SIDs.
		SECURITY_DIALUP_SID						("S-1-5-1"),
		SECURITY_NETWORK_SID					("S-1-5-2"), 
		SECURITY_BATCH_SID						("S-1-5-3"), 
		SECURITY_LOGON_IDS_SID					("S-1-5-5-X-Y"), 
		SECURITY_SERVICE_SID					("S-1-5-6"), 
		SECURITY_ANONYMOUS_LOGON_SID			("S-1-5-7"), 
		SECURITY_PROXY_SID						("S-1-5-8"), 
		SECURITY_ENTERPRISE_CONTROLLERS_SID		("S-1-5-9"), 
		SECURITY_PRINCIPAL_SELF_SID				("S-1-5-10"), 
		SECURITY_AUTHENTICATED_USER_SID			("S-1-5-11"), 
		SECURITY_RESTRICTED_CODE_SID			("S-1-5-12"), 
		SECURITY_TERMINAL_SERVER_SID			("S-1-5-13"), 
		SECURITY_LOCAL_SYSTEM_SID				("S-1-5-18"), 
		SECURITY_NT_NON_UNIQUE					("S-1-5-21"), 
		SECURITY_BUILTIN_DOMAIN_SID				("S-1-5-32");
		
		private WellKnownSid(String value) { this.value = value; }
		private final String value;
		public String value() { return this.value; }
	}
	
	//A map of well-known sids to their names
	//We cannot use an EnumMap here because Java does not allow '-' in identifier, so we could not make an 
	//enum of {S-1-3-1 etc.} and use it as key of EnumMap
	static final Map<String, WellKnownSid> wellKnownSidMap = new HashMap<String, WellKnownSid>();
	static {
		for (WellKnownSid sid: WellKnownSid.values()) wellKnownSidMap.put(sid.value(), sid);
	}

	// The following are for other well-known SIDs not found in the above WellKnownSidMap

	//predefined identifier authority constants for universal well-known SIDs;
	//the last value is used with Windows well-known SIDs.
	enum WellKnownSidAuth {
		SECURITY_NULL_SID_AUTHORITY			("S-1-0"),
		SECURITY_WORLD_SID_AUTHORIT			("S-1-1"),
		SECURITY_LOCAL_SID_AUTHORITY		("S-1-2"),
		SECURITY_CREATOR_SID_AUTHORITY		("S-1-3"),
		SECURITY_NT_AUTHORITY				("S-1-5");
		
		private WellKnownSidAuth(String value) { this.value = value; }
		private final String value;
		String value() { return value; }
	};
	
	//RIDs are used to specify mandatory integrity level.
	//TODO: it seems that SIDs starting with S- have 10 ordinal, whereas others have 16 ordinal
	enum IntegrityLevelRid {
		SECURITY_MANDATORY_UNTRUSTED_RID			("00000000"),
		SECURITY_MANDATORY_LOW_INTEGRITY_RID		("00001000"),
		SECURITY_MANDATORY_MEDIUM_INTEGRITY_RID		("00002000"),
		SECURITY_MANDATORY_SYSTEM_INTEGRITY_RID		("00004000"),
		SECURITY_MANDATORY_PROTECTED_PROCESS_RID	("00005000");
		
		private IntegrityLevelRid(String value) { this.value = value; }
		private final String value;
		String value() { return value; }
	}
	
	// Well-known SIDs type on Windows
	enum WinWellKnownRid {
		WinNullRid(0), 
		WinWorldRid(1), 
		WinLocalRid(2), 
		WinCreatorOwnerRid(3), 
		WinCreatorGroupRid(4), 
		WinCreatorOwnerServerRid(5), 
		WinCreatorGroupServerRid(6), 
		WinNtAuthorityRid(7), 
		WinDialupRid(8), 
		WinNetworkRid(9), 
		WinBatchRid(10), 
		WinInteractiveRid(11), 
		WinServiceRid(12), 
		WinAnonymousRid(13), 
		WinProxyRid(14), 
		WinEnterpriseControllersRid(15), 
		WinSelfRid(16), 
		WinAuthenticatedUserRid(17), 
		WinRestrictedCodeRid(18), 
		WinTerminalServerRid(19), 
		WinRemoteLogonIdRid(20), 
		WinLogonIdsRid(21), 
		WinLocalSystemRid(22), 
		WinLocalServiceRid(23), 
		WinNetworkServiceRid(24), 
		WinBuiltinDomainRid(25), 
		WinBuiltinAdministratorsRid(26), 
		WinBuiltinUsersRid(27), 
		WinBuiltinGuestsRid(28), 
		WinBuiltinPowerUsersRid(29), 
		WinBuiltinAccountOperatorsRid(30), 
		WinBuiltinSystemOperatorsRid(31), 
		WinBuiltinPrintOperatorsRid(32), 
		WinBuiltinBackupOperatorsRid(33), 
		WinBuiltinReplicatorRid(34), 
		WinBuiltinPreWindows2000CompatibleAccessRid(35), 
		WinBuiltinRemoteDesktopUsersRid(36), 
		WinBuiltinNetworkConfigurationOperatorsRid(37), 
		WinAccountAdministratorRid(38), 
		WinAccountGuestRid(39), 
		WinAccountKrbtgtRid(40), 
		WinAccountDomainAdminsRid(41), 
		WinAccountDomainUsersRid(42), 
		WinAccountDomainGuestsRid(43), 
		WinAccountComputersRid(44), 
		WinAccountControllersRid(45), 
		WinAccountCertAdminsRid(46), 
		WinAccountSchemaAdminsRid(47), 
		WinAccountEnterpriseAdminsRid(48), 
		WinAccountPolicyAdminsRid(49), 
		WinAccountRasAndIasServersRid(50), 
		WinNTLMAuthenticationRid(51), 
		WinDigestAuthenticationRid(52), 
		WinSChannelAuthenticationRid(53), 
		WinThisOrganizationRid(54), 
		WinOtherOrganizationRid(55), 
		WinBuiltinIncomingForestTrustBuildersRid(56), 
		WinBuiltinPerfMonitoringUsersRid(57), 
		WinBuiltinPerfLoggingUsersRid(58), 
		WinBuiltinAuthorizationAccessRid(59), 
		WinBuiltinTerminalServerLicenseServersRid(60), 
		WinBuiltinDCOMUsersRid(61), 
		WinBuiltinIUsersRid(62), 
		WinIUserRid(63), 
		WinBuiltinCryptoOperatorsRid(64), 
		WinUntrustedLabelRid(65), 
		WinLowLabelRid(66), 
		WinMediumLabelRid(67), 
		WinHighLabelRid(68), 
		WinSystemLabelRid(69), 
		WinWriteRestrictedCodeRid(70), 
		WinCreatorOwnerRightsRid(71), 
		WinCacheablePrincipalsGroupRid(72), 
		WinNonCacheablePrincipalsGroupRid(73), 
		WinEnterpriseReadonlyControllersRid(74), 
		WinAccountReadonlyControllersRid(75), 
		WinBuiltinEventLogReadersGroup(76), 
		WinNewEnterpriseReadonlyControllersRid(77), 
		WinBuiltinCertSvcDComAccessGroup(78);

		private WinWellKnownRid(int value) { this.value = Integer.toString(value); }
		private final String value;
		String value() { return value; }
	}

	//A map of other well-known SIDs not found in the above WellKnownSidMap
	//We cannot use an EnumMap here because Java does not allow '-' in identifier, so we could not make an 
	//enum of {S-1-3-1 etc.} and use it as key of EnumMap
	static final Map<String, Enum<?>> wellKnownSidMap2 = new HashMap<String, Enum<?>>();
	static {
		for (WellKnownSidAuth sidAuth: WellKnownSidAuth.values()) {
			for (WinWellKnownRid rid: WinWellKnownRid.values())
				wellKnownSidMap2.put(sidAuth.value() + "-" + rid.value(), rid);
			for (IntegrityLevelRid rid: IntegrityLevelRid.values())
				wellKnownSidMap2.put(sidAuth.value() + "-" + rid.value(), rid);
		}
	}
}