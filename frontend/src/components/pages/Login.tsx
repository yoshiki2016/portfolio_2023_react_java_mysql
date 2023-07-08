import React, { useState, memo } from "react";
import { useNavigate } from "react-router-dom";
import { Input, FormControl, FormLabel, VStack, Center, InputGroup, InputRightElement, Button } from "@chakra-ui/react";
import { PrimaryButton } from "../atoms/button/PrimaryButton";
import { useAuth } from "../../hooks/useAuth";

export const Login: React.FC = memo(() => {
  const { isLoading, login } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();
  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => { setUsername(event.target.value); };
  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => { setPassword(event.target.value); };

  const handleLogin = () => { login(username, password) };
  const userRegister = () => navigate("/user");

  return (
    <Center height="100vh">
      <VStack spacing={4}>
        <FormControl>
          <FormLabel>ユーザー名</FormLabel>
          <Input type="text" value={username} onChange={handleUsernameChange} />
        </FormControl>
        <FormControl>
          <FormLabel>パスワード</FormLabel>
          <Input type="password" value={password} onChange={handlePasswordChange} />
        </FormControl>
        <PrimaryButton
          isDisabled={username === "" || password === ""}
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
