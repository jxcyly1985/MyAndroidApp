# ============================================================================
#  Name	 : build_help.mk
#  Part of  : AppDemo
# ============================================================================
#  Name	 : build_help.mk
#  Part of  : AppDemo
#
#  Description: This make file will build the application help file (.hlp)
# 
# ============================================================================

do_nothing :
	@rem do_nothing

# build the help from the MAKMAKE step so the header file generated
# will be found by cpp.exe when calculating the dependency information
# in the mmp makefiles.

MAKMAKE : AppDemo_0xED46D97D.hlp
AppDemo_0xED46D97D.hlp : AppDemo.xml AppDemo.cshlp Custom.xml
	cshlpcmp AppDemo.cshlp
ifeq (WINSCW,$(findstring WINSCW, $(PLATFORM)))
	md $(EPOCROOT)epoc32\$(PLATFORM)\c\resource\help
	copy AppDemo_0xED46D97D.hlp $(EPOCROOT)epoc32\$(PLATFORM)\c\resource\help
endif

BLD : do_nothing

CLEAN :
	del AppDemo_0xED46D97D.hlp
	del AppDemo_0xED46D97D.hlp.hrh

LIB : do_nothing

CLEANLIB : do_nothing

RESOURCE : do_nothing
		
FREEZE : do_nothing

SAVESPACE : do_nothing

RELEASABLES :
	@echo AppDemo_0xED46D97D.hlp

FINAL : do_nothing
