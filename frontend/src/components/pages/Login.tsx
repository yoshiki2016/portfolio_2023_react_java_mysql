import React, { useState, memo } from "react";
import { useNavigate } from "react-router-dom";
import { Input, FormControl, FormLabel, VStack, Center } from "@chakra-ui/react";
import { PrimaryButton } from "../atoms/button/PrimaryButton";
import { useAuth } from "../../hooks/useAuth";

export const Login: React.FC = memo(() => {
  const { isLoading, login } = useAuth();
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();
  const handleUserNameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setUserName(event.target.value); };
  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => { setPassword(event.target.value); };

  const handleLogin = () => { login(userName, password) };
  const userRegister = () => navigate("/user");

  return (
    <Center height="100vh">
      <VStack spacing={4}>
        <FormControl>
          <FormLabel>ユーザー名</FormLabel>
          <Input type="text" value={userName} onChange={handleUserNameChange} />
        </FormControl>
        <FormControl>
          <FormLabel>パスワード</FormLabel>
          <Input type="password" value={password} onChange={handlePasswordChange} />
        </FormControl>
        <PrimaryButton
          isDisabled={userName === "" || password === ""}
          onClick={handleLogin}
          isLoading={isLoading}>
          ログイン
        </PrimaryButton>
        <PrimaryButton
          isDisabled={false}
          onClick={userRegister}
          isLoading={false}>
          アカウント作成
        </PrimaryButton>
      </VStack>
    </Center>
  );
});
