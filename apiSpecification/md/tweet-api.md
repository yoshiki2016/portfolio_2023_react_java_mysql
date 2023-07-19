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

+ Response 200 (application/json)
  ログインに成功した場合
  + Body
    {
      "userId": 1,
      "loginFlag": true
    }

+ Response 200 (application/json)
  ログインに失敗した場合
  + Body
    {
      "userId": 0,
      "loginFlag": false
    }

## POST /user/register
ユーザーの登録処理をします。
以下のパラメータをJSON形式で送信します。

+ givenName (string) - 名字
+ familyName (string) - 名前
+ userName (string) - ユーザー名
+ password (string) - パスワード
+ email (string) - Eメールアドレス

+ Request (application/json)
  {
    "givenName": "サンプル",
    "familyName": "花子"
    "userName": "h_sample",
    "password": "Abc12345",
    "email": "abc@sample.com"
  }

+ Response 201 (application/json)
  + Body
    {
      "message": "the user successfully created"
    }


## GET /user_setting/{id}
idに指定したユーザーの情報を取得します。
+ Parameters
  + id (number) - ユーザーのID

+ Response 200 (application/json)
  + Body
    {
      "givenName": "サンプル",
      "familyName": "花子"
      "userName": "h_sample",
      "password": "",
      "email": "abc@sample.com"
    }

+ Response 404 (application/json)
  + Body
    {
      "error": "Not Found",
      "timestamp": "2023-07-19T11:25:12.017870+09:00\[Asia/Tokyo\]",
      "message": "resource not found",
      "status": "404",
      "path": "/user_setting/10"
    }

## PATCH /user_setting
idに指定したユーザーの情報を更新します。
パスワードの更新がある場合、パスワードの更新が無い場合で別の処理になります。

+ userId (number) - ユーザーID
+ givenName (string) - 名字
+ familyName (string) - 名前
+ userName (string) - ユーザー名
+ showPasswordFlag (boolean) - パスワード更新フラグ
+ password (string) - パスワード
+ newPassword (string) - 新パスワード
+ email (string) - Eメールアドレス

+ Request (application/json)
  {
  "userId": 1,
  "givenName": "山下",
  "familyName": "花子",
  "userName": "y_sample",
  "showPasswordFlag": false,
  "password": "",
  "newPassword": "",
  "email": "efg@sample.com"
  }

+ Response 200 (application/json)
  + Body
    {
      "message": "the user successfully updated"
    }

+ Request (application/json)
  {
  "userId": 1,
  "givenName": "山下",
  "familyName": "花子",
  "userName": "y_sample",
  "showPasswordFlag": true,
  "password": "Abc12345",
  "newPassword": "P@ssword1!",
  "email": "efg@sample.com"
  }

+ Response 200 (application/json)
  + Body
    passwordとuserIdのペアがusersテーブルに存在する場合更新成功
    {
      "message": "the user successfully updated"
    }

+ Response 404 (application/json)
  + Body
    passwordとuserIdのペアがusersテーブルに存在しない場合更新失敗
    {
      "error": "Not Found",
      "timestamp": "2023-07-19T11:25:12.017870+09:00\[Asia/Tokyo\]",
      "message": "resource not found",
      "status": "404",
      "path": "/user_setting"
    }

# Group Common

## 存在しないエンドポイントへのアクセス
ステータスコード: `404`
```json
{
  "message": "resource not found"
}
```
