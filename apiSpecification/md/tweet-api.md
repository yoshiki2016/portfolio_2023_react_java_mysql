FORMAT: 1A
# tweeterクローンAPI仕様書

# Group Users

## POST /user/login
ログイン処理を実行します。
以下のパラメータをJSON形式で送信します。

+ userName (string) - アカウント名
+ password (string) - パスワード

+ Request (application/json)
        {
          "userName": "t_hanako",
          "password": "Abc12345"
        }

## ログインに成功した場合
+ Response 200 (application/json)
  + Body
    {
      "userId": 1,
      "loginFlag": true
    }

## ログインに失敗した場合
+ Response 200 (application/json)
    + Body
      {
      "userId": 0,
      "loginFlag": false
      }

# Group Common

## 存在しないエンドポイントへのアクセス
ステータスコード: `404`
```json
{
  "message": "resource not found"
}
```
