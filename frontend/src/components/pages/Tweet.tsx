import React, { memo } from 'react';
import { Center, Stack } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import { PrimaryButton } from '../atoms/button/PrimaryButton';
import { useLoginUser } from '../../hooks/providers/useLoginUserProvider';

export const Tweet: React.FC = memo(() => {
  const navigate = useNavigate();
  const { loginUser } = useLoginUser();
  const { loginFlag, userId } = loginUser;
  return (
    <>
      {loginFlag ? (
        <>
          <p>Tweetのトップページ</p>
          <br />
          <p>{`ログインユーザーID: ${userId}`}</p>
        </>
      ) : (
        <Center h="100vh">
          <Stack spacing={6}>
            <p>不正なログインです</p>
            <PrimaryButton onClick={() => { navigate("/") }}>ログインページへ</PrimaryButton>
          </Stack>
        </Center>
      )}
    </>
  );
});
