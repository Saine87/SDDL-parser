# SDDL Parser


## Overview

The Security Descriptor String Format is a text format for storing or transporting information in a security descriptor.

SDDL Parser is a java program used to parse SDDL String to give more the string format humand-readable.

#### Usage

go to home project

mvn package

java -classpath target/SDDL-parser-1.0-SNAPSHOT.jar sddl.Parser "O:AUG:AND:P(A;;GA;;;SY)(A;;GRGWGX;;;BA)(A;;GRGWGX;;;WD)(A;;GRGX;;;RC)"

#### output

Parsing Security Descriptor Definition Language (SDDL) string: 

[ O:AUG:AND:P(A;;GA;;;SY)(A;;GRGWGX;;;BA)(A;;GRGWGX;;;WD)(A;;GRGX;;;RC) ] 


Owner: Authenticated users

Group: Anonymous Logon

Discresionary ACE:

    Element #0: (A;;GA;;;SY)
		ACEType: [ACCESS_ALLOWED]
		ACEFlags: []
		ACEPermissions: [SDDL_GENERIC_ALL]
		ACEObjectType: 
		ACEInheritedObjectType: 
		ACETrustee: Local system
    Element #1: (A;;GRGWGX;;;BA)
		ACEType: [ACCESS_ALLOWED]
		ACEFlags: []
		ACEPermissions: [SDDL_GENERIC_READ, SDDL_GENERIC_WRITE, SDDL_GENERIC_EXECUTE]
		ACEObjectType: 
		ACEInheritedObjectType: 
		ACETrustee: Builtin (local) administrators
    Element #2: (A;;GRGWGX;;;WD)
		ACEType: [ACCESS_ALLOWED]
		ACEFlags: []
		ACEPermissions: [SDDL_GENERIC_READ, SDDL_GENERIC_WRITE, SDDL_GENERIC_EXECUTE]
		ACEObjectType: 
		ACEInheritedObjectType: 
		ACETrustee: Everyone ( World )
    Element #3: (A;;GRGX;;;RC)
		ACEType: [ACCESS_ALLOWED]
		ACEFlags: []
		ACEPermissions: [SDDL_GENERIC_READ, SDDL_GENERIC_EXECUTE]
		ACEObjectType: 
		ACEInheritedObjectType: 
		ACETrustee: Restricted code


