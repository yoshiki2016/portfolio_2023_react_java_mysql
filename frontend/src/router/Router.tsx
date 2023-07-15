import { Route, Routes } from "react-router-dom";
import { Login } from "../components/pages/Login";
import { Tweet } from "../components/pages/Tweet";
import { UserRegister } from "../components/pages/UserRegister";
import { Page404 } from "../components/pages/Page404";
import { LoginUserProvider } from "../hooks/providers/useLoginUserProvider";
import { UserSetting } from "../components/pages/UserSetting";

export const Router = () => {
  return (
    <>
      <LoginUserProvider>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/tweet" element={<Tweet />} />
          <Route path="/user" element={<UserRegister />} />
          <Route path="/user_setting" element={<UserSetting />} />
          <Route path="*" element={<Page404 />} />
        </Routes>
      </LoginUserProvider>
    </>
  );
};
