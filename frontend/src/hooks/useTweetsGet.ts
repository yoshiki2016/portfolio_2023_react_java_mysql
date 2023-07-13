import axios from "axios";
import { useCallback, useState } from "react";
import { useMessage } from "./useMessage";
import { Tweet } from "../types/api/tweet";

export const useTweetsGet = () => {
  const { showMessage } = useMessage();
  const [isLoadingTweet, setIsLoadingTweet] = useState(false);
  const [tweets, setTweets] = useState<Array<Tweet>>([]);

  const tweetsGet = useCallback(() => {
    setIsLoadingTweet(true);
    axios.get<Array<Tweet>>("http://localhost:8080/tweets")
      .then((res) => setTweets(res.data))
      .catch(() => showMessage({ title: "ツイートの取得に失敗しました。", status: "error" }))
      .finally(() => setIsLoadingTweet(false));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return { tweetsGet, isLoadingTweet, tweets };
};
