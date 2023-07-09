import axios from "axios";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMessage } from "./useMessage";

type Props = {
  givenName: string;
  familyName: string;
  username: string;
  password: string;
  email: string;
}

export const useUserRegister = () => {
  const navigate = useNavigate();
  const { showMessage } = useMessage();
  const [isLoading, setIsLoading] = useState(false);
  const userRegister = useCallback((props: Props) => {
    console.log("User登録処理起動");
    setIsLoading(true);
    axios.post("http://localhost:8080/user/register", {
      givenName: props.givenName,
      familyName: props.familyName,
      username: props.username,
      password: props.password,
      email: props.email
    }).then((res) => {
      console.log(res);
    }).catch(() => {
      showMessage({ title: "ネットワークエラーが発生しました。", status: "error" });
    });
    setIsLoading(false);
  }, []);
  return { userRegister, isLoading };
};
