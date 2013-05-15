# SDDL Parser
=============



## Overview

The Security Descriptor String Format is a text format for storing or transporting information in a security descriptor.

SDDL Parser is a java program used to parse SDDL String to give more the string format humand-readable.

### Usage

go to home project

mvn package

java -classpath target/SDDL-parser-1.0-SNAPSHOT.jar sddl.Parser "D:P(A;;GA;;;SY)(A;;GRGWGX;;;BA)(A;;GRGWGX;;;WD)(A;;GRGX;;;RC)"