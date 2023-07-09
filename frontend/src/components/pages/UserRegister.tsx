import React, { memo, useState } from "react";
import { Center, FormControl, FormLabel, Input, VStack } from "@chakra-ui/react";
import { PrimaryButton } from "../atoms/button/PrimaryButton";
import { useUserRegister } from "../../hooks/useUserRegister";

export const UserRegister: React.FC = memo(() => {
  const [givenName, setGivenName] = useState("");
  const [familyName, setFamilyName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");


  const handleGivenNameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setGivenName(event.target.value); };
  const handleFamlyNameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setFamilyName(event.target.value); };
  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setUsername(event.target.value); };
  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => { setPassword(event.target.value); };
  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => { setEmail(event.target.value); };

  // useUserRegisterから受け取るようにする。
  // const isLoading = false;
  // const userRegister = () => alert("ユーザー登録");
  const { userRegister, isLoading } = useUserRegister();
  const onClickUserRegister = () => userRegister({ givenName, familyName, username, password, email });
  return (
    <>
      <Center height="100vh">
        <VStack spacing={4}>
          <FormControl>
            <FormLabel>名字</FormLabel>
            <Input type="text" value={familyName} onChange={handleFamlyNameChange} />
          </FormControl>
          <FormControl>
            <FormLabel>名前</FormLabel>
            <Input type="text" value={givenName} onChange={handleGivenNameChange} />
          </FormControl>
          <FormControl>
            <FormLabel>ユーザー名</FormLabel>
            <Input type="text" value={username} onChange={handleUsernameChange} />
          </FormControl>
          <FormControl>
            <FormLabel>パスワード</FormLabel>
            <Input type="password" value={password} onChange={handlePasswordChange} />
          </FormControl>
          <FormControl>
            <FormLabel>Eメール</FormLabel>
            <Input type="email" value={email} onChange={handleEmailChange} />
          </FormControl>
          <PrimaryButton
            isDisabled={
              familyName === "" || givenName === "" ||
              username === "" || password === "" || email === ""
            }
            onClick={onClickUserRegister}
            isLoading={isLoading}>
            ユーザー登録
          </PrimaryButton>
        </VStack>
      </Center>
    </>
  );
});
