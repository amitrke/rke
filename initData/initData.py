import requests

baseURL = 'http://localhost:8080/api/'

def restCall(dataBlock):
    restURL = baseURL+dataBlock['url']
    print "REST call for", restURL
    response = requests.post(restURL, json=dataBlock['data'])
    if (response.status_code != 200):
        print(response.status_code)
    else:
        print(response.json())
    
    return response


userObj = {
        "url": "users/",
        "type": "POST",
        "data": {"userId":1,"status":"Active","imageURL":"/abc/def/a.jpg"}
    }

resp = restCall(userObj)

if resp.status_code != 200:
    exit

userId = resp.json()["response"]

dataJSON = [
    {
        "url": "articles/",
        "type": "POST",
        "data": {"userId":userId,"status":"Active","title":"Test Title","imageURL":"/abc/def/a.jpg","description":"Test Description","fullText":"Test Full Text","priority":1}
    },
    {
        "url": "articles/",
        "type": "POST",
        "data": {"userId":userId,"status":"Active","title":"Test Title","imageURL":"/abc/def/a.jpg","description":"Test Description","fullText":"Test Full Text","priority":1}
    }
]


for dataBlock in dataJSON:
    restCall(dataBlock)