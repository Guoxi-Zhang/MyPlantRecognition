#!/usr/bin/python
# coding=utf-8
import io
import sys

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
import requests
import base64
import sys


# client_id 为官网获取的API Key， client_secret 为官网获取的Secret Key
def get_access_token(API_Key, Secret_Key):
    host = 'https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials'
    host += '&client_id=' + API_Key  #API Key
    host += '&client_secret=' + Secret_Key  #Secret Key
    res = requests.get(host)
    if res:
        my_token = res.json()['access_token']
        return my_token


#API链接
request_url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant"
api_key = "BAnYOfq3ZwQ3UGwZnwFOjgUa"
secret_key = "WqBUVYRI2UzgTKnwLNTuy0i3T4GpWTkw"
# 二进制方式打开图片文件
f = open(sys.argv[1], 'rb')
img = base64.b64encode(f.read())
params = {"image": img}

access_token = get_access_token(api_key, secret_key)
request_url = request_url + "?access_token=" + access_token
headers = {'content-type': 'application/x-www-form-urlencoded'}
response = requests.post(request_url, data=params, headers=headers)
s = ''
num = 0
if response:
    for item in response.json()['result']:
        num += 1
        name = item['name']
        score = item['score']
        s = s + str(num) + ": " + str(name) + " 准确率： " + str(score) + ","
    s = s[0:len(s) - 2]
    print(s)
