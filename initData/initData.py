import requests

baseURL = 'http://localhost:8080/api/'

dataJSON = [
    {
        "url": "articles/",
        "type": "POST",
        "data": {"userId":1,"status":"Active","title":"Test Title","imageURL":"/abc/def/a.jpg","description":"Test Description","fullText":"Test Full Text","priority":1}
    },
    {
        "url": "articles/",
        "type": "POST",
        "data": {"userId":2,"status":"Active","title":"Test Title","imageURL":"/abc/def/a.jpg","description":"Test Description","fullText":"Test Full Text","priority":1}
    }
]

def restCall(dataBlock):
    restURL = baseURL+dataBlock['url']
    print "REST call for", restURL
    response = requests.post(restURL, json=dataBlock['data'])
    if (response.status_code != 200):
        print(response.status_code)
    else:
        print(response.json())
for dataBlock in dataJSON:
    restCall(dataBlock)