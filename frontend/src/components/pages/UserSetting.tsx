import React, { memo, useEffect, useState } from "react";
import { Center, Checkbox, FormControl, FormLabel, Input, VStack } from "@chakra-ui/react";
import { PrimaryButton } from "../atoms/button/PrimaryButton";
import { useLoginUser } from "../../hooks/providers/useLoginUserProvider";
import { IncorrectLogin } from "./IncorrectLogin";
import { useUserGet } from "../../hooks/useUserGet";

export const UserSetting: React.FC = memo(() => {
  const { loginUser } = useLoginUser();
  const { loginFlag, userId } = loginUser;

  const [givenName, setGivenName] = useState("");
  const [familyName, setFamilyName] = useState("");
  const [userName, setUserName] = useState("");
  const [showPasswordFlag, setShowPasswordFlag] = useState(false);
  const [password, setPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [email, setEmail] = useState("");

  // ユーザー情報取得
  const { userGet, isLoadingUser, user } = useUserGet();
  useEffect(() => userGet(userId), []);

  // user情報が取得されるまでuserの値がnullになるので、初期値を設定
  useEffect(() => {
    if (user) {
      setGivenName(user.givenName); setFamilyName(user.familyName);
      setUserName(user.userName); setEmail(user.email);
    }
  }, [user]);

  const handleGivenNameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setGivenName(event.target.value); };
  const handleFamlyNameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setFamilyName(event.target.value); };
  const handleUserNameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setUserName(event.target.value); };
  const handleShowPasswordFlagChange = (event: any) => {
    setPassword(""); setNewPassword("");
    setShowPasswordFlag(!showPasswordFlag);
  }
  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => { setPassword(event.target.value); };
  const handleNewPasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => { setNewPassword(event.target.value); };
  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => { setEmail(event.target.value); };

  // ユーザー情報更新
  const onClickUserSetting = () => { alert("ユーザー設定") };
  return (
    <>
      {loginFlag ? (
        <>
          <Center height="100vh">
            <VStack spacing={4}>
              <FormControl>
                <FormLabel>名字(更新用の画面)</FormLabel>
                <Input type="text" value={familyName} onChange={handleFamlyNameChange} />
              </FormControl>
              <FormControl>
                <FormLabel>名前</FormLabel>
                <Input type="text" value={givenName} onChange={handleGivenNameChange} />
              </FormControl>
              <FormControl>
                <FormLabel>ユーザー名</FormLabel>
                <Input type="text" value={userName} onChange={handleUserNameChange} />
              </FormControl>
              <FormControl>
                <Checkbox isChecked={showPasswordFlag} onChange={handleShowPasswordFlagChange}>
                  パスワードを更新
                </Checkbox>
              </FormControl>
              {showPasswordFlag ? (
                <>
                  <FormControl>
                    <FormLabel>旧パスワード</FormLabel>
                    <Input type="password" value={password} onChange={handlePasswordChange} />
                  </FormControl>
                  <FormControl>
                    <FormLabel>新パスワード</FormLabel>
                    <Input type="password" value={newPassword} onChange={handleNewPasswordChange} />
                  </FormControl>
                </>
              ) : (
                // 表示しない
                <></>
              )}
              <FormControl>
                <FormLabel>Eメール</FormLabel>
                <Input type="email" value={email} onChange={handleEmailChange} />
              </FormControl>
              <PrimaryButton
                isDisabled={
                  familyName === "" || givenName === "" ||
                  userName === "" || password === "" || email === ""
                }
                onClick={onClickUserSetting}
                isLoading={isLoadingUser}>
                ユーザー更新
              </PrimaryButton>
            </VStack>
          </Center>
        </>
      ) : (<IncorrectLogin />)}
    </>
  );
});
