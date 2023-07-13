import axios from "axios";
import { useCallback, useState } from "react";
import { useMessage } from "./useMessage";

type Props = {
  tweet: string;
  userId: number | null;
}

export const useTweetRegister = () => {
  const { showMessage } = useMessage();
  const [isLoading, setIsLoading] = useState(false);
  const tweetRegister = useCallback(async (props: Props) => {
    setIsLoading(true);
    if (props.userId !== null) {
      await axios.post("http://localhost:8080/tweet/register", {
        tweet: props.tweet,
        userId: props.userId
      }).then(() => {
        showMessage({ title: "ツイートしました。", status: "success" });
      }).catch(() => {
        showMessage({ title: "ネットワークエラーが発生しました。", status: "error" });
      }).finally(() => {
        setIsLoading(false);
      });
    } else {
      showMessage({ title: "不正なユーザーで投稿しないで下さい。", status: "error" });
      setIsLoading(false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return { tweetRegister, isLoading };
};
