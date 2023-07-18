FORMAT: 1A
# ユーザー管理API仕様書(テスト)

# Group Users

## POST /users
ユーザーを登録します。
以下のパラメータをJSON形式で送信します。

+ name (string, required) - ユーザー名。255文字以内。
+ birthdate (string, required) - 生年月日。yyyy-MM-dd形式。未来日付は不可。

+ Request (application/json)

        {
          "name": "yoshihito koyama",
          "bithdate": "2022-01-01"
        }

+ Response 201 (application/json)
  + Body
        {
          "id": 1,
          "message": "user successfully created"
        }

+ Response 400
  + Body
        [
          {
            "field":"name",
            "messages":[
                "cannot be empty",
                "maximum length is 255"
            ]
          },
          {
            "field":"birthdate",
            "messages":[
                "cannot be null",
                "format should be yyyy-MM-dd",
                "cannot set future date"
            ]
          }
        ]

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

