## Verify Payments android SDK [ ![Download](https://api.bintray.com/packages/pavel-gabriel/verifypayments/android-sdk/images/download.svg) ](https://bintray.com/pavel-gabriel/verifypayments/android-sdk/_latestVersion)
Verify Payments is an embeddable payment form for desktop and mobile devices.

## Installation
Before you start you have to sign up for a Verify Payments account [here](https://verifypayments.com/).

Download [the latest AAR](https://bintray.com/pavel-gabriel/verifypayments/android-sdk/_latestVersion) or grab via Maven:
```xml
<dependency>
  <groupId>com.verifypayments</groupId>
  <artifactId>android-sdk</artifactId>
  <version>LATEST_VERSION</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.verifypayments:android-sdk:LATEST_VERSION'
```

## Usage
Before calling android sdk you must fetch session id from your application server. Then start `PaymentActivity` for result:
```
PaymentActivity.startForResult(this, publicKey, sessionId, null, true, PAY_REQUEST_CODE)
```
and process result in `onActivityResult`:
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == PAY_REQUEST_CODE) {
        if (resultCode == Activity.RESULT_OK) {
            val status = data!!.getStringExtra(PaymentActivity.EXTRA_RESULT_STATUS)
            val id = data.getStringExtra(PaymentActivity.EXTRA_RESULT_ID)
            // process success
        } else {
            // process cancel/error
        }
    }    
}
```

## Licence
The MIT License

Copyright (c) 2018-2019 Verify Payments (https://verifypayments.com/)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
