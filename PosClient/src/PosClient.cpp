//
// Created by nasanjargal on 1/11/16.
//

#include "PosClient.h"
#include "ApiLoader.h"

void deleteQApplication(QCoreApplication *qapp) {
    qapp->quit();
    exit(EXIT_SUCCESS);
}

void deleteApiLoader(ApiLoader *api) {
    delete api;
}

static int argc = 1;
static char *argv[1] = {NULL};
QSharedPointer<QCoreApplication> appPointer = QSharedPointer<QCoreApplication>(new QCoreApplication(argc, argv),
                                                                               deleteQApplication);
QSharedPointer<ApiLoader> apiLoaderPointer = QSharedPointer<ApiLoader>(new ApiLoader(), deleteApiLoader);

JNIEXPORT jboolean JNICALL Java_mn_mta_vatps_pos_client_PosClient_loadLibrary
        (JNIEnv *env, jclass c, jstring param) {

    char const *cParam = env->GetStringUTFChars(param, 0);
    QString libName(QString::fromStdString(cParam));

    apiLoaderPointer->load(libName);

    return true;
}

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_checkApi
        (JNIEnv *env, jclass c) {
    string result = apiLoaderPointer->checkApi();
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_getInformation
        (JNIEnv *env, jclass c) {
    string result = apiLoaderPointer->getInformation();
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_callFunction
        (JNIEnv *env, jclass c, jstring funcName, jstring param) {
    char const *cFuncName = env->GetStringUTFChars(funcName, 0);
    char const *cParam = env->GetStringUTFChars(param, 0);
    string result = apiLoaderPointer->callFunction(string(cFuncName), string(cParam));
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_put
        (JNIEnv *env, jclass c, jstring param) {
    char const *cParam = env->GetStringUTFChars(param, 0);
    string result = apiLoaderPointer->put(string(cParam));
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_returnBill
        (JNIEnv *env, jclass c, jstring param) {
    char const *cParam = env->GetStringUTFChars(param, 0);
    string result = apiLoaderPointer->returnBill(string(cParam));
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jstring JNICALL Java_mn_mta_vatps_pos_client_PosClient_sendData
        (JNIEnv *env, jclass c) {
    string result = apiLoaderPointer->sendData();
    return env->NewStringUTF(result.c_str());
}
