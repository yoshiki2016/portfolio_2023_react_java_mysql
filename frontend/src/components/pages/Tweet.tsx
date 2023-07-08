import React, { memo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLoginUser } from '../../hooks/providers/useLoginUserProvider';
import { IncorrectLogin } from './IncorrectLogin';

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
      ) : (<IncorrectLogin />)}
    </>
  );
});
