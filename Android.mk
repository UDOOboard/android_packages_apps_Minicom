LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional tests

LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_MODULE_PATH := $(TARGET_OUT_APPS)
LOCAL_PACKAGE_NAME := Minicom
LOCAL_REQUIRED_MODULES := libserial_port
#
include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
#
# # Use the following include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
#
