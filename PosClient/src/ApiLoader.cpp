//
// Created by nasanjargal on 1/4/16.
//

#include "ApiLoader.h"
#include <QString>

#ifdef WIN32
string& BstrToStdString(const BSTR bstr, string& dst, int cp = CP_UTF8)
{
    if (!bstr)
    {
        // define NULL functionality. I just clear the target.
        dst.clear();
        return dst;
    }

    // request content length in single-chars through a terminating
    //  nullchar in the BSTR. note: BSTR's support imbedded nullchars,
    //  so this will only convert through the first nullchar.
    int res = WideCharToMultiByte(cp, 0, bstr, -1, NULL, 0, NULL, NULL);
    if (res > 0)
    {
        dst.resize(res);
        WideCharToMultiByte(cp, 0, bstr, -1, &dst[0], res, NULL, NULL);
    }
    else
    {    // no content. clear target
        dst.clear();
    }
    return dst;
}

// conversion with temp.
string BstrToString(BSTR bstr, int cp = CP_UTF8)
{
    string str;
    BstrToStdString(bstr, str, cp);
    return str;
}

#endif

UParam stringToUParam(string str) {
#ifdef WIN32
    QString qstr = QString::fromStdString(str);
    UParam param = qstr.toStdWString().c_str();
    return param;
#else
    return str;
#endif
}

string convertUstringToString(UString str) {
#ifdef WIN32
    string strResult = BstrToString(str);
    return strResult;
#else
    return str;
#endif
}

ApiLoader::ApiLoader() {

}

void ApiLoader::load(QString &path) {
    this->libPosAPI = new QLibrary(path);

    if (!libPosAPI->load()) {
        throw libPosAPI->errorString().toStdString();
    }

    this->checkApiP = (checkApiFunc)
            this->libPosAPI->resolve("checkApi");
    this->getInformationP = (getInformationFunc) libPosAPI->resolve("getInformation");
    this->callFunctionP = (callFunctionFunc) libPosAPI->resolve("callFunction");
    this->putP = (putFunc) libPosAPI->resolve("put");
    this->returnBillP = (returnBillFunc) libPosAPI->resolve("returnBill");
    this->sendDataP = (sendDataFunc) libPosAPI->resolve("sendData");

}

ApiLoader::~ApiLoader() {
    delete this->libPosAPI;
}

string ApiLoader::checkApi() {
    if(this-libPosAPI->isLoaded()) {
        UString result = this->checkApiP();
        return convertUstringToString(result);
    }
    throw string("Library Not Loaded");
}

string ApiLoader::getInformation() {
    if(this-libPosAPI->isLoaded()) {
        UString result = this->getInformationP();
        return convertUstringToString(result);
    }
    throw string("Library Not Loaded");
}

string ApiLoader::callFunction(string name, string param) {
    if(this-libPosAPI->isLoaded()) {
        UString result = this->callFunctionP(stringToUParam(name), stringToUParam(param));
        return convertUstringToString(result);
    }
    throw string("Library Not Loaded");
}

string ApiLoader::put(string param) {
    if(this-libPosAPI->isLoaded()) {
        UString result = this->putP(stringToUParam(param));
        return convertUstringToString(result);
    }
    throw string("Library Not Loaded");
}

string ApiLoader::returnBill(string param) {
    if(this-libPosAPI->isLoaded()) {
        UString result = this->returnBillP(stringToUParam(param));
        return convertUstringToString(result);
    }
    throw string("Library Not Loaded");
}

string ApiLoader::sendData() {
    if(this-libPosAPI->isLoaded()) {
        UString result = this->sendDataP();
        return convertUstringToString(result);
    }
    throw string("Library Not Loaded");
}
