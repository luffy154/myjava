import com.groovy.test.Person
import groovy.json.JsonSlurper;

jsonStr = '{\n' +
        '    "code": "000",\n' +
        '    "msg": "ok",\n' +
        '    "data": [\n' +
        '        {\n' +
        '            "billMaster": {\n' +
        '                "channelOrderKey": "7473854291518688754",\n' +
        '                "saasOrderKey": "59012020250221000574_427",\n' +
        '                "shopID": 3001842,\n' +
        '                "brandID": 379517,\n' +
        '                "person": 1,\n' +
        '                "channelKey": "wechat_app",\n' +
        '                "orderSubType": 0,\n' +
        '                "paidAmount": 9.90,\n' +
        '                "shopRealAmount": 0.00,\n' +
        '                "foodAmount": 9.90,\n' +
        '                "foodCount": 1,\n' +
        '                "commission": 0.00,\n' +
        '                "channelOrderTime": 0,\n' +
        '                "deliverFee": 0.00,\n' +
        '                "deliverSource": 0,\n' +
        '                "orderTime": 20250221205159\n' +
        '            },\n' +
        '            "billFood": [\n' +
        '                {\n' +
        '                    "channelOrderKey": "7473854291518688754",\n' +
        '                    "saasOrderKey": "59012020250221000574_427",\n' +
        '                    "orderSubType": 0,\n' +
        '                    "foodCategoryKey": "2023051100000042",\n' +
        '                    "foodNumber": 1,\n' +
        '                    "unitKey": "91910003",\n' +
        '                    "foodRealPrice": 9.90,\n' +
        '                    "foodVipPrice": 0.00,\n' +
        '                    "foodPayPrice": 9.9000,\n' +
        '                    "foodPayPriceReal": 9.9000,\n' +
        '                    "foodPriceAmount": 9.9000,\n' +
        '                    "foodRealAmount": 9.9000,\n' +
        '                    "isSetFood": 0\n' +
        '                }\n' +
        '            ],\n' +
        '            "billPay": [\n' +
        '                {\n' +
        '                    "channelOrderKey": "7473854291518688754",\n' +
        '                    "saasOrderKey": "59012020250221000574_427",\n' +
        '                    "payFrom": 0,\n' +
        '                    "orderSubType": 0,\n' +
        '                    "paySubjectCode": "11311001",\n' +
        '                    "paySubjectKey": "103",\n' +
        '                    "paySubjectName": "微信扫码点单",\n' +
        '                    "paySubjectFeeAmount": 9.90,\n' +
        '                    "paySubjectReceivedAmount": 9.90,\n' +
        '                    "paySubjectDiscountAmount": 0.00\n' +
        '                }\n' +
        '            ]\n' +
        '        },\n' +
        '        {\n' +
        '            "billMaster": {\n' +
        '                "channelOrderKey": "7473715190396095986",\n' +
        '                "saasOrderKey": "59012020250221000076_27",\n' +
        '                "shopID": 3001842,\n' +
        '                "brandID": 379517,\n' +
        '                "person": 1,\n' +
        '                "channelKey": "wechat_app",\n' +
        '                "orderSubType": 0,\n' +
        '                "paidAmount": 38.20,\n' +
        '                "shopRealAmount": 0.00,\n' +
        '                "foodAmount": 44.80,\n' +
        '                "foodCount": 2,\n' +
        '                "commission": 0.00,\n' +
        '                "channelOrderTime": 0,\n' +
        '                "deliverFee": 0.00,\n' +
        '                "deliverSource": 0,\n' +
        '                "orderTime": 20250221115213\n' +
        '            },\n' +
        '            "billFood": [\n' +
        '                {\n' +
        '                    "channelOrderKey": "7473715190396095986",\n' +
        '                    "saasOrderKey": "59012020250221000076_27",\n' +
        '                    "orderSubType": 0,\n' +
        '                    "foodCategoryKey": "2018051700000027",\n' +
        '                    "foodNumber": 1,\n' +
        '                    "unitKey": "808005760010002",\n' +
        '                    "foodRealPrice": 8.80,\n' +
        '                    "foodVipPrice": 0.00,\n' +
        '                    "foodPayPrice": 8.8000,\n' +
        '                    "foodPayPriceReal": 7.5000,\n' +
        '                    "foodPriceAmount": 8.8000,\n' +
        '                    "foodRealAmount": 8.8000,\n' +
        '                    "isSetFood": 0\n' +
        '                },\n' +
        '                {\n' +
        '                    "channelOrderKey": "7473715190396095986",\n' +
        '                    "saasOrderKey": "59012020250221000076_27",\n' +
        '                    "orderSubType": 0,\n' +
        '                    "foodCategoryKey": "2018051700000031",\n' +
        '                    "foodNumber": 1,\n' +
        '                    "unitKey": "816005150010002",\n' +
        '                    "foodRealPrice": 36.00,\n' +
        '                    "foodVipPrice": 0.00,\n' +
        '                    "foodPayPrice": 36.0000,\n' +
        '                    "foodPayPriceReal": 30.7000,\n' +
        '                    "foodPriceAmount": 36.0000,\n' +
        '                    "foodRealAmount": 36.0000,\n' +
        '                    "isSetFood": 0\n' +
        '                }\n' +
        '            ],\n' +
        '            "billPay": [\n' +
        '                {\n' +
        '                    "channelOrderKey": "7473715190396095986",\n' +
        '                    "saasOrderKey": "59012020250221000076_27",\n' +
        '                    "payFrom": 0,\n' +
        '                    "orderSubType": 0,\n' +
        '                    "paySubjectCode": "11311001",\n' +
        '                    "paySubjectKey": "103",\n' +
        '                    "paySubjectName": "微信扫码点单",\n' +
        '                    "paySubjectFeeAmount": 34.90,\n' +
        '                    "paySubjectReceivedAmount": 34.90,\n' +
        '                    "paySubjectDiscountAmount": 0.00\n' +
        '                },\n' +
        '                {\n' +
        '                    "channelOrderKey": "7473715190396095986",\n' +
        '                    "saasOrderKey": "59012020250221000076_27",\n' +
        '                    "payFrom": 0,\n' +
        '                    "orderSubType": 0,\n' +
        '                    "paySubjectCode": "51010615",\n' +
        '                    "paySubjectKey": "204",\n' +
        '                    "paySubjectName": "会员券抵扣",\n' +
        '                    "paySubjectFeeAmount": 9.90,\n' +
        '                    "paySubjectReceivedAmount": 3.30,\n' +
        '                    "paySubjectDiscountAmount": 0.00\n' +
        '                }\n' +
        '            ],\n' +
        '            "billPreferential": [\n' +
        '                {\n' +
        '                    "name": "满38元立减9.9元券",\n' +
        '                    "saasOrderKey": "59012020250221000076_27",\n' +
        '                    "businessId": 100000000004632,\n' +
        '                    "activityType": 1,\n' +
        '                    "preferentialAmount": 9.90\n' +
        '                }\n' +
        '            ]\n' +
        '        }' +
        ']}';
def jsonSlurper = new JsonSlurper();
jsonObject = jsonSlurper.parseText(jsonStr)

aCollection = ["a","b","c"]
bCollection = ["b","c","d"]
dCollection = aCollection - bCollection
sumCollection = aCollection + bCollection
sumCollectionUniq = sumCollection.unique{it.toLowerCase()}

Person person = new Person()
person.age = 1
person.name = 'ssssss'

jsonMap  = jsonObject.data.groupBy{it.billMaster.channelOrderKey}


println person
println jsonMap
println sumCollectionUniq
println sumCollection
println dCollection

println "reuslt:${jsonObject.code}"
