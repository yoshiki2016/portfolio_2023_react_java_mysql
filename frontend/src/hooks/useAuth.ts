import axios from "axios";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMessage } from "./useMessage";
import { useLoginUser } from "./providers/useLoginUserProvider";

export const useAuth = () => {
  const navigate = useNavigate();
  const { showMessage } = useMessage();
  const { setLoginUser } = useLoginUser();
  const [isLoading, setIsLoading] = useState(false);

  const login = useCallback((username: string, password: string) => {
    setIsLoading(true);
    axios.post("http://localhost:8080/user/login", {
      username: username,
      password: password,
    }).then((res) => {
      const { loginFlag, userId } = res.data;
      if (loginFlag) {
        setLoginUser({ userId, loginFlag });
        navigate("/tweet");
      } else {
        setLoginUser({ userId, loginFlag });
        showMessage({ title: "ログインに失敗しました。", status: "error" });
      }
    }).catch(() => {
      showMessage({ title: "ネットワークエラーが発生しました。", status: "error" });
    });
    setIsLoading(false);
  }, []);
  return { login, isLoading };
};
