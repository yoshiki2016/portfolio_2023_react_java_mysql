import { memo, FC } from "react";
import { useNavigate } from "react-router-dom";
import { Center, Stack } from "@chakra-ui/react";
import { PrimaryButton } from "../atoms/button/PrimaryButton";

export const Page404: FC = memo(() => {
  const navigate = useNavigate();
  return (
    <Center h="100vh">
      <Stack spacing={6}>
        <p>ページが見つかりません</p>
        <PrimaryButton onClick={() => { navigate("/") }}>ログインページへ</PrimaryButton>
      </Stack>
    </Center>
  );
});
