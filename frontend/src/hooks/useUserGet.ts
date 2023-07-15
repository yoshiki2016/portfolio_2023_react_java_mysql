import axios from "axios";
import { useCallback, useState } from "react";
import { useMessage } from "./useMessage";
import { User } from "../types/api/user";

export const useUserGet = () => {
  const { showMessage } = useMessage();
  const [isLoadingUser, setIsLoadingUser] = useState(false);
  const [user, setUser] = useState<User>();

  const userGet = useCallback((props: number) => {
    console.log("ユーザーID");
    console.log(props);
    setIsLoadingUser(true);
    if (props !== 0) {
      axios.get<User>(`http://localhost:8080/user_setting/${props}`)
        .then((res) => setUser(res.data))
        .catch(() => showMessage({ title: "ユーザー情報の取得に失敗しました。", status: "error" }))
        .finally(() => setIsLoadingUser(false));
    } else {
      showMessage({ title: "不正なユーザーです。", status: "error" });
      setIsLoadingUser(false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return { userGet, isLoadingUser, user };
};
