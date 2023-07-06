import React, { useState, memo } from 'react';
import { Button, Input, FormControl, FormLabel, VStack, Alert, AlertIcon, Center } from '@chakra-ui/react';
import axios from 'axios';

export const Login: React.FC = memo(() => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [loginError, setLoginError] = useState('');

  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleLogin = async () => {
    setIsLoading(true);
    setLoginError('');

    const response = await axios.post('http://localhost:8080/user/login', {
      username: username,
      password: password,
    }).then((res) => {
      const { loginFlag, userId } = res.data;
      if (loginFlag) {
        // ログイン完了
        console.log("ログイン成功");
      } else {
        setLoginError('認証に失敗しました。');
      }
    })
      .catch(() => {
        setLoginError('ログインに失敗しました。ネットワークエラーが発生しました。');
      });
    setIsLoading(false);
  };

  return (
    <Center height="100vh">
      <VStack spacing={4}>
        {loginError && (
          <Alert status="error" variant="subtle" borderRadius="md">
            <AlertIcon />
            {loginError}
          </Alert>
        )}
        <FormControl>
          <FormLabel>ユーザー名</FormLabel>
          <Input type="text" value={username} onChange={handleUsernameChange} />
        </FormControl>
        <FormControl>
          <FormLabel>パスワード</FormLabel>
          <Input type="password" value={password} onChange={handlePasswordChange} />
        </FormControl>
        <Button colorScheme="blue" onClick={handleLogin} isLoading={isLoading}>
          ログイン
        </Button>
      </VStack>
    </Center>
  );
});
