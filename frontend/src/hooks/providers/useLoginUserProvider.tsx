import { createContext, Dispatch, ReactNode, SetStateAction, useContext, useState } from "react";

// ログイン状態とログインユーザーのIDを持つ
type LoginUser = {
  userId: number | null;
  loginFlag: boolean;
};

type LoginUserContextType = {
  loginUser: LoginUser;
  setLoginUser: Dispatch<SetStateAction<LoginUser>>;
};

const LoginUserContext = createContext<LoginUserContextType>({} as LoginUserContextType);

// ログインユーザーのIDとログイン状態を持つプロバイダー
export const LoginUserProvider = (props: { children: ReactNode }) => {
  const { children } = props;
  const [loginUser, setLoginUser] = useState<LoginUser>({ userId: null, loginFlag: false });
  return (
    <LoginUserContext.Provider value={{ loginUser, setLoginUser }}>
      {children}
    </LoginUserContext.Provider>
  );
};

export const useLoginUser = (): LoginUserContextType => useContext(LoginUserContext);
