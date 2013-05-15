package sddl;

/**
 * @author BQ
 *
 */
import java.util.*;

/* SDDL and SID References:
 SDDL Syntax refer to http://www.washington.edu/computing/support/windows/UWdomains/SDDL.html
 SDDL String format refer to http://msdn.microsoft.com/en-us/library/aa379570%28VS.85%29.aspx

 SID String format refer to http://msdn.microsoft.com/en-us/library/aa379570%28VS.85%29.aspx
 SID string constants for well-known SIDs are defined in Sddl.h above
 Well-known SIDs (both universal well-known SIDs and the specific well-known Sids on Windows platform)
                 refer to http://msdn.microsoft.com/en-us/library/aa379649%28VS.85%29.aspx
 WELL_KNOWN_SID_TYPE Enumeration refer to http://msdn.microsoft.com/en-us/library/aa379650%28VS.85%29.aspx
*/
public class Sddl extends SddlConstants {
	static int errs;
	
	static class NullACLsInSddl extends Exception {
		private static final long serialVersionUID = 1L;

		NullACLsInSddl() {
			super("The security descriptor string format does not support NULL ACLs.");
			errs++;
		}
	}
	
	static class UnSupportedTagInSddl extends Exception {
		private static final long serialVersionUID = 1L;

		UnSupportedTagInSddl(char tag, String sddlstr) {
			super("Unsupported tag " + tag
					+ " in the security descriptor string "
					+ sddlstr + ".");
			errs++;
		}
	}
	
	static class InvalidTokenInSddl extends Exception {
		private static final long serialVersionUID = 1L;

		InvalidTokenInSddl(String tok, int index, String token) {
			super("Invalid token " + tok
					+ " at index " + index
					+ " in the token string "
					+ token + ".");
			errs++;
		}
	}
	
	static class IllegalFormatSddl extends Exception {
		private static final long serialVersionUID = 1L;

		IllegalFormatSddl(String tok) {
			super("Illegal Format SDDL: " + tok);
			errs++;
		}
	}
	
	/* Except from: http://msdn.microsoft.com/en-us/library/aa379597%28VS.85%29.aspx
	 * 				
	 * SID Components
	 * A SID value includes components that provide information about the SID structure and components
	 * that uniquely identify a trustee. A SID consists of the following components:
     * The revision level of the SID structure
     * A 48-bit identifier authority value that identifies the authority that issued the SID
     * A variable number of subauthority or relative identifier (RID) values that uniquely identify the
     * trustee relative to the authority that issued the SID

	 * These functions use the following standardized string notation for SIDs, which makes it simpler to
	 *  visualize their components:
	 *  S-R-I-S¡­
	 *  In this notation, the literal character "S" identifies the series of digits as a SID,
	 *  R is the revision level, I is the identifier-authority value, and S¡­ is one or more subauthority values.
	 *  
	 * In the security descriptor definition language (SDDL), security descriptor string use SID strings for the following components of a security descriptor:
     *    Owner
     *    Primary group
     *    The trustee in an ACE
     * A SID string in a security descriptor string can use either the standard string representation of 
     * a SID (S-R-I-S-S¡­) or one of the string constants defined in Sddl.h. For more information about the 
     * standard SID string notation, see SID Components.
     * 
     * Well-known security identifiers (SIDs) identify generic groups and generic users. For example,
     * there are well-known SIDs to identify the following groups and users:
     * Everyone or World, which is a group that includes all users.
     * CREATOR_OWNER, which is used as a placeholder in an inheritable ACE. When the ACE is inherited,
     * the system replaces the CREATOR_OWNER SID with the SID of the object's creator.
     * The Administrators group for the built-in domain on the local computer.
     * 
     * There are universal well-known SIDs, which are meaningful on all secure systems using this security
     *  model, including operating systems other than Windows. In addition, there are well-known SIDs that 
     *  are meaningful only on Windows systems.
	 */
	// First we check if it is a SID constant string defined in sddl.h, which is a subset of well-known SIDs.
	// If it is, get its corresponding description defined in sddl.h;
	// otherwise, check if it is a well-known SIDs(both universal well-known SIDs and specific well-known SIDs on Windows)
	// otherwise, it is resolved by calling a RPC routine to the server.
	class Sid {
		String sidstr;								// SID string or SID constant string
		String name;								// SID name
		String descr;								// human-readable description
		private boolean isConstant;
		private String[] sub;						// S-R-I-S-S...
		
		Sid(String sidstr) {
			if (sidstr != null && !sidstr.isEmpty()) {
				try {
					sub = sidstr.split(SDDL_DASH);
					if (sub.length > 1) {							// it is a standard SID string
						sidlist.add(this);
						this.isConstant = false;
					} else {										// it is a SID constant string
						if (sidAliasMap.containsKey(sidstr)) {
							this.name = sidAliasMap.get(sidstr).toString();
							this.descr = sidAliasMap.get(sidstr).descr();
							this.isConstant = true;
						} else throw new InvalidTokenInSddl(sidstr, 0, sidstr);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			this.sidstr = sidstr;
		}
		
		boolean isConstant() { return isConstant; }
		@Override
		public String toString() { return (descr != null ? descr : (name != null ? name : sidstr)); }
	}
	
	class Ace {
		AceType[] aceType;								//allow/deny/audit
		AceFlag[] aceFlags;								//inheritance and audit settings
		AceRight[] acePermissions;						//list of incremental permissions
		Sid gUID;										//Object type
		Sid iGUID;										//Inherited object type
		Sid sID;										//Trustee
		
		Ace(String ace) {
			if (ace != null && !ace.isEmpty()) {
				try {
					String[] fields = ace.split(SDDL_SEPERATOR);
					if (fields.length == 6) {
						aceType = tokenParse(fields[0], aceTypeMap, SDDL_ACE_TYPE_SIZE, new AceType[0]);
						aceFlags = tokenParse(fields[1], aceFlagMap, SDDL_ACE_FLAG_SIZE, new AceFlag[0]);
						acePermissions = tokenParse(fields[2], aceRightMap, SDDL_RIGHT_SIZE, new AceRight[0]);
						gUID = new Sid(fields[3]);
						iGUID = new Sid(fields[4]);
						sID = new Sid(fields[5]);
					} else throw new IllegalFormatSddl(ace);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		@Override
		public String toString() {
			StringBuilder str = new StringBuilder();
			
			if (this.aceType == null) 
				str.append("\tACEType:\t" + NL);
			else
				str.append("\t\tACEType: " + Arrays.toString(this.aceType) + NL);
			
			if (this.aceFlags == null) 
				str.append("\tACEFlags:\t" + NL);
			else
				str.append("\t\tACEFlags: " + Arrays.toString(this.aceFlags) + NL);
			
			if (this.acePermissions == null) 
				str.append("\tACEPermissions:\t" + NL);
			else
				str.append("\t\tACEPermissions: " + Arrays.toString(this.acePermissions) + NL);
			
			str.append("\t\tACEObjectType: " + this.gUID + NL);
			str.append("\t\tACEInheritedObjectType: " + this.iGUID + NL);
			str.append("\t\tACETrustee: " + this.sID + NL);
			
			return str.toString();
		}
	}
	
	String header;						//revision info etc.
	EnumSet<SdCtr>	control;			//http://msdn.microsoft.com/en-us/library/aa379566%28VS.85%29.aspx
	
	Sid owner;
	Sid group;
	SddlFlag[] daclFlags;
	Ace[] daces;
	SddlFlag[] saclFlags;
	Ace[] saces;
	
	private String sddlstr;				// normalized sddl string
	
	//list of standard sids and guids in this sddl string, passed into
	//RPC routine resolveSids() for resolving sids.
	//SID constants defined in sddl.h and the well-known SIDs including both universal and specific Windows platfrom
	// are resolved here in SDDL construct. Other SIDs are resolved in Class SddlFormat by calling RPC to the server.
	List<Sid>	sidlist = Collections.synchronizedList(new LinkedList<Sid>());	
	
	/*
	 * @param sddlstr
	 * @throws NullACLsInSddl
	 * @throws UnSupportedTagInSddl
	 * @throws InvalidTokenInSddl
	 */
	Sddl(String sddlstr) throws NullACLsInSddl, UnSupportedTagInSddl, InvalidTokenInSddl, IllegalFormatSddl {
		// We assume sddlstr is a normalized legal SDDL String
		/* Excerpt:
		 * The format is a null-terminated string with tokens to indicate each of the four main components of
		 *  a security descriptor: owner (O:), primary group (G:), DACL (D:), and SACL (S:).
		 */
		// null-terminated string is meaningless in Java since Java String does not depend on null as a string
		// terminator, it is only meaningful in network transmission. We assume the sddlstr passed as an argument
		// is properly processed by the caller. However in case there is a null at the end, we simply discard it.

		// Besides, we cannot assume SDDL string contains all the four tags and in that order.
		/* Excerpt: Unneeded components can be omitted from the security descriptor string.
		 * For example, if the SE_DACL_PRESENT flag is not set in the input security descriptor, 
		 * ConvertSecurityDescriptorToStringSecurityDescriptor does not include a D: component in the 
		 * output string. You can also use the SECURITY_INFORMATION bit flags to indicate the components 
		 * to include in a security descriptor string.
		 * 
		 * The security descriptor string format does not support NULL ACLs.
		 * 
		 * To denote an empty ACL, the security descriptor string includes the D: or S: token with no 
		 * additional string information. The security descriptor string stores the SECURITY DESCRIPTOR
		 * CONTROL bits in different ways. The SE_DACL_PRESENT or SE_SACL_PRESENT bits are indicated by the
		 * presence of the D: or S: token in the string. Other bits that apply to the DACL or SACL are stored
		 *  in dacl_flags and sacl_flags. The SE_OWNER_DEFAULTED, SE_GROUP_DEFAULTED, SE_DACL_DEFAULTED, 
		 *  and SE_SACL_DEFAULTED bits are not stored in a security descriptor string. 
		 *  The SE_SELF_RELATIVE bit is not stored in the string, but ConvertStringSecurityDescriptorToSecurityDescriptor
		 *  always sets this bit in the output security descriptor.
		 */
		// Please refer to SDDL string format description in http://msdn.microsoft.com/en-us/library/aa379570%28VS.85%29.aspx		
		if (sddlstr == null || sddlstr.isEmpty()) {
			throw new NullACLsInSddl();
		}

		// in case there is a null at the end, discard it.
		if (sddlstr.charAt(sddlstr.length()-1) == SDDL_NULL) {
			sddlstr = sddlstr.substring(0, sddlstr.length()-1);
		}

		// eliminate whitespace characters if any
		sddlstr = sddlstr.replaceAll(SDDL_SPACES, SDDL_EMPTY);
		
		this.control = EnumSet.of(SdCtr.SE_SELF_RELATIVE);
		
		String[] tokens = sddlstr.split(SDDL_DELIMINATOR);

		for (int i =0; i < tokens.length - 1; i++) {
			// there is at least a tag there
			char tag = tokens[i].charAt(tokens[i].length()-1);
			// token may be empty
			String token = tokens[i+1].substring(0, tokens[i+1].length()-1);
			switch (tag) {
			case SDDL_OWNER:
				this.owner = new Sid(token);
				break;
			case SDDL_GROUP:
				this.group = new Sid(token);
				break;
			case SDDL_DACL:
				this.control.add(SdCtr.SE_DACL_PRESENT);
				toACL(token, SDDL_DACL);
				break;
			case SDDL_SACL:
				this.control.add(SdCtr.SE_SACL_PRESENT);
				toACL(token, SDDL_SACL);
				break;
			default:
				throw new UnSupportedTagInSddl(tag, sddlstr);
			}
		}
		
		// look for SID descriptions for the well-known SIDs
		for (Iterator<Sid> siditer = sidlist.iterator(); siditer.hasNext();) {
			Sid sid = siditer.next();
			
			if (wellKnownSidMap.containsKey(sid.sidstr)) {
				sid.name = wellKnownSidMap.get(sid.sidstr).toString();
				siditer.remove();
			}
			else if (wellKnownSidMap2.containsKey(sid.sidstr)) {
				sid.name = wellKnownSidMap2.get(sid.sidstr).toString();
				siditer.remove();
			}
			else if (sid.sidstr.startsWith("S-1-5-5-")) {	// handle the case S-1-5-5-X-Y
				sid.name = "SECURITY_LOGON_IDS_SID";
				siditer.remove();
			}
		}
		
		// the rest SIDs are resolved in Class SddlFormat by calling RPC routine to the server

		// keep record of the normalized sddl string
		this.sddlstr = sddlstr;
	}
	
	private void toACL(String token, final char tag) {
		SddlFlag[] aclFlags = null;
		Ace[] aces = null;
		
		if (!token.isEmpty()) {
			//there must be a '('
			int f_endindex = token.indexOf(SDDL_ACE_BEGINC);
			if (f_endindex > 0) 
				try {
					aclFlags = tokenParse(token.substring(0, f_endindex), sddlFlagMap, SDDL_FLAG_SIZE, new SddlFlag[0]);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			String[] str_aces = token.substring(f_endindex).split("[" + SDDL_ACE_BEGIN + "]|["
					+ SDDL_ACE_END + "][" + SDDL_ACE_BEGIN + "]|[" + SDDL_ACE_END + "]");
			// the first is empty, start from 1
			aces = new Ace[str_aces.length-1];
			for (int i = 1; i < str_aces.length; i++) {
				aces[i-1] = new Ace(str_aces[i]);
			}
		}
		if (tag == SDDL_DACL) {
			this.daclFlags = aclFlags;
			this.daces = aces;
		} else {
			this.saclFlags = aclFlags;
			this.saces = aces;
		}
	}
	
	/*
	 * @param token is the String to be parsed.
	 * @param tokenMap is the token map parsed against.
	 * @param tokenSize is the maximal token size allowed. usually 2. if it is 2, token often has size of 1 or 2.
	 * @return an array of valid tokens parsed.
	 */
	private static <T extends Enum<T>> T[] tokenParse(String token, Map<String, T> tokenMap, int tokenSize, T[] ta) throws InvalidTokenInSddl {
		List<T> list = new ArrayList<T>();
		
		int	index = 0;
		String tok = token.substring(index);
		int tSize = tokenSize;
		String t = null;

		// we first look at the token with size of 2, if invalid, for the token with size of 1, etc.
		while(!tok.isEmpty()) {
			while (tSize > 0 && tok.length() >= tSize) {
				t = tok.substring(0, tSize);
				if (tokenMap.containsKey(t)) {
					list.add(tokenMap.get(t));
					index += tSize;
					break;
				} else {
					tSize--;
					continue;
				}
			}	//end of inner while
			if (tSize == 0)	{ //token not found
				throw new InvalidTokenInSddl(t, index, token);
			} else if (tok.length() < tSize) {	//handle the case when the last tok is less than tokenSize
				tSize--;
				continue;
			}
			tok = token.substring(index);
			tSize = tokenSize;
		} //end of outer while
		
		return (list.toArray(ta));
	}
	
	// return a normalized SDDL String: legal with whitespace characters removed
	@Override
	public String toString() {
		return this.sddlstr;
	}
}