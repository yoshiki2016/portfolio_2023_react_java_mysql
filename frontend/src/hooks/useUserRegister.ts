import axios from "axios";
import { useCallback, useState } from "react";
import { useMessage } from "./useMessage";
import { useNavigate } from "react-router-dom";

type Props = {
  givenName: string;
  familyName: string;
  userName: string;
  password: string;
  email: string;
}

export const useUserRegister = () => {
  const navigate = useNavigate();
  const { showMessage } = useMessage();
  const [isLoading, setIsLoading] = useState(false);
  const userRegister = useCallback((props: Props) => {
    setIsLoading(true);
    axios.post("http://localhost:8080/user/register", {
      givenName: props.givenName,
      familyName: props.familyName,
      userName: props.userName,
      password: props.password,
      email: props.email
    }).then(() => {
      showMessage({ title: "ユーザー登録完了しました。", status: "success" });
    }).catch(() => {
      showMessage({ title: "ネットワークエラーが発生しました。", status: "error" });
    }).finally(() => navigate("/"));
    setIsLoading(false);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return { userRegister, isLoading };
};
