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

### ログインに成功した場合
+ Response 200 (application/json)
  + Body
        {
          "userId": 1,
          "loginFlag": true
        }

### ログインに失敗した場合
+ Response 200 (application/json)
    + Body
      {
      "userId": 0,
      "loginFlag": false
      }
 
## GET /users/{id}

idに指定したユーザーの情報を取得します。

+ Parameters
  + id: 1 (number) - ユーザーのID


+ Response 200 (application/json)
        {
          "id": 1,
          "name": "yoshihito koyama",
          "bithdate": "2022-01-01"
        }

+ Response 404 (application/json)
        {
          "message": "resource not found"
        }

## GET /users

ユーザーを検索します。

+ Parameters
  + name (string, mandatory) - ユーザー名。前方一致で検索。
  + birthdate (string, optional) - yyyy-MM-dd形式。完全一致で検索。

+ Response 200 (application/json)
        [
          {
            "id": 1,
            "name": "yoshihito koyama",
            "bithdate": "2022-01-01"
          }
        ]

+ Response 200 (application/json)
        []


## PATCH /users/{id}

idに指定したユーザーの情報を更新します。
+ name (string, required) - ユーザー名。255文字以内。
+ birthdate (string, required) - 生年月日。yyyy-MM-dd形式。未来日付は不可。

+ Parameters
  + id: 1 (number) - ユーザーのID

+ Request (application/json)
        {
          "name": "yoshihito koyama",
          "bithdate": "2022-01-01"
        }

+ Response 200 (application/json)
        {
          "message": "user successfully updated"
        }

+ Response 404 (application/json)
        {
          "message": "resource not found"
        }

## DELETE /users/{id}

+ Parameters

  + id: 1 (number) - An unique identifier of the user.

+ Response 200 (application/json)
        {
          "message": "user successfully deleted"
        }

+ Response 404 (application/json)
        {
          "message": "resource not found"
        }

# Group Common

## 存在しないエンドポイントへのアクセス
ステータスコード: `404`
```json
{
  "message": "resource not found"
}
```

## サーバーエラー
ステータスコード: `500`
```json
{
  "message": "system error"
}
```

