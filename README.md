# ONE-PUSH 推送服务

## 设计思路
1. 移动端使用推送服务时，先通过register接口注册用户的移动设备信息，获取一个分配的设备编号
2. 移动端连接push server的websocket，传入此设备编号
3. 业务系统服务端通过push接口发送消息
4. 移动端会收到websocket发来的在线或离线消息

## 服务地址

http://localhost:8080

## Rest API

- register, 注册移动设备。业务系统用户登录移动设备时进行注册，注册后即可接受推送消息，注册一次即可，服务端会一直保存设备信息，直到用户unregister
- unregister, 取消注册，用户退出业务系统或者切换用户时需要取消注册
- push, 推送消息，提供服务端接口用于业务系统发送消息

### register

```
url: /msgpush/register
method: POST
Content-Type: application/json
request object:
{
    "alias": "testuserid1",//必须，别名，一般设置为业务系统的userid，现在默认支持按别名推送消息
    "appId": "testapp1", //必须，appid
    "platform": "ANDROID",//必须，设备类型：ANDROID,IOS,PC,WEB
    "nativeToken": "andToken0011",//必须，设备识别码，ios要使用ios token,用于apns发送离线消息，其它设备没要求,唯一即可
	"mobile": "13700001111",//可选，手机号码
    "tag": ["tag1","sz"] //可选，标签，以后支持按标签推送消息
}

response object:
{
    "id": "xxxx"//返回的内部编号，建立websocket连接时传入?deviceId=xxx
}
```

### unregister

```
url: /msgpush/unregister/{deviceId}
method: POST
Content-Type: application/json
```

### websocket connect

```
url: ws://localhost:8080/msgpush.ws?deviceId=xxx

接收的消息格式：
{
	"appId":"testapp1", //应用id
	"tenantCode":"", //租户代码，多租户支持
	"alert":"alert title", //提示消息
	"extra":"testmsg", //提示消息具体业务数据
	"pushMsgType":"MSG",//消息类型：MSG-普通消息，CMD-指令，业务系统扩展用
	"sound":"default", //IOS设备时才有，提示音
	"badge": 2,//IOS设备时才有，未读数
	"needConfirm":false //是否需要客户端确认，需要确认时，客户端要发送确认消息到服务端
}

```

### push msg

```
url: /msgpush/send
method: POST
Content-Type: application/json

request object:
{
	"appId":"testapp1",//必须
	"appKey":"dkdka", //必须,app密钥，只有授权才能发消息
	"tenantCode": "xxx",//可选，租户代码
	"platform":"ALL", //必须,受众的设备类型，ALL-所有设备
	"pushMsgType":"MSG",//可选,消息类型（MSG-普通消息，CMD-特殊指令）,默认：MSG
	"needConfirm":false ,//可选，是否要求客户端确认
	"duration": 10000,//可选，单位:秒，提醒消息有效期，needConfirm=true时有效，表示在有效期内如果没有确认会多次发送
	"audienceType":"ALIAS", //必须,指定查找受众方式，默认ALIAS,以后支持按tag发送
	"audiences":["testuser1"],//必须,哪些受众，ALL-所有用户
	"extra":"testmsg",//可选，业务系统自定义的消息数据
	"alert":" this is a alert",//必须,提醒消息
	"sound":"default",//可选，IOS用
	"badge":1,//可选，IOS用
	"sender":"user2",//可选，发送者
	"sendDeive":"ANDROID",//可选，发送设备
	"smsMessage":"sms message"//可选，离线时是否发送短信，以后扩展用
}

response object:
{
	"requestId":"xxx"//成功会返回requestId
}
```

### 更多技术文档

[Spring Boot + Gradle + Websocket 构建推送服务](https://github.com/yaoyasong/one-push/wiki/Build-One-Push)
