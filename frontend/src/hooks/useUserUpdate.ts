import axios from "axios";
import { useCallback, useState } from "react";
import { useMessage } from "./useMessage";
import { useNavigate } from "react-router-dom";

type Props = {
  userId: number;
  givenName: string;
  familyName: string;
  userName: string;
  showPasswordFlag: boolean;
  password: string;
  newPassword: string;
  email: string;
}

export const useUserUpdate = () => {
  const navigate = useNavigate();
  const { showMessage } = useMessage();
  const [isUpdate, setIsUpdate] = useState(false);
  const userUpdate = useCallback((props: Props) => {
    console.log("ユーザー更新");
    setIsUpdate(true);
    axios.patch("http://localhost:8080/user_setting", {
      userId: props.userId,
      givenName: props.givenName,
      familyName: props.familyName,
      userName: props.userName,
      showPasswordFlag: props.showPasswordFlag,
      password: props.password,
      newPassword: props.newPassword,
      email: props.email
    }).then(() => {
      showMessage({ title: "ユーザー登録完了しました。", status: "success" });
    }).catch(() => {
      showMessage({ title: "ネットワークエラーが発生しました。", status: "error" });
    }).finally(() => navigate("/tweet"));
    setIsUpdate(false);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return { userUpdate, isUpdate };
};
