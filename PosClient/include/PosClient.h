#include <jni.h>

#ifndef _Included_mn_mta_vatps_pos_client_PosClient
#define _Included_mn_mta_vatps_pos_client_PosClient
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL Java_mn_mta_vatps_pos_client_PosClient_loadLibrary
        (JNIEnv *, jclass, jstring);

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_checkApi
        (JNIEnv *, jclass);

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_getInformation
        (JNIEnv *, jclass);

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_callFunction
        (JNIEnv *, jclass, jstring, jstring);

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_put
        (JNIEnv *, jclass, jstring);

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_returnBill
        (JNIEnv *, jclass, jstring);

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_sendData
        (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
