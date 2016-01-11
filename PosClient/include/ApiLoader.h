//
// Created by nasanjargal on 1/4/16.
//

#ifndef POSCLIENT_APILOADER_H
#define POSCLIENT_APILOADER_H

#include <string>
#include <QtCore>

using namespace std;

#ifdef WIN32
#include <comutil.h>

typedef BSTR UString;
typedef const wchar_t* UParam;
#else
typedef string UString;
typedef string UParam;
#endif

typedef UString(*checkApiFunc)(void);

typedef UString(*getInformationFunc)(void);

typedef UString(*callFunctionFunc)(UParam, UParam);

typedef UString(*putFunc)(UParam);

typedef UString(*returnBillFunc)(UParam);

typedef UString(*sendDataFunc)(void);

class ApiLoader {

private:

    QLibrary *libPosAPI;

    checkApiFunc checkApiP;
    getInformationFunc getInformationP;
    callFunctionFunc callFunctionP;
    putFunc putP;
    returnBillFunc returnBillP;
    sendDataFunc sendDataP;

public:
    ApiLoader();

    ~ApiLoader();

    void load(QString&);

    string checkApi();

    string getInformation();

    string callFunction(string, string);

    string put(string);

    string returnBill(string);

    string sendData();

};


#endif //POSCLIENT_APILOADER_H
