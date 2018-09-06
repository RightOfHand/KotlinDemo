//JSEvent
// var dom = document;
String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
}
var JSEvent = function(el) {
    return new _JSEvent(el);
};
var _JSEvent = function(el) {
    this.el = (el && el.nodeType == 1)? el: document;
};
_JSEvent.prototype = {
    constructor: _JSEvent,
    addEvent: function(type, fn, capture) {
        var el = this.el;

        if (window.addEventListener) {
            el.addEventListener(type, fn, capture);

            var ev = document.createEvent("HTMLEvents");
            ev.initEvent(type, capture || false, false);
            // 在元素上存储创建的事件，方便自定义触发
            if (!el["ev" + type]) {
                el["ev" + type] = ev;
            }

        } else if (window.attachEvent) {
            el.attachEvent("on" + type, fn);
            if (isNaN(el["cu" + type])) {
                // 自定义属性，触发事件用
                el["cu" + type] = 0;
            }

            var fnEv = function(event) {
                if (event.propertyName == "cu" + type) {
                    fn.call(el);
                }
            };

            el.attachEvent("onpropertychange", fnEv);

            // 在元素上存储绑定的propertychange事件，方便删除
            if (!el["ev" + type]) {
                el["ev" + type] = [fnEv];
            } else {
                el["ev" + type].push(fnEv);
            }
        }

        return this;
    },
    fireEvent: function(type) {
        var el = this.el;
        if (typeof type === "string") {
            if (document.dispatchEvent) {
                if (el["ev" + type]) {
                    el.dispatchEvent(el["ev" + type]);
                }
            } else if (document.attachEvent) {
                // 改变对应自定义属性，触发自定义事件
                el["cu" + type]++;
            }
        }
        return this;
    },
    removeEvent: function(type, fn, capture) {
        var el = this.el;
        if (window.removeEventListener) {
            el.removeEventListener(type, fn, capture || false);
        } else if (document.attachEvent) {
            el.detachEvent("on" + type, fn);
            var arrEv = el["ev" + type];
            if (arrEv instanceof Array) {
                for (var i=0; i<arrEv.length; i+=1) {
                    // 删除该方法名下所有绑定的propertychange事件
                    el.detachEvent("onpropertychange", arrEv[i]);
                }
            }
        }
        return this;
    }
};
//loadURL
function loadURL(url) {
    var iFrame;
    iFrame = document.createElement("iframe");
    iFrame.setAttribute("src", url);
    iFrame.setAttribute("style", "display:none;");
    iFrame.setAttribute("height", "0px");
    iFrame.setAttribute("width", "0px");
    iFrame.setAttribute("frameborder", "0");
    document.body.appendChild(iFrame);
    // 发起请求后这个 iFrame 就没用了，所以把它从 dom 上移除掉
    iFrame.parentNode.removeChild(iFrame);
    iFrame = null;
}
//AND Mapping
var AndMapping = {
    'model': 'k3LE',
    'uevent': '0Qzb',
    'networkType': 'Swlw',
    'hardware': 'Jnh9',
    'packageName': 'VQFA',
    'availableMemory': 'qiHP',
    'existPipe': '7eMA',
    'totalSystem': 'bxIT',
    'displayRom': '2vam',
    'wifiMacAddress': '_Y6j',
    'timeZone': 'q5aJ',
    'resolution': '9_vV',
    'baseStation': 'yXoP',
    'musicHash': 'FNei',
    'photosHash': 'GLBj',
    'brightness': 'qm7W',
    'startupTime': 'oA3w',
    'version': 'XKTz',
    'currentWifi': 'bOhy',
    'battery': '89Fw',
    'existQemu': 'JC9t',
    'UDID': 'NfP-',
    'availableSD': 'Uaj4',
    'totalMemory': 'oUvy',
    'simulator': 'YIxZ',
    'cellularIP': 'PneH',
    'IMSI': '7UTv',
    'bootloader': 'O0oS',
    'board': 'hZFp',
    'totalSD': 'LtgM',
    'availableSystem': 'H6Rg',
    'product': 'qHkh',
    'sensors': '4i37',
    'wifiList': 'wXg4',
    'bluetooth': 'qCjR',
    'IMEI': '9POg',
    'nearbyBaseStation': 'EyOF',
    'appVersion': 'wfQn',
    'manufacturer': 'EAAo',
    'device': 'JjpN',
    'brand': 'BKn7',
    'rooted': 'UqMW',
    'activeTime': 'R7of',
    'contactsHash': 'kClR',
    'cpuType': 'aKfq',
    'cpuABI': 'U3oU',
    'algID': 'algID',
    'phoneNumber':'nrYU',
    'sdkVersion': 'Vkrm',
    'hashCode': 'hashCode',
    'serial': 'AUSx',
    'user': 'BPiZ',
    'radio': 'qjoF',
    'syncookies': '4MYi',
    'networkCountryIso': 'lRtB',
    'adb': 'fhag',
    'misc': 'nDJC',
    'stat': 'QEYN',
    'IOPorts': '4-es',
    'simSerialNumber': 'WCxt',
    'cpufreq': 'DR1H',
    'tags': 'l4wv',
    'simCountryIso': 'Hx1a',
    'fingerprint': 'RIY7',
    'id': 'pWFF',
    'switch': 'eLSf',
    'phoneType': 'LrgZ',
    'voiceMailNumber': 'd8FW',
    'ppp': 'xCiW',
    'networkOperator': 'csj7',
    'host': 'R0Cu',
    'parameters': '9Sgh',
    'type': 'EwPA',
    'isProxy': 'm6C7',
    'isVPN': 'G0rn',
    'allList': 'CneL',
    'runningList': 'gCnN',
    'account': 'mvIR',
    'sensorList': 'BtS2',
    'coordinates': 'Kavb',
    'appCache': '9PRr',
    'userAgent': '0aew',
};
//embed.js
var SDKElement = "";
var dom = document;
var dfp  = "";
var webviewType = '';
/**
 对URL字符进行过滤
 **/
function replaceEle(element){
    if(element==null||element==""||element==undefined){
        return null;
    }
    return element.replace(/\&|\+|\?|\%|\#|\/|\=/g, "");
}
/**
 注入SDK采集要素接口
 param1: 采集要素json结合
 param2: 平台信息 iOS/AND
 **/
function inputFields(jsonObj,platform,url){
    try{
        window.webviewType = platform;
        if(platform ==="AND"){
            window.SDKElement = JSON.parse(jsonObj);
            window.SDKElement["platform"] = "AND";
        }else{
            window.SDKElement = null;
        }
        if(window.SDKElement!=null && platform==="AND"){
            var replaceResult = replaceEle(window.SDKElement["userAgent"]);
            if(replaceResult!=null) window.SDKElement["userAgent"] = replaceResult;
            replaceResult = replaceEle(window.SDKElement["fingerprint"]);
            if(replaceResult!=null) window.SDKElement["fingerprint"] = replaceResult;
            replaceResult = replaceEle(window.SDKElement["phoneNumber"]);
            if(replaceResult!=null) window.SDKElement["phoneNumber"] = replaceResult;
            replaceResult = replaceEle(window.SDKElement["timeZone"]);
            if(replaceResult!=null) window.SDKElement["timeZone"] = replaceResult;
            replaceResult = replaceEle(window.SDKElement["displayRom"]);
            if(replaceResult!=null) window.SDKElement["displayRom"] = replaceResult;
            replaceResult = replaceEle(window.SDKElement["currentWifi"]);
            if(replaceResult!=null) window.SDKElement["currentWifi"] = replaceResult;
            replaceResult = replaceEle(window.SDKElement["voiceMailNumber"]);
            if(replaceResult!=null) window.SDKElement["voiceMailNumber"] = replaceResult;
        }
        if(SDKElement!=null&&SDKElement!=""&&SDKElement!=undefined){
            var script = document.createElement("script");
            script.src=url;
            document.head.appendChild(script);
        }
    }catch(err){
        JS.onFailed(err.message);
    }

}
function getFingerPrint(){
    new JSEvent(dom).addEvent("RailEvent", function() {
        if(window.webviewType==="AND"){
            document.cookie=dfp;
            JS.onSuccess(dfp);
        }
    });
}
getFingerPrint();